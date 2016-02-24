package com.rmn.testrail.parameters;

public enum GetMilestonesFilter implements ApiFilter {
    //Request filter for get_milestones
    IS_COMPLETED("is_completed");

    private String filter;
    GetMilestonesFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
