package com.lapsho.app;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.Integer;


public class LifeEmulator
{
    private final static String HTMLIZE_LIFE = "✅";

    private final static String HTMLIZE_DEATH = "❌";

    private final static String EXPANSION_TOP_KEY = "top";

    private final static String EXPANSION_BOTTOM_KEY = "bottom";

    private final static String EXPANSION_LEFT_KEY = "left";

    private final static String EXPANSION_RIGHT_KEY = "right";

    private final static String KEY_X = "x";

    private final static String KEY_Y = "y";

    private final static String KEY_STATUS = "status";

    private final static int STATUS_ALIVE = 1;

    private final static int STATUS_DEAD = 0;

    private final static int NEIGHBORS_TO_KEEP_ALIVE = 2;

    private final static int NEIGHBORS_TO_REVIVE = 3;

    public static String htmlize(int[][] table) {
        return Arrays.stream(table)
                .map(row -> Arrays.stream(row)
                        .mapToObj(cell -> (cell == STATUS_ALIVE) ? HTMLIZE_LIFE : HTMLIZE_DEATH)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public int[][] getGeneration(int[][] cells, int generations) {
        if (generations <= 0 || cells.length < 1 || cells[0].length < 1) {
            return cells;
        }

        for (; generations > 0; generations--) {
            ArrayList<MetamorphoseCellData> innerMetamorphosis = calculateInnerSpace(cells);
            Map<String, ArrayList<Integer>> expanse = calculateExpanse(cells);
            cells = executeMetamorphoses(cells, innerMetamorphosis);
            cells = executeExpanse(cells, expanse);
            cells = executeCollapse(cells);
        }

        return cells;
    }

    private ArrayList<MetamorphoseCellData> calculateInnerSpace(int[][] cells) {
        ArrayList<MetamorphoseCellData> metamorphoses = new ArrayList<>();

        for (int y = 0; y < cells.length; y++) {

            for (int x = 0; x < cells[y].length; x++) {
                int status = cells[y][x];
                int neighbors = countNeighbors(cells, y, x);
                int newStatus;

                if (neighbors == NEIGHBORS_TO_KEEP_ALIVE) {
                    newStatus = status;

                } else if (neighbors == NEIGHBORS_TO_REVIVE) {
                    newStatus = STATUS_ALIVE;

                } else {
                    newStatus = STATUS_DEAD;
                }

                if (status != newStatus) {
                    metamorphoses.add(new MetamorphoseCellData(y, x, newStatus));
                }
            }
        }

        return metamorphoses;
    }

    private int countNeighbors(int[][] cells, int y, int x) {
        int count = 0;

        for (int neighborY = y - 1; neighborY <= (y + 1); neighborY++) {

            if ( (neighborY < 0) || (neighborY >= cells.length) ) {
                continue;
            }

            for (int neighborX = x - 1; neighborX <= (x + 1); neighborX++) {

                if ( (neighborX < 0) || (neighborX >= cells[neighborY].length) ) {
                    continue;
                }

                if ( (neighborY == y) && (neighborX == x) ) {
                    continue;
                }

                if (cells[neighborY][neighborX] == STATUS_ALIVE) {
                    count++;
                }
            }
        }

        return count;
    }

    private Map<String, ArrayList<Integer>> calculateExpanse(int[][] cells) {
        Map<String, ArrayList<Integer>> expanse = new HashMap<>();
        expanse.put(EXPANSION_TOP_KEY, new ArrayList<>());
        expanse.put(EXPANSION_BOTTOM_KEY, new ArrayList<>());
        expanse.put(EXPANSION_LEFT_KEY, new ArrayList<>());
        expanse.put(EXPANSION_RIGHT_KEY, new ArrayList<>());

        ArrayList<Integer> topCells = new ArrayList<>();
        ArrayList<Integer> bottomCells = new ArrayList<>();
        ArrayList<Integer> leftCells = new ArrayList<>();
        ArrayList<Integer> rightCells = new ArrayList<>();

        for (int x = 0; x < cells[0].length; x++) {
            observeLifeOnEdge(cells, topCells, 0, x, x);
            registerExpanse(topCells, expanse, EXPANSION_TOP_KEY);
            observeLifeOnEdge(cells, bottomCells, cells.length - 1, x, x);
            registerExpanse(bottomCells, expanse, EXPANSION_BOTTOM_KEY);
        }

        for (int y = 0; y < cells.length; y++) {
            observeLifeOnEdge(cells, leftCells, y, 0, y);
            registerExpanse(leftCells, expanse, EXPANSION_LEFT_KEY);
            observeLifeOnEdge(cells, rightCells, y, cells[0].length - 1, y);
            registerExpanse(rightCells, expanse, EXPANSION_RIGHT_KEY);
        }

        return expanse;
    }

    private void observeLifeOnEdge(int[][] cells, ArrayList<Integer> edge, int y, int x, int indexToSave) {
        if (cells[y][x] == STATUS_ALIVE) {
            edge.add(indexToSave);

        } else {
            edge.clear();
        }
    }

    private void registerExpanse(ArrayList<Integer> edge, Map<String, ArrayList<Integer>> expanse, String key) {
        if (edge.size() >= NEIGHBORS_TO_REVIVE) {
            expanse.get(key).add(edge.get(1));
        }
    }

    private int[][] executeMetamorphoses(int[][] cells, ArrayList<MetamorphoseCellData> innerMetamorphosis) {
        for (MetamorphoseCellData cell: innerMetamorphosis) {
            cells[cell.getColumn()][cell.getRow()] = cell.getStatus();
        }

        return cells;
    }

    private int[][] executeExpanse(int[][] cells, Map<String, ArrayList<Integer>> expanse) {
        cells = executeVectorExpanse(cells, expanse, EXPANSION_TOP_KEY);
        cells = executeVectorExpanse(cells, expanse, EXPANSION_BOTTOM_KEY);
        cells = executeVectorExpanse(cells, expanse, EXPANSION_LEFT_KEY);
        cells = executeVectorExpanse(cells, expanse, EXPANSION_RIGHT_KEY);

        return cells;
    }

    private int[][] executeVectorExpanse(int[][] cells, Map<String, ArrayList<Integer>> expanse, String vector) {
        if (expanse.get(vector).size() == 0) {
            return cells;
        }
        ArrayList<MetamorphoseCellData> metamorphoses = new ArrayList<>();
        cells = mergeEmptySpace(cells, vector);

        for (Integer index: expanse.get(vector)) {

            switch (vector) {
                case EXPANSION_TOP_KEY -> metamorphoses.add(new MetamorphoseCellData(0, index, STATUS_ALIVE));
                case EXPANSION_BOTTOM_KEY ->
                        metamorphoses.add(new MetamorphoseCellData(cells.length - 1, index, STATUS_ALIVE));
                case EXPANSION_LEFT_KEY -> metamorphoses.add(new MetamorphoseCellData(index, 0, STATUS_ALIVE));
                case EXPANSION_RIGHT_KEY ->
                        metamorphoses.add(new MetamorphoseCellData(index, cells[0].length - 1, STATUS_ALIVE));
            }
        }

        return executeMetamorphoses(cells, metamorphoses);
    }

    private int[][] mergeEmptySpace(int[][] cells, String vector) {

        if (vector.equals(EXPANSION_TOP_KEY)) {
            return mergeExtraRowToSpace(new int[1][cells[0].length], cells);

        } else if (vector.equals(EXPANSION_BOTTOM_KEY)) {
            return mergeExtraRowToSpace(cells, new int[1][cells[0].length]);
        }

        for (int rowIndex = 0; rowIndex < cells.length; rowIndex++) {

            if (vector.equals(EXPANSION_LEFT_KEY)) {

                cells[rowIndex] = mergeExtraColumnToSpace(new int[1], cells[rowIndex]);

            } else if (vector.equals(EXPANSION_RIGHT_KEY)) {
                cells[rowIndex] = mergeExtraColumnToSpace(cells[rowIndex], new int[1]);
            }
        }

        return cells;
    }

    private int[][] mergeExtraRowToSpace(int[][] array1, int[][] array2) {
        int len1 = array1.length;
        int len2 = array2.length;
        int[][] result = new int[len1 + len2][array1[0].length];
        System.arraycopy(array1, 0, result, 0, len1);
        System.arraycopy(array2, 0, result, len1, len2);

        return result;
    }

    private int[] mergeExtraColumnToSpace(int[] array1, int[] array2) {
        int len1 = array1.length;
        int len2 = array2.length;
        int[] result = new int[len1 + len2];
        System.arraycopy(array1, 0, result, 0, len1);
        System.arraycopy(array2, 0, result, len1, len2);

        return result;
    }

    private int[][] executeCollapse(int[][] cells) {

        if (validateEdgeToCollapse(cells, EXPANSION_TOP_KEY)) {
            int[][] reducedCells = new int[cells.length - 1][cells[0].length];
            System.arraycopy(cells, 1, reducedCells, 0, cells.length - 1);
            cells = reducedCells;
        }

        if (validateEdgeToCollapse(cells, EXPANSION_BOTTOM_KEY)) {
            int[][] reducedCells = new int[cells.length - 1][cells[0].length];
            System.arraycopy(cells, 0, reducedCells, 0, cells.length - 1);
            cells = reducedCells;
        }

        if (validateEdgeToCollapse(cells, EXPANSION_LEFT_KEY)) {
            int[][] collapsedCells = new int[cells.length][cells[0].length - 1];

            for (int i = 0; i < cells.length; i++) {
                int [] row = new int[cells[0].length - 1];
                System.arraycopy(cells[i], 1, row, 0, cells[i].length - 1);
                collapsedCells[i] = row;
            }

            cells = collapsedCells;
        }

        if (validateEdgeToCollapse(cells, EXPANSION_RIGHT_KEY)) {
            int[][] collapsedCells = new int[cells.length][cells[0].length - 1];

            for (int i = 0; i < cells.length; i++) {
                int [] row = new int[cells[0].length - 1];
                System.arraycopy(cells[i], 0, row, 0, cells[i].length - 1);
                collapsedCells[i] = row;
            }

            cells = collapsedCells;
        }

        if (cells.length == 0) {
            cells = new int[1][0];
        }

        return cells;
    }

    private boolean validateEdgeToCollapse(int[][] cells, String vector) {
        if (cells.length == 0) {
            return false;
        }
        int index = 0;
        int limit = 0;

        switch (vector) {
            case EXPANSION_TOP_KEY -> {
                index = 0;
                limit = cells[0].length;
            }
            case EXPANSION_BOTTOM_KEY -> {
                index = cells.length - 1;
                limit = cells[0].length;
            }
            case EXPANSION_LEFT_KEY -> {
                index = 0;
                limit = cells.length;
            }
            case EXPANSION_RIGHT_KEY -> {
                index = cells[0].length - 1;
                limit = cells.length;
            }
        }

        for (int i = 0; i < limit; i++) {
            int y = (vector.equals(EXPANSION_TOP_KEY) || vector.equals(EXPANSION_BOTTOM_KEY)) ? index : i;
            int x = (vector.equals(EXPANSION_LEFT_KEY) || vector.equals(EXPANSION_RIGHT_KEY)) ? index : i;

            if (cells[y][x] == STATUS_ALIVE) {
                return false;
            }
        }

        return true;
    }

    private class MetamorphoseCellData {
        private int column;

        private int row;

        private int status;

        MetamorphoseCellData(int y, int x, int status) {
            this.column = y;
            this.row = x;
            this.status = status;
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        public int getStatus() {
            return status;
        }
    }
}