#### **FOR A PRETTIER URL WE USE 'ds' TO REPRESENT drone-service.**

This code is for a RESTful API endpoint for creating and loading drones for delivery using Spring Boot framework.

> Endpoint: POST /ds/create-drone

This endpoint is used to register a new drone. The request body should contain an object of the Drone class, which should have the following properties:

    serialNumber: The serial number of the drone (required)
    model: The model of the drone (required)
    weightLimit: The maximum weight the drone can carry (required)
    batteryCapacity: The current battery capacity of the drone (required)

The endpoint will perform some validation logic to ensure that the required fields are present and that the weight limit is above 0 and the batteryCapacity is above 0. It will also check if the drone is already registered by checking its serial number. If the weight limit exceeds the maximum allowed value, it will return an error. If all the validation checks pass, the drone will be saved to the database and a success response will be returned.
> Endpoint: POST /ds/load-drone-by-prescription

This endpoint is used to load a drone with a list of medications. The request body should contain a list of medication IDs. The endpoint will first check if there is an idle drone available. If not, it will return an error. If there is an idle drone, it will check if the battery capacity is higher than 25%. If not, it will return an error.

Next, the endpoint will loop through the list of medication IDs and fetch each medication from the database to get its weight. It will then check that the total weight of all medications is less than the weight limit of the drone, and if so, it will load the drone with the medication. The drone's state will be changed to "LOADED" after loading, and the loaded drone will be updated in the database.

The endpoint will return a success message with the number of drones that were loaded successfully.

    Note: The code contains some hardcoded values, such as recipient with id=1L, the weight limit is 500 and battery capacity is 25.