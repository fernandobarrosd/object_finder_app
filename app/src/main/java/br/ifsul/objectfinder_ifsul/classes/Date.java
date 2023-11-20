package br.ifsul.objectfinder_ifsul.classes;


public class Date  {
    private int day;
    private int month;

    private int year;

    private Date() {}

    private Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public int getDay() {
        return day;
    }


    public int getMonth() {
        return month;
    }


    public int getYear() {
        return year;
    }

    public static class DateBuilder {
        public Date date;

        public DateBuilder() {
            date = new Date();
        }

        public DateBuilder setDay(int day) {
            date.day = day;
            return this;
        }

        public DateBuilder setMonth(int month) {
            date.month = month;
            return this;
        }

        public DateBuilder setYear(int year) {
            date.year = year;
            return this;
        }

        public Date build() {
            return date;
        }
    }


}
