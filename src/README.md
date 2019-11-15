# Deploy new snapshot to public repo

The mvn-repo branch contains the code for the current snapshot in the repository. If you would like to change the code in the snapshot,
makes sure to merge this code into the mvn-repo branch and then run the following commands to publish the new snapshot in the public Maven repository.

Snapshot location: `https://oss.sonatype.org/#nexus-search;quick~mgage`

### Prerequisites

* You will need to create a valid username and password for the Sonatype repository and add this to your `settings.xml` file in your local `.m2` directory.
* You will need to update the version tag to deploy a new version so there are no naming conflicts
* Make sure you are in currently in the branch that you intend to deploy

### Preparing code for release
The command will prepare the release to the Maven repo
If the command fails here, it will create all prerequisite data for the release up to the point of failure
The next run of this command will start from the previous failure

`mvn clean release:clean release:prepare -e`


### Performing release
This command will execute the steps necessary for the release of new snapshot. This phase will also call the 
goals for the maven-release-plugin in the pom.xml (`build > pluginManagement > plugins > plugin`). If this 
configuration did not specify the deploy command in the goals tag, you will have to make a separate command 
to execute the deployment and will not depend on the plugin configuration (`mvn clean deploy -P release`).
The following command is the preferred way of deploying the snapshot.

`mvn release:perform -e`

