package com.rmn.testrail.service;

import com.rmn.testrail.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mmerrell on 3/17/14
 */
@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = { TestRailServiceIntegrationTest.class })
public class TestIntegrationSuite {
    private static Logger log = LoggerFactory.getLogger(TestIntegrationSuite.class.getSimpleName());

    //The setUp method initializes the list of projects, and the rest of the suite uses information from there
    protected static TestRailService service = new TestRailService();
    protected static Project project;
    protected static TestPlan testPlan;
    protected static TestRun testRun;
    protected static TestInstance testInstance;
    protected static TestSuite testSuite;
    protected static TestCase testCase;
    protected static Section section;
    protected static Integer assignedToId;

    protected static boolean destructiveTestsOk = false;

    @BeforeClass
    public static void setUp() throws IOException, InterruptedException {
        //Get the TestRails credentials from the testrails.properties file
        Properties properties = new Properties();
        InputStream resource = TestRailServiceIntegrationTest.class.getClassLoader().getResourceAsStream("testrails.properties");
        if (null == resource) {
            destructiveTestsOk = false;
            return;
        }
        properties.load(resource);

        //destructiveTestsOk is a property in testrails.properties, which indicates whether or not this test suite should execute the set of tests
        // that add test results, update test cases, etc. If "true" it will pick a test case, and start to go to work on it. Everything will
        // be logged of course, but the API cannot undo this work--the end-points do not exist. You should only set this to "true" if you're
        // altering the code that works on these read-write methods
        String destructiveTestsOkProperty = properties.getProperty("destructiveTestsOk");
        if (StringUtils.isEmpty(destructiveTestsOkProperty)) {
            destructiveTestsOk = false;
        } else {
            destructiveTestsOk = Boolean.valueOf(destructiveTestsOkProperty);
        }

        //Set up all the credentials
        String clientId = properties.getProperty("clientId");
        Assume.assumeNotNull(clientId);

        String username = properties.getProperty("username");
        Assume.assumeNotNull(username);

        String password = properties.getProperty("password");
        Assume.assumeNotNull(password);

        service.setClientId(clientId);
        service.setUsername(username);
        service.setPassword(password);

        //We have to report any test results against a user. That will be gathered from the properties file. If it's not there,
        // don't run the tests that report results, regardless of the value set on destructiveTestsOk
        String assignedToIdProperty = properties.getProperty("assignedToId");
        if (StringUtils.isEmpty(assignedToIdProperty)) {
            assignedToId = 0;
            destructiveTestsOk = false;
        } else {
            assignedToId = Integer.valueOf(assignedToIdProperty);
        }

        //Verify that we can actually talk to the service
        Assume.assumeTrue(service.verifyCredentials());

        //Initialize the Projects data used throughout the test. Each test will make an assumption about the data available, and if it's not, the test will be skipped
        initScenario();
    }

    private static void initScenario() {
        try {
            initProject();
        } catch (Exception ex) {
            log.error("An exception was thrown while initializing entities for Integration Tests");
            ex.printStackTrace();
        }
    }

    private static void initProject() {
        for (Project currentProject: service.getProjects()) {
            if (currentProject.isCompleted()) {
                continue;
            }
            project = currentProject;
            log.debug("Using Project [{}] for testing", project.getName());

            for (TestPlan currentTestPlan: currentProject.getTestPlans()) {
                if (currentTestPlan.isCompleted()) {
                    continue;
                }
                testPlan = currentTestPlan;
                log.debug("Using TestPlan [{}] for testing", testPlan.getName());

                for (TestRun currentTestRun: currentTestPlan.getTestRuns()) {
                    if (currentTestRun.isCompleted()) {
                        continue;
                    }
                    testRun = currentTestRun;
                    log.debug("Using TestRun [{}] for testing", testRun.getName());

                    for (TestInstance currentTestInstance: currentTestRun.getTests()) {
                        if (currentTestInstance.getCaseId() == null) {
                            continue;
                        }
                        testInstance = currentTestInstance;
                        log.debug("Using TestInstance [{}] for testing", testInstance.getId());

                        testCase = service.getTestCase(testInstance.getCaseId());
                        log.debug("Using TestCase [{}] for testing", testCase.getTitle());

                        testSuite = service.getTestSuite(testCase.getSuiteId());
                        section = service.getSections(project.getId(), testSuite.getId()).get(0);
                        break;
                    }
                    if (testCase != null) {
                        break;
                    }
                }
                if (testRun != null) {
                    break;
                }
            }
            if (testPlan != null) {
                break;
            }
        }
    }
}
