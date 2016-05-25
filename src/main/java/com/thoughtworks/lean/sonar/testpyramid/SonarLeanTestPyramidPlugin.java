package com.thoughtworks.lean.sonar.testpyramid;

import com.google.common.collect.ImmutableList;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;
import static com.thoughtworks.lean.sonar.testpyramid.Constants.*;
import java.util.List;


@Properties({
        @Property(key = LEAN_TESTPYRAMID_JUNIT_REPORT_PATH, name = "JUnit Report path", defaultValue = "target/surefire-reports/"),
        @Property(key = LEAN_TESTPYRAMID_JUNIT_EXCLUDE_TEST_PATTERNS, name = "JUnit Test Exclude (regex)patterns"),
        @Property(key = LEAN_TESTPYRAMID_JUNIT_INTEGRATION_TEST_PATTERNS, name = "JUnit Integration/Component/API TestCase (regex)patterns", defaultValue = "^IT.*$,^API_.*$,^CT.*$"),
        @Property(key = LEAN_TESTPYRAMID_JUNIT_FUNCTIONAL_TEST_PATTERNS, name = "JUnit Functional/UI TestCase (regex)patterns", defaultValue = "^FT.*$,^UI_.*$"),
        //
        @Property(key = LEAN_TESTPYRAMID_CUCUMBER_REPORT_PATH, name = "Cucumber Report path (json)", defaultValue = "target/cucumber.json"),
        @Property(key = LEAN_TESTPYRAMID_CUCUMBER_INTEGRATION_TEST_TAGS, name = "Cucumber Integration/API/Component test tags(@)", defaultValue = "@api_test,@integration_test,@component_test"),
        @Property(key = LEAN_TESTPYRAMID_CUCUMBER_FUNCTIONAL_TEST_TAGS, name = "Cucumber Functional/UI test tags(@)", defaultValue = "@functional_test,@ui_test"),
        //
        @Property(key = LEAN_TESTPYRAMID_GAUGE_REPORT_PATH, name = "Gauge Report path (read result.js)", defaultValue = "reports/"),
        @Property(key = LEAN_TESTPYRAMID_GAUGE_INTEGRATION_TEST_TAGS, name = "Gauge Integration/API/Component test tags", defaultValue = "api_test,integration_test,component_test"),
        @Property(key = LEAN_TESTPYRAMID_GAUGE_FUNCTIONAL_TEST_TAGS, name = "Gauge Functional/UI test tags", defaultValue = "functional_test,ui_test")

}
)
public class SonarLeanTestPyramidPlugin extends SonarPlugin {

    public List getExtensions() {
        return ImmutableList.of(
                SonarTestPyramidSensor.class,
                SonarTestPyramidMetrics.class,
                SonarTestPyramidWidget.class,
                SonarTestPyramidWidgetV2.class
        );
    }
}
