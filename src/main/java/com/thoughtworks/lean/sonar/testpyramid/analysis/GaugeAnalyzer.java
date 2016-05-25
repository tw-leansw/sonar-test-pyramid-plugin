package com.thoughtworks.lean.sonar.testpyramid.analysis;

import com.google.common.collect.Sets;
import com.thoughtworks.lean.sonar.testpyramid.Constants;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import com.thoughtworks.lean.sonar.testpyramid.util.JXPathMap;
import com.thoughtworks.lean.sonar.testpyramid.util.ScriptUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ch.lambdaj.collection.LambdaCollections.with;


public class GaugeAnalyzer {
    Logger logger = LoggerFactory.getLogger(getClass());
    String reportPath;
    private Set<String> integrationTestTags;
    private Set<String> functionalTestTags;
    FileSystem fileSystem;

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public GaugeAnalyzer setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    public GaugeAnalyzer(Set<String> integrationTestTags, Set<String> functionalTestTags) {
        this.integrationTestTags = integrationTestTags;
        this.functionalTestTags = functionalTestTags;
    }

    public GaugeAnalyzer(Settings settings, FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.reportPath = settings.getString(Constants.LEAN_TESTPYRAMID_GAUGE_REPORT_PATH);
        this.integrationTestTags = Sets.newHashSet(settings.getStringArray(Constants.LEAN_TESTPYRAMID_GAUGE_INTEGRATION_TEST_TAGS));
        this.functionalTestTags = Sets.newHashSet(settings.getStringArray(Constants.LEAN_TESTPYRAMID_GAUGE_FUNCTIONAL_TEST_TAGS));
    }

    public void analyse(TestsCounter testsCounter) {
        try {
            logger.debug("start gauge test pyramid analyse");
            String reportString = IOUtils.toString(new FileInputStream(fileSystem.resolvePath(reportPath + "html-report/js/result.js")));
            analyse(ScriptUtil.eval(reportString), testsCounter);
        } catch (IOException e) {
            logger.warn("cant read gauge report!");
        }
    }



    public void analyse(JXPathMap jxPathMap, TestsCounter testCounter) {
        List<Map> specResults = jxPathMap.get("/gaugeExecutionResult/suiteResult/specResults");
        List<JXPathMap> wrapedSpecResults = with(specResults).convert(JXPathMap.toJxPathFunction);
        for (JXPathMap spec : wrapedSpecResults) {
            Set<String> tags = spec.getStringSet("protoSpec/tags");
            double scenarioCount = Double.parseDouble(spec.get("scenarioCount").toString());
            TestType testType = TestType.UNIT_TEST;
            if (Sets.intersection(tags, functionalTestTags).size() > 0) {
                testType = TestType.FUNCTIONAL_TEST;
            } else if (Sets.intersection(tags, integrationTestTags).size() > 0) {
                testType = TestType.INTEGRATION_TEST;
            }
            String specName = spec.get("protoSpec/specHeading");
            testCounter.incrementTestsFor(testType, scenarioCount);
            logger.debug(String.format("find gauge test spec:%s scenarioCount:%.0f type:%s", specName, scenarioCount, testType.name()));
        }
    }

}
