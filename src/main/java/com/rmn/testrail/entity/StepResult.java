package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

public class StepResult extends Step {

    public StepResult() {
    }

    public StepResult(Step step, String actual, Integer statusId) {
        super(step.getContent(), step.getExpected());
        this.actual = actual;
        this.statusId = statusId;
    }

    @JsonProperty("actual")
    private String actual;
    public String getActual() { return actual; }
    public void setActual(String actual) { this.actual = actual; }

    @JsonProperty("status_id")
    private Integer statusId;
    public Integer getStatusId() { return statusId; }
    public void setStatusId(Integer statusId) { this.statusId = statusId; }
}
