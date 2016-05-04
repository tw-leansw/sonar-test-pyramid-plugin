package com.thoughtworks.lean.sonar.testpyamid.analysis;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestJruby {
    @Test
    public void test_uuid() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();

        ScriptEngine engine = manager.getEngineByName("jruby");

        engine.eval("include Java  \n" +
                "java.lang.System.out.println \"hello world\"  ");

        engine.eval("include Java  \n" +
                "test= java.util.UUID.randomUUID \n" +
                "java.lang.System.out.println test");
    }
}
