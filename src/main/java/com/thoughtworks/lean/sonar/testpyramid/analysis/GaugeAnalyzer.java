package com.thoughtworks.lean.sonar.testpyramid.analysis;

import ch.lambdaj.function.convert.Converter;
import com.google.common.base.Function;
import com.google.common.collect.Sets;
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

    public GaugeAnalyzer(Set<String> integrationTestTags, Set<String> functionalTestTags) {
        this.integrationTestTags = integrationTestTags;
        this.functionalTestTags = functionalTestTags;
    }

    public GaugeAnalyzer(Settings settings, FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.reportPath = settings.getString("sonar.leansw.testpyramid.gauge.report.path");
        this.integrationTestTags = Sets.newHashSet(settings.getStringArray("sonar.leansw.testpyramid.gauge.integration.test.tags"));
        this.functionalTestTags = Sets.newHashSet(settings.getStringArray("sonar.leansw.testpyramid.gauge.functional.test.tags"));
    }

    public void analyse(TestsCounter testsCounter) {
        try {
            String reportString = IOUtils.toString(new FileInputStream(fileSystem.resolvePath(reportPath + "html-report/js/result.js")));
            analyse(ScriptUtil.eval(reportString), testsCounter);
        } catch (IOException e) {
            logger.warn("cant read gauge report!");
        }
    }

    public void analyse(JXPathMap jxPathMap, TestsCounter testCounter) {
        List<Map> specResults = jxPathMap.get("/gaugeExecutionResult/suiteResult/specResults");
        List<JXPathMap> wrapedSpecResults = with(specResults).convert(toJxPathFunction);
        for (JXPathMap spec : wrapedSpecResults) {
            Set<String> tags = Sets.newHashSet((List<String>) spec.get("protoSpec/tags"));
            double scenarioCount = spec.get("scenarioCount");
            TestType testType = TestType.UNIT_TEST;
            if (Sets.intersection(tags, functionalTestTags).size() > 0) {
                testType = TestType.FUNCTIONAL_TEST;
            } else if (Sets.intersection(tags, integrationTestTags).size() > 0) {
                testType = TestType.INTEGRATION_TEST;
            }
            String specName = spec.get("protoSpec/specHeading");
            testCounter.incrementTestsFor(testType, (int) scenarioCount);
            logger.debug(String.format("find test spec:%s scenarioCount:%.0f type:%s", specName, scenarioCount, testType.name()));
        }
    }

    private Converter<Map,JXPathMap> toJxPathFunction = new Converter<Map, JXPathMap>() {
        @Override
        public JXPathMap convert(Map map) {
            return new JXPathMap(map);
        }

    };
}
