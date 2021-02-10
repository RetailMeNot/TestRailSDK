package com.rmn.testrail.parameters;

/**
 * The GetCasesFilter is used to generate filters specifically for the getTestCases methods.
 *
 * @author vliao
 */
public enum GetCasesFilter implements ApiFilter {
    //Request filters for get_cases
    CREATED_AFTER("created_after"),
    CREATED_BEFORE("created_before"),
    CREATED_BY("created_by"),
    LIMIT("limit"),                 //250 by default.  requires TestRail 6.7 or later
    MILESTONE_ID("milestone_id"),
    OFFSET("offset"),               //requires TestRail 6.7 or later
    PRIORITY_ID("priority_id"),
    TEMPLATE_ID("template_id"),
    TYPE_ID("type_id"),
    UPDATED_AFTER("updated_after"),
    UPDATED_BEFORE("updated_before"),
    UPDATED_BY("updated_by");

    private final String filter;
    GetCasesFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
