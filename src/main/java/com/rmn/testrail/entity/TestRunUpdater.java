package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class has the available fields when updating a TestRun.
 *
 * @author Sergey Franchuk
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRunUpdater extends BaseEntity {

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("milestone_id")
    private Integer milestoneId;
    public Integer getMilestoneId() { return milestoneId; }
    public void setMilestoneId(Integer milestoneId) { this.milestoneId = milestoneId; }

    @JsonProperty("include_all")
    private Boolean includeAll;
    public Boolean isIncludeAll() { return includeAll; }
    public void setIncludeAll(Boolean includeAll) { this.includeAll = includeAll; }

    @JsonProperty("case_ids")
    private Integer[] caseIds;
    public Integer[] getCaseIds() { return caseIds; }
    public void setCaseIds(Integer[] caseIds) { this.caseIds = caseIds; }
}
