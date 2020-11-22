package project.utils;

import project.model.State;


import java.util.*;

public enum Table {
    INSTANCE;

    private LinkedList<List> rows;

    private int column;

    private int[] columnLen;

    private static int margin = 2;

    private boolean printHeader = true;


    public void appendRow() {
        if (!rows.isEmpty()) {
            List temp = rows.getLast();
            if (temp.isEmpty())
                return;
        }
        List row = new ArrayList(column);
        rows.add(row);
    }

    public Table appendColum(Object value) {
        if (value == null) {
            value = "";
        }
        List row = rows.get(rows.size() - 1);
        row.add(value);
        int len = value.toString().getBytes().length;

        // to find the max len of each column
        if (columnLen[row.size() - 1] < len)
            columnLen[row.size() - 1] = len;
        return this;
    }

    @Override
    public String toString() {
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
                buf.append(printChar(' ', columnLen[i] - (ColoredText.isColored(o)? o.length() - 9 : o.length()) + margin));
            }
            buf.append("|\n");
            if (printHeader && ii == 0)
                buf.append("|").append(printChar('=', sumlen + margin * 2 * column + (column - 1))).append("|\n");
            else
                buf.append("|").append(printChar('-', sumlen + margin * 2 * column + (column - 1))).append("|\n");
        }
        return buf.toString();
    }

    private String printChar(char c, int len) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buf.append(c);
        }
        return buf.toString();
    }


    public void printStateMachine(LinkedHashMap<String, State> states , HashSet<String> letters ,State startState ,LinkedHashSet<State> endStates ) {
        rows = new LinkedList<List>();
        this.column = letters.size()+1;
        this.columnLen = new int[column];

            // first row
            INSTANCE.appendRow();
            INSTANCE.appendColum("States/letters");
            letters.forEach(l -> INSTANCE.appendColum(l));

            // row for each state
             states.forEach((s, state) -> {
                 INSTANCE.appendRow();
                 INSTANCE.appendColum((state == startState) ? ColoredText.ToGreen(s) : (endStates.contains(state)? ColoredText.ToRed(s) : s ) );
                Iterator<String> lettersIterator = letters.iterator();
                while (lettersIterator.hasNext()) {
                    String l = lettersIterator.next();
                    l = (l.equals("Lambda")) ? "@" : l;
                    if (!state.getTransactions().keySet().contains(l)) {
                        INSTANCE.appendColum(" ");
                    } else {
                        String row = "{";
                        boolean firstT = true;

                        for (String letter : state.getTransactions().keySet()) {
                            if (letter.equals(l)) {
                                for(State state1 : state.getTransactions().get(letter)) {
                                    row = (firstT) ? row : row + " ,";
                                    row += state1.getState();
                                    firstT = false;
                                }
                            }
                        }

                        row += "}";
                        INSTANCE.appendColum(row);
                    }
                }
            });

                 System.out.println(INSTANCE);
    }

    public void printStateMachine(LinkedHashMap<String, State> states , HashSet<String> letters ,State startState ,State endState ) {
        LinkedHashSet<State> endStates = new LinkedHashSet<>();
       endStates.add(endState);
       printStateMachine(states , letters, startState , endStates);

    }

}
