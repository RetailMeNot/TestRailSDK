package com.rmn.testrail.parameters;

/**
 * The GetMilestonesFilter is used to generate filters specifically for the getMilestone and getMilestones methods.
 *
 * @author vliao
 */
public enum GetMilestonesFilter implements ApiFilter {
    //Request filter for get_milestones
    IS_COMPLETED("is_completed");

    private String filter;
    GetMilestonesFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
