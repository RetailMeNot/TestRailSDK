package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

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
    private Integer createdOn;
    public Integer getCreatedOn() { return createdOn; }
    public void setCreatedOn(Integer createdOn) { this.createdOn = createdOn; }

    @JsonProperty("updated_by")
    private Integer updatedBy;
    public Integer getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Integer updatedBy) { this.updatedBy = updatedBy; }

    @JsonProperty("updated_on")
    private Integer updatedOn;
    public Integer getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(Integer updatedOn) { this.updatedOn = updatedOn; }

    @JsonProperty("suite_id")
    private Integer suiteId;
    public Integer getSuiteId() { return suiteId; }
    public void setSuiteId(Integer suiteId) { this.suiteId = suiteId; }

    @JsonProperty("template_id") //requires TestRail 5.2 or later
    private Integer templateId;
    public Integer getTemplateId() { return templateId; }
    public void setTemplateId(Integer templateId) { this.templateId = templateId; }

    @JsonProperty("custom_state")
    private Integer customState;
    public Integer getCustomState() { return customState; }
    public void setCustomState(Integer customState) { this.customState = customState; }

    @JsonProperty("custom_steps_separated")
    private List<Step> customStepsSeparated;
    public List<Step> getSteps() { return customStepsSeparated; }
    public void setSteps(List<Step> customStepsSeparated) { this.customStepsSeparated = customStepsSeparated; }
    public void addStep(Step step) { this.customStepsSeparated.add(step); }

    @JsonProperty("custom_preconds")
    private String customPreconditions;
    public String getCustomPreconditions() { return customPreconditions; }
    public void setCustomPreconditions(String customPreconditions) { this.customPreconditions = customPreconditions; }

    /**
     * Update (as in upload changes to TestRail) this TestCase--NOTE: This method actually makes the request to TestRails. Use it carefully!
     */
    public void updateTestCase() {
        getTestRailService().updateTestCase(this, this.getId());
    }
}