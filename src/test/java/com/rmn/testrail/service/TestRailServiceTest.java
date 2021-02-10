package com.rmn.testrail.service;


import com.rmn.testrail.entity.Project;
import com.rmn.testrail.entity.Section;
import com.rmn.testrail.entity.TestCase;
import com.rmn.testrail.entity.TestInstance;
import com.rmn.testrail.entity.TestPlan;
import com.rmn.testrail.entity.TestResult;
import com.rmn.testrail.entity.TestRun;
import com.rmn.testrail.entity.TestSuite;
import com.rmn.testrail.util.MockHTTPUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TestRailServiceTest {
    private TestRailService getTestRailsEntities(String fileName) {
        MockHTTPUtils utils = new MockHTTPUtils();
        TestRailService service = new TestRailService();
        service.setHttpUtils( utils );

        String contents = getFileContents(fileName);
        utils.setContentsFromConnection(contents);
        return service;
    }

    @Test
    public void testGetProjects() {
        TestRailService service = getTestRailsEntities("ProjectsPaged.json");
        List<Project> projects = service.getProjects();
        Assert.assertEquals("Sandbox", projects.get(0).getName());
    }

    @Test
    public void testGetTestSuites() {
        TestRailService service = getTestRailsEntities("TestSuites.json");
        List<TestSuite> suites = service.getTestSuites(0);
        Assert.assertEquals("Test Suite", suites.get(0).getName());
    }

    @Test
    public void testGetSections() {
        TestRailService service = getTestRailsEntities("SectionsPaged.json");
        List<Section> sections = service.getSections(0, 0);
        Assert.assertEquals("All Test Cases",  sections.get(0).getName());
    }

    @Test
    public void testGetTestCase() {
        TestRailService service = getTestRailsEntities("TestCase.json");
        TestCase testCase = service.getTestCase(0);
        Assert.assertEquals(Integer.valueOf(5), testCase.getCreatedBy());
        Assert.assertEquals(Integer.valueOf(1392300984), testCase.getCreatedOn());
        Assert.assertEquals("1m 5s", testCase.getEstimate());
        Assert.assertNull(testCase.getEstimateForecast());
        Assert.assertEquals(Integer.valueOf(1), testCase.getId());
        Assert.assertEquals(Integer.valueOf(7), testCase.getMilestoneId());
        Assert.assertEquals(Integer.valueOf(2), testCase.getPriorityId());
        Assert.assertEquals("RF-1, RF-2", testCase.getRefs());
        Assert.assertEquals(Integer.valueOf(1), testCase.getSectionId());
        Assert.assertEquals(Integer.valueOf(1), testCase.getSuiteId());
        Assert.assertEquals("Change document attributes (author, title, organization)", testCase.getTitle());
        Assert.assertEquals(Integer.valueOf(4), testCase.getTypeId());
        Assert.assertEquals(Integer.valueOf(1), testCase.getUpdatedBy());
        Assert.assertEquals(Integer.valueOf(1393586511), testCase.getUpdatedOn());
        Assert.assertEquals(Integer.valueOf(1), testCase.getTemplateId());

    }

    @Test
    public void testGetTestCases() {
        TestRailService service = getTestRailsEntities("TestCasesPaged.json");
        List<TestCase> testCases = service.getTestCases(0, 0);
        Assert.assertEquals("Test Case",  testCases.get(0).getTitle());
        Assert.assertEquals("Steve - First Test Case",  testCases.get(1).getTitle());
        testCases = service.getTestCases(0, 0, 0);
        Assert.assertEquals("Test Case",  testCases.get(0).getTitle());
        Assert.assertEquals("Steve - First Test Case",  testCases.get(1).getTitle());
        testCases = service.getTestCasesSingleSuiteMode(0);
        Assert.assertEquals("Test Case",  testCases.get(0).getTitle());
        Assert.assertEquals("Steve - First Test Case",  testCases.get(1).getTitle());
        testCases = service.getTestCasesSingleSuiteMode(0, 0);
        Assert.assertEquals("Test Case",  testCases.get(0).getTitle());
        Assert.assertEquals("Steve - First Test Case",  testCases.get(1).getTitle());
    }

    @Test
    public void testGetCaseFields() {
        TestRailService service = getTestRailsEntities("TestCaseCustomFields.json");
        String customFields = service.getCaseFields();
        Assert.assertEquals("[  {    \"configs\": [      {        \"context\": {          \"is_global\": true,          \"project_ids\": null        },        \"id\": \"..\",        \"options\": {          \"default_value\": \"\",          \"format\": \"markdown\",          \"is_required\": false,          \"rows\": \"5\"        }      }    ],    \"description\": \"The preconditions of this test case. ..\",    \"display_order\": 1,    \"id\": 1,    \"label\": \"Preconditions\",    \"name\": \"preconds\",    \"system_name\": \"custom_preconds\",    \"type_id\": 3  },  {    \"configs\": [      {        \"context\": {          \"is_global\": false,          \"project_ids\": null        },        \"id\": \"..\",        \"options\": {          \"default_value\": \"\",          \"format\": \"markdown\",          \"is_required\": false,          \"rows\": \"5\"        }      }    ],    \"description\": \"The custom test field of this test case. ..\",    \"display_order\": 1,    \"id\": 1,    \"label\": \"Preconditions\",    \"name\": \"testfield\",    \"system_name\": \"custom_test\",    \"type_id\": 3  }]", customFields);
    }

    @Test
    public void testGetCaseTypes() {
        TestRailService service = getTestRailsEntities("TestCaseTypes.json");
        String customFields = service.getCaseTypes();
        Assert.assertEquals("[  {    \"id\": 1,    \"is_default\": false,    \"name\": \"Automated\"  },  {    \"id\": 2,    \"is_default\": false,    \"name\": \"Functionality\"  },  {    \"id\": 6,    \"is_default\": true,    \"name\": \"Other\"  }]", customFields);
    }

    @Test
    public void testGetConfigurations() {
        TestRailService service = getTestRailsEntities("Configurations.json");
        String customFields = service.getCaseTypes();
        Assert.assertEquals("[  {    \"configs\": [      {        \"group_id\": 1,        \"id\": 1,        \"name\": \"Chrome\"      },      {        \"group_id\": 1,        \"id\": 2,        \"name\": \"Firefox\"      },      {        \"group_id\": 1,        \"id\": 3,        \"name\": \"Internet Explorer\"      }    ],    \"id\": 1,    \"name\": \"Browsers\",    \"project_id\": 1  },  {    \"configs\": [      {        \"group_id\": 2,        \"id\": 6,        \"name\": \"Ubuntu 12\"      },      {        \"group_id\": 2,        \"id\": 4,        \"name\": \"Windows 7\"      },      {        \"group_id\": 2,        \"id\": 5,        \"name\": \"Windows 8\"      }    ],    \"id\": 2,    \"name\": \"Operating Systems\",    \"project_id\": 1  }]", customFields);
    }

    @Test
    public void testGetTestRuns() {
        TestRailService service = getTestRailsEntities("TestRunsPaged.json");
        List<TestRun> testRuns = service.getTestRuns(0);
        Assert.assertEquals("Test Suite", testRuns.get(0).getName());
    }

    @Test
    public void testGetTestPlans() {
        TestRailService service = getTestRailsEntities("TestPlansPaged.json");
        List<TestPlan> testPlans = service.getTestPlans(0);
        Assert.assertEquals("Test Plan 2",  testPlans.get(0).getName());
    }

    @Test
    public void testGetTestResults() {
        TestRailService service = getTestRailsEntities("TestResultsPaged.json");
        List<TestResult> testResults = service.getTestResults(0);
        Assert.assertEquals(186187, (int) testResults.get(0).getId());
    }

    @Test
    public void testGetTests() {
        TestRailService service = getTestRailsEntities("TestInstancesPaged.json");
        List<TestInstance> testResults = service.getTests(0);
        Assert.assertEquals(6408, (int) testResults.get(0).getCaseId());
    }

    /**
     * Returns the contents of the given file as a List of Strings
     * @param filename The name of the file to read. This file must be on the classpath at runtime
     * @return String with file contents
     */
    public static String getFileContents( String filename ) {
        InputStream stream = TestRailServiceTest.class.getClassLoader().getResourceAsStream( filename );

        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String strLine;
            while ((strLine = br.readLine()) != null )   {
                contents.append(strLine);
            }
            stream.close();
        } catch (IOException ex) {
            throw new RuntimeException("Could not read file: " + filename);
        }
        return contents.toString();
    }
}
