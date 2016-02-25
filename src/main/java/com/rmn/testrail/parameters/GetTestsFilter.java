package com.rmn.testrail.parameters;

public enum GetTestsFilter implements ApiFilter {
    //Request filters for get_tests
    STATUS_ID("status_id");

    private String filter;
    GetTestsFilter(String filter) { this.filter = filter; }

    public String getFilter() {
        return filter;
    }
}
