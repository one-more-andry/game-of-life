package com.lapsho.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;


public class AppTest 
{
    @Test
    public void lifeEmulation_OnValidInput3x3Gen1_ShouldPass() {
        int[][] input = {
                {1,0,0},
                {0,1,1},
                {1,1,0}};
        int[][] expected = {
                {0,1,0},
                {0,0,1},
                {1,1,1}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation result" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + App.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_OneCell_ShouldDie() {
        int[][] input = {{1}};
        int[][] expected = {{}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "The output should be empty" + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_ThreeCells_ShouldProduceExtraOne() {
        int[][] input = {
                {0,1},
                {1,1}};
        int[][] expected = {
                {1,1},
                {1,1}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + App.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_CellExpansion_ShouldExtendArrayVertical() {
        int[][] input = {{1,1,1}};
        int[][] expected = {
                {1},
                {1},
                {1}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + App.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_CellExpansion_ShouldExtendArrayHorizontal() {
        int[][] input = {
                {1},
                {1},
                {1}};
        int[][] expected = {{1,1,1}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + App.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_DeadCells_ShouldBeCropped() {
        int[][] input = {
                {1,1,0},
                {1,1,0}};
        int[][] expected = {
                {1,1},
                {1,1}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + App.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_NoLivingCells_EmptyArrayShouldBeReturned() {
        int[][] input = {{}};
        int[][] expected = {{}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Empty array expected" + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output));
    }

    @Test
    public void lifeEmulation_TestTwoGliders() {
        int[][] input = {
                {1, 1, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 1, 1, 1}};
        int[][] expected = {
                {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1}
        };

        int[][] output = App.getGeneration(input, 16);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid response" + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + App.htmlize(output) + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + App.htmlize(expected));
    }
}
