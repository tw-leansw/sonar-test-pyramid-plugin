package com.thoughtworks.lean.sonar.testpyramid.analysis;

import ch.lambdaj.function.convert.Converter;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import com.thoughtworks.lean.sonar.testpyramid.util.JUnitUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.lambdaj.collection.LambdaCollections.with;

public class JUnitAnalyzer {
    Logger logger = LoggerFactory.getLogger(getClass());

    private String reportPath;
    private FileSystem fileSystem;
    private String[] integrationTestPatterns;
    private String[] functionalTestPatterns;
    private String[] excludePatterns;


    public JUnitAnalyzer(String integrationTestPatterns, String functionalTestPatterns,
                         String excludePatterns) {

        this.functionalTestPatterns = functionalTestPatterns.split(",");
        this.excludePatterns = excludePatterns.split(",");
        this.integrationTestPatterns = integrationTestPatterns.split(",");
    }


    public JUnitAnalyzer(Settings settings, FileSystem system) {
        this.fileSystem = system;
        this.reportPath = settings.getString("lean.testpyramid.junit.report.path");
        this.excludePatterns = settings.getStringArray("lean.testpyramid.junit.exclude.test.patterns");
        this.integrationTestPatterns = settings.getStringArray("lean.testpyramid.junit.integration.test.patterns");
        this.functionalTestPatterns = settings.getStringArray("lean.testpyramid.junit.functional.test.patterns");

    }


    public boolean checkPatterns(final String str, String[] patterns) {
        List<Boolean> bools = with(patterns).convert(new Converter<String, Boolean>() {
            @Override
            public Boolean convert(String pattern) {
                Pattern regPattern = Pattern.compile(pattern);
                Matcher matcher = regPattern.matcher(str);
                return matcher.matches();
            }
        });
        for (Boolean bool : bools) {
            if (bool) {
                return true;
            }
        }
        return false;

    }


    public boolean isExcluded(String testCase) {
        return checkPatterns(testCase, excludePatterns);
    }

    public boolean isIntegrationTest(String testCase) {
        return checkPatterns(testCase, integrationTestPatterns);
    }

    public boolean isFunctionalTest(String testCase) {
        return checkPatterns(testCase, functionalTestPatterns);
    }

    public void analyse(TestsCounter testsCounter) {
        logger.info("start junit test pyramid analyse");
        analyse(testsCounter, fileSystem.resolvePath(reportPath));
    }

    public void analyse(TestsCounter testsCounter, File dir) {
        if (dir.exists() && dir.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(dir, new String[]{"xml"}, false);
            for (File file : files) {
                String testCase = JUnitUtil.getTestCaseName(file.getName());
                int scenarioCount = 0;
                try {
                    Document doc = Jsoup.parse(file, "UTF-8");
                    scenarioCount = doc.select("testcase").size();
                } catch (IOException e) {
                    logger.warn("read junit report file error!");
                }
                if (isExcluded(testCase)) {
                    continue;
                } else if (isFunctionalTest(testCase)) {
                    testsCounter.incrementTestsFor(TestType.FUNCTIONAL_TEST, scenarioCount);
                } else if (isIntegrationTest(testCase)) {
                    testsCounter.incrementTestsFor(TestType.INTEGRATION_TEST, scenarioCount);
                } else {
                    testsCounter.incrementTestsFor(TestType.UNIT_TEST, scenarioCount);
                }
            }

        } else {
            logger.warn("junit report directory is not exsits!");
        }


    }

}
