package project.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Table {

    private LinkedList<List> rows = new LinkedList<List>();

    private int column;

    private int[] columnLen;

    private static int margin = 2;

    private boolean printHeader = true;

    public Table(int column) {
        this.column = column;
        this.columnLen = new int[column];
    }

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
                buf.append(printChar(' ', columnLen[i] - o.getBytes().length + margin));
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

}
