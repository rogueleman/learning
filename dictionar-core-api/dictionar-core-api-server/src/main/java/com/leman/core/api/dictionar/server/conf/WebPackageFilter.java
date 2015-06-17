package com.leman.core.api.dictionar.server.conf;

import java.util.regex.Pattern;

import org.springframework.core.type.filter.RegexPatternTypeFilter;

public class WebPackageFilter extends RegexPatternTypeFilter {

    public WebPackageFilter() {
        super(Pattern.compile("com\\.leman\\.core\\.api\\.dictionar\\.server\\.anagram\\.services\\.stub\\..*"));
    }

}

