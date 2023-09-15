# register-demo
A Spring-Boot Demo of registering with email
This project has implemented a backend API to register a user, it can also
edit/read/soft delete single or multiple users.

Key functions
1. The application runs with Spring-Boot and in one single project
2. The application can be packaged via Docker, and can be built, packaged and run
   with Docker in one single shell script. Please see the details of the following instructions.
3. Upon successful registration, it can send out a welcome email(using fake SMTP server).
4. The codes have proper validation, error handling and testing.

Additional functions
1. REST API documentation
2. Using of latest comment design patterns, e.g. microservices
3. Performance testing
4. UI to register, edit, deactivate, show existing registered users and view all emails
that were sent.

## How to run?
- Running with Docker：
> ```bash
> $ mvnw package
> $ docker build -t register-demo .
> $ docker image ls
> $ docker run -d -p 8080:8080 register-demo
> ```
