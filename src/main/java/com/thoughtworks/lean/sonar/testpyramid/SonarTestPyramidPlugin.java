package com.thoughtworks.lean.sonar.testpyramid;

import com.google.common.collect.ImmutableList;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.List;


@Properties({
        @Property(key = "sonar.leansw.testpyramid.junit.report.path", name = "JUnit Report path", defaultValue = "target/surefire-reports/"),
        @Property(key = "sonar.leansw.testpyramid.junit.test.exclude.pattern", name = "JUnit Test Exclude path", defaultValue = "target/surefire-reports/"),
        @Property(key = "sonar.leansw.testpyramid.junit.integration.test.pattern", name = "JUnit Integration/API TestCase pattern", defaultValue = "^IT**$,^API_**$"),
        @Property(key = "sonar.leansw.testpyramid.junit.ui.test.pattern", name = "JUnit Functional/UI TestCase pattern", defaultValue = "^FT**$,^UI_**$"),
        //
        @Property(key = "sonar.leansw.testpyramid.cucumber.report.path", name = "Cucumber Report path (report.json)", defaultValue = "target/cucumber/"),
        @Property(key = "sonar.leansw.testpyramid.cucumber.integration.test.tags", name = "Cucumber Integration test tags", defaultValue = "api_test,integration_test"),
        @Property(key = "sonar.leansw.testpyramid.cucumber.functional.test.tags", name = "Cucumber Functional/UI test tags", defaultValue = "functional_test,ui_test"),
        //
        @Property(key = "sonar.leansw.testpyramid.gauge.report.path", name = "Gauge Report path (report.json)", defaultValue = "reports/"),
        @Property(key = "sonar.leansw.testpyramid.gauge.integration.test.tags", name = "Gauge Integration test tags", defaultValue = "api_test,integration_test"),
        @Property(key = "sonar.leansw.testpyramid.gauge.functional.test.tags", name = "Gauge Functional/UI test tags", defaultValue = "functional_test,ui_test")

}
)
public class SonarTestPyramidPlugin extends SonarPlugin {

    public List getExtensions() {
        return ImmutableList.of(
                SonarTestPyramidSensor.class,
                SonarTestPyramidMetrics.class,
                SonarTestPyramidWidget.class
        );
    }
}
