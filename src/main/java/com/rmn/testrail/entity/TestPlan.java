package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmerrell
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestPlan extends BaseEntity {
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

    @JsonProperty("milestone_id")
    private Integer milestoneId;
    public Integer getMilestoneId() { return milestoneId; }
    public void setMilestoneId(Integer milestoneId) { this.milestoneId = milestoneId; }

    @JsonProperty("is_completed")
    private Boolean isCompleted;
    public Boolean isCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    @JsonProperty("created_by")
    private int createdBy;
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    @JsonProperty("created_on")
    private String createdOn;
    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }

    @JsonProperty("completed_on")
    private String completedOn;
    public String getCompletedOn() { return completedOn; }
    public void setCompletedOn(String completedOn) { this.completedOn = completedOn; }

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
    public void setFailedCount(Integer failedCount ) { this.failedCount = failedCount; }

    @JsonProperty("project_id")
    private Integer projectId;
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    @JsonProperty("assignedto_id")
    private Integer assignedtoId;
    public Integer getAssignedtoId() { return assignedtoId; }
    public void setAssignedtoId(Integer assignedtoId) { this.assignedtoId = assignedtoId; }

    @JsonProperty("url")
    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @JsonProperty("entries")
    private List<TestRunGroup> entries;
    public void setEntries(List<TestRunGroup> entries) { this.entries = entries; }

    /**
     * Returns the list of entries within this TestPlan (assuming some TestRuns have been created and initialized)
     * @return The List of TestRunGroup entries within this TestPlan
     */
    public List<TestRunGroup> getEntries() {
        //Re-query the API for the TestRunGroups (see note below). It's best to do this each time the entries are requested for better safety
        refreshEntriesFromService();
        return entries;
    }

    // Chances are good that you just queried for a bunch of test plans from a project or suite, and that list of plans didn't include the TestRun information for each TestPlan.
    // This block of code re-queries the API for this specific test plan to make certain it will include information about the TestRunGroup entities it contains
    private void refreshEntriesFromService() {
        TestPlan plan = getTestRailService().getTestPlan(this.getId());
        setEntries(plan.entries);
    }

    /**
     * Returns the list of TestRuns associated with this TestPlan (only returns the most recent TestRuns at the moment)
      * @return The most recent list of TestRuns associated with this TestPlan
     */
    public List<TestRun> getTestRuns() {
        //Re-query the API for the TestRunGroups (see note below)
        refreshEntriesFromService();

        //Get the first TestRun in each "entry:run" element and pile it up with the first entries in all the other TestRunGroups to form one list of TestRuns. Not sure this is the ideal way to do
        // things, but by leaving getEntries() public you can get to the other test runs on your own
        List<TestRun> testRuns = new ArrayList<TestRun>(entries.size());
        for (TestRunGroup group: entries) {
            TestRun testRun = group.getRuns().get(0);
            if (null != testRun) {
                testRun.setTestRailService(getTestRailService());
                testRuns.add(testRun);
            }
        }
        return testRuns;
    };
    }
