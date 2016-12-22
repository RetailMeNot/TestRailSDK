package com.rmn.testrail.parameters;

/**
 * The ApiFilterValue object represents a request filter url parameter and String value.
 * Declaring an ApiFilterValue object: new ApiFilterValue(GetOBJECT_TYPEFilter.ENUM_NAME, VALUE);
 * <br><br>
 * OBJECT_TYPE can be Cases, Milestones, Plans, Projects, Results, ResultsForRun, Runs, Tests
 * <br>
 * ENUM_NAME is the enumeration for the filter you wish to use
 * <br>
 * VALUE is the value for that filter
 *
 * @author vliao
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
