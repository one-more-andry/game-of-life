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

    public static String htmlize(int[][] table) {
        return Arrays.stream(table)
                .map(row -> Arrays.stream(row)
                        .mapToObj(cell -> (cell == 1) ? HTMLIZE_LIFE : HTMLIZE_DEATH)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static int[][] getGeneration(int[][] cells, int generations) {
        Map<Integer, Map<Integer, Integer>> cellsList = new HashMap<>();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {

                if (!cellsList.containsKey(i)) {
                    cellsList.put(i, new HashMap<>());
                }
                cellsList.get(i).put(j, cells[i][j]);
            }
        }

        Map<Integer, Map<Integer, Integer>> innerMetamorphosis = calculateInnerSpace(cellsList);
//        Map<Integer, Map<Integer, Integer>> expandedSpace = calculateExpanse(cellsList);
//        cellsList = executeMetamorphoses(cellsList, innerMetamorphosis, expandedSpace);
//        cellsList = removeEmptySpace(cellsList);

        return cellsList.values().stream()
                .map(subset -> subset.values().stream()
                        .mapToInt(i -> i)
                        .toArray()
                )
                .toArray(int[][]::new);
    }

    private static Map<Integer, Map<Integer, Integer>> calculateInnerSpace(Map<Integer, Map<Integer, Integer>> cellsList) {
        Map<Integer, Map<Integer, Integer>> metamorphoses = new HashMap<>();

        for (int x = 0; x < cellsList.size(); x++) {

            for (int y = 0; y < cellsList.size(); y++) {
                int status = cellsList.get(x).get(y);
                int neighbors = countNeighbors(cellsList, x, y);
                int newStatus;

                if (neighbors == 2) {
                    newStatus = status;

                } else if (neighbors == 3) {
                    newStatus = 1;

                } else {
                    newStatus = 0;
                }

                if (status != newStatus) {

                    if (!metamorphoses.containsKey(x)) {
                        metamorphoses.put(x, new HashMap<>());
                    }
                    metamorphoses.get(x).put(y, newStatus);
                }
            }
        }

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

    private static Map<Integer, Map<Integer, Integer>> calculateExpanse(Map<Integer, Map<Integer, Integer>> cellsList) {
        Map<Integer, Map<Integer, Integer>> expanse = new HashMap<>();

        return expanse;
    }

    private static Map<Integer, Map<Integer, Integer>> executeMetamorphoses(Map<Integer, Map<Integer, Integer>> cellsList, Map<Integer, Map<Integer, Integer>> innerMetamorphosis, Map<Integer, Map<Integer, Integer>> expandedSpace) {
        return cellsList;
    }

    private static Map<Integer, Map<Integer, Integer>> removeEmptySpace(Map<Integer, Map<Integer, Integer>> cellsList) {
        boolean reduced = true;

        do {
            reduced = false;

        } while (reduced);

        return cellsList;
    }
}