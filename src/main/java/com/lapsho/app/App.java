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
        List<List<Integer>> cellsList = Arrays.stream(cells)
                .map(subSet -> Arrays.stream(subSet).boxed().collect(Collectors.toList()))
                .toList();

        List<List<Integer>> innerMetamorphosis = calculateInnerSpace(cellsList);
        Map<String, List<Integer>> expandedSpace = calculateExpanse(cellsList);
        cellsList = executeMetamorphoses(cellsList, innerMetamorphosis, expandedSpace);
        cellsList = removeEmptySpace(cellsList);
        
        return cellsList.stream()
                .map(subSet -> subSet.stream()
                        .mapToInt(Integer::intValue)
                        .toArray())
                .toArray(int[][]::new);
    }

    private static List<List<Integer>> calculateInnerSpace(List<List<Integer>> cellsList) {
        List<List<Integer>> metamorphoses = new ArrayList<>();

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

                    if (indexExists(cellsList, x) == false) {
                        cellsList.set(x, new ArrayList<Integer>());
                    }
                    metamorphoses.get(x).set(y, newStatus);
                }
            }
        }

        return metamorphoses;
    }

    private static int countNeighbors(List<List<Integer>> cellsList, int x, int y) {
        int count = 0;

        return count;
    }

    private static boolean indexExists(List<List<Integer>> cellsList, int x) {

        return true;
    }

    private static Map<String, List<Integer>> calculateExpanse(List<List<Integer>> cellsList) {
        Map<String, List<Integer>> expanse = new HashMap<>();

        return expanse;
    }

    private static List<List<Integer>> executeMetamorphoses(List<List<Integer>> cellsList, List<List<Integer>> innerMetamorphosis, Map<String, List<Integer>> expandedSpace) {
        return cellsList;
    }

    private static List<List<Integer>> removeEmptySpace(List<List<Integer>> cellsList) {
        boolean reduced = true;

        do {
            reduced = false;

        } while (reduced);

        return cellsList;
    }
}
