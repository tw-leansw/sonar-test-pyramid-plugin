package com.thoughtworks.lean.sonar.testpyramid;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public class SonarTestPyramidMetrics implements Metrics {

    public final static Metric UNIT_TESTS_METRIC = new Metric.Builder(
            "lean.testpyramid.unittests",
            "Number Of Unit Tests",
            Metric.ValueType.INT)
            .setDomain(CoreMetrics.DOMAIN_TESTS)
            .setQualitative(true)
            .create();
    public final static Metric INTEGRATION_TESTS_METRIC = new Metric.Builder(
            "lean.testpyramid.integrationtests",
            "Number Of Integration/API Tests",
            Metric.ValueType.INT)
            .setDomain(CoreMetrics.DOMAIN_TESTS)
            .setQualitative(true)
            .create();
    public static final Metric FUNCTIONAL_TESTS_METRIC = new Metric.Builder(
            "lean.testpyramid.functionaltests",
            "Number Of Functional/UI Tests",
            Metric.ValueType.INT)
            .setDomain(CoreMetrics.DOMAIN_TESTS)
            .setQualitative(true)
            .create();

    public List<Metric> getMetrics() {
        return Arrays.asList(UNIT_TESTS_METRIC, INTEGRATION_TESTS_METRIC, FUNCTIONAL_TESTS_METRIC);
    }
}