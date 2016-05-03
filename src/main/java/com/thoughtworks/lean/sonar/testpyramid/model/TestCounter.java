package com.thoughtworks.lean.sonar.testpyramid.model;

public class TestCounter {
    private double numberOfTests;
    private TestType testType;

    public TestCounter(TestType testType) {
        this.testType = testType;
        this.numberOfTests = 0.0;
    }

    public double getNumberOfTests() {
        return numberOfTests;
    }

    public void increment() {
        this.numberOfTests++;
    }

    public void increment(double n) {
        this.numberOfTests += n;
    }

    public TestType getTestType() {
        return testType;
    }
}
