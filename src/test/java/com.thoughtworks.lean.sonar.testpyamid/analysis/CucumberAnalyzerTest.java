package com.thoughtworks.lean.sonar.testpyamid.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.thoughtworks.lean.sonar.testpyramid.analysis.CucumberAnalyzer;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import com.thoughtworks.lean.sonar.testpyramid.util.JXPathMap;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;


public class CucumberAnalyzerTest {

    @Test
    public void should_return_correct_test_pyramid() throws IOException {

        // given
        JXPathMap ctx = new JXPathMap(new ObjectMapper().readValue(getClass().getResourceAsStream("/cucumber_report.json"), Object.class));
        TestsCounter testsCounter=new TestsCounter();
        CucumberAnalyzer cucumberAnalyzer=new CucumberAnalyzer(Sets.newHashSet("@api_test"),Sets.newHashSet("@ui_test"));

        // when

        cucumberAnalyzer.analyse(ctx,testsCounter);
        // then
        assertEquals(13.0,testsCounter.getNumberOfTests(TestType.UNIT_TEST));
        assertEquals(7.0,testsCounter.getNumberOfTests(TestType.INTEGRATION_TEST));
        assertEquals(3.0,testsCounter.getNumberOfTests(TestType.FUNCTIONAL_TEST));



    }


    @Test
    public void should_return_correct_test_pyramid_2() throws IOException {
        //ui-test的真实数据
        // given
        JXPathMap ctx = new JXPathMap(new ObjectMapper().readValue(getClass().getResourceAsStream("/cucumber_report_2.json"), Object.class));
        TestsCounter testsCounter=new TestsCounter();
        CucumberAnalyzer cucumberAnalyzer=new CucumberAnalyzer(Sets.newHashSet("@api_test"),Sets.newHashSet("@ui_test"));

        // when

        cucumberAnalyzer.analyse(ctx,testsCounter);
        // then
        assertEquals(0.0,testsCounter.getNumberOfTests(TestType.UNIT_TEST));
        assertEquals(0.0,testsCounter.getNumberOfTests(TestType.INTEGRATION_TEST));
        assertEquals(3.0,testsCounter.getNumberOfTests(TestType.FUNCTIONAL_TEST));



    }

}
