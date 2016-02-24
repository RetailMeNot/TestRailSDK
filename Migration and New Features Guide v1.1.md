# Upgrade guide for TestRailsSDK 1.1

## Test Cases
### Get Test Cases:
Now supports single suite mode. To omit `suiteId`, call `getTestCasesSingleSuiteMode`.
Now supports custom filters. To add a filter, call `getTestCases` with extra `ApiFilterValue` parameters like so: `getTestCases(projectId, suiteId, sectionId, parameter1, parameter2, ... parameterN)`. To create an ApiFilterValue parameter, declare a `new ApiFilterValue(GetCasesFilter.<ENUM>, <VALUE>);`

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
Now supports `isCompleted` filter. To use call `getMilestones` with additional `ApiFilterValue` parameter. Method declaring a new ApiFilterValue is `new ApiFilterValue(GetMilestonesFilter.IS_COMPLETED, {"true"|"false"});`

### Add Milestone:
Now uses `EmptyMilestone` type for creating milestones. This will not allow user to add parameters that are not accepted. Method is `public Milestone addMilestone(EmptyMilestone milestone, int projectId);`

### Update Milestone:
Now supports updating a milestone. Method is `updateMilestone(int milestoneId, boolean isCompleted);`
