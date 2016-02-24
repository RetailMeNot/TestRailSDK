package com.rmn.testrail.parameters;

public enum GetPlansFilter implements ApiFilter{
    CREATED_AFTER("created_after"),
    CREATED_BEFORE("created_before"),
    CREATED_BY("created_by"),
    IS_COMPLETED("is_completed"),
    LIMIT("limit"),
    MILESTONE_ID("milestone_id"),

    private String filter;
    GetPlansFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
