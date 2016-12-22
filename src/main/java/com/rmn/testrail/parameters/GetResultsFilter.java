package com.rmn.testrail.parameters;

/**
 * The GetResultsFilter is used to generate filters specifically for the getTestResult methods.
 *
 * @author vliao
 */
public enum GetResultsFilter implements ApiFilter {
    //Request filters for get_results
    LIMIT("limit"),
    OFFSET("offset"),
    STATUS_ID("status_id");

    private String filter;
    GetResultsFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
