package com.lapsho.app;

import java.util.Arrays;
import java.util.List;
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

    public static int[][] getGeneration(int[][] cells, int generations) {
        List<List<Integer>> cellsList = Arrays.stream(cells)
                .map(subSet -> Arrays.stream(subSet).boxed().collect(Collectors.toList()))
                .toList();

        return cellsList.stream()
                .map(subSet -> subSet.stream()
                        .mapToInt(Integer::intValue)
                        .toArray())
                .toArray(int[][]::new);
    }

    public static String htmlize(int[][] table) {
        return Arrays.stream(table)
                .map(row -> Arrays.stream(row)
                        .mapToObj(cell -> (cell == 1) ? HTMLIZE_LIFE : HTMLIZE_DEATH)
                        .collect(Collectors.joining("")))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
