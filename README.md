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

