package com.rmn.testrail.entity;

import org.junit.Assert;
import org.junit.Test;

public class TestRunCreatorTest {
	TestRunCreator testRunCreator = new TestRunCreator();

	@Test
	public void nameGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.getName());
		testRunCreator.setName("New Name");
		String expectedName = "New Name";
		Assert.assertEquals(expectedName, testRunCreator.getName());
	}

	@Test
	public void descriptionGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.getDescription());
		testRunCreator.setDescription("New Description");
		String expectedDescription = "New Description";
		Assert.assertEquals(expectedDescription, testRunCreator.getDescription());
	}

	@Test
	public void suiteIdGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.getSuiteId());
		testRunCreator.setSuiteId(666);
		Integer expectedId = 666;
		Assert.assertEquals(expectedId, testRunCreator.getSuiteId());
	}

	@Test
	public void milestoneIdGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.getMilestoneId());
		testRunCreator.setMilestoneId(777);
		Integer expectedId = 777;
		Assert.assertEquals(expectedId, testRunCreator.getMilestoneId());
	}

	@Test
	public void includeAllGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.isIncludeAll());
		testRunCreator.setIncludeAll(true);
		Boolean expectedIncludeAllValue = true;
		Assert.assertEquals(expectedIncludeAllValue, testRunCreator.isIncludeAll());
	}

	@Test
	public void assignedToIdGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.getAssignedToId());
		testRunCreator.setAssignedToId(888);
		Integer expectedId = 888;
		Assert.assertEquals(expectedId, testRunCreator.getAssignedToId());
	}

	@Test
	public void caseIdsGettersAndSettersShouldWorksCorrectly() {
		Assert.assertNull(testRunCreator.getCaseIds());
		testRunCreator.setCaseIds(new Integer[]{111, 22, 3});
		Integer[] expectedCasesId = {111, 22, 3};
		Assert.assertEquals(expectedCasesId, testRunCreator.getCaseIds());
	}
}