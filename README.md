## Description of the case from employer

* The task is to design and implement a backend service to control an appliance such as a wash machine or an oven. 
* The API should be REST based and the state of the appliance should be persisted to any form of persistent storage. 
* There is no need for any frontend, but we expect there to be a README.md file with build directions and examples of how to invoke the REST API (e.g. curl).

* The project should be implemented using Java. 
* Feel free to use any 3rd party library that you are comfortable with. 
* Unit tests are expected and the assignment will be assessed based on good programming practices and design.

Please use GitHub or Bitbucket to publish your source code.

## Description of realisation

* Service design is based on the idea that smart devices have only predefined programs, and the only thing user should worry about is to choose the appropriate one of them, but not to care about the parameters(ex. temperature or duration)
* It provides both administration and user experience. 
* As an administrator, you can provide user with new smart devices and add new programs for them. 
* As a user u can get information about smart devices, their models, programs, add device to remote control, start and pause a program.
* API documented with OpenApi swagger located on http://localhost:(your_port)/remote-control/docs/swagger.html and u can try it right there

## Important notes



