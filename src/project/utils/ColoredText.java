package project.utils;

public class ColoredText {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String GREEN_TEXT = "\u001B[32m";

    public static String ToRed(String text) {
        return RED_TEXT + text + ANSI_RESET ;
    }

    public static String ToGreen(String text) {
        return GREEN_TEXT + text + ANSI_RESET ;
    }
}
