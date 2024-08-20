package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @BeforeAll
    public static void beforeAllTestCases(){
        System.out.println("Before All TestCases");
    }

    @AfterAll
    public static void afterAllTestCases(){
        System.out.println("After All TestCases");
    }

    @Test
    void shouldReturnTrueWhenDietRecommended () {
        // Given
        double weight = 89.0;
        double height = 1.72;
        // When
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        // Then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalseWhenNotDietRecommended() {
        // Given
        double weight = 50.0;
        double height = 1.92;
        // When
        boolean result = BMICalculator.isDietRecommended(weight, height);
        // Then
        assertFalse(result);
    }

    @Test
    void shouldThrowsArithmeticException(){
        // Given
        double weight = 50.0;
        double height = 0.0;
        // When
        Executable executable = () -> {BMICalculator.isDietRecommended(weight, height);};
        // Then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void shouldReturnTheCoderWithWorstBMI(){
        // Given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        // When
        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        // Then
        assertAll(
                () -> assertEquals(1.82, coderWithWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWithWorstBMI.getWeight())
        );
    }

    @Test
    void shouldReturnNullWorstBMICoder(){
        // Given
        ArrayList<Coder> coders = new ArrayList<>();
        // When
        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        // Then
        assertNull(coderWithWorstBMI);
    }

    @Test
    void shouldReturnCorrectBMIScore(){
        // Given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        double[] expectedScore = {18.52, 29.59, 19.5};
        // When
        double[] bmiScores = BMICalculator.getBMIScores(coders);
        // Then
        assertArrayEquals(bmiScores, expectedScore, 0.04);
    }

}