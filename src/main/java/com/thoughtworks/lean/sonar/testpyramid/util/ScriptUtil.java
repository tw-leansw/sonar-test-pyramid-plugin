package com.thoughtworks.lean.sonar.testpyramid.util;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Map;

public class ScriptUtil {
    private ScriptUtil() {
    }

    public static  JXPathMap eval(String scriptString) {
        Context cx = Context.enter();
        try {
            // given
            String jsString = null;
            Scriptable scope = cx.initStandardObjects();
            cx.evaluateString(scope, scriptString, "<cmd>", 1, null);
            return new JXPathMap((Map) scope);

        } finally {
            Context.exit();
        }
    }


}
