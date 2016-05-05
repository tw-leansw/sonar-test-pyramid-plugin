package com.thoughtworks.lean.sonar.testpyamid.analysis;

import com.thoughtworks.lean.sonar.testpyramid.analysis.JUnitAnalyzer;
import com.thoughtworks.lean.sonar.testpyramid.model.TestType;
import com.thoughtworks.lean.sonar.testpyramid.model.TestsCounter;
import com.thoughtworks.lean.sonar.testpyramid.util.JUnitUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public class JUnitAnalyzerTest {
    @Test
    public void should_analyze_junit_report_success(){

        File reportDir=FileUtils.getFile(getClass().getResource("/surefire-reports").getFile());
        JUnitAnalyzer jUnitAnalyzer=new JUnitAnalyzer("^IT.*$,^API_.*$","^FT.*$,^UI_*$","");
        TestsCounter testsCounter=new TestsCounter();
        jUnitAnalyzer.analyse(testsCounter,reportDir);

        // then
        assertEquals(18.0,testsCounter.getNumberOfTests(TestType.UNIT_TEST));
        assertEquals(4.0,testsCounter.getNumberOfTests(TestType.INTEGRATION_TEST));
        assertEquals(2.0,testsCounter.getNumberOfTests(TestType.FUNCTIONAL_TEST));
    }

    @Test
    public void should_get_testCase_name(){
        String testCaseName= JUnitUtil.getTestCaseName("TEST-org.thoughtworks.lean.pyramid.UnitTest1Test.xml");
        assertEquals("UnitTest1Test",testCaseName);
    }
}
