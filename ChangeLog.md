##  Changes between 1.3 and 1.3.1
Fixed setTemplateId to target templateId instead of suiteId field

##  Changes between 1.2.1 and 1.3
Added SectionCreator
<br>
Added Step
<br>
Added StepResult

##  Changes between 1.2 and 1.2.1
Added more information to migration guide
<br>
Improved documentation on project
<br>
PlanEntry and PlanEntryRun classes now use object Boolean (instead of primitive boolean) because requests require null instead of default false
<br>
postRESTBodyReturn method logic was simplified, and the returned response has method setTestRailService called to initialize associated service

##  Changes between 1.1.6 and 1.2
Fixed a bug with setCaseId method declaring the wrong parameter name
<br>
Added new TestRunUpdater class with limited fields for updating test runs
<br>
The updateTestRun method now takes a TestRunUpdater object instead of a TestRun object - please update your code to use it instead

##  Changes between 1.1.5 and 1.1.6
Fixed a bug with setAssignedToId method declaring the wrong parameter name
<br>
Added unit tests for TestRunCreator Class

##  Changes between 1.1.4 and 1.1.5
Fixed a bug with setCaseIds method declaring the wrong parameter type and name

##  Changes between 1.1.3 and 1.1.4
Fixed a bug where an http connection for a GET request was being incorrectly set to output.

##  Changes between 1.1.2 and 1.1.3
Added ability to override authorization encoding logic in HTTPUtils.

##  Changes between 1.1.1 and 1.1.2
Fixed a bug where the end of the url was not attached to custom TestRail instantiations.

##  Changes between 1.1 and 1.1.1
Fixed a bug where "null" was being appended when a field was empty.

##  Changes between 0.9 and 1.1
There are many various new helpers for endpoints. Please refer to the [Migration and New Features Guide v1.1.md](Migration and New Features Guide v1.1.md)

##  Changes between 0.8 and 0.9

There are no breaking API changes. The new features introduced are all backward compatible. Please let the project team know if this turns out not to be the case (open an issue on github)

 * (issue #22) Adding retry for 429 response
 * (issue #9) `suite_mode` added to Project entity
 * (issue #9) `description` added to Section entity
 * (issue #9) `completed_on`, `is_completed`, `is_baseline`, `is_master` added to TestSuite entity
 * `add_run` endpoint support - allows you to create new TestRun entities
 * `add_plan` endpoint support - allows you to create new TestPlan entities
 * `add_milestone` endpoint support - allows you to create new Milestones
 * `get_result` and `get_results` endpoint support. You can now query TestResults!
 * `get_user`, `get_user_by_email`, and `get_users` endpoing support - allows you to query users
 * addTestPlanEntry() method for TestRun/Milestone support
 * added (private) `postRESTBodyReturn` method to TestRailService to make POST abstraction easier
 * added generics support to same, improved testability of the code, and added some integration tests
 * Made getEntityList and getEntitySingle protected instead of private (to facilitate customizations)
 * Fixed a bug which obscured the root cause of a connection error with the TestRail server. Now the stack trace will actually print correctly)
 * (issue #5) Downgraded to Java 1.6 at a user's request
 * Made integration tests query for a "Sandbox" project in which to perform the destructive tests
 * (issue #8) Added support for those with local TestRail instance to be able to do so (previous version only supported hosted installations, e.g. "https://mycompany.testrail.com/")
 * Unit tests updated accordingly (but not 100% coverage yet)
 
 
