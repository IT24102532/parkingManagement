package lk.sliit.parkingmanagement.oopapp.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "PrintTransactionsServlet", urlPatterns = {"/print/transactions"})
public class PrintTransactionsServlet extends HttpServlet {
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final ParkingSlotDao slotDao = new ParkingSlotDaoImpl();
    private final Logger LOGGER = Logger.getLogger(PrintTransactionsServlet.class.getName());

    Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
    Font totalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filter = request.getParameter("filter");
        List<Transaction> transactions;
        try {
            transactions = transactionDao.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        YearMonth thisMonth = YearMonth.now();
        String filterKeyword = switch (filter) {
            case "recent5" -> "Recent [05] Transactions";
            case "recent10" -> "Recent [10] Transactions";
            case "today" -> "Transactions of Today - " +
                    today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            case "thisWeek" -> "Transactions of This Week - Week " +
                    today.format(DateTimeFormatter.ofPattern("ww")) + " of " +
                    today.format(DateTimeFormatter.ofPattern("yyyy"));
            case "thisMonth" -> "Transactions of This Month - " +
                    today.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
            default -> "Transactions of All Time";
        };


        transactions = switch (filter) {
            case "recent5" -> transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            case "recent10" -> transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
            case "today" -> transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                    .filter(t -> t.getCreatedAt().toLocalDate().isEqual(today))
                    .collect(Collectors.toList());
            case "thisWeek" -> transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                    .filter(t -> !t.getCreatedAt().toLocalDate().isBefore(startOfWeek) && !t.getCreatedAt().toLocalDate().isAfter(today))
                    .collect(Collectors.toList());
            case "thisMonth" -> transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                    .filter(t -> YearMonth.from(t.getCreatedAt().toLocalDate()).equals(thisMonth))
                    .collect(Collectors.toList());
            default -> transactions;
        };

        // Set response headers for PDF download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"transactions.pdf\"");

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);

            Image logo = null;
            try {
                logo = Image.getInstance(getServletContext().getRealPath("/WEB-INF/icon.png"));
            } catch (BadElementException | IOException e) {
                System.out.println("Image not found");
            }
            assert logo != null;


            document.open();
            document.setMargins(40, 40, 80, 40);

            logo.scaleToFit(50, 50);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);


            Paragraph title = new Paragraph("Transaction Report\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            Paragraph subtitle = new Paragraph(filterKeyword, bodyFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(30f);
            table.setWidths(new float[] {2.5f, 2.5f, 2.5f, 1.5f});
            table.getDefaultCell().setBorderWidth(0.5f);
            table.getDefaultCell().setBorderColor(BaseColor.GRAY);

            String[] headers = {"User", "Slot", "Date", "Amount"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new BaseColor(51, 102, 153));
                cell.setPadding(5);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            boolean alternate = false;
            for (Transaction t : transactions) {
                User user = userDao.findById(t.getUserId());
                Booking booking;
                try {
                    booking = bookingDao.getById(t.getBookingId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ParkingSlot slot;
                try {
                    slot = slotDao.getById(booking.getSlotId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


                String userName = user.getFirstName() + " " + user.getLastName();
                String slotName = slot.getSlotName();
                String amount = String.format("$%.2f", t.getAmount());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
                String date = t.getCreatedAt().format(formatter);

                addStyledCell(table, userName, bodyFont, alternate);
                addStyledCell(table, slotName, bodyFont, alternate);
                addStyledCell(table, date, bodyFont, alternate);
                addStyledCell(table, amount, bodyFont, alternate);

                alternate = !alternate;
            }
            double totalAmount = transactions.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total", totalFont));
            totalLabelCell.setColspan(3);
            totalLabelCell.setBackgroundColor(new BaseColor(220, 220, 220));
            totalLabelCell.setBorderWidthTop(1f);
            totalLabelCell.setBorderColorTop(BaseColor.DARK_GRAY);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setPadding(5);

            PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("$%.2f", totalAmount), totalFont));
            totalValueCell.setBackgroundColor(new BaseColor(220, 220, 220));
            totalValueCell.setBorderWidthTop(1f);
            totalValueCell.setBorderColorTop(BaseColor.DARK_GRAY);
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell.setPadding(5);

            table.addCell(totalLabelCell);
            table.addCell(totalValueCell);
            document.add(table);

            Paragraph footer = new Paragraph(
                    "Generated on: " + LocalDate.now() + "\n" +
                            "Park.me Int - © 2025 Park.me",
                    footerFont
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            response.setContentLength(baos.size());
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
        } catch (DocumentException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving data");
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
    private void addStyledCell(PdfPTable table, String content, Font font, boolean alternate) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setBackgroundColor(alternate ? BaseColor.LIGHT_GRAY : BaseColor.WHITE);
        if (content.startsWith("$")) {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        table.addCell(cell);
    }
}

