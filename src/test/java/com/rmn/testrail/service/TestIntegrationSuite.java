package com.rmn.testrail.service;

import com.rmn.testrail.entity.Project;
import com.rmn.testrail.entity.Section;
import com.rmn.testrail.entity.TestCase;
import com.rmn.testrail.entity.TestInstance;
import com.rmn.testrail.entity.TestPlan;
import com.rmn.testrail.entity.TestRun;
import com.rmn.testrail.entity.TestSuite;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * @author mmerrell
 */
@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = { TestRailServiceIntegrationTest.class })
public class TestIntegrationSuite {
    private static Logger log = LoggerFactory.getLogger(TestIntegrationSuite.class.getSimpleName());

    //The setUp method initializes the list of projects, and the rest of the suite uses information from there
    protected static TestRailService service = new TestRailService();
    public static TestRailService getService() { return service; }

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
            log.info("Your testrails.properties file is blank--shutting off destructive tests");
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
            log.info("Your testrails.properties file does not contain the 'destructiveTestsOk' property--shutting off destructive tests");
        } else {
            destructiveTestsOk = Boolean.valueOf(destructiveTestsOkProperty);
            log.info("Located 'destructiveTestsOk' property--setting to " + destructiveTestsOk);
        }

        //Call the method that will extract the correct properties into the correct place within the Service
        initService(properties);


        //We have to report any test results against a user. That will be gathered from the properties file. If it's not there,
        // don't run the tests that report results, regardless of the value set on destructiveTestsOk
        String assignedToIdProperty = properties.getProperty("assignedToId");
        if (StringUtils.isEmpty(assignedToIdProperty)) {
            assignedToId = 0;
            destructiveTestsOk = false;
            log.info("'assignedToId' property is missing--shutting off destructive tests");
        } else {
            assignedToId = Integer.valueOf(assignedToIdProperty);
            log.info("Using 'assignedToId' value: " + assignedToId);
        }

        //Verify that we can actually talk to the service
        Assume.assumeTrue(getService().verifyCredentials());

        //Initialize the Projects data used throughout the test. Each test will make an assumption about the data available, and if it's not, the test will be skipped
        initScenario();
    }

    /**
     * This will pull the correct properties into the correct attributes for the service, based on how you have them defined in the properties files.
     * It will use the clientId method by default, but if clientId is null, it will try to pull in the api_endpoint property. You can manipulate the
     * properties files to have this take the path you want. It will (currently) not run both, just to prevent a heavy load on the server. There are
     * two integration tests in place already to test them in isolation (assuming you have one or both set up correctly).
     *
     * If both clientId and apiEndpoint are present, apiEndpoint will be ignored in favor of using the clientId for the "hosted" TestRail Instance
     * @param properties
     */
    private static void initService(Properties properties) throws MalformedURLException {
        //Set up all the credentials

        //If clientId isn't in there or it's blank, look for api_endpoint. If it's there and not null, that means we have a "local" TestRail Instance.
        // Set the service's apiEndpoint accordingly for the remainder of the test.
        //If both properties are defined, api_endpoint will be ignored
        String clientId = properties.getProperty("clientId");
        if (null == clientId || clientId.isEmpty()) {
            String apiEndpoint = properties.getProperty("api_endpoint");
            Assume.assumeNotNull(apiEndpoint);
            getService().setApiEndpoint(new URL(apiEndpoint));
        } else {
            //If clientID wasn't null after all, set it, and use the "hosted" TestRail Instance code path
            getService().setClientId(clientId);
        }

        String username = properties.getProperty("username");
        Assume.assumeNotNull(username);

        String password = properties.getProperty("password");
        Assume.assumeNotNull(password);

        getService().setUsername(username);
        getService().setPassword(password);
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
        for (Project currentProject: getService().getProjects()) {
            if (currentProject.getName().equals("Sandbox")) {
                project = currentProject;
            } else {
                continue;
            }

            if (project == null || project.isCompleted()) {
                throw new RuntimeException("There was no 'Sandbox' project associated with this account, or the Sandbox project was already marked as 'Complete'. Please ensure there is an open Sandbox project in this account");
            }

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

                        testCase = getService().getTestCase(testInstance.getCaseId());
                        log.debug("Using TestCase [{}] for testing", testCase.getTitle());

                        testSuite = getService().getTestSuite(testCase.getSuiteId());
                        section = getService().getSections(project.getId(), testSuite.getId()).get(0);
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
