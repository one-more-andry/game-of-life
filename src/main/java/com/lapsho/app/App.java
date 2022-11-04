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

        return metamorphoses;
    }

    private static int countNeighbors(Map<Integer, Map<Integer, Integer>> cellsList, int x, int y) {
        int count = 0;

        for (int neighborX = x - 1; neighborX <= (x + 1); neighborX++) {
            if (!cellsList.containsKey(neighborX)) {
                continue;
            }

            for (int neighborY = y - 1; neighborY <= (y + 1); neighborY++) {

                if (cellsList.get(neighborX).containsKey(neighborY) &&
                        (neighborX != x || neighborY != y) &&
                        cellsList.get(neighborX).get(neighborY) == 1
                ) {
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