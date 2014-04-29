package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A TestRails TestCase entity. In TestRails, a TestCase is more or less a "template" for executing a sequence of steps--you do not report
 * TestResults against a TestCase, but against a TestInstance (represented as a "Test" in TestRail, but named "TestInstance" here as to
 * avoid conflicts with the @Test annotation found in popular xUnit frameworks
 *
 * If you have custom fields on TestCases in TestRails, it will be necessary to extend this class and add those fields in order to capture them.
 * Otherwise they will be ignored.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @JsonProperty("title")
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("section_id")
    private Integer sectionId;
    public Integer getSectionId() { return sectionId; }
    public void setSectionId(Integer sectionId) { this.sectionId = sectionId; }

    @JsonProperty("type_id")
    private Integer typeId;
    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }

    @JsonProperty("priority_id")
    private Integer priorityId;
    public Integer getPriorityId() { return priorityId; }
    public void setPriorityId(Integer priorityId) { this.priorityId = priorityId; }

    @JsonProperty("estimate")
    private String estimate;
    public String getEstimate() { return estimate; }
    public void setEstimate(String estimate) { this.estimate = estimate; }

    @JsonProperty("estimate_forecast")
    private String estimateForecast;
    public String getEstimateForecast() { return estimateForecast; }
    public void setEstimateForecast(String estimate_forecast) { this.estimateForecast = estimate_forecast; }

    @JsonProperty("refs")
    private String refs;
    public String getRefs() { return refs; }
    public void setRefs(String refs) { this.refs = refs; }

    @JsonProperty("milestone_id")
    private Integer milestoneId;
    public Integer getMilestoneId() { return milestoneId; }
    public void setMilestoneId(Integer milestoneId) { this.milestoneId = milestoneId; }

    @JsonProperty("created_by")
    private Integer createdBy;
    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    @JsonProperty("created_on")
    private String createdOn;
    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }

    @JsonProperty("updated_by")
    private String updatedBy;
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    @JsonProperty("updated_on")
    private String updatedOn;
    public String getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(String updatedOn) { this.updatedOn = updatedOn; }

    @JsonProperty("suite_id")
    private Integer suiteId;
    public Integer getSuiteId() { return suiteId; }
    public void setSuiteId(Integer suiteId) { this.suiteId = suiteId; }

    /**
     * Update the type for this TestCase--NOTE: This method actually makes the request to TestRails. Use it carefully!
     * @param type The type id for the TestCase. This value is determined by your specific TestRails implementation. Consult your
     *             TestRails administrator to find out the value you need
     */
    public void updateType( int type ) {
        getTestRailService().updateTestCaseType(this.getId(), type);
    }
}