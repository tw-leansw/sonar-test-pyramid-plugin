package com.thoughtworks.lean.sonar.testpyramid.analysis;

import com.thoughtworks.lean.sonar.testpyramid.SonarTestPyramidMetrics;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import org.sonar.api.measures.Measure;

public class TestPyramidAnalysis {

    private Measure measureForUnitTests;
    private Measure measureForIntegrationTests;
    private Measure measureForFunctionalTests;

    public TestPyramidAnalysis(TestsCounter testsCounter) {
        measureForUnitTests = new Measure(SonarTestPyramidMetrics.UNIT_TESTS_METRIC,
                testsCounter.getNumberOfTests(TestType.UNIT_TEST));
        measureForIntegrationTests = new Measure(SonarTestPyramidMetrics.INTEGRATION_TESTS_METRIC,
                testsCounter.getNumberOfTests(TestType.INTEGRATION_TEST));
        measureForFunctionalTests = new Measure(SonarTestPyramidMetrics.FUNCTIONAL_TESTS_METRIC,
                testsCounter.getNumberOfTests(TestType.FUNCTIONAL_TEST));
    }

    public Measure numberOfUnitTests() {
        return measureForUnitTests;
    }

    public Measure numberOfIntegrationTests() {
        return measureForIntegrationTests;
    }

    public Measure numberOfFunctionalTests() {
        return measureForFunctionalTests;
    }

}
