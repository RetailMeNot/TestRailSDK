package com.rmn.testrail.parameters;

/**
 * The GetProjectsFilter is used to generate filters specifically for the getProject methods.
 *
 * @author vliao
 */
public enum GetProjectsFilter implements ApiFilter {
    //Request filter for get_projects
    IS_COMPLETED("is_completed");

    private String filter;
    GetProjectsFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
