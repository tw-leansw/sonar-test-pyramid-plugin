package com.thoughtworks.lean.sonar.testpyramid.analysis;

import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;

public class TestPyramidAnyalyzer {

    JUnitAnalyzer jUnitAnalyzer;
    CucumberAnalyzer cucumberAnalyzer;
    GaugeAnalyzer gaugeAnalyzer;
    TestsCounter testsCounter = new TestsCounter();

    public TestPyramidAnyalyzer(Settings settings, FileSystem projectFileSystem) {
        jUnitAnalyzer = new JUnitAnalyzer(settings, projectFileSystem);
        gaugeAnalyzer = new GaugeAnalyzer(settings, projectFileSystem);
        cucumberAnalyzer = new CucumberAnalyzer(settings, projectFileSystem);
    }

    public TestPyramidAnalysis analyse() {
        jUnitAnalyzer.analyse(testsCounter);
        cucumberAnalyzer.analyse(testsCounter);
        gaugeAnalyzer.analyse(testsCounter);
        return new TestPyramidAnalysis(testsCounter);
    }


}
