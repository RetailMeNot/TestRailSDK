package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * This class has all the fields in a TestRun API request.
 *
 * @author mmerrell
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRun extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    @JsonProperty("config")
    private String config;
    public String getConfig() { return config; }
    public void setConfig(String config) { this.config = config; }

    @JsonProperty("is_completed")
    private Boolean isCompleted;
    public Boolean isCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    @JsonProperty("passed_count")
    private Integer passedCount;
    public Integer getPassedCount() { return passedCount; }
    public void setPassedCount(Integer passedCount) { this.passedCount = passedCount; }

    @JsonProperty("blocked_count")
    private Integer blockedCount;
    public Integer getBlockedCount() { return blockedCount; }
    public void setBlockedCount(Integer blockedCount) { this.blockedCount = blockedCount; }

    @JsonProperty("untested_count")
    private Integer untestedCount;
    public Integer getUntestedCount() { return untestedCount; }
    public void setUntestedCount(Integer untestedCount) { this.untestedCount = untestedCount; }

    @JsonProperty("retest_count")
    private Integer retestCount;
    public Integer getRetestCount() { return retestCount; }
    public void setRetestCount(Integer retestCount) { this.retestCount = retestCount; }

    @JsonProperty("failed_count")
    private Integer failedCount;
    public Integer getFailedCount() { return failedCount; }
    public void setFailedCount(Integer failedCount) { this.failedCount = failedCount; }

    @JsonProperty("created_by")
    private Integer createdBy;
    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy_by) { this.createdBy = createdBy_by; }

    @JsonProperty("created_on")
    private String createdOn;
    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }

    @JsonProperty("project_id")
    private Integer projectId;
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    @JsonProperty("plan_id")
    private Integer planId;
    public Integer getPlanId() { return planId; }
    public void setPlanId(Integer planId) { this.planId = planId; }

    @JsonProperty("assignedto_id")
    private Integer assignedtoId;
    public Integer getAssignedtoId() { return assignedtoId; }
    public void setAssignedtoId(Integer assignedtoId) { this.assignedtoId = assignedtoId; }

    @JsonProperty("include_all")
    private Boolean includeAll;
    public Boolean isIncludeAll() { return includeAll; }
    public void setIncludeAll(Boolean includeAll) { this.includeAll = includeAll; }

    @JsonProperty("completed_on")
    private Integer completedOn;
    public Integer getCompletedOn() { return completedOn; }
    public void setCompletedOn(Integer completedOn) { this.completedOn = completedOn; }

    @JsonProperty("url")
    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @JsonProperty("config_ids")
    private List<Integer> configIds;
    public List<Integer> getConfigIds() { return configIds; }
    public void setConfigIds(List<Integer> configIds) { this.configIds = configIds; }

    /**
     * @return the list of TestInstance entities associated with this TestRun
     */
    public List<TestInstance> getTests() {
        return getTestRailService().getTests( this.getId() );
    }
}
