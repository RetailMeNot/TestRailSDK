package com.rmn.testrail.parameters;

/**
 * The GetTestsFilter is used to generate filters specifically for the getTests method.
 *
 * @author vliao
 */
public enum GetTestsFilter implements ApiFilter {
    //Request filters for get_tests
    STATUS_ID("status_id"),
    LIMIT("limit"),                 //250 by default.  requires TestRail 6.7 or later
    OFFSET("offset");               //requires TestRail 6.7 or later

    private final String filter;
    GetTestsFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
