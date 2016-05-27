package com.thoughtworks.lean.sonar.testpyramid.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.thoughtworks.lean.sonar.testpyramid.Constants;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import com.thoughtworks.lean.sonar.testpyramid.util.JXPathMap;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.collection.LambdaCollections.with;

public class CucumberAnalyzer {
    Logger logger = LoggerFactory.getLogger(getClass());
    String reportPath;
    private Set<String> integrationTestTags;
    private Set<String> functionalTestTags;
    FileSystem fileSystem;
    private boolean skip = false;


    public CucumberAnalyzer(Set<String> integrationTestTags, Set<String> functionalTestTags) {
        this.integrationTestTags = integrationTestTags;
        this.functionalTestTags = functionalTestTags;
    }

    public CucumberAnalyzer(Settings settings, FileSystem fs) {
        this.fileSystem = fs;
        this.reportPath = settings.getString(Constants.LEAN_TESTPYRAMID_CUCUMBER_REPORT_PATH);
        this.integrationTestTags = Sets.newHashSet(settings.getStringArray(Constants.LEAN_TESTPYRAMID_CUCUMBER_INTEGRATION_TEST_TAGS));
        this.functionalTestTags = Sets.newHashSet(settings.getStringArray(Constants.LEAN_TESTPYRAMID_CUCUMBER_FUNCTIONAL_TEST_TAGS));
        this.skip = settings.getBoolean(Constants.LEAN_TESTPYRAMID_CUCUMBER_SKIP);
    }

    public void analyse(TestsCounter testsCounter) {
        if (skip) {
            logger.info("test pyramid cucumber report analysis skipped!");
            return;
        }
        try {
            logger.info("start cucumber test pyramid analyse");
            analyse(new JXPathMap(new ObjectMapper().readValue(fileSystem.resolvePath(reportPath), Object.class)), testsCounter);
        } catch (IOException e) {
            logger.warn("cant read cucumber report! reportPath:" + reportPath);
        }
    }

    public void analyse(JXPathMap jxPathMap, TestsCounter testsCounter) {
        List<Map> features = jxPathMap.get("/");
        List<JXPathMap> wrapedFeatures = with(features).convert(JXPathMap.toJxPathFunction);
        for (JXPathMap feature : wrapedFeatures) {
            List<Map> tags = feature.get("tags");
            Set<String> tagNames = toTagnames(tags);
            List<Map> scenarios = feature.get("elements");
            List<String> wrappedScenarios = with(scenarios).convert(JXPathMap.toJxPathFunction).extract(on(JXPathMap.class).getString("type")).retain(Matchers.equalTo("scenario"));

            double scenarioCount = wrappedScenarios.size();
            TestType testType = TestType.UNIT_TEST;
            if (Sets.intersection(tagNames, functionalTestTags).size() > 0) {
                testType = TestType.FUNCTIONAL_TEST;
            } else if (Sets.intersection(tagNames, integrationTestTags).size() > 0) {
                testType = TestType.INTEGRATION_TEST;
            }
            String featureName = feature.getString("name");
            testsCounter.incrementTestsFor(testType, scenarioCount);
            logger.debug(String.format("find cucumber test feature:%s scenarioCount:%.0f type:%s", featureName, scenarioCount, testType.name()));
        }

    }

    public Set<String> toTagnames(List<Map> tags) {
        List<JXPathMap> wrapedTags = with(tags).convert(JXPathMap.toJxPathFunction);
        return new HashSet<>(with(wrapedTags).extract(on(JXPathMap.class).getString("name")));
    }

}
