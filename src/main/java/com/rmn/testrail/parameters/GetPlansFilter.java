package com.rmn.testrail.parameters;

/**
 * The GetMilestonesFilter is used to generate filters specifically for the getMilestone and getMilestones methods.
 *
 * @author vliao
 */
public enum GetPlansFilter implements ApiFilter{
    //Request filter for get_plans
    CREATED_AFTER("created_after"),
    CREATED_BEFORE("created_before"),
    CREATED_BY("created_by"),
    IS_COMPLETED("is_completed"),
    LIMIT("limit"),
    MILESTONE_ID("milestone_id");

    private String filter;
    GetPlansFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
