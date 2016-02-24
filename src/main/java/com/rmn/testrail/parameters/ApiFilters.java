package com.rmn.testrail.parameters;

public class ApiFilters {
    public static String append(ApiFilter filter, String value) {
        return filter.filter + value;
    }
}

