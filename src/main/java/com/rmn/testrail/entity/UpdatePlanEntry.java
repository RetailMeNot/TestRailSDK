package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author vliao
 */
public class UpdatePlanEntry extends BaseEntity {
    @JsonProperty("assignedto_id")
    private Integer assignedToId;
    public Integer getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Integer assignedToId) { this.assignedToId = assignedToId; }

    @JsonProperty("include_all")
    private boolean includeAll;
    public boolean getIncludeAll() { return includeAll; }
    public void setIncludeAll(boolean includeAll) { this.includeAll = includeAll; }

    @JsonProperty("case_ids")
    private List<Integer> caseIds;
    public List<Integer> getCaseIds() { return caseIds; }
    public void setCaseIds(List<Integer> caseIds) { this.caseIds = caseIds; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description") //requires TestRail 5.2 or later
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
