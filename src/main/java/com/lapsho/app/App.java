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
        Map<Integer, List<Integer>> cellsList = new HashMap<>();

        for (int i = 0; i < cells.length; i++) {
            cellsList.put(i, Arrays.stream(cells[i]).boxed().collect(Collectors.toList()));
        }

        Map<Integer, List<Integer>> innerMetamorphosis = calculateInnerSpace(cellsList);
        Map<Integer, List<Integer>> expandedSpace = calculateExpanse(cellsList);
        cellsList = executeMetamorphoses(cellsList, innerMetamorphosis, expandedSpace);
        cellsList = removeEmptySpace(cellsList);

        return cellsList.values()
                .stream()
                .map(subset -> subset
                        .stream()
                        .mapToInt(Integer::intValue)
                        .toArray()
                )
                .toArray(int[][]::new);
    }

    private static Map<Integer, List<Integer>> calculateInnerSpace(Map<Integer, List<Integer>> cellsList) {
        Map<Integer, List<Integer>> metamorphoses = new HashMap<>();

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
                        metamorphoses.put(x, new ArrayList<>());
                    }
                    metamorphoses.get(x).set(y, newStatus);
                }
            }
        }

        return metamorphoses;
    }

    private static int countNeighbors(Map<Integer, List<Integer>> cellsList, int x, int y) {
        int count = 0;

        return count;
    }

    private static boolean indexExists(Map<Integer, List<Integer>> cellsList, int x) {

        return true;
    }

    private static Map<Integer, List<Integer>> calculateExpanse(Map<Integer, List<Integer>> cellsList) {
        Map<Integer, List<Integer>> expanse = new HashMap<>();

        return expanse;
    }

    private static Map<Integer, List<Integer>> executeMetamorphoses(Map<Integer, List<Integer>> cellsList, Map<Integer, List<Integer>> innerMetamorphosis, Map<Integer, List<Integer>> expandedSpace) {
        return cellsList;
    }

    private static Map<Integer, List<Integer>> removeEmptySpace(Map<Integer, List<Integer>> cellsList) {
        boolean reduced = true;

        do {
            reduced = false;

        } while (reduced);

        return cellsList;
    }
}
