# Getting Started

### Quick Documentation
The `Drug.java` class contains the blueprint for the drug object including properties of uid, name, manufacturer, quantity, and price. Each field contains validation with a message. The uid is randomly generated and is assumed to be unique. This is done in substitution for an actual database. Additionally, there are getters for each of the properties.

The `DrugController.java` class contains the different routes for the API. The rest controller annotation is used here to establish routes for getting the list of all drugs (GET), adding a drug (POST), getting a drug by id (GET), query drugs by manufacturer and/or name (GET), and update the drug quantity and/or price by id (PUT).

The `DrugService.java` class contains the business logic for each of the routes. A static map of Drug objects is initialized to simulate a database directory of drugs. The uid is used as the key while the drug is the value.

The `ErrorHandler.java` class is used to catch exceptions when they are thrown after using the API and send a response to the user.

The `Constants.java` class is simply used for creating mock data that is used at the start of the application to create a directory of drug objects.

### Starting Guide

1. Clone the repository
2. Ensure you have Java JDK version 23
3. Run the TestapiApplication.java file to start the server on port 8080
4. The application inputs mock data at the start. Check that the server is running by going to localhost:8080/api/drugs. You should see a list of 1000 drug elements.

### API Guide

The functionality of the API can be assessed through the unit tests available through the `TestapiApplicationTests.java` file.

Postman can be used to test the HTTP endpoints:

**Adding Drugs**

**/api/addDrugs**

New drugs can be added to the directory through the POST endpoint. The body should contain an JSON array of Drug Elements that have name (String), manufacturer (String), quantity (int) and price (double) attributes.

**Get Drug By ID**

**/api/getDrugById/{id}**

Through the  GET request, you can get a particular drug by substituting the UUID with the id in the route.

**Getting All Drugs**

**/api/drugs**

This can be done by simply going to the route.

**Query Drugs**

**/api/query**

This GET request allows you to query all drugs by manufacturer and or name. They have to be sent in as form-data of key value pairs with the keys being manufacturer and/or name and the values being whatever you're searching for.

**Update Drug**

**/api/updateDrug/{id}**

Through this PUT request, you can update the drug quantity and/or price by passing in the uid of the drug as a query parameter.
