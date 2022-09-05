package com.lapsho.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;


public class AppTest 
{
    @Test
    public void lifeEmulation_OnValidInput3x3Gen1_ShouldPass()
    {
        int[][] input = {
                {1,0,0},
                {0,1,1},
                {1,1,0}};
        int[][] expected = {
                {0,1,0},
                {0,0,1},
                {1,1,1}};
        int[][] output = App.getGeneration(input, 1);

        assertTrue(Arrays.deepEquals(expected, output), "Invalid emulation result");
    }
}
