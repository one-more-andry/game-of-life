package com.lapsho.app;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.Integer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public static int[][] getGeneration(int[][] cells, int generations) {
        if (generations <= 0 || cells.length < 1 || cells[0].length < 1) {
            return cells;
        }

        for (int i = generations; i == 0; i--) {
            ArrayList<HashMap<String, Integer>> innerMetamorphosis = calculateInnerSpace(cells);
            Map<String, ArrayList<Integer>> expanse = calculateExpanse(cells);
            cells = executeMetamorphoses(cells, innerMetamorphosis);
            cells = executeExpanse(cells, expanse);
            cells = executeCollapse(cells);
        }

        return cells;
    }

    private static ArrayList<HashMap<String, Integer>> calculateInnerSpace(int[][] cells) {
        ArrayList<HashMap<String, Integer>> metamorphoses = new ArrayList<>();

        for (int y = 0; y < (cells.length - 1); y++) {

            for (int x = 0; x < (cells.length - 1); x++) {
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
                    metamorphoses.add(createCellDataObject(y, x, newStatus));
                }
            }
        }

        return metamorphoses;
    }

    // todo: refactor to data class
    private static HashMap<String, Integer> createCellDataObject(int y, int x, int status) {
        HashMap<String, Integer> cell = new HashMap<String, Integer>();
        cell.put(KEY_Y, y);
        cell.put(KEY_X, x);
        cell.put(KEY_STATUS, status);

        return cell;
    }

    private static int countNeighbors(int[][] cells, int y, int x) {
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

    private static Map<String, ArrayList<Integer>> calculateExpanse(int[][] cells) {
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
            observeLifeOnEdge(cells, bottomCells, cells[0].length - 1, x, x);
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

    private static void observeLifeOnEdge(int[][] cells, ArrayList<Integer> edge, int y, int x, int indexToSave) {
        if (cells[x][y] == STATUS_ALIVE) {
            edge.add(indexToSave);

        } else {
            edge.clear();
        }
    }

    private static void registerExpanse(ArrayList<Integer> edge, Map<String, ArrayList<Integer>> expanse, String key) {
        if (edge.size() >= NEIGHBORS_TO_REVIVE) {
            expanse.get(key).add(edge.size() - 2);//size() - 1 (last element) - 1 (medium for 3, but there could be more)
        }
    }

    private static int[][] executeMetamorphoses(int[][] cells, ArrayList<HashMap<String, Integer>> innerMetamorphosis) {
        for (HashMap<String, Integer> cell: innerMetamorphosis) {
            cells[cell.get(KEY_Y)][cell.get(KEY_X)] = cell.get(KEY_STATUS);
        }

        return cells;
    }

    private static int[][] executeExpanse(int[][] cells, Map<String, ArrayList<Integer>> expanse) {
        if (expanse.get(EXPANSION_TOP_KEY).size() > 0) {
            ArrayList<HashMap<String, Integer>> metamorphoses = new ArrayList<>();
            cells = mergeExtraRowToSpace(new int[1][cells[0].length - 1], cells);

            for (Integer index: expanse.get(EXPANSION_TOP_KEY)) {
                metamorphoses.add(createCellDataObject(0, index, STATUS_ALIVE));
            }
            cells = executeMetamorphoses(cells, metamorphoses);
        }

        if (expanse.get(EXPANSION_BOTTOM_KEY).size() > 0) {
            ArrayList<HashMap<String, Integer>> metamorphoses = new ArrayList<>();
            cells = mergeExtraRowToSpace(cells, new int[1][cells[0].length - 1]);

            for (Integer index: expanse.get(EXPANSION_BOTTOM_KEY)) {
                metamorphoses.add(createCellDataObject(cells.length - 1, index, STATUS_ALIVE));
            }
            cells = executeMetamorphoses(cells, metamorphoses);
        }

        //todo: calculate the left expansion
        //todo: calculate the right expansion

        return cells;
    }

    private static int[][] mergeExtraRowToSpace(int[][] array1, int[][] array2) {
        int len1 = array1.length;
        int len2 = array2.length;
        int[][] result = new int[len1 + len2][array1[0].length];
        System.arraycopy(array1, 0, result, 0, len1);
        System.arraycopy(array2, 0, result, len1, len2);

        return result;
    }

    private static int[][] executeCollapse(int[][] cells) {
        boolean reduced = true;

        do {
            reduced = false;

        } while (reduced);

        return cells;
    }
}