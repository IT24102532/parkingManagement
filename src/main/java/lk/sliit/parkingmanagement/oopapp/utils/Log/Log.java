package lk.sliit.parkingmanagement.oopapp.utils.Log;

public class Log {
    private String message;
    private String icon;
    private String color = AnsiColor.WHITE;
    private String label = "";

    public static Log type(LogType type) {
        Log log = new Log();
        log.icon = type.icon;
        log.label = type.label;
        log.color = type.color;
        return log;
    }

    public static Log custom() {
        return new Log();
    }

    public Log icon(String icon) {
        this.icon = icon;
        return this;
    }

    public Log label(String label) {
        this.label = label;
        return this;
    }

    public Log color(String colorName) {
        this.color = AnsiColor.fromName(colorName);
        return this;
    }

    public Log message(String message) {
        this.message = message;
        return this;
    }

    public void print() {
        System.out.println(color + icon + "[" + label + "] " + message + AnsiColor.RESET);
    }

    public void println() {
        print();
    }
}

