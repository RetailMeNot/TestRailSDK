# Upgrade guide for migration from TestRailSDK <0.9 and below to TestRailSDK 1.1 and above
Please do NOT use versions below 1.1.6! Certain methods had bugs with them that were fixed.

## Test Cases
### Get Test Cases:
Now supports single suite mode. To omit `suiteId`, call `getTestCasesSingleSuiteMode`.
Now supports request filters. To add a filter, call `getTestCases` with extra `ApiFilterValue` parameters like so: `getTestCases(projectId, suiteId, sectionId, parameter1, parameter2, ... parameterN)`. To create an ApiFilterValue parameter, declare a `new ApiFilterValue(GetCasesFilter.<ENUM>, <VALUE>);`

### TestCase Object:
now supports property `templateId`
`createdOn` is now an Integer
`updatedBy` is now an Integer
`updatedOn` is now an Integer
Now supports updating any and all fields using `testCaseObject.updateTestCase();`

### Add Test Cases:
Now supports adding a test case.
Method is `addTestCase(TestCase testCase, int sectionId);`

### Delete Test Cases:
Now supports deleting a test case. Method is `deleteTestCase(int caseId);` **Please note: Deleting a test case cannot be undone and also permanently deletes all test results in active test runs (i.e. test runs that haven't been closed (archived) yet).**

## Case Fields
### Get Case Fields:
Now supports getting a test case fields. Method is `getCaseFields();`

## Case Types
### Get Case Types:
Now supports getting test case types. Method is `getCaseTypes();`

## Configurations
### Get Configurations:
Now supports getting configurations. Method is `getConfigurations();`

### Add Configuration:
Now supports adding a configuration. Method is `public void addConfig(String name, int configGroupId);`

### Add Configuration Group:
Now supports adding a configuration group. Method is `getConfigGroup(String name, int projectId);`

### Update Configuration Group:
Now supports updating a configuration group. Method is `updateConfigGroup(String name, int configGroupId);`

### Update Configuration:
Now supports updating a configuration. Method is `updateConfig(String name, int configId);`

### Delete Configuration Group:
Now supports deleting a configuration group. Method is `deleteConfigGroup(int configGroupId);` **Please note: Deleting a configuration group cannot be undone and also permanently deletes all configurations in this group. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.**

### Delete Configuration:
Now supports deleting a configuration. Method is `deleteConfig(int configId);` **Please note: Deleting a configuration cannot be undone. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.**

## Milestones
### Get Milestone:
Now supports `isCompleted` filter. To use call `getMilestones` with additional `ApiFilterValue` parameter. Method declaring a new ApiFilterValue is `new ApiFilterValue(GetMilestonesFilter.IS_COMPLETED, {"1"|"0"});`

### Add Milestone:
Now uses `EmptyMilestone` type for creating milestones. This will not allow user to add parameters that are not accepted. Method is `public Milestone addMilestone(EmptyMilestone milestone, int projectId);`

### Update Milestone:
Now supports updating a milestone. Method is `updateMilestone(int milestoneId, boolean isCompleted);`

## Plans
### Get Plans:
Now supports request filters. To add a filter, call `getTestPlans` with extra `ApiFilterValue` parameters like so: `getTestCases(projectId, suiteId, sectionId, parameter1, parameter2, ... parameterN)`. To create an ApiFilterValue parameter, declare a `new ApiFilterValue(GetPlansFilter.<ENUM>, <VALUE>);`

### Add Plan:
Now supports description field in TestPlanCreator object.
Remove no longer present field `assignedTo_id`

### Add Plan Entry:
Now supports description field in PlanEntry object.

### Update Plan:
Now supports updating a plan. Method is `updateTestPlan(int planId, TestPlanCreator testPlan)`

### Update Plan Entry:
Now supports updating a plan entry. Uses UpdatePlanEntry object, similar to PlanEntry but with less fields. Method is `updateTestPlanEntry(int planId, int entryId, UpdatePlanEntry updatePlanEntry)`

### Close Plan:
Now supports closing a plan. Method is `closeTestPlan(int planId)` **Please note: Closing a test plan cannot be undone.**

### Delete Plan:
Now supports deleting a plan. Method is `deleteTestPlan(int planId)` **Please note: Deleting a test plan cannot be undone and also permanently deletes all test runs & results of the test plan.**

### Delete Plan Entry:
Now supports deleting a plan. Method is `deleteTestPlanEntry(int planId, int entryId)` **Please note: Deleting a test run from a plan cannot be undone and also permanently deletes all related test results.**

## Priorities
### Get Priorities
Now supports getting priorities. Method is `getPriorities();`

## Projects
### Get Projects
Now supports `isCompleted` filter. To use call `getProjects` with additional `ApiFilterValue` parameter. Method declaring a new ApiFilterValue is `new ApiFilterValue(GetProjectsFilter.IS_COMPLETED, {"1"|"0"});`

### Add Project
Now supports adding a project. Method is `addProject(ProjectNew newProject);`

### Update Project
Now supports updating a project. Method is `updateProject(int projectId, final boolean isCompleted);`

### Delete Project
Now supports deleting a project. Method is `deleteProject(int projectId);` **Please note: Deleting a project cannot be undone and also permanently deletes all test suites & cases, test runs & results and everything else that is part of the project.**

## Results
### Get Results
Now supports request filters. To add a filter, call `getTestResults` with extra `ApiFilterValue` parameters like so: `getTestCases(testInstanceId, parameter1, parameter2, ... parameterN)`. To create an ApiFilterValue parameter, declare a `new ApiFilterValue(GetResultsFilter.<ENUM>, <VALUE>);`

### Get Results For Case
Now supports getting results for a particular case. Method is `getTestResultsForCase(int runId, int caseId, ApiFilterValue... apiFilters);`

### Get Results For Run
Now supports getting results for a particular run. Method is `getTestResultsForRun(int runId, ApiFilterValue... apiFilters);`

### Add Result
Now returns newly reported result.
Removed unsupported fields in TestResult object.

### Add Result For Case
Now supports adding result for a particular case. Method is `addTestResultForCase(int runId, int caseId, TestResult result);`

### Add Results
Now returns newly reported results as a TestResults object (not a list).

### Add Results for Cases
Now supports adding results for a particular case. Method is `addTestResultsForCases(int runId, TestResults result);`

## Result Fields
### Get Result Fields
Now supports getting result fields. Method is `getResultFields();`

## Runs
### Get Runs
Now supports request filters. To add a filter, call `getTestRuns` with extra `ApiFilterValue` parameters like so: `getTestCases(projectId, parameter1, parameter2, ... parameterN)`. To create an ApiFilterValue parameter, declare a `new ApiFilterValue(GetRunsFilter.<ENUM>, <VALUE>);`

### Update Run
Now supports updating a run. Method is `updateTestRun(int runId, TestRun testRun);`

### Close Run
Now returns TestRun object on closing.
Now supports closing a run just by run ID. Method is `closeTestRun(int runId);` **Please note: Closing a test run cannot be undone.**

### Delete Run
Now supports deleting a run. Method is `deleteTestRun(int runId);` **Please note: Deleting a test run cannot be undone and also permanently deletes all tests & results of the test run.**

## Sections
### Get Section
Now supports getting a section. Method is `getSection(int sectionId);`

## Statuses
### Get Statuses
Now supports getting statuses. Method is `getStatuses();`

## Suites
### Add Suite
Now supports adding a suite. Method is `addTestSuite(int projectId, TestSuiteCreator testSuite);`

### Update Suite
Now supports adding a suite. Method is `updateTestSuite(int suiteId, TestSuite testSuite);`

### Delete Suite
Now supports deleting a suite. Method is `deleteTestSuite(int suiteId);` **Please note: Deleting a test suite cannot be undone and also deletes all active test runs & results, i.e. test runs & results that weren't closed (archived) yet.**

## Templates
### Get Templates
Now supports getting templates. Method is `getTemplates();`

## Tests
### Get Test
Now support getting a test. Method is `getTest(int testId);`

### Get Tests
Now support new fields in TestInstance object.
Now supports request filter `status_id` in GetTestsFilter.
