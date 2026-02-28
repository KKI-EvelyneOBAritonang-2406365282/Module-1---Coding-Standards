### REFLECTION 1
So I implemented new Edit and Delete Product features using Spring Boot following the 
MVC architecture. I separated between Controller, Service, Repository, and View layers to have
clean code and improve readability. The controller only handles HTTP requests, while business logic 
is placed inside the service layer, which use the single responsibility principle.

To help me with the code, I used findById, update, and delete, making the code easier to understand 
and maintain. I also ensured each product has a unique productId generated 
automatically to prevent errors and broken routes. 

While developing, I found several mistakes such as missing productId, incorrect URL mappings, and unimplemented 
service methods. I improved the code by adding ID generation, implementing findById/update/delete methods, and 
aligning HTML routes with controller mappings. Overall, the project helped me understand clean architecture 
and coding practices in Spring Boot applications.

### REFLECTION 2
1. After writing the unit tests, I felt more confident about my code because I could see that the main 
logic worked as expected without having to test everything manually. There is no exact number of unit tests 
that must be written for a class, what matters is that the tests cover the important behaviors, including both 
valid and invalid cases. Code coverage helps show how much of the code is exercised by tests, but even if the 
coverage reaches 100%, it does not mean the code is completely free of bugs. 

2. If I needed to create another functional test suite to verify the number of products in the list, 
I would likely reuse the same setup logic, such as initializing the server, base URL, and WebDriver. 
This would result in a new test class that looks very similar to the previous functional test classes, 
with the main difference being the test steps related to counting the products.
3. When creating a new functional test suite, the code can start to feel messy if a lot of setup code is copied 
and pasted across different test classes. This kind of duplication makes the code harder to maintain and more likely 
to break when something changes. To keep things cleaner, common setup logic should be moved into a shared base 
class or helper methods. That way, each test class can focus on testing specific features, making the code easier 
to read and maintain.

------------------------------------------------
During the exercise, one of the main code quality issues I fixed was related to PMD rules in the test class. Initially, 
the default EshopApplicationTests did not contain any assertions, which triggered the JUnitTestsShouldIncludeAssert rule.
After I added assertTrue(true), PMD reported additional violations such as UnnecessaryBooleanAssertion and JUnitAssertionsShouldIncludeMessage, because the assertion was meaningless and did not include a message. To fix this properly, I replaced it with a more meaningful assertion using assertNotNull and included a clear message so it complied with the PMD rules. My strategy was to read the PMD error messages carefully, understand why the rule was triggered, and then modify the test so it was both logically valid and compliant with the configured quality standards instead of just trying to bypass the rule.

Based on my GitHub Actions workflow, I believe the current implementation already meets the definition of Continuous Integration and Continuous Deployment. It fulfills Continuous Integration because every time I push code to the repository, the workflow automatically builds the project, runs all tests, and checks code quality using PMD and JaCoCo without manual intervention. This ensures that integration problems are detected early. It also meets Continuous Deployment because after the build and checks pass, the application can be deployed automatically to the PaaS (Render), making the latest working version available online. Since the entire process from commit to deployment runs automatically through the pipeline, I think it reflects the core principles of CI/CD.


Deployment link: https://module-1-coding-standards-k7hm.onrender.com