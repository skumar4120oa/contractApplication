# contractApplication

contractApplication REST API 

RESTful API Development
a. Minimal Contract object attributes:
i. Contract Id ii. Name
iii. Business Number
iv. Contract Activation Date (Read Only)
v. Amount Requested
vi. Status [Approved, Denied] (Read Only)
b. Required API end-points
i. Return a list of all approved contracts
ii. Return a single contract
iii. Create a new contract
iv. Update an existing contract
v. Delete an existing contract
c. API Validation
i. Using the method of your choice, provide a manner where the end-points (noted above) can be validated during your on-site assessment.
d. User Story
As a Contract Specialist, I would like a way to add multiple contracts that will automatically determine the activation date and approval status in order to improve the accuracy of contract approvals.
Acceptance Criteria:
i. A Sales Contract can be any amount requested
ii. Express Contract can be any amount less than $50,000
iii. All contracts should be stored in the same location
iv. The status is ‘Approved’ if the amount requested is within the range stated above
v. The Activation Date is set to the current date when the status is set to ‘Approved’

How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary.

Clone this repository

Make sure you are using JDK 1.8 and Maven 3.x

You can build the project and run the tests by running mvn clean package

Once successfully built, you can run the service by:

mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
