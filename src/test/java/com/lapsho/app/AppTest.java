package com.lapsho.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.*;


public class AppTest 
{
    private LifeEmulator emulator = new LifeEmulator();

    @Test
    public void lifeEmulation_Input_ShouldNotBeMutated() {
        int[][] inputOrigin = {
                {0,1},
                {1,1}};
        int[][] input = {
                {0,1},
                {1,1}};
        emulator.getGeneration(input, 1);

        assertThat(inputOrigin).as("The method should not mutate the input").isDeepEqualTo(input);
    }
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
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation result" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + LifeEmulator.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
    }

    @Test
    public void lifeEmulation_OneCell_ShouldDie() {
        int[][] input = {{1}};
        int[][] expected = {{}};
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "The output should be empty" + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
    }

    @Test
    public void lifeEmulation_ThreeCells_ShouldProduceExtraOne() {
        int[][] input = {
                {0,1},
                {1,1}};
        int[][] expected = {
                {1,1},
                {1,1}};
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + LifeEmulator.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
    }

    @Test
    public void lifeEmulation_CellExpansion_ShouldExtendArrayVertical() {
        int[][] input = {{1,1,1}};
        int[][] expected = {
                {1},
                {1},
                {1}};
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + LifeEmulator.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
    }

    @Test
    public void lifeEmulation_CellExpansion_ShouldExtendArrayHorizontal() {
        int[][] input = {
                {1},
                {1},
                {1}};
        int[][] expected = {{1,1,1}};
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + LifeEmulator.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
    }

    @Test
    public void lifeEmulation_DeadCells_ShouldBeCropped() {
        int[][] input = {
                {1,1,0},
                {1,1,0}};
        int[][] expected = {
                {1,1},
                {1,1}};
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid emulation" + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + LifeEmulator.htmlize(expected) + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
    }

    @Test
    public void lifeEmulation_NoLivingCells_EmptyArrayShouldBeReturned() {
        int[][] input = {{}};
        int[][] expected = {{}};
        int[][] output = emulator.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output),
                "Empty array expected" + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output));
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

        int[][] output = emulator.getGeneration(input, 16);

        assertTrue(Arrays.deepEquals(expected, output),
                "Invalid response" + System.lineSeparator() +
                        "Provided: " + System.lineSeparator() + LifeEmulator.htmlize(output) + System.lineSeparator() +
                        "Expected: " + System.lineSeparator() + LifeEmulator.htmlize(expected));
    }
}
