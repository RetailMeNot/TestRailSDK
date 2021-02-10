package com.rmn.testrail.parameters;

/**
 * The GetProjectsFilter is used to generate filters specifically for the getProject methods.
 *
 * @author vliao
 */
public enum GetProjectsFilter implements ApiFilter {
    //Request filter for get_projects
    IS_COMPLETED("is_completed"),
    LIMIT("limit"),                 //250 by default.  requires TestRail 6.7 or later
    OFFSET("offset");               //requires TestRail 6.7 or later

    private final String filter;
    GetProjectsFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
