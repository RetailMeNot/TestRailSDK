package com.rmn.testrail.service;

import com.rmn.testrail.entity.*;
import com.rmn.testrail.entity.Error;
import com.rmn.testrail.parameters.ApiFilterValue;
import com.rmn.testrail.parameters.ApiParameter;
import com.rmn.testrail.parameters.ApiParameters;
import com.rmn.testrail.parameters.GetResultsFilter;
import com.rmn.testrail.util.HTTPUtils;
import com.rmn.testrail.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mmerrell
 */
public class TestRailService implements Serializable {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This might not last forever--we'll need to make "v2" a variable at some point--but this works for the moment
     */
    private static final String ENDPOINT_SUFFIX = "index.php?/api/v2/%s%s";

    /**
     * Used this way, the default implementation will assume that the TestRail instance is hosted by TestRail on their server. As such, you pass in
     * your "client ID", and it will get put into the correct place. If you're hosting a local instance, you'll have to use the (URL, String, String)
     * constructor in order to pass the full URL for your instance
     */
    private String apiEndpoint = "https://%s.testrail.com/";
    private String username;
    private String password;
    private HTTPUtils utils = new HTTPUtils();

    public TestRailService() {}

    /**
     * Construct a new TestRailService with the necessary information to start communication immediately
     * @param clientId The clientID--usually the "{id}.testrail.com" you are assigned when you first open an account
     * @param username The username you will use to communicate with the API. It is recommended to create an account with minimal privileges, specifically for API use
     * @param password The password to use with this account
     */
    public TestRailService(String clientId, String username, String password) {
        this.apiEndpoint = String.format(apiEndpoint, clientId) + ENDPOINT_SUFFIX;
        this.username = username;
        this.password = password;
    }

    /**
     * Construct a new TestRailService against a local instance. This requires you to pass the FULL URL of the local instance, including your client ID
     * @param apiEndpoint The full URL of the service you are using (only the domain, not the "index.php" part. It should look like "https://server-ip/testRail/",
     *                    including the final '/')
     * @param username The username you will use to communicate with the API. It is recommended to create an account with minimal privileges, specifically for API use
     * @param password The password to use with this account
     */
    public TestRailService(URL apiEndpoint, String username, String password) {
        this.apiEndpoint = apiEndpoint.toString() + ENDPOINT_SUFFIX;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets the "API Endpoint" for the TestRails service--this if for locally-hosted instances of TestRail, and should
     * include the full base URL, e.g. "https://secure-ip/testrail/", including the final forward-slash "/"
     * @param apiEndpoint Your API end-point (including the Client ID)
     */
    public void setApiEndpoint(URL apiEndpoint) {
        this.apiEndpoint = apiEndpoint.toString() + ENDPOINT_SUFFIX;
    }

    /**
     * Sets the "client id" for the TestRails service--this usually equates to the lowest-level
     * domain, e.g. http://[foo].testrail.com...
     * @param clientId Your Client ID (provided by TestRails)
     */
    public void setClientId(String clientId) { this.apiEndpoint = String.format(apiEndpoint, clientId) + ENDPOINT_SUFFIX; }

    /**
     * The user name for the API-enabled user
     * @param username Your Username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * The user name for the API-enabled user
     * @param password Your Password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Sets the HTTPUtils object (this is not static because we need to stub these methods for unit testing)
     * @param utils The {@link HTTPUtils} object
     */
    public void setHttpUtils(HTTPUtils utils) { this.utils = utils; }

    /**
     * Uses generics and magic to return any kind of TestRails Entity in List context (i.e. a list of Entities)
     * @param clazz The Class you're interested in mapping the JSON to--must derive from BaseEntity
     * @param apiCall The TestRails end-point you're going after (just the call itself, e.g. "get_projects".
     *                The v2 and authentication are provided elsewhere)
     * @param param The rest of the URL you're querying. You're on your own as to how to construct this parameter--
     *              consult the API documentation (http://docs.gurock.com/testrail-api/start) for more details
     * @param <T> The Type of BaseEntity you're trying to construct. As long as your 'clazz' param extends BaseEntity,
     *           this will be taken care of
     * @return A List of the Entity you're trying to get from the Service
     */
    protected  <T extends BaseEntity> List<T> getEntityList(Class<T> clazz, String apiCall, String param) {
        HttpURLConnection connection = getRESTRequest(apiCall, param);
        log.debug("");
        String contents = utils.getContentsFromConnection(connection);
        List<T> entities = JSONUtils.getMappedJsonObjectList(clazz, contents);
        for (T suite: entities) {
            suite.setTestRailService(this);
        }
        return entities;
    }

    /**
     * Uses generics and magic to return any kind of TestRails Entity in Single context (i.e. a single Entity, not a List)
     * @param clazz The Class you're interested in mapping the JSON to--must derive from BaseEntity
     * @param apiCall The TestRails end-point you're going after (just the call itself, e.g. "get_project".
     *                The v2 and authentication are provided elsewhere)
     * @param param The rest of the URL you're querying. You're on your own as to how to construct this parameter--
     *              consult the API documentation (http://docs.gurock.com/testrail-api/start) for more details
     * @param <T> The Type of BaseEntity you're trying to construct. As long as your 'clazz' param extends BaseEntity,
     *           this will be taken care of
     * @return The Entity you're trying to get from the Service
     */
    protected  <T extends BaseEntity> T getEntitySingle(Class<T> clazz, String apiCall, String param) {
        HttpURLConnection connection = getRESTRequest(apiCall, param);
        String contents = utils.getContentsFromConnection(connection);
        T entity = JSONUtils.getMappedJsonObject(clazz, contents);
        entity.setTestRailService(this);
        return entity;
    }

    /**
     * Pings the API, mainly to ensure that your credentials are correct
     * @return Whether or not it was able to establish a successful connection
     * @throws IOException occurs when unable to read response code from HttpUrlConnection
     */
    public boolean verifyCredentials() throws IOException {
        //At the moment this just grabs a list of projects and makes sure the response code is valid. The API does not have
        // a "version" or "ping" end-point, so this seemed like the only way to talk to it without knowing some data first
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_PROJECTS.getCommand(), "");
        return connection.getResponseCode() == 200;
    }


    //BEGIN API HELPER METHODS
    //================================================================

    //API: Cases------------------------------------------------------

    /**
     * Returns the TestCase with the given id
     * @param testCaseId The TestCase ID (in TestRails, this will be something like 'C7', but just provide the 7)
     * @return The TestCase associated with this id
     */
    public TestCase getTestCase(int testCaseId) {
        return getEntitySingle(TestCase.class, TestRailCommand.GET_CASE.getCommand(), Integer.toString(testCaseId));
    }

    /**
     * Get the complete list of all test cases in this Project (if the project is operating in single suite mode)
     * @param projectId The ID of the project
     * @param apiFilters one or more request filters built on GetCasesFilter enums
     * @return the List of TestCase entities associated with this TestSuite
     */
    public List<TestCase> getTestCasesSingleSuiteMode(int projectId, ApiFilterValue... apiFilters) {
        return getTestCases(projectId, -1, -1, apiFilters);
    }

    /**
     * Get the complete list of all test cases in this Project (if the project is operating in single suite mode)
     * @param projectId The ID of the project
     * @param sectionId The Section ID
     * @param apiFilters one or more request filters built on GetCasesFilter enums
     * @return the List of TestCase entities associated with this TestSuite
     */
    public List<TestCase> getTestCasesSingleSuiteMode(int projectId, int sectionId, ApiFilterValue... apiFilters) {
        return getTestCases(projectId, -1, sectionId, apiFilters);
    }

    /**
     * Get the complete list of all test cases in this TestSuite
     * @param projectId The ID of the project
     * @param suiteId The Suite ID (in TestRails, this will be something like 'S7', but just provide the 7)
     * @param apiFilters one or more request filters built on GetCasesFilter enums
     * @return the List of TestCase entities associated with this TestSuite
     */
    public List<TestCase> getTestCases(int projectId, int suiteId, ApiFilterValue... apiFilters) {
        return getTestCases(projectId, suiteId, -1, apiFilters);
    }

    /**
     * Get the list of test cases in this TestSuite for the Section indicated
     * @param projectId The ID of the project
     * @param suiteId The Suite ID (in TestRails, this will be something like 'S7', but just provide the 7)
     * @param sectionId The Section ID
     * @param apiFilters one or more request filters built on GetCasesFilter enums
     * @return A List of the TestCases in this Suite
     */
    public List<TestCase> getTestCases(int projectId, int suiteId, int sectionId, ApiFilterValue... apiFilters) {
        String params = Integer.toString(projectId);
        if (suiteId > 0) {
            params += ApiParameters.append(ApiParameter.SUITE_ID, suiteId);
        }
        if (sectionId > 0) {
            params += ApiParameters.append(ApiParameter.SECTION_ID, sectionId);
        }
        for (ApiFilterValue apiFilter : apiFilters) {
            params += apiFilter.append();
        }
        return getEntityList(TestCase.class, TestRailCommand.GET_CASES.getCommand(), params);
    }

    /**
     * Creates a new test case.
     * @param testCase the new test case
     * @param sectionId The ID of the section the test case should be added to
     * @return the newly created TestCase object
     */
    public TestCase addTestCase(TestCase testCase, int sectionId) {
        return postRESTBodyReturn(TestRailCommand.ADD_CASE.getCommand(), Integer.toString(sectionId), testCase, TestCase.class);
    }

    /**
     * Updates an existing test case (partial updates are supported, i.e. you can submit and update specific fields only).
     * @param testCase a TestCase object with fields to be updated set. (i.e. TestCase updateCase = new TestCase(); updateCase.setPriorityId(2); )
     * @param caseId The ID of the test case
     * @return the updated TestCase object
     */
    public TestCase updateTestCase(TestCase testCase, int caseId) {
        return postRESTBodyReturn(TestRailCommand.UPDATE_CASE.getCommand(), Integer.toString(caseId), testCase, TestCase.class);
    }

    /**
     * WARNING: Permanently delete an existing test case. Please note: Deleting a test case cannot be undone and also permanently deletes all test results in active test runs (i.e. test runs that haven't been closed (archived) yet).
     * @param caseId The ID of the test case
     */
    public void deleteTestCase(int caseId) {
        postRESTBody(TestRailCommand.DELETE_CASE.getCommand(), Integer.toString(caseId), null);
    }


    //API: Case Fields------------------------------------------------

    /**
     * Returns a list of available test case custom fields.
     * @return String with JSON response, you must parse the string yourself
     */
    public String getCaseFields() {
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_CASE_FIELDS.getCommand(), null);
        return utils.getContentsFromConnection(connection);
    }

    //API: Case Types-------------------------------------------------

    /**
     * Returns a list of available case types.
     * @return String with JSON response, you must parse the string yourself
     */
    public String getCaseTypes() {
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_CASE_TYPES.getCommand(), null);
        return utils.getContentsFromConnection(connection);
    }
    //API: Configurations---------------------------------------------

    /**
     * Returns a list of available configurations, grouped by configuration groups (requires TestRail 3.1 or later).
     * @param projectId The ID of the project
     * @return String with JSON response, you must parse the string yourself
     */
    public String getConfigurations(int projectId) {
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_CONFIGS.getCommand(), Integer.toString(projectId));
        return utils.getContentsFromConnection(connection);
    }

    /**
     * Creates a new configuration group.
     * @param name The name of the configuration group
     * @param projectId The ID of the project the configuration group should be added to
     */
    public void addConfigGroup(final String name, int projectId) {
        postRESTBody(TestRailCommand.ADD_CONFIG_GROUP.getCommand(), null,
                new BaseEntity() {
                    @JsonProperty("name")
                    private String nameString = name;
                });
    }

    /**
     * Creates a new configuration group.
     * @param name The name of the configuration
     * @param configGroupId The ID of the configuration group the configuration should be added to
     */
    public void addConfig(final String name, int configGroupId) {
        postRESTBody(TestRailCommand.ADD_CONFIG.getCommand(), Integer.toString(configGroupId),
                new BaseEntity() {
                    @JsonProperty("name")
                    private String nameString = name;
                });
    }

    /**
     * Updates an existing configuration group.
     * @param name The new name of the configuration group
     * @param configGroupId The ID of the configuration group
     */
    public void updateConfigGroup(final String name, int configGroupId) {
        postRESTBody(TestRailCommand.UPDATE_CONFIG_GROUP.getCommand(), Integer.toString(configGroupId),
                new BaseEntity() {
                    @JsonProperty("name")
                    private String nameString = name;
                });
    }

    /**
     * Updates an existing configuration.
     * @param name The new name of the configuration
     * @param configId The ID of the configuration
     */
    public void updateConfig(final String name, int configId) {
        postRESTBody(TestRailCommand.UPDATE_CONFIG.getCommand(), Integer.toString(configId),
                new BaseEntity() {
                    @JsonProperty("name")
                    private String nameString = name;
                });
    }

    /**
     * Updates an existing configuration group.
     * Please note: Deleting a configuration group cannot be undone and also permanently deletes all configurations in this group. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.
     * @param configGroupId The ID of the configuration group
     */
    public void deleteConfigGroup(int configGroupId) {
        postRESTBody(TestRailCommand.DELETE_CONFIG_GROUP.getCommand(), Integer.toString(configGroupId), null);
    }

    /**
     * Deletes an existing configuration.
     * Please note: Deleting a configuration cannot be undone. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.
     * @param configId The ID of the configuration
     */
    public void deleteConfig(int configId) {
        postRESTBody(TestRailCommand.DELETE_CONFIG.getCommand(), Integer.toString(configId), null);
    }

    //API: Milestones-------------------------------------------------

    /**
     * Returns the Milestone object with the given ID
     * @param milestoneId the ID of the Milestone you're interested in
     * @return The Milestone object
     */
    public Milestone getMilestone(int milestoneId) {
        return getEntitySingle(Milestone.class, TestRailCommand.GET_MILESTONE.getCommand(), Integer.toString(milestoneId));
    }

    /**
     * Returns a list of all the Milestones in the given project ID
     * @param projectId the ID of project you want the Milestones from
     * @param isCompleted only accepts ApiFilter.IS_COMPLETED (numerical boolean {0|1})
     * @return the list of all the Milestones in the project
     */
    public List<Milestone> getMilestones(int projectId, ApiFilterValue... isCompleted) {
        return getEntityList(Milestone.class, TestRailCommand.GET_MILESTONES.getCommand(), Integer.toString(projectId) + (isCompleted.length > 0 ? isCompleted[0].append() : ""));
    }

    /**
     * Creates a new milestone.
     * @param milestone The EmptyMilestone object with parameters for creating a new milestone
     * @param projectId The ID of the project the milestone should be added to
     * @return Returns the new milestone that was created
     */
    public Milestone addMilestone(EmptyMilestone milestone, int projectId) {
        return postRESTBodyReturn(TestRailCommand.ADD_MILESTONE.getCommand(), Integer.toString(projectId), milestone, Milestone.class);
    }

    public Milestone updateMilestone(int milestoneId, final boolean isCompleted) {
        return postRESTBodyReturn(TestRailCommand.UPDATE_MILESTONE.getCommand(),
                Integer.toString(milestoneId),
                new BaseEntity() {
                    @JsonProperty("is_completed")
                    private String isCompletedBoolean = isCompleted ? "1":"0";
                },
                Milestone.class);
    }


    //API: Plans------------------------------------------------------

    /**
     * The TestPlan assocated with the indicated id
     * @param planId The id of the TestPlan you're interested in
     * @return The TestPlan entity indicated by the id
     */
    public TestPlan getTestPlan(int planId) {
        return getEntitySingle(TestPlan.class, TestRailCommand.GET_PLAN.getCommand(), Integer.toString(planId));
    }

    /**
     * The List of TestPlan entities the indicated Project contains
     * @param projectId The id of the project you're interested in
     * @param apiFilters one or more request filters built on GetPlansFilter enums
     * @return A List of TestPlan entities for the indicated Project
     */
    public List<TestPlan> getTestPlans(int projectId, ApiFilterValue... apiFilters) {
        String params = Integer.toString(projectId);
        for (ApiFilterValue apiFilter : apiFilters) {
            params += apiFilter.append();
        }
        return getEntityList(TestPlan.class, TestRailCommand.GET_PLANS.getCommand(), params);
    }

    /**
     * Adds a Test Plan in TestRails
     * @param projectId the ID of the project to add the Test Plan to
     * @param testPlan the skeleton Test Plan object the TestRails Test Plan will be based off of
     * @return the completed Test Plan created in TestRails
     */
    public TestPlan addTestPlan(int projectId, TestPlanCreator testPlan) {
        return postRESTBodyReturn(TestRailCommand.ADD_PLAN.getCommand(), Integer.toString(projectId), testPlan, TestPlan.class);
    }

    /**
     * Adds a Test Plan Entry in TestRails
     * @param planId the ID of the Test Plan to add the Test Plan Entry to
     * @param planEntry the skeleton Plane Entry object the TestRails Plan Entry (Test Run) will be based off of
     * @return the completed Plan Entry created in TestRails
     */
    public PlanEntry addTestPlanEntry(int planId, PlanEntry planEntry) {
        return postRESTBodyReturn(TestRailCommand.ADD_PLAN_ENTRY.getCommand(), Integer.toString(planId), planEntry, PlanEntry.class);
    }

    /**
     * Updates an existing test plan (partial updates are supported, i.e. you can submit and update specific fields only).
     * With the exception of the entries field, this method supports the same POST fields as add_plan.
     * @param planId The ID of the test plan
     * @param testPlan The (partially) updated test plan
     * @return the updated test plan
     */
    public TestPlan updateTestPlan(int planId, TestPlanCreator testPlan) {
        return postRESTBodyReturn(TestRailCommand.UPDATE_PLAN.getCommand(), Integer.toString(planId), testPlan, TestPlan.class);
    }

    /**
     * Updates one or more existing test runs in a plan (partial updates are supported, i.e. you can submit and update specific fields only).
     * @param planId The ID of the test plan
     * @param entryId The ID of the test plan entry (note: not the test run ID)
     * @param updatePlanEntry the (partial) updates to the plan entry
     * @return the updated plan entry
     */
    public PlanEntry updateTestPlanEntry(int planId, String entryId, UpdatePlanEntry updatePlanEntry) {
        return postRESTBodyReturn(TestRailCommand.UPDATE_PLAN_ENTRY.getCommand(), Integer.toString(planId) + "/" + entryId, updatePlanEntry, PlanEntry.class);
    }

    /**
     * Closes an existing test plan and archives its test runs and results.
     * Please note: Closing a test plan cannot be undone.
     * @param planId The ID of the test plan
     * @return the closed test plan
     */
    public TestPlan closeTestPlan(int planId) {
        return postRESTBodyReturn(TestRailCommand.CLOSE_PLAN.getCommand(), Integer.toString(planId), null, TestPlan.class);
    }

    /**
     * Deletes an existing test plan.
     * Please note: Deleting a test plan cannot be undone and also permanently deletes all test runs and results of the test plan.
     * @param planId The ID of the test plan
     */
    public void deleteTestPlan(int planId) {
        postRESTBody(TestRailCommand.DELETE_PLAN.getCommand(), Integer.toString(planId), null);
    }

    /**
     * Deletes one or more existing test runs from a plan.
     * Please note: Deleting a test run from a plan cannot be undone and also permanently deletes all related test results.
     * @param planId The ID of the test plan
     * @param entryId The ID of the test plan entry (note: not the test run ID)
     */
    public void deleteTestPlanEntry(int planId, int entryId) {
        postRESTBody(TestRailCommand.DELETE_PLAN_ENTRY.getCommand(), Integer.toString(planId) + "/" + Integer.toString(entryId), null);
    }


    //API: Priorities-------------------------------------------------

    /**
     * Returns a list of available priorities.
     * @return a list of Priority objects
     */
    public List<Priority> getPriorities() {
        return getEntityList(Priority.class, TestRailCommand.GET_PRIORITIES.getCommand(), null);
    }


    //API: Projects---------------------------------------------------

    /**
     * Returns the Project, specified by id
     * @param projectId The TestRails Project Id
     * @return The Project, or null if it doesn't exist
     */
    public Project getProject(int projectId) {
        return getEntitySingle(Project.class, TestRailCommand.GET_PROJECT.getCommand(), Integer.toString(projectId));
    }

    /**
     * Looks up the Project, specified by Name
     * @param projectName The Name of the Project (including spaces)
     * @return The Project, or null if it doesn't exist
     */
    public Project getProjectByName(String projectName) {
        for (Project project: this.getProjects()) {
            if (project.getName().equals(projectName)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Returns all Project entities related to this account
     * @param isCompleted ApiFilterValue object based off of GetProjectsFilter.IS_COMPLETED enum
     * @return The List of ALL Projects available to this user
     */
    public List<Project> getProjects(ApiFilterValue... isCompleted) {
        return getEntityList(Project.class, TestRailCommand.GET_PROJECTS.getCommand(), isCompleted.length > 0 ? isCompleted[0].append() : "");
    }

    /**
     * Creates a new project (admin status required).
     * @param newProject project information of new project to add
     * @return new project that was created
     */
    public Project addProject(ProjectCreator newProject) {
        return postRESTBodyReturn(TestRailCommand.ADD_PROJECT.getCommand(), null, newProject, Project.class);
    }

    /**
     * Updates an existing project (admin status required; partial updates are supported, i.e. you can submit and update specific fields only).
     * @param projectId The ID of the project
     * @param isCompleted Specifies whether a project is considered completed or not
     * @return the updated Project object
     */
    public Project updateProject(int projectId, final boolean isCompleted) {
        return postRESTBodyReturn(TestRailCommand.ADD_PROJECT.getCommand(),
                Integer.toString(projectId),
                new BaseEntity() {
                    @JsonProperty("is_completed")
                    private String isCompletedBoolean = isCompleted ? "1":"0";
                },
                Project.class);
    }

    /**
     * Deletes an existing project (admin status required).
     * Please note: Deleting a project cannot be undone and also permanently deletes all test suites and cases, test runs and results and everything else that is part of the project.
     * @param projectId The ID of the project
     */
    public void deleteProject(int projectId) {
        postRESTBody(TestRailCommand.DELETE_PROJECT.getCommand(), Integer.toString(projectId), null);
    }

    //API: Results----------------------------------------------------

    /**
     * Returns the most recent TestResult object for the given TestInstance
     * @param testInstanceId The TestInstance you're interested in (gathered from the specific TestRun)
     * @return The most recent TestResult for the given TestInstance
     */
    public TestResult getTestResult(int testInstanceId) {
        List<TestResult> results = getTestResults(testInstanceId, new ApiFilterValue(GetResultsFilter.LIMIT, "1"));
        if (null == results || results.size() == 0) {
            return null;
        }
        return results.get(0);
    }

    /**
     * Returns a List of the TestResults (up to the 'limit' parameter provided) associated with the indicated TestInstance, most recent first
     * @param testInstanceId The TestInstance id
     * @param apiFilters one or more request filters built on GetResultsFilter enums
     * @return A List of TestResults in descending chronological order (i.e. most recent first)
     */
    public List<TestResult> getTestResults(int testInstanceId, ApiFilterValue... apiFilters) {
        List<TestResult> results = getEntityList(TestResult.class, TestRailCommand.GET_RESULTS.getCommand(), Integer.toString(testInstanceId) + new ApiFilterValue(GetResultsFilter.LIMIT, "1").append());
        if (null == results) {
            return null;
        }
        String params = Integer.toString(testInstanceId);
        for (ApiFilterValue apiFilter : apiFilters) {
            params += apiFilter.append();
        }
        return getEntityList(TestResult.class, TestRailCommand.GET_RESULTS.getCommand(), params);
    }

    /**
     * Returns a list of test results for a test run and case combination.
     * @param runId The ID of the test run
     * @param caseId The ID of the test case
     * @param apiFilters one or more request filters built on GetResultsFilter enums
     * @return A List of TestResults in descending chronological order (i.e. most recent first)
     */
    public List<TestResult> getTestResultsForCase(int runId, int caseId, ApiFilterValue... apiFilters) {
        String params = Integer.toString(runId) + "/" + Integer.toString(caseId);
        for (ApiFilterValue apiFilter : apiFilters) {
            params += apiFilter.append();
        }
        return getEntityList(TestResult.class, TestRailCommand.GET_RESULTS_FOR_CASE.getCommand(), params);
    }

    /**
     * Returns a list of test results for a test run and case combination.
     * @param runId The ID of the test run
     * @param apiFilters one or more request filters built on GetResultsFilter enums
     * @return A List of TestResults in descending chronological order (i.e. most recent first)
     */
    public List<TestResult> getTestResultsForRun(int runId, ApiFilterValue... apiFilters) {
        String params = Integer.toString(runId);
        for (ApiFilterValue apiFilter : apiFilters) {
            params += apiFilter.append();
        }
        return getEntityList(TestResult.class, TestRailCommand.GET_RESULTS_FOR_RUN.getCommand(), params);
    }

    /**
     * (Adds a new test result, comment or assigns a test. It's recommended to use add_results instead if you plan to add results for multiple tests.)
     * Add a TestResult to a particular TestInstance, given the TestInstance id
     * @param testId The id of the TestInstance to which you would like to add a TestResult entity
     * @param result TestResult entity you wish to add to this TestInstance
     * @return the new test result
     */
    public TestResult addTestResult(int testId, TestResult result) {
        HttpResponse response = postRESTBody(TestRailCommand.ADD_RESULT.getCommand(), Integer.toString(testId), result);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("TestResult was not properly added to TestInstance [%d]: %s", testId, response.getStatusLine().getReasonPhrase()));
        }
        try {
            return JSONUtils.getMappedJsonObject(TestResult.class, utils.getContentsFromHttpResponse(response));
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
            return null;
        }
    }

    /**
     * Adds a new test result, comment or assigns a test (for a test run and case combination). It's recommended to use add_results_for_cases instead if you plan to add results for multiple test cases.
     * @param runId The ID of the test run
     * @param caseId The ID of the test case
     * @param result TestResult entity you wish to add to this TestInstance
     * @return the new test result
     */
    public TestResult addTestResultForCase(int runId, int caseId, TestResult result) {
        HttpResponse response = postRESTBody(TestRailCommand.ADD_RESULT_FOR_CASE.getCommand(), Integer.toString(runId) + "/" + Integer.toString(caseId), result);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("TestResult was not properly added to Run ID: %d | Case ID: %d: %s", runId, caseId, response.getStatusLine().getReasonPhrase()));
        }
        try {
            return JSONUtils.getMappedJsonObject(TestResult.class, utils.getContentsFromHttpResponse(response));
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
            return null;
        }
    }

    /**
     * (Adds one or more new test results, comments or assigns one or more tests. Ideal for test automation to bulk-add multiple test results in one step.)
     * Add a TestResult to a particular TestInstance, given the TestInstance id
     * @param runId The id of the TestRun to which you would like to add a TestResults entity
     * @param results A TestResults entity (which can include multiple TestResult entities) you wish to add to this TestRun
     * @return the newly created TestResults object
     */
    public TestResults addTestResults(int runId, TestResults results) {
        HttpResponse response = postRESTBody(TestRailCommand.ADD_RESULTS.getCommand(), Integer.toString(runId), results);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("TestResults was not properly added to TestRun [%d]: %s", runId, response.getStatusLine().getReasonPhrase()));
        }
        TestResults returnedResults = new TestResults();
        try {
            returnedResults.setResults(JSONUtils.getMappedJsonObjectList(TestResult.class, utils.getContentsFromHttpResponse(response)));
            return returnedResults;
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
            return null;
        }
    }

    /**
     * (Adds one or more new test results, comments or assigns one or more tests. Ideal for test automation to bulk-add multiple test results in one step.)
     * Add a TestResult to a particular TestInstance, given the TestInstance id
     * @param runId The id of the TestRun to which you would like to add a TestResults entity
     * @param results A TestResults entity (which can include multiple TestResult entities) you wish to add to this TestRun
     * @return the newly created TestResults object
     */
    public TestResults addTestResultsForCases(int runId, TestResults results) {
        HttpResponse response = postRESTBody(TestRailCommand.ADD_RESULTS_FOR_CASES.getCommand(), Integer.toString(runId), results);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("TestResults was not properly added to TestRun [%d]: %s", runId, response.getStatusLine().getReasonPhrase()));
        }
        TestResults returnedResults = new TestResults();
        try {
            returnedResults.setResults(JSONUtils.getMappedJsonObjectList(TestResult.class, utils.getContentsFromHttpResponse(response)));
            return returnedResults;
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
            return null;
        }
    }

    //API: Result Fields----------------------------------------------

    /**
     * Returns a list of available test result custom fields.
     * @return String with JSON response, you must parse the string yourself
     */
    public String getResultFields() {
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_RESULT_FIELDS.getCommand(), null);
        return utils.getContentsFromConnection(connection);
    }


    //API: Runs-------------------------------------------------------

    /**
     * Returns TestRun associated with the specific TestRun ID passed in (assuming you know it)
     * @param testRunId The id of the TestRun requested
     * @return The TestRun active for this TestRun ID
     */
    public TestRun getTestRun(int testRunId) {
        return getEntitySingle(TestRun.class, TestRailCommand.GET_RUN.getCommand(), Integer.toString(testRunId));
    }

    /**
     * Returns all the Active TestRuns associated with the given Project
     * @param projectId The id of the Project
     * @param apiFilters one or more request filters built on GetRunsFilter enums
     * @return The List of TestRuns currently active for this Project
     */
    public List<TestRun> getTestRuns(int projectId, ApiFilterValue... apiFilters) {
        String params = Integer.toString(projectId);
        for (ApiFilterValue apiFilter : apiFilters) {
            params += apiFilter.append();
        }
        return getEntityList(TestRun.class, TestRailCommand.GET_RUNS.getCommand(), params);
    }

    /**
     * Add a TestRun via a slimmed down new TestRunCreator entity to get around non-obvious json serialization problems
     * with the TestRun entity
     * @param projectId the id of the project to bind the test run to
     * @param run the TestRunCreator object with information on the new TestRun
     * @return The newly created TestRun object
     */
    public TestRun addTestRun(int projectId, TestRunCreator run) {
        TestRun newSkeletonTestRun = postRESTBodyReturn(TestRailCommand.ADD_RUN.getCommand(), Integer.toString(projectId), run, TestRun.class);
        return getTestRun(newSkeletonTestRun.getId());
    }

    /**
     * Updates an existing test run (partial updates are supported, i.e. you can submit and update specific fields only).
     * @param runId The ID of the test run
     * @param testRunUpdater the {@link TestRunUpdater} object with available for updating fields
     * @return the updated test run
     */
    public TestRun updateTestRun(int runId, TestRunUpdater testRunUpdater) {
        return postRESTBodyReturn(TestRailCommand.UPDATE_RUN.getCommand(), Integer.toString(runId), testRunUpdater, TestRun.class);
    }

    /**
     * Closes an existing test run and archives its tests and results.
     * Please note: Closing a test run cannot be undone.
     * @param run The TestRun you want to close
     * @return the newly closed test run
     */
    public TestRun closeTestRun(TestRun run) {
        HttpResponse response = postRESTBody(TestRailCommand.CLOSE_RUN.getCommand(), Integer.toString(run.getId()), run);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("TestRun was not properly closed, TestRunID [%d]: %s", run.getId(), response.getStatusLine().getReasonPhrase()));
        }
        try {
            return JSONUtils.getMappedJsonObject(TestRun.class, utils.getContentsFromHttpResponse(response));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Closes an existing test run and archives its tests and results.
     * Please note: Closing a test run cannot be undone.
     * @param runId The ID of the test run
     * @return the newly closed test run
     */
    public TestRun closeTestRun(int runId) {
        HttpResponse response = postRESTBody(TestRailCommand.CLOSE_RUN.getCommand(), Integer.toString(runId), null);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(String.format("TestRun was not properly closed, TestRunID [%d]: %s", runId, response.getStatusLine().getReasonPhrase()));
        }
        try {
            return JSONUtils.getMappedJsonObject(TestRun.class, utils.getContentsFromHttpResponse(response));
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
            return null;
        }
    }

    /**
     * The ID of the test run
     * Please note: Deleting a test run cannot be undone and also permanently deletes all tests and results of the test run.
     * @param runId The ID of the test run
     */
    public void deleteTestRun(int runId) {
        postRESTBody(TestRailCommand.DELETE_RUN.getCommand(), Integer.toString(runId), null);
    }


    //API: Sections---------------------------------------------------

    /**
     * Creates a new section under suite.
     * @param projectId The ID of the project the test suite should be added to
     * @param sectionCreator The information needed to create a new section
     * @return the newly created section
     */
    public Section addSection(int projectId, SectionCreator sectionCreator) {
        return postRESTBodyReturn(TestRailCommand.ADD_SECTION.getCommand(), Integer.toString(projectId), sectionCreator, Section.class);
    }

    /**
     * Returns an existing section.
     * @param sectionId The ID of the section
     * @return a Section object
     */
    public Section getSection(int sectionId) {
        return getEntitySingle(Section.class, TestRailCommand.GET_SECTION.getCommand(), Integer.toString(sectionId));
    }
    /**
     * Returns the List of Section entities the indicated Project and TestSuite entities contain
     * @param projectId The id of the Project you're interested in
     * @param suiteId The id of the TestSuite you're interested in
     * @return A List of Section entities for the indicated Project/TestSuite
     */
    public List<Section> getSections( int projectId, int suiteId ) {
        return getEntityList(Section.class, TestRailCommand.GET_SECTIONS.getCommand(), Integer.toString(projectId) + ApiParameters.append(ApiParameter.SUITE_ID, suiteId));
    }


    //API: Statuses---------------------------------------------------

    /**
     * Returns a list of available test statuses.
     * @return String with JSON response, you must parse the string yourself
     */
    public String getStatuses() {
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_STATUSES.getCommand(), null);
        return utils.getContentsFromConnection(connection);
    }


    //API: Suites-----------------------------------------------------

    /**
     * Returns the TestSuite for the id given
     * @param suiteId The Suite ID (in TestRails, this will be something like 'S7', but just provide the 7)
     * @return A TestSuite
     */
    public TestSuite getTestSuite( int suiteId ) {
        return getEntitySingle(TestSuite.class, TestRailCommand.GET_SUITE.getCommand(), Integer.toString(suiteId));
    }

    /**
     * Returns all the TestSuites for the project id given
     * @param projectId The Project ID (in TestRails, this will be something like 'P7', but just provide the 7)
     * @return A List of Suites
     */
    public List<TestSuite> getTestSuites( int projectId ) {
        return getEntityList(TestSuite.class, TestRailCommand.GET_SUITES.getCommand(), Integer.toString(projectId));
    }

    /**
     * Creates a new test suite.
     * @param projectId The ID of the project the test suite should be added to
     * @param testSuite The information needed to create a new test suite
     * @return the newly created test suite
     */
    public TestSuite addTestSuite(int projectId, TestSuiteCreator testSuite) {
        return postRESTBodyReturn(TestRailCommand.ADD_SUITE.getCommand(), Integer.toString(projectId), testSuite, TestSuite.class);
    }

    /**
     * Updates an existing test suite (partial updates are supported, i.e. you can submit and update specific fields only).
     * @param suiteId The ID of the test suite
     * @param testSuite The (partially) updated test suite
     * @return the newly updated test suite
     */
    public TestSuite updateTestSuite(int suiteId, TestSuite testSuite) {
        return postRESTBodyReturn(TestRailCommand.UPDATE_SUITE.getCommand(), Integer.toString(suiteId), testSuite, TestSuite.class);
    }

    /**
     * Deletes an existing test suite.
     * Please note: Deleting a test suite cannot be undone and also deletes all active test runs and results, i.e. test runs and results that weren't closed (archived) yet.
     * @param suiteId The ID of the test suite
     */
    public void deleteTestSuite(int suiteId) {
        postRESTBody(TestRailCommand.DELETE_SUITE.getCommand(), Integer.toString(suiteId), null);
    }

    //API: Templates--------------------------------------------------

    /**
     * Returns a list of available templates (requires TestRail 5.2 or later).
     * @return The ID of the project
     */
    public String getTemplates() {
        HttpURLConnection connection = getRESTRequest(TestRailCommand.GET_TEMPLATES.getCommand(), null);
        return utils.getContentsFromConnection(connection);
    }


    //API: Tests------------------------------------------------------

    /**
     * Returns an existing test.
     * @param testId The ID of the test
     * @return TestInstance object
     */
    public TestInstance getTest(int testId) {
        return getEntitySingle(TestInstance.class, TestRailCommand.GET_TEST.getCommand(), Integer.toString(testId));
    }

    /**
     * Returns all TestInstances associated with the given TestRun
     * @param testRunId The id of the TestRun you're interested in
     * @param statusId ApiFilterValue object based off of GetTestsFilter.STATUS_ID enum
     * @return The List of TestInstances associated with this TestRun
     */
    public List<TestInstance> getTests(int testRunId, ApiFilterValue... statusId) {
        return getEntityList(TestInstance.class, TestRailCommand.GET_TESTS.getCommand(), Integer.toString(testRunId) + (statusId.length > 0 ? statusId[0].append() : ""));
    }


    //API: Users------------------------------------------------------

    /**
     * Get a user by id
     * @param id ID associated with user
     * @return User object
     */
    public User getUserById(int id) {
        return getEntitySingle(User.class, TestRailCommand.GET_USER_BY_ID.getCommand(), "" + id);
    }

    /**
     * Get a user by email address
     * @param email Email associated with user
     * @return User object
     */
    public User getUserByEmail(String email) {
        return getEntitySingle(User.class, TestRailCommand.GET_USER_BY_EMAIL.getCommand(), "&email=" + email);
    }

    /**
     * Get the entire list of users from the API
     * @return List of User objects
     */
    public List<User> getUsers() {
        return getEntityList(User.class, TestRailCommand.GET_USERS.getCommand(), "");
    }

    //================================================================
    //END API HELPER METHODS



    /**
     * Builds the proper TestRails request URL based on the type and number of parameters. It tries to be smart about how to add
     * parameters to calls that require 0, 1, or 2 arguments
     * @param apiCall The end-point you wish to request
     * @param urlParams The full parameters of the request you're making (it's up to you to make it correct)
     * @return The URL you've built
     */
    private String buildRequestURL(String apiCall, String urlParams) {
        //Some API calls take 2 parameters, like get_cases/16/1231, so we need to account for both
        String argString = "";
        if (!StringUtils.isEmpty(urlParams)) {
            argString = String.format("/%s", urlParams);
        }

        //Build the complete url
        return String.format(apiEndpoint, apiCall, argString);
    }

    /**
     * Makes the specified call to the API using either 1 or 2 args. These args will be validated and inserted before making the actual GET request
     * @param apiCall The specific call to make to the API (NOT including the URL)
     * @param urlParams The first parameter
     * @return An active, open connection in a post-response state
     */
    private HttpURLConnection getRESTRequest(String apiCall, String urlParams) {
        String completeUrl = buildRequestURL(apiCall, urlParams);

        try {
            //log the complete url
            log.debug("url: {}", completeUrl);

            //Add the application/json header
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");

            //Log the curl call for easy reproduction
//            log.warn(utils.getCurlCommandStringGet(completeUrl, headers));

            String authentication = utils.encodeAuthenticationBase64(username, password);
            return utils.getHTTPRequest(completeUrl, authentication, headers);
        } catch (IOException e) {
            log.error("An IOException was thrown while trying to process a REST Request against URL: {}", completeUrl);
        }

        throw new RuntimeException(String.format( "Connection is null (probably hit timeout), check parameters for [%s]", completeUrl));
    }

    /**
     * Posts the given String to the given TestRails end-point
     * @param apiCall The end-point that expects to receive the entities (e.g. "add_result")
     * @param urlParams The remainder of the URL required for the POST. It is up to you to get this part right
     * @param entity The BaseEntity object to use at the POST body
     * @return The Content of the HTTP Response
     */
    private HttpResponse postRESTBody(String apiCall, String urlParams, BaseEntity entity) {
        HttpClient httpClient = new DefaultHttpClient();
        String completeUrl = buildRequestURL( apiCall, urlParams );

        try {
            HttpPost request = new HttpPost( completeUrl );
            String authentication = utils.encodeAuthenticationBase64(username, password);
            request.addHeader("Authorization", "Basic " + authentication);
            request.addHeader("Content-Type", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            byte[] body = mapper.writeValueAsBytes(entity);
            request.setEntity(new ByteArrayEntity(body));

            HttpResponse response = executeRequestWithRetry(request, 2);
            if (response.getStatusLine().getStatusCode() != 200) {
                Error error = JSONUtils.getMappedJsonObject(Error.class, utils.getContentsFromHttpResponse(response));
                log.error("Response code: {}", response.getStatusLine().getStatusCode());
                log.error("TestRails reported an error message: {}", error.getError());
                request.addHeader("Encoding", "UTF-8");
            }
            return response;
        }
        catch (IOException e) {
            log.error(String.format("An IOException was thrown while trying to process a REST Request against URL: [%s]", completeUrl), e.toString());
            throw new RuntimeException(String.format("Connection is null, check URL: %s", completeUrl));
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * Posts the given String to the given TestRails end-point
     *
     * @param apiCall The end-point that expects to receive the entities (e.g. "add_result")
     * @param urlParams The remainder of the URL required for the POST. It is up to you to get this part right
     * @param entity The BaseEntity object to use at the POST body
     * @param returnEntityType The Class of the return type you wish to receive (helps avoid casting from the calling method)
     * @return The Content of the HTTP Response
     */
    private <T extends BaseEntity> T postRESTBodyReturn(String apiCall, String urlParams, BaseEntity entity, Class<T> returnEntityType) {
        HttpClient httpClient = new DefaultHttpClient();
        String completeUrl = buildRequestURL( apiCall, urlParams );

        try {
            HttpPost request = new HttpPost( completeUrl );
            String authentication = utils.encodeAuthenticationBase64(username, password);
            request.addHeader("Authorization", "Basic " + authentication);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Encoding", "UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            byte[] body = mapper.writeValueAsBytes(entity);
            request.setEntity(new ByteArrayEntity(body));

            HttpResponse response = executeRequestWithRetry(request, 2);
            int responseStatusCode = response.getStatusLine().getStatusCode();
            if (responseStatusCode == 200) {
                log.info("Returning a JSON mapped object from calling api integration point");
                T mappedJsonObject = JSONUtils.getMappedJsonObject(returnEntityType, utils.getContentsFromHttpResponse(response));
                mappedJsonObject.setTestRailService(this);
                return mappedJsonObject;
            } else {
                Error error = JSONUtils.getMappedJsonObject(Error.class, utils.getContentsFromHttpResponse(response));
                log.error("Response code: {}", responseStatusCode);
                log.error("TestRails reported an error message: {}", error.getError());
            }
        }
        catch (IOException e) {
            log.error(String.format("An IOException was thrown while trying to process a REST Request against URL: [%s]", completeUrl), e);
            throw new RuntimeException(String.format("Connection is null, check URL: %s", completeUrl), e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
		return null;
    }

    /**
     * Execute POST request with retry
     * @param request
     * @param retries
     * @return
     * @throws IOException
     */
    private HttpResponse executeRequestWithRetry(HttpPost request, int retries) throws IOException {
        boolean connected = false;
        int RETRY_DELAY_MS = 0;
        int retryDelayInMS;

        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;

        for (int retry = 0; retry < retries && !connected; retry++) {
            if (retry > 0) {
                log.warn("retry " + retry + "/" + retries);
                try {
                    log.debug("Sleeping for retry: " + RETRY_DELAY_MS);
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException e) {
                    // lets ignore this
                }
            }

            // try posting request
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 429) {
                log.warn(" **429 for POST**");
                retryDelayInMS = Integer.parseInt(response.getFirstHeader("Retry-After").getValue()) * 1000; // sec to ms
                RETRY_DELAY_MS = retryDelayInMS;  // set delay and retry
            } else {
                break; // if not 429, break
            }
        }
        return response;
    }
}

