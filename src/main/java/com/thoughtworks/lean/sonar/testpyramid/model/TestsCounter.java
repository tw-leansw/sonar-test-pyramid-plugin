package com.thoughtworks.lean.sonar.testpyramid.model;

import java.util.HashMap;
import java.util.Map;

public class TestsCounter {
    private Map<TestType, TestCounter> testCounterMap;

    public TestsCounter(TestCounter... testCounters) {
        this.testCounterMap = new HashMap<TestType, TestCounter>();
        for (TestCounter testCounter : testCounters) {
            testCounterMap.put(testCounter.getTestType(), testCounter);
        }
    }

    public void incrementTestsFor(TestType testType) {
        if (testCounterMap.get(testType) == null) {
            testCounterMap.put(testType, new TestCounter(testType));
        }
        testCounterMap.get(testType).increment();
    }

    public double getNumberOfTests(TestType testType) {
        if (testCounterMap.get(testType) != null) {
            return testCounterMap.get(testType).getNumberOfTests();
        }
        return 0;
    }
}
