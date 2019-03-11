# TestRailSDK

Integrate TestRail into your projects without having to know their API

[Migration and New Features Guide for Version 1.1](Migration and New Features Guide v1.1.md)

## Installation

This is a Maven project, so you can either deploy a SNAPSHOT jar to your local Maven repo, or you can just point to the latest version in Maven Central

### Building From Source
Download the source, then from the command-line, run the `mvn install` command. This will deploy a SNAPSHOT jar file to your local Maven repo

Once the jar has been deployed, you should be able to include a small snippet in your pom file:

    <dependency>
        <groupId>com.retailmenot</groupId>
        <artifactId>testrailsdk</artifactId>
        <version>1.3.1</version>
    </dependency>

If you're currently using Maven, and your repos, paths, and IDE are all set up correctly, you should be able to address the classes in this project immediately.

If you prefer to build a jar file and include it into your classpath, run `mvn package`, and the jar file should appear in the target folder under the main folder.

### Maven Central
To point to the jar file in Maven Central, include this xml snippet in your pom.xml file:

```xml
    <dependency>
        <groupId>com.retailmenot</groupId>
        <artifactId>testrailsdk</artifactId>
        <version>1.3.1</version>
    </dependency>
```

## Supported TestRails API Version

This SDK supports the v2 version of the TestRails API. We will attempt to maintain backwards compatibility when the API changes, but this support cannot be assured. The more help we get on this project, the better the likelihood of it happening.

### Project Maturity
This project has been in general use within our offices for over a year, having been used initially with their v1 API, and having been refactored to support v2. The original design was strong enough to allow us to maintain backwards compatibility and only rewrite the innards. It is fairly mature in terms of what we support, but there are entities and operations introduced in v2 that we don't yet support. Those exceptions will be noted below.

## Entities supported

**Template-style entities**--test entities which form the basis of a Test Run, but which never get Test Results reported to them:

* Project - A collection of TestSuites
* TestSuite - A collection of Test Sections and/or Test Cases
* Section (Optional) - An arbitrary grouping of Test Cases within a Test Suite. They are optional, and Sections can also contain other Sections
* TestCase - A single Test Case, which forms the basis of a TestInstance. Results are never reported to TestCases--they are reported to TestInstances

**Execution-style entities**--these entities, based on the ones above, are the ones used in reporting and releases:

* TestPlan - A grouping of Test Suites, Sections, and Test Cases that can be used as a template for a Test Run
* TestRun - An execution plan for a given Release, which acts as a "record" of the state of a release
* TestInstance - A single instance of a Test Case in the context of a Test Run. Results of "PASS", "FAIL", "WARNING", etc are reported directly against this entity
* TestResult - The execution record of a single Test Instance within a given Test Run

## Modeling Custom Fields

TestRail allows you to add custom fields to many entities. We support this, but you'll have to work a little harder to implement. When parsing the JSON entities that support custom fields, the Jackson processor ignores "unknown" fields

One of the more common customizations is to make a new Test Status--the only default statuses (as of v2) are "Passed", "Blocked", "Failed", "Untested", and "Retest". In our project, we have added "Warning" (for when we want to distinguish between tests that had problems during set-up or tear-down) and "Error" (when the automated execution itself fails, for some reason not related to the application under test). In order to model this properly, we use the static method TestStatus.addStatus():

```
    TestStatus.addStatus("Skipped", 6);
    TestResult result = new TestResult();
    result.setVerdict("Skipped");
    result.setComment("SKIPPED result worked!!");
    TestIntegrationSuite.service.addTestResult(13529, result);
```

Contact your administrator to get the ID of the custom status--they should be able to tell you how they set it up. The source contains unit tests (`TestRailServiceIntegrationTest.testAddCustomTestCaseStatus`)


## Service vs Entities

You can either call the TestRailService directly, which exposes various methods for creating/querying/updating/deleting entities, or you can allow the service to return real objects, which have more semantic method definitions, allowing for richer, easy to use syntax.

## Entity Usage

Each object can be used in either List or Singleton mode, depending on your context. Many query methods accept IDs (the id # of the object) you're looking for, but many also allow you to search entities by name or by other attributes.

## Examples

These don't contain much in the way of error-handling: if the objects you're querying are null, you'll get null back. Check for their existence before calling further methods.

### Print out the names of all the TestRuns (active and inactive) in the project
```
        //Get the current TestPlan, based on the project and test plan name
        Project project = testRailService.getProjectByName(projectName);
        TestPlan plan = project.getTestPlanByName(testPlanName);
        
        //That test plan will contain one or more test runs, each of 
        // which contains multiple "Tests" (or TestInstances as we call them)

        // The TestInstance is the entity we care about when we're reporting the test result
        List<TestRun> testRuns = plan.getTestRuns();

        //Iterate through the test runs, and build a map of "TestCase ID" to "TestInstance ID"
        for ( TestRun testRun: testRuns ) {
            System.out.println("TestRun: " + testRun.getName());
        }
```

### Add a TestResult to a TestInstance

```
        TestResult result = new TestResult();
        result.setTestId(5);
        result.setAssignedtoId(10);
        result.setVerdict("Passed");
        result.setComment("Test Foo: PASS");
        TestIntegrationSuite.service.addTestResult(13529, result);
```

## Contributing

All pull requests are greatly appreciated! This project is intended for all users of TestRails, but we've only implemented the methods and objects we've needed to handle our own workload. If you need new features, open an issue on github, or better yet, contribute your own features! We've made every attempt to keep the code simple and clean, so you should be able to follow our examples.

The TestRail SDK is a Maven/Java project, using JUnit for unit tests, Cobertura for coverage checks, and Apache commons libraries for the HTTP work.



### Executing the Service Integration Tests
If you choose to build locally from IDEA, Eclipse, or even command line, you'll have to follow these steps to make the unit tests work:

**Important! These integration tests will first query your TestRail account in order to build a hierarchy of queryable items. It will, in some cases, alter data in your account unless you follow the instructions below!**

* Open testrails.properties.sample
* Edit 3 fields inside to set up the `clientId` (usually your <companyname>.testrail.com address), `username`, and `password`
 * For those with LOCAL instances of TestRail: there's a new field called "api_endpoint" to put in the properties file (e.g. "https://secure-ip/testrail/"). Be sure to include the final forward slash ("/")!
* Locate the `destructiveTestsOk` field and set it to `true` or `false`--this will determine whether it runs only the read-only tests or will also execute tests that update test cases, add test results, etc
* The assignedToId field is the id of the user you wish to use for reporting TestResults in the unit tests. It is recommended to use an account dedicated to automation, which leaves a nice audit trail and allows you to quickly determine what has been altered by these tests
* Execute the following command to build the project and run the unit tests:

```
    mvn clean verify 
```
The log output should explain clearly which entities were queried and altered within your account.

## Maintainers Only - Releasing to Production

Prerequisite:
1. Install gnupg
```
    brew install gnupg2
```
2. You may require a symlink to gpg from gpg2 due to the age of the maven-gpg-plugin. Duplicate the `gpg` symlink and rename it `gpg2` inside of `/usr/bin/local`

The following command should sign and upload a jar to the maven repository:
```
    mvn clean verify javadoc:jar source:jar gpg:sign deploy
```

1. Log into `https://oss.sonatype.org/`.
2. Once you have uploaded a repository go to "Staging Repositories"
3. Find the module (usually has com.retailmenot-#### in the name where #### is the build number). Typically at the very bottom.
4. Select and close the repository.
5. Once the repository successfully closes (which requires javadocs and signing), select and release the repository.
6. The repository will be available on maven shortly.

## License

This project has been released under the MIT license. Please see the license.txt file for more details.
