package com.rmn.testrail.service;

/**
 * TestRails end-points
 * @author mmerrell
 */
public enum TestRailCommand {
    GET_PROJECTS("get_projects"),
    GET_PROJECT("get_project"),
    GET_SUITES("get_suites"),
    GET_SUITE("get_suite"),
    GET_PLANS("get_plans"),
    GET_PLAN("get_plan"),
    GET_SECTIONS("get_sections"),
    GET_RUNS("get_runs"),
    GET_RUN("get_run"),
    GET_CASES("get_cases"),
    GET_CASE("get_case"),
    UPDATE_CASE("update_case"),
    GET_TESTS("get_tests"),
    GET_RESULTS("get_results"),
    ADD_RESULT("add_result"),
    ADD_RESULTS("add_results"),
    ADD_RUN("add_run"),
    CLOSE_RUN("close_run"),
    ADD_PLAN("add_plan"),
    ADD_PLAN_ENTRY("add_plan_entry"),
    ADD_MILESTONE("add_milestone"),
    GET_MILESTONE("get_milestone"),
    GET_MILESTONES("get_milestones"),
    GET_USERS("get_users"),
    GET_USER_BY_ID("get_user"),
    GET_USER_BY_EMAIL("get_user_by_email");

    private String command;
    private TestRailCommand(String command) {
        this.command = command;
    }
    public String getCommand() { return command; }
}
