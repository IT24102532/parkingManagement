package lk.sliit.parkingmanagement.oopapp.utils.Sort;

import lk.sliit.parkingmanagement.oopapp.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class GlobalSort {
    public static void insertionSortByDates(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            LocalDateTime kt = key.getCreatedAt();
            int j = i - 1;
            while (j >= 0 && list.get(j).getCreatedAt().isBefore(kt)) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}
