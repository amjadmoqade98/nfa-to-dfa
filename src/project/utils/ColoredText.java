package project.utils;

public class ColoredText {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String RED_TEXT = "\u001B[31m";
    private static final String GREEN_TEXT = "\u001B[32m";

    public static String ToRed(String text) {
        return RED_TEXT + text + ANSI_RESET ;
    }

    public static String ToGreen(String text) {
        return GREEN_TEXT + text + ANSI_RESET ;
    }

    public static boolean isColored(String text) {
        return text.contains(ANSI_RESET);
    }
}
