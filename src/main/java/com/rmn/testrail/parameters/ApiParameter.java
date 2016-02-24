package com.rmn.testrail.parameters;

public enum ApiParameter {

    CASE_ID("&case_id="),
    CONFIG_GROUP_ID("&config_group_id="),
    CONFIG_ID("config_id="),
    EMAIL("&email="),
    ENTRY_ID("&entry_id="),
    MILESTONE_ID("&milestone_id="),
    PLAN_ID("&plan_id="),
    PROJECT_ID("&project_id="),
    RUN_ID("&run_id="),
    SUITE_ID("&suite_id="),
    SECTION_ID("&section_id="),
    TEST_ID("&test_id="),
    USER_ID("&user_id=");

    public final String parameter;
    ApiParameter(String parameter) {
        this.parameter = parameter;
    }
}
