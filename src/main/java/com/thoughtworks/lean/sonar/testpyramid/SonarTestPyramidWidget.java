package com.thoughtworks.lean.sonar.testpyramid;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;

public class SonarTestPyramidWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    public String getId() {
        return "lean_test_pyramid";
    }

    public String getTitle() {
        return "Lean Test Pyramid";
    }

    @Override
    protected String getTemplatePath() {
        return "/com/thoughtworks/lean/sonar/testpyramid/test-pyramid-widget.html.erb";
    }
}
