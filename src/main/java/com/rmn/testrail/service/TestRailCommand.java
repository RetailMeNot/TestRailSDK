package com.rmn.testrail.service;

/**
 * A set of TestRails end-points
 * User: mmerrell
 * Date: 10/28/13
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
    GET_CASES("get_cases"),
    GET_CASE("get_case"),
    UPDATE_CASE("update_case"),
    GET_TESTS("get_tests"),
    GET_RESULTS("get_results"),
    ADD_RESULT("add_result"),
    ADD_RESULTS("add_results"),
    ADD_RUN("add_run"),
    CLOSE_RUN("close_run");

    private String command;
    private TestRailCommand(String command) {
        this.command = command;
    }
    public String getCommand() { return command; }
}
