package com.rmn.testrail.parameters;

public enum GetCasesFilter implements ApiFilter {
    //Request filters for get_cases
    CREATED_AFTER("created_after"),
    CREATED_BEFORE("created_before"),
    CREATED_BY("created_by"),
    MILESTONE_ID("milestone_id"),
    PRIORITY_ID("priority_id"),
    TEMPLATE_ID("template_id"),
    TYPE_ID("type_id"),
    UPDATED_AFTER("updated_after"),
    UPDATED_BEFORE("updated_before"),
    UPDATED_BY("updated_by");

    private String filter;
    GetCasesFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
