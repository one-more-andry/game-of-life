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
    public static int[][] getGeneration(int[][] cells, int generations) {
        List<List<Integer>> cellsList = Arrays.stream(cells)
                .map(l -> Arrays.stream(l).boxed().collect(Collectors.toList()))
                .toList();

        return cellsList.stream()
                .map(l -> l.stream()
                        .mapToInt(Integer::intValue)
                        .toArray())
                .toArray(int[][]::new);
    }
}
