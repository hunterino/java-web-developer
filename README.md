# Java/Web Developer Project

What does a modern web development stack use:
* Enables easy development setup
* Allows for cloud hosting - not on any specific platform


## Feature Request App
Build a web application that allows the user to manage "feature requests".

A "feature request" is a request for a new feature that will be added onto an existing piece of
software. Assume that the user is an employee at WCF who would be entering this information after
having some correspondence with a client that is requesting the feature.  The necessary fields
are:

* **Title:** A short, descriptive name of the feature request.
* **Description:** A long description of the feature request.
* **Client:** A selection list of clients (use "Client A", "Client B", "Client C")
* **Client Priority:** The feature's priority number according to the client (1...n). Client Priority numbers
should not repeat for the given client, so if a priority is set on a new feature as "1", then all
other feature requests for that client should be adjusted.
* **Target Date:** The date that the client is hoping to have the feature.
* **Product Area:** A selection list of product areas (use 'Policies', 'Billing', 'Claims',
'Reports')

## Tech Stack

Requirements - local development
* Java 11
* Node 12.19.x --lts
* Gradle 6.1.x - tested with, may work with others.
* (optional) docker

## Setup And Run
Install Jhipster
```shell
npm install -g generator-jhipster
```
clone the repot from git:
```shell
git clone https://github.com/hunterino/java-web-developer.git
```
```shell
cd java-web-developer
```
Install node dependencies
```shell
npm install
```

run
```shell
gradle
```

It will start and be running [Landing Page](https://localhost:8080/)

## Logins

* user - username=user password=user
* admin - username=admin password=admin

## Be sure and checkout - login as admin
* the admin tools
** [API Documentation - Swagger UI](http://localhost:8080/admin/docs)
** [Database Design ](http://localhost:8080/h2-console/login.jsp)
* the language change

## Advanced features

### generate CI/CD

```shell
jhipster ci-cd
``` 
#### specify platform

* Jenkins pipeline
* Azure Pipelines
* GitLab CI
* GitHub Actions
* Travis CI
* CircleCI

## Cloud Deployment

### Kuberneties

[Full Instructions](https://www.jhipster.tech/kubernetes/)

### Deploy to docker

-v parameter need to be altered

```shell
docker container run --name jhipster -v ~/{User_Directory_From_repot}:/home/jhipster/app -v ~/.m2:/home/jhipster/.m2 -p 8080:8080 -p 9000:9000 -p 3001:3001 -d -t jhipster/jhipster
```
example
```shell
docker container run --name jhipster -v ~/code/java-web-developer:/home/jhipster/app -v ~/.m2:/home/jhipster/.m2 -p 8080:8080 -p 9000:9000 -p 3001:3001 -d -t jhipster/jhipster
```

## Next Steps
*Better error message when a non unique customer/customer_priority is saved
* Performance tests with Gatling.
* Behaviour-driven tests with Cucumber.
* Angular/React/Vue integration tests with Protractor.




The following are the tools our team favors.  They are preferred, but not required, for this project.

* Java 11
* Spring Boot
* JPA/Hibernate
* Angular


## Guidelines

Build your own public repo on github, and call it whatever you like. Build your solution in your
repo, and include a README.md file that contains the detailed instructions for running your web app.
Email the URL for your github repo to your hiring manager once you begin the project so we can review 
your progress. Once your project is completed, please email your hiring manager.

One of the major goals in this project is to see how you fill in ambiguities in your own creative
way. There is no such thing as a perfect project here, just interpretations of the instructions
above, so be creative in your approach.

We want to be respectful of your time and set realistic expectations for submission. To help guide you, we 
have included the list below which details common practices in the best projects we receive. It is rare for 
a project to match every item in this list, but the candidates we hire typically showcase several of 
these features in their work.

## Technology

1. Open Source - based on jhipster open source project

2. *Decoupled Backend*. Swagger based restful api, including swagger descriptor, for generating other front ends.

3. *Test Suites with Continuous Integration*. see ci/cd above for configuration

4. *Usable, Responsive Interface*. Seamless single page interface, with dynamic loading and fault tollerance.

5. Bonuses include 
5.1 kuberneties/docker deploy
5.2 internationalization support
5.3 ability to generate flutter ios and android applications
5.4 ci/cd support for a variety of platforms

