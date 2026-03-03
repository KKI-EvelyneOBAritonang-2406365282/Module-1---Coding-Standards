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
the default EshopApplicationTests did not contain any assertions, which triggered the JUnitTestsShouldIncludeAssert 
rule. 
After I added assertTrue(true), PMD reported additional violations such as UnnecessaryBooleanAssertion and 
JUnitAssertionsShouldIncludeMessage, because the assertion was meaningless and did not include a message. To fix this 
properly, I replaced it with a more meaningful assertion using assertNotNull and included a clear message so it 
complied with the PMD rules. My strategy was to read the PMD error messages carefully, understand why the rule was 
triggered, and then modify the test so it was both logically valid and compliant with the configured quality standards 
instead of just trying to bypass the rule.

Based on my GitHub Actions workflow, I believe the current implementation already meets the definition of Continuous Integration and Continuous Deployment. It fulfills Continuous Integration because every time I push code to the repository, the workflow automatically builds the project, runs all tests, and checks code quality using PMD and JaCoCo without manual intervention. This ensures that integration problems are detected early. It also meets Continuous Deployment because after the build and checks pass, the application can be deployed automatically to the PaaS (Render), making the latest working version available online. Since the entire process from commit to deployment runs automatically through the pipeline, I think it reflects the core principles of CI/CD.


Deployment link: https://module-1-coding-standards-k7hm.onrender.com

--------------------------------------------------------------

In this assignment, I refactored both the Product and Car modules in my project to properly follow the SOLID principles. 
For the Single Responsibility Principle (SRP), I made sure that each class has only one responsibility. The Controller 
classes only handle HTTP requests and responses, the Service classes handle business logic, and the Repository classes 
handle data storage. Previously, some logic like searching and deleting data was implemented inside the Service instead 
of the Repository, which mixed responsibilities. After refactoring, all data-related operations such as findById, update, 
and delete are handled inside the Repository layer, so each layer now has a clear and single responsibility.

For the Open Closed Principle (OCP), I structured the system so that it is open for extension but closed for 
modification. If I want to add a new entity such as Motorcycle in the future, I can create a new Controller, Service, 
and Repository without modifying existing Product or Car classes. This allows the system to grow without changing stable 
code.

For the Liskov Substitution Principle (LSP), I removed incorrect inheritance between CarController and ProductController. 
Previously, CarController extended ProductController, which was not logically correct because a CarController is not a 
specialized version of ProductController. This could break program correctness. After refactoring, both controllers are 
independent classes, so LSP is satisfied.

For the Interface Segregation Principle (ISP), I ensured that interfaces such as ProductService and CarService only 
contain methods that are relevant to their specific entities. There are no unnecessary methods in the interfaces, so
classes are not forced to implement methods they do not use.

For the Dependency Inversion Principle (DIP), I introduced repository interfaces (ProductRepositoryInterface and 
CarRepositoryInterface). Instead of depending directly on concrete repository classes, the Service layer now depends 
on these abstractions. The concrete repositories implement these interfaces. This makes the system more flexible and
easier to modify in the future, for example if I want to switch to a database implementation instead of using an 
in-memory list.

After applying these changes, both Product and Car modules now follow all five SOLID principles. The code structure is
cleaner, easier to understand, and more maintainable, especially for future development.