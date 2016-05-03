package com.thoughtworks.lean.sonar.testpyamid.analysis;

import com.google.common.collect.Sets;
import com.thoughtworks.lean.sonar.testpyramid.analysis.GaugeAnalyzer;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import com.thoughtworks.lean.sonar.testpyramid.util.JXPathMap;
import com.thoughtworks.lean.sonar.testpyramid.util.ScriptUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;


public class GaugeAnalyzerTest {
    @Test
    public void should_return_1_functional_test() throws IOException {

        // given
        // given
        String jsString = IOUtils.toString(getClass().getResourceAsStream("/gauge_report.js"));
        JXPathMap ctx = ScriptUtil.eval(jsString);
        TestsCounter testsCounter=new TestsCounter();
        GaugeAnalyzer gaugeAnalyzer=new GaugeAnalyzer(Sets.newHashSet("api_test"),Sets.newHashSet("ui_test"));

        // when

        gaugeAnalyzer.analyse(ctx,testsCounter);
        // then
        assertEquals(1.0,testsCounter.getNumberOfTests(TestType.FUNCTIONAL_TEST));




    }
}
