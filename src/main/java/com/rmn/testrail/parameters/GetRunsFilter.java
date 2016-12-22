package com.rmn.testrail.parameters;

/**
 * The GetRunsFilter is used to generate filters specifically for the getTestRun and getTestRuns methods.
 *
 * @author vliao
 */
public enum GetRunsFilter implements ApiFilter {
    //Request filters for get_runs
    CREATED_AFTER("created_after"),
    CREATED_BEFORE("created_before"),
    CREATED_BY("created_by"),
    IS_COMPLETED("is_completed"),
    LIMIT("limit"),
    MILESTONE_ID("milestone_id"),
    SUITE_ID("suite_id");

    private String filter;
    GetRunsFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
