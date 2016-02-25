package com.rmn.testrail.parameters;

public enum GetRunsFilter implements ApiFilter {
    //Request filters for get_cases
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
