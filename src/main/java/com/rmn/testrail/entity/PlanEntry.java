package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author jsteigel
 */
public class PlanEntry extends BaseEntity {
    @JsonProperty("suite_id")
    private Integer suiteId;
    public Integer getSuiteId() { return suiteId; }
    public void setSuiteId(Integer suiteId) { this.suiteId = suiteId; }

    @JsonProperty("assignedto_id")
    private Integer assignedToId;
    public Integer getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Integer assignedToId) { this.assignedToId = assignedToId; }

    @JsonProperty("include_all")
    private Boolean includeAll;
    public Boolean getIncludeAll() { return includeAll; }
    public void setIncludeAll(Boolean includeAll) { this.includeAll = includeAll; }

    @JsonProperty("config_ids")
    private List<Integer> configIds;
    public List<Integer> getConfigIds() { return configIds; }
    public void setConfigIds(List<Integer> configIds) { this.configIds = configIds; }

    @JsonProperty("runs")
    private List<PlanEntryRun> runs;
    public List<PlanEntryRun> getRuns() { return runs; }
    public void setRuns(List<PlanEntryRun> runs) { this.runs = runs; }

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
