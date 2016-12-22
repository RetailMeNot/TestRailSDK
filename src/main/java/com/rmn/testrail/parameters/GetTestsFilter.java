package com.rmn.testrail.parameters;

/**
 * The GetTestsFilter is used to generate filters specifically for the getTests method.
 *
 * @author vliao
 */
public enum GetTestsFilter implements ApiFilter {
    //Request filters for get_tests
    STATUS_ID("status_id");

    private String filter;
    GetTestsFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
