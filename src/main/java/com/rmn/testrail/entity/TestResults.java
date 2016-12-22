package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of TestResults entities
 *
 * @author mmerrell
 */
public class TestResults extends BaseEntity {
    @JsonProperty("results")
    private List<TestResult> results = new ArrayList<TestResult>();
    public List<TestResult> getResults() { return results; }
    public void setResults(List<TestResult> results) { this.results = results; }

    /**
     * Allows you to add a test result to the list of results that will be posted
     * @param result The TestResult object you want to add
     */
    public void addResult(TestResult result) {
        results.add(result);
    }
}
