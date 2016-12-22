package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class has all the fields that you can provide when creating a TestRun
 *
 * @author Colin McCormack
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRunCreator extends BaseEntity {

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("suite_id")
    private Integer suiteId;
    public Integer getSuiteId() { return suiteId; }
    public void setSuiteId(Integer suiteId) { this.suiteId = suiteId; }

    @JsonProperty("milestone_id")
    private Integer milestoneId;
    public Integer getMilestoneId() { return milestoneId; }
    public void setMilestoneId(Integer milestoneId) { this.milestoneId = milestoneId; }

    @JsonProperty("include_all")
    private Boolean includeAll;
    public Boolean isIncludeAll() { return includeAll; }
    public void setIncludeAll(Boolean includeAll) { this.includeAll = includeAll; }

    @JsonProperty("assignedto_id")
    private Integer assignedToId;
    public Integer getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Integer assignedToId) { this.assignedToId = assignedToId; }

    @JsonProperty("case_ids")
    private Integer[] caseIds;
    public Integer[] getCaseIds() { return caseIds; }
    public void setCaseIds(Integer[] caseIds) { this.caseIds = caseIds; }
}
