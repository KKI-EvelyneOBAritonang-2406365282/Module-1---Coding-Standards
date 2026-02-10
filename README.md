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