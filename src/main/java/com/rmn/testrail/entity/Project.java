package com.rmn.testrail.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A Project as defined within the API. This object offers some convenience methods, which will remove some of the busy work about re-querying that
 *  you would have to handle otherwise.
 *
 *  I did not implement the "get_runs" end-point, because I doubt it is useful, it's slow,
 *   it returns a gigantic payloads, and if you really want it, you can stitch it together yourself.
 *   I'm afraid if I implement it, someone will use it and be completely baffled by the results...
 *   It returns ALL test runs underneath ALL test plans within the history of the project. Useless. If
 *   you can present me with a good use-case for implementing it, I'll be all ears (and very surprised)
 *
 * @author mmerrell
 */
public class Project extends BaseEntity {
    @JsonProperty("id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @JsonProperty("name")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("announcement")
    private String announcement;
    public String getAnnouncement() { return announcement; }
    public void setAnnouncement( String announcement ) { this.announcement = announcement; }

    @JsonProperty("show_announcement")
    private Boolean showAnnouncement;
    public Boolean isShowAnnouncement() { return showAnnouncement; }
    public void setShowAnnouncement(Boolean showAnnouncement) { this.showAnnouncement = showAnnouncement; }

    @JsonProperty("is_completed")
    private Boolean isCompleted;
    public Boolean isCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    @JsonProperty("url")
    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @JsonProperty("completed_on")
    private String completedOn;
    public String getCompletedOn() { return completedOn; }
    public void setCompletedOn(String completedOn) { this.completedOn = completedOn; }

    @JsonProperty("suite_mode")
    private Integer suiteMode;
    public Integer getSuiteMode() { return suiteMode; }
    public void setSuiteMode(Integer suiteMode) { this.suiteMode = suiteMode; }

    /**
     * Returns all TestSuites listed under this project
     * @return The list of TestSuites for this project
     */
    public List<TestSuite> getTestSuites() {
        return getTestRailService().getTestSuites(this.getId());
    }

    /**
     * Looks for the TestSuite that matches the name given
     * @param name The name of the TestSuite you're searching for
     * @return The TestSuite you're looking for, or null
     */
    public TestSuite getTestSuiteByName(String name) {
        for (TestSuite suite: this.getTestSuites()) {
            if (suite.getName().equals(name)) {
                return suite;
            }
        }
        return null;
    }

    /**
     * Return ALL TestPlans for this project
     * @return All the TestPlans defined under this project
     */
    public List<TestPlan> getTestPlans() {
        return getTestRailService().getTestPlans(this.getId());
    }

    /**
     * Looks for the TestPlan that matches the name given
     * @param name The name of the TestPlan you're searching for
     * @return The TestPlan you're looking for, or null
     */
    public TestPlan getTestPlanByName(String name) {
        for (TestPlan plan: this.getTestPlans()) {
            if (plan.getName().equals(name)) {
                return plan;
            }
        }
        return null;
    }
}
