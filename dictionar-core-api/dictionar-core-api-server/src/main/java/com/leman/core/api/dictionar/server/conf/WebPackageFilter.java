package com.leman.core.api.dictionar.server.conf;

import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

public class WebPackageFilter extends RegexPatternTypeFilter {

	public WebPackageFilter() {
		super(Pattern.compile("com\\.leman\\.core\\.api\\.dictionar\\.server\\.anagram\\.services\\.stub\\..*"));
	}

}

