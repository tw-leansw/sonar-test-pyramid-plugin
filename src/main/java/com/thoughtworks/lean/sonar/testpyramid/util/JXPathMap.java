package com.thoughtworks.lean.sonar.testpyramid.util;

import ch.lambdaj.function.convert.Converter;
import org.apache.commons.jxpath.JXPathContext;

import java.util.*;

public class JXPathMap {
    JXPathContext jxPathContext;

    public JXPathMap(Object map) {
        this.jxPathContext = JXPathContext.newContext(map);
    }

    public <T> T get(String key) {
        return (T) jxPathContext.getValue(key);
    }

    public Set<String> getStringSet(String key) {
        List<String> strings = get(key);
        return strings == null ? new HashSet<String>() : new HashSet<>(strings);
    }

    public String getString(String key){
        String string = get(key);
        return string;
    }

    public static Converter<Map,JXPathMap> toJxPathFunction = new Converter<Map, JXPathMap>() {
        @Override
        public JXPathMap convert(Map map) {
            return new JXPathMap(map);
        }

    };
}
