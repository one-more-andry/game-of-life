package com.lapsho.app;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.Integer;

/**
 * Hello world!
 *
 */
public class App 
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

    public static String htmlize(int[][] table) {
        return Arrays.stream(table)
                .map(row -> Arrays.stream(row)
                        .mapToObj(cell -> (cell == 1) ? HTMLIZE_LIFE : HTMLIZE_DEATH)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static int[][] getGeneration(int[][] cells, int generations) {
        for (int i = generations; i == 0; i--) {
            ArrayList<HashMap<String, Integer>> innerMetamorphosis = calculateInnerSpace(cells);
            Map<String, ArrayList<Integer>> expanse = calculateExpanse(cells);
            cells = executeMetamorphoses(cells, innerMetamorphosis, expanse);
            cells = removeEmptySpace(cells);
        }

        return cells;
    }

    private static ArrayList<HashMap<String, Integer>> calculateInnerSpace(int[][] cells) {
        ArrayList<HashMap<String, Integer>> metamorphoses = new ArrayList<>();

        for (int x = 0; x < (cells.length - 1); x++) {

            for (int y = 0; y < (cells.length - 1); y++) {
                int status = cells[x][y];
                int neighbors = countNeighbors(cells, x, y);
                int newStatus;

                if (neighbors == 2) {
                    newStatus = status;

                } else if (neighbors == 3) {
                    newStatus = 1;

                } else {
                    newStatus = 0;
                }

                if (status != newStatus) {
                    HashMap<String, Integer> cell = new HashMap<String, Integer>();
                    cell.put(KEY_X, x);
                    cell.put(KEY_Y, y);
                    cell.put(KEY_STATUS, newStatus);
                    metamorphoses.add(cell);
                }
            }
        }

        return metamorphoses;
    }

    private static int countNeighbors(int[][] cells, int x, int y) {
        int count = 0;

        for (int neighborX = x - 1; neighborX <= (x + 1); neighborX++) {

            if ( (neighborX < 0) || (neighborX >= cells.length) ) {
                continue;
            }

            for (int neighborY = y - 1; neighborY <= (y + 1); neighborY++) {

                if ( (neighborY < 0) || (neighborY >= cells[neighborX].length) ) {
                    continue;
                }

                if ( (neighborX == x) && (neighborY == y) ) {
                    continue;
                }

                if (cells[neighborX][neighborY] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    private static Map<String, ArrayList<Integer>> calculateExpanse(int[][] cells) {
        Map<String, ArrayList<Integer>> expanse = new HashMap<>();
        expanse.put(EXPANSION_TOP_KEY, new ArrayList<>());
        expanse.put(EXPANSION_BOTTOM_KEY, new ArrayList<>());
        expanse.put(EXPANSION_LEFT_KEY, new ArrayList<>());
        expanse.put(EXPANSION_RIGHT_KEY, new ArrayList<>());

        //todo: cycle for top and bottom edges; -1 x (row), x.length + 1
        ArrayList<Integer> topCount = new ArrayList<>();

        //todo: cycle for left and right edges; -1 y (column),  y.length +1

        return expanse;
    }

    private static int[][] executeMetamorphoses(int[][] cells, ArrayList<HashMap<String, Integer>> innerMetamorphosis, Map<String, ArrayList<Integer>> expanse) {
        return cells;
    }

    private static int[][] removeEmptySpace(int[][] cells) {
        boolean reduced = true;

        do {
            reduced = false;

        } while (reduced);

        return cells;
    }
}