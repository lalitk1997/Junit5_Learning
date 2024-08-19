package com.healthycoderapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    void shouldCalculateBMI() {
        assertTrue(BMICalculator.isDietRecommended(10, 10));
    }

}