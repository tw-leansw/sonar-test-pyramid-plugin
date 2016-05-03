package com.thoughtworks.lean.sonar.testpyramid;

import com.thoughtworks.lean.sonar.testpyramid.analysis.TestPyramidAnalysis;
import com.thoughtworks.lean.sonar.testpyramid.analysis.TestPyramidAnyalyzer;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import java.io.File;
import java.util.List;


public class SonarTestPyramidSensor implements Sensor {

    private Settings settings;
    private FileSystem fileSystem;


    public SonarTestPyramidSensor(FileSystem fileSystem, Project project, Settings settings) {
        this.fileSystem = fileSystem;
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
        return new TestPyramidAnyalyzer(settings, fileSystem);
    }

    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }

}
