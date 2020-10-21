package com.inet.codebase.utils;

/**
 * @author HCY
 */
public class ArchivesUtils {
    private String date;
    private Integer amount;

    public ArchivesUtils() {
    }

    public ArchivesUtils(String date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ArchivesUtils{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}
