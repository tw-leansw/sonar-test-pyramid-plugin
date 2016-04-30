package com.thoughtworks.lean.sonar.testpyramid;

import com.thoughtworks.lean.sonar.testpyramid.analysis.TestPyramidAnalysis;
import com.thoughtworks.lean.sonar.testpyramid.analysis.JUnitAnalyzer;
import com.thoughtworks.lean.sonar.testpyramid.analysis.TestPyramidAnyalyzer;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import java.io.File;
import java.util.List;

@Properties({
        @Property(key = "sonar.leansw.testpyramid.junit.report.path", name = "JUnit Report path", defaultValue = "target/surefire-reports/"),
        @Property(key = "sonar.leansw.testpyramid.junit.test.exclude.pattern", name = "JUnit Test Exclude path", defaultValue = "target/surefire-reports/"),
        @Property(key = "sonar.leansw.testpyramid.junit.integration.test.pattern", name = "JUnit Integration/API TestCase pattern", defaultValue = "^IT**$,^API_**$"),
        @Property(key = "sonar.leansw.testpyramid.junit.ui.test.pattern", name = "JUnit Functional/UI TestCase pattern", defaultValue = "^FT**$,^UI_**$"),
        //
        @Property(key = "sonar.leansw.testpyramid.cucumber.report.path", name = "Cucumber Report path (report.json)", defaultValue = "target/cucumber/"),
        @Property(key = "sonar.leansw.testpyramid.cucumber.integration.test.tags", name = "Cucumber Integration test tags", defaultValue = "api_test,integration_test"),
        @Property(key = "sonar.leansw.testpyramid.cucumber.functional.test.tags", name = "Cucumber Functional/UI test tags", defaultValue = "functional_test,ui_test")
}
)
public class SonarTestPyramidSensor implements Sensor {

    private Settings settings;

    public SonarTestPyramidSensor(Settings settings) {
        this.settings = settings;
    }

    public void analyse(Project project, SensorContext sensorContext) {
        List<File> testDirs = project.getFileSystem().getTestDirs();
        TestPyramidAnalysis analysis = createAnalyzer().analyse();

        sensorContext.saveMeasure(analysis.numberOfUnitTests());
        sensorContext.saveMeasure(analysis.numberOfIntegrationTests());
        sensorContext.saveMeasure(analysis.numberOfFunctionalTests());
    }

    private TestPyramidAnyalyzer createAnalyzer() {
        return new TestPyramidAnyalyzer(settings);
    }

    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }

}
