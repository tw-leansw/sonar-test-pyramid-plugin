package com.thoughtworks.lean.sonar.testpyramid;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;

import java.util.UUID;

public class SonarTestPyramidWidgetV2 extends AbstractRubyTemplate implements RubyRailsWidget {

    public String getId() {
        return "lean_test_pyramidv2";
    }

    public String getTitle() {
        return "Lean Test Pyramid V2";
    }

    @Override
    protected String getTemplatePath() {

        return "/com/thoughtworks/lean/sonar/testpyramid/test-pyramid-widget-v2.html.erb";
    }
}
