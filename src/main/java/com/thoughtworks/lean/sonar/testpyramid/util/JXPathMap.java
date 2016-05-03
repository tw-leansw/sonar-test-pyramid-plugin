package com.thoughtworks.lean.sonar.testpyramid.util;

import org.apache.commons.jxpath.JXPathContext;

import java.util.Map;

public class JXPathMap {
    Map map;
    JXPathContext jxPathContext;

    public JXPathMap(Map map) {
        this.map = map;
        this.jxPathContext = JXPathContext.newContext(map);
    }

    public <T> T get(String key){
        return  (T)jxPathContext.getValue(key);
    }
}
