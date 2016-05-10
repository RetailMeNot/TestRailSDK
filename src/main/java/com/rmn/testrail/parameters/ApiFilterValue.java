package com.rmn.testrail.parameters;

/**
 * @author vliao
 *
 * The ApiFilterValue object represents a request filter url parameter and String value.
 */
public class ApiFilterValue {
    private ApiFilter apiFilter;
    private String value;
    public ApiFilterValue(ApiFilter apiFilter, String value) {
        this.apiFilter = apiFilter;
        this.value = value;
    }

    public String append() {
        return "&" + this.apiFilter.getFilter() + "=" + value;
    }
}
