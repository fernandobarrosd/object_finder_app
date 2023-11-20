package br.ifsul.objectfinder_ifsul.utils;

public abstract class NumberUtils {
    public static String formatToTwoNumbers(Integer number) {
        return number >= 10 ? String.valueOf(number) : "0" + number;
    }
}
