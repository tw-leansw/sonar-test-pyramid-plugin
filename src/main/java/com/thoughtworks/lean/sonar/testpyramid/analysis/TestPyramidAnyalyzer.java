package com.thoughtworks.lean.sonar.testpyramid.analysis;

import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import org.sonar.api.config.Settings;

public class TestPyramidAnyalyzer {

    JUnitAnalyzer jUnitAnalyzer;
    CucumberAnalyzer cucumberAnalyzer;
    TestsCounter testsCounter = new TestsCounter();

    public TestPyramidAnyalyzer(Settings settings) {
        jUnitAnalyzer = new JUnitAnalyzer(settings);
        cucumberAnalyzer = new CucumberAnalyzer(settings);
    }

    public TestPyramidAnalysis analyse() {
        jUnitAnalyzer.analyse(testsCounter);
        cucumberAnalyzer.analyse(testsCounter);
        return new TestPyramidAnalysis(testsCounter);
    }


}
