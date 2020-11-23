package project.utils;

import project.model.State;


import java.util.*;

public class Table {

    private static LinkedList<List> rows;

    private static int column;

    private static int[] columnLen;

    private static int margin = 2;

    private static boolean printHeader = true;


    private static void appendRow() {
        if (!rows.isEmpty()) {
            List temp = rows.getLast();
            if (temp.isEmpty())
                return;
        }
        List row = new ArrayList(column);
        rows.add(row);
    }

    private static void appendColum(Object value) {
        if (value == null) {
            value = "";
        }
        List row = rows.get(rows.size() - 1);
        row.add(value);
        int len = value.toString().getBytes().length;

        // to find the max len of each column
        if (columnLen[row.size() - 1] < len)
            columnLen[row.size() - 1] = len;
    }

    private static String table() {
        StringBuilder buf = new StringBuilder();

        int sumlen = 0;
        for (int len : columnLen) {
            sumlen += len;
        }
        if (printHeader)
            buf.append("|").append(printChar('=', sumlen + margin * 2 * column + (column - 1))).append("|\n");
        else
            buf.append("|").append(printChar('-', sumlen + margin * 2 * column + (column - 1))).append("|\n");
        for (int ii = 0; ii < rows.size(); ii++) {
            List row = rows.get(ii);
            for (int i = 0; i < column; i++) {
                String o = "";
                if (i < row.size())
                    o = row.get(i).toString();
                buf.append('|').append(printChar(' ', margin)).append(o);
                buf.append(printChar(' ', columnLen[i] - (ColoredText.isColored(o) ? o.length() - 9 : o.length()) + margin));
            }
            buf.append("|\n");
            if (printHeader && ii == 0)
                buf.append("|").append(printChar('=', sumlen + margin * 2 * column + (column - 1))).append("|\n");
            else
                buf.append("|").append(printChar('-', sumlen + margin * 2 * column + (column - 1))).append("|\n");
        }
        return buf.toString();
    }

    private static String printChar(char c, int len) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buf.append(c);
        }
        return buf.toString();
    }


    public static void printStateMachine(Map<String, State> states, Set<String> letters, State startState, Set<State> finalStates) {
        rows = new LinkedList<List>();
        column = letters.size() + 1;
        columnLen = new int[column];

        // first row
        appendRow();
        appendColum("States/letters");
        letters.forEach(l -> appendColum(l));
        // row for each state
        states.forEach((s, state) -> {
            appendRow();
            appendColum((state == startState) ? ColoredText.ToGreen(s) : (finalStates.contains(state) ? ColoredText.ToRed(s) : s));
            Iterator<String> lettersIterator = letters.iterator();
            while (lettersIterator.hasNext()) {
                String l = lettersIterator.next();
                l = (l.equals("Lambda")) ? "@" : l;
                if (!state.getTransactions().keySet().contains(l)) {
                    appendColum(" ");
                } else {
                    String row = "{";
                    boolean firstT = true;

                    for (String letter : state.getTransactions().keySet()) {
                        if (letter.equals(l)) {
                            for (State state1 : state.getTransactions().get(letter)) {
                                row = (firstT) ? row : row + " ,";
                                row += state1.getState();
                                firstT = false;
                            }
                        }
                    }
                    row += "}";
                    appendColum(row);
                }
            }
        });

        System.out.println(table());
    }

    public static void printStateMachine(Map<String, State> states, Set<String> letters, State startState, State finalState) {
        Set<State> finalStates = new LinkedHashSet<>();
        finalStates.add(finalState);
        printStateMachine(states, letters, startState, finalStates);
    }
}
