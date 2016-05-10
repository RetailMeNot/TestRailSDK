package com.rmn.testrail.parameters;

public enum ApiParameter {

    SUITE_ID("&suite_id="),
    SECTION_ID("&section_id=");

    public final String parameter;
    ApiParameter(String parameter) {
        this.parameter = parameter;
    }
}
