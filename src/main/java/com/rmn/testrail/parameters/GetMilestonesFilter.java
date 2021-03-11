package com.rmn.testrail.parameters;

/**
 * The GetMilestonesFilter is used to generate filters specifically for the getMilestone and getMilestones methods.
 *
 * @author vliao
 */
public enum GetMilestonesFilter implements ApiFilter {
    //Request filter for get_milestones
    IS_COMPLETED("is_completed"),
    IS_STARTED("is_started"),
    LIMIT("limit"),                 //250 by default.  requires TestRail 6.7 or later
    OFFSET("offset");               //requires TestRail 6.7 or later

    private final String filter;
    GetMilestonesFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
