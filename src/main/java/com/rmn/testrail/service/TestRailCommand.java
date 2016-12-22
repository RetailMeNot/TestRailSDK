package com.rmn.testrail.service;

/**
 * TestRails end-points
 *
 * @author mmerrell
 */
public enum TestRailCommand {
    //API: Cases
    GET_CASE("get_case"),
    GET_CASES("get_cases"),
    ADD_CASE("add_case"),
    UPDATE_CASE("update_case"),
    DELETE_CASE("delete_case"), //Please note: Deleting a test case cannot be undone and also permanently deletes all test results in active test runs (i.e. test runs that haven't been closed (archived) yet).

    //API: Case Fields
    GET_CASE_FIELDS("get_case_fields"),

    //API: Case Types
    GET_CASE_TYPES("get_case_types"),

    //API: Configurations
    GET_CONFIGS("get_configs"),
    ADD_CONFIG_GROUP("add_config_group"),
    ADD_CONFIG("add_config"),
    UPDATE_CONFIG_GROUP("update_config_group"),
    UPDATE_CONFIG("update_config"),
    DELETE_CONFIG_GROUP("delete_config_group"), //Please note: Deleting a configuration group cannot be undone and also permanently deletes all configurations in this group. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.
    DELETE_CONFIG("delete_config"), //Please note: Deleting a configuration cannot be undone. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.

    //API: Milestones
    GET_MILESTONE("get_milestone"),
    GET_MILESTONES("get_milestones"),
    ADD_MILESTONE("add_milestone"),
    UPDATE_MILESTONE("update_milestone"),
    DELETE_MILESTONE("delete_milestone"), //Please note: Deleting a milestone cannot be undone.

    //API: Plans
    GET_PLAN("get_plan"),
    GET_PLANS("get_plans"),
    ADD_PLAN("add_plan"),
    ADD_PLAN_ENTRY("add_plan_entry"),
    UPDATE_PLAN("update_plan"),
    UPDATE_PLAN_ENTRY("update_plan_entry"),
    CLOSE_PLAN("close_plan"), //Please note: Closing a test plan cannot be undone.
    DELETE_PLAN("delete_plan"), //Please note: Deleting a test plan cannot be undone and also permanently deletes all test runs & results of the test plan.
    DELETE_PLAN_ENTRY("delete_plan_entry"), //Please note: Deleting a test run from a plan cannot be undone and also permanently deletes all related test results.

    //API: Priorities
    GET_PRIORITIES("get_priorities"),

    //API: Projects
    GET_PROJECT("get_project"),
    GET_PROJECTS("get_projects"),
    ADD_PROJECT("add_project"),
    UPDATE_PROJECT("update_project"),
    DELETE_PROJECT("delete_project"),

    //API: Results
    GET_RESULTS("get_results"),
    GET_RESULTS_FOR_CASE("get_results_for_case"),
    GET_RESULTS_FOR_RUN("get_results_for_run"),
    ADD_RESULT("add_result"),
    ADD_RESULT_FOR_CASE("add_result_for_case"),
    ADD_RESULTS("add_results"),
    ADD_RESULTS_FOR_CASES("add_results_for_cases"),

    //API: Result Fields
    GET_RESULT_FIELDS("get_result_fields"),

    //API: Runs
    GET_RUN("get_run"),
    GET_RUNS("get_runs"),
    ADD_RUN("add_run"),
    UPDATE_RUN("update_run"),
    CLOSE_RUN("close_run"), //Please note: Closing a test run cannot be undone.
    DELETE_RUN("delete_run"), //Please note: Deleting a test run cannot be undone and also permanently deletes all tests & results of the test run.

    //API: Sections
    GET_SECTION("get_section"),
    GET_SECTIONS("get_sections"),
    ADD_SECTION("add_section"),
    UPDATE_SECTION("update_section"),
    DELETE_SECTION("delete_section"), //Please note: Deleting a section cannot be undone and also deletes all related test cases as well as active tests & results, i.e. tests & results that weren't closed (archived) yet.

    //API: Statuses
    GET_STATUSES("get_statuses"),

    //API: Suites
    GET_SUITE("get_suite"),
    GET_SUITES("get_suites"),
    ADD_SUITE("add_suite"),
    UPDATE_SUITE("update_suite"),
    DELETE_SUITE("delete_suite"), //Please note: Deleting a test suite cannot be undone and also deletes all active test runs & results, i.e. test runs & results that weren't closed (archived) yet.

    //API: Templates
    GET_TEMPLATES("get_templates"),

    //API: Tests
    GET_TEST("get_test"),
    GET_TESTS("get_tests"),

    //API: Users
    GET_USER_BY_ID("get_user"),
    GET_USER_BY_EMAIL("get_user_by_email"),
    GET_USERS("get_users");

    private String command;
    private TestRailCommand(String command) {
        this.command = command;
    }
    public String getCommand() { return command; }
}
