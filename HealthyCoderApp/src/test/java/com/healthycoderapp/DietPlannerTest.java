package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DietPlannerTest {

    private DietPlanner dietPlanner;

    @BeforeEach
    void setUp() {
        dietPlanner = new DietPlanner(20, 30, 50);
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----Test Case Completed-----");
    }

    @Test
    void shouldCreateDietPlanner() {
        // Given
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan dietPlan = new DietPlan(2202, 110, 73, 275);
        // When
        DietPlan actualDietPlan = dietPlanner.calculateDiet(coder);
        // Then
        assertAll(
                () -> assertEquals(dietPlan.getCalories(), actualDietPlan.getCalories()),
                () -> assertEquals(dietPlan.getCarbohydrate(), actualDietPlan.getCarbohydrate()),
                () -> assertEquals(dietPlan.getFat(), actualDietPlan.getFat()),
                () -> assertEquals(dietPlan.getProtein(), actualDietPlan.getProtein())
        );
    }
}
