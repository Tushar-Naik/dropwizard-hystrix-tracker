package io.dropwizard.hystrix.path.tracker.filters.impl;

import lombok.AllArgsConstructor;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

/**
 * @author tushar.naik
 * @version 1.0  01/05/17 - 8:20 PM
 */
@AllArgsConstructor
public class NamedPathTrackerFilter extends PathTrackerFilter {
    private String key;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.key = filterConfig.getInitParameter("key");
    }

    @Override
    public String identifyKey(ServletRequest servletRequest) {
        return key;
    }
}
