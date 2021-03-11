package com.rmn.testrail.parameters;

/**
 * The GetResultsForRunFilter is used to generate filters specifically for the getTestResultsForRun method.
 *
 * @author vliao
 */
public enum GetResultsForRunFilter implements ApiFilter {
    //Request filters for get_results_for_run
    CREATED_AFTER("created_after"),
    CREATED_BEFORE("created_before"),
    CREATED_BY("created_by"),
    LIMIT("limit"),
    OFFSET("offset"),               //requires TestRail 6.7 or later
    STATUS_ID("status_id");
    
    private final String filter;
    GetResultsForRunFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
