package com.rmn.testrail.entity;

import com.rmn.testrail.service.TestRailService;

import java.io.Serializable;

/**
 * @author mmerrell
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -3770248751816746138L;

    private TestRailService testRailService;
    protected TestRailService getTestRailService() { return testRailService; }
    public void setTestRailService(TestRailService testRailService) { this.testRailService = testRailService; }
}
