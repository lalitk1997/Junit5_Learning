package com.healthycoderapp;

import jdk.jfr.Name;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    String simulateEnvironment = "dev";

    @BeforeAll
    public static void beforeAllTestCases(){
        System.out.println("Before All TestCases");
    }

    @AfterAll
    public static void afterAllTestCases(){
        System.out.println("After All TestCases");
    }

    @Nested
    class IsDietRecommended{

        @Test
        void shouldReturnTrueWhenDietRecommended () {
            // Given
            Assumptions.assumeTrue(BMICalculatorTest.this.simulateEnvironment.equals("prod"));
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

        @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
        void shouldReturnFalseWhenNotDietRecommendedRepeat() {
            // Given
            double weight = 50.0;
            double height = 1.92;
            // When
            boolean result = BMICalculator.isDietRecommended(weight, height);
            // Then
            assertFalse(result);
        }

        // Parameterized
        @ParameterizedTest
        @ValueSource(doubles = {70.0, 89.9, 90.2, 99.9})
        void shouldReturnFalseWhenNotRecommendedParameter(double coderWeight) {
            // Given
            double weight = coderWeight;
            double height = 1.72;
            // When
            boolean result = BMICalculator.isDietRecommended(weight, height);
            // Then
            assertFalse(result);

        }

    }








    @ParameterizedTest(name = "weight{0}, height{1}")
    @CsvSource({
            "90.0, 1.72",
            "80.0, 1.42",
            "99.8, 1.12"
    })
    void shouldReturnTrueWhenRecommendedParameter(double weight, double height) {
        // Given
        // When
        boolean result = BMICalculator.isDietRecommended(weight, height);
        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "weight{0}, height{1}")
    @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
    void shouldReturnTrueWhenRecommendedParameterFile(double weight, double height) {
        // Given
        // When
        boolean result = BMICalculator.isDietRecommended(weight, height);
        // Then
        assertTrue(result);
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
    @DisabledOnOs(OS.MAC)
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

    @Test
    @DisplayName("Customized Name")
    @Disabled
    void shouldReturnCodersWithWorstBMIInMillisecond(){
        // Given
        List<Coder> coders = new ArrayList<>();
        for(int i=0; i<10000; i++)
                coders.add(new Coder(1.0 + i, 10.0 + i));
        // When
        Executable executable = () -> {BMICalculator.findCoderWithWorstBMI(coders);};
        // Then
        assertTimeout(Duration.ofMillis(3), executable);
    }

}