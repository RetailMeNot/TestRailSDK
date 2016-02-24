package com.rmn.testrail.parameters;

public enum ApiFilter {
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
    UPDATED_BY("updated_by"),

    //Request filter for get_milestones
    IS_COMPLETED("is_completed");

    public final String filter;
    ApiFilter(String filter) {
        this.filter = filter;
    }
}
