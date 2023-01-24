# Drone Medication Delivery REST Service

This is a Java Spring Boot application that provides a REST service for drone delivery of medication items. It uses an H2 in-memory database to store information about the items and deliveries.
Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

    Java 8 or higher
    Maven
    Spring Boot 2.x

Installing (If you dont have the zipped file)
Clone the repository to your local machine.

     git clone https://github.com/daraxdray/drone-medication-delivery.git


Build the project using Maven.

    mvn clean install

Run the Spring Boot application.

    mvn spring-boot:run

The application will be running at http://localhost:8080.

_NB: The database has been preloaded with dummy data for drones, medications, recipient and delivery tables 
The app has a periodic task to check drones battery levels and create history/audit event log for within interval of **30sec**_

### REST Endpoints

###### FOR A PRETTIER URL WE USE

###### 'ds' TO REPRESENT drone-service.

###### 'md' RO REPRESENT medications


The following REST endpoints are available:

∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞
Register Drone

    1. Endpoint: POST /ds/create-drone

_This endpoint is used to register a new drone. The request body should contain an object of the Drone class, which should have the following properties:_

    serialNumber: The serial number of the drone (required)
    model: The model of the drone (required)
    weightLimit: The maximum weight the drone can carry (required)
    batteryCapacity: The current battery capacity of the drone (required)

**Sample Request**  localhost:8080/ds/create-drone
Body --

        {
        "model":"Lightweight",
        "serialNumber":"Be441147834deLL",
        "weightLimit":"300",
        "batteryCapacity":80,
        "state":"IDLE"
        }

**NOTE:** _The endpoint will perform some validation logic to ensure that the required fields are present and that the weight limit is above 0 and the batteryCapacity is above 0. It will also check if the drone is already registered by checking its serial number. If the weight limit exceeds the maximum allowed value, it will return an error. If all the validation checks pass, the drone will be saved to the database and a success response will be returned._

∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞
∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞

Load Drone with Medication Items

    2. Endpoint: POST /ds/load-drone-by-medication-items


This endpoint is used to load a drone with a list of medications. The request body should contain a list of medication IDs.

**Sample Request**
`[1,9,1,7,6]`


_The endpoint will first check if there is an idle drone available. If not, it will return an error. If there is an idle drone, it will check if the battery capacity is higher than 25%. If not, it will return an error._

_Next, the endpoint will loop through the list of medication IDs and fetch each medication from the database to get its weight. It will then check that the total weight of all medications is less than the weight limit of the drone, and if so, it will load the drone with the medication. The drone's state will be changed to "LOADED" after loading, and the loaded drone will be updated in the database._

_The endpoint will return a success message with the number of drones that were loaded successfully.**_

    Note: The code contains some hardcoded values, such as recipient with id=1L, the weight limit is 500 and battery capacity is 25.

∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞


Checking loaded medication items for a given drone

    3. Endpoint: GET "/ds/get-loaded-medications/{drone_id}

The endpoint is used to get the list of medications loaded on a drone.

_**Sample Request** localhost:8080/ds/get-loaded-medications/2_


Checking available drones for loading

    Endpoint GET  ds/get-available-drones-for-loading


Check drone battery level for a given drone

    5. Endpoint GET "/ds/get-drone-battery-level/{drone_id}"

The endpoint is used to get drone battery level information.

**Sample Request** localhost:8080/ds//ds/get-drone-battery-level/2





###### OTHER ENDPOINTS
###DRONES ENDPOINT
∞ Used to fetch all drones on the system

    Endpoint: GET "/ds/get-drones

∞   Used to fetch all deliveries of a drone.

    Endpoint: GET "/ds/get-deliveries/{drone_id}"

∞ Used fetch details of a drone.

    Endpoint GET "/ds/get-drone/{drone_id}

∞ Used to update a drone.

    Endpoint: PUT /ds/update-drone/{drone_id}
    BODY =
    {
    "model":"Lightweight",
    "serialNumber":"as,fs,22,de,",
    "weightLimit":"300",
    "batteryCapacity":80,
    "state":"IDLE"
    }

∞ Used to delete a drone

    Endpoint: DELETE "/ds/delete-drone/{drone_id}

∞ Used to load a specific drone with specific medication item

    Endpoint POST "/ds/load-drone/{drone_id}/{medication_id}

∞ Used to change the state of a drone to  any of the drone states

    Endpoint: PUT /ds/change-drone-status/{drone_id}/{state}

***Sample requests:**  
_localhost:8080/ds/change-drone-status/2/LOADING_


###MEDICATION ENDPOINT
∞ Used to create a medication

    Endpoint: POST "/md/create"

∞ Used to create multiple medications

    Endpoint: POST /md/create-all

∞ Used to list all medications

    Endpoint: GET //md/get-medications

∞ Used to get a medication information

    Endpoint: GET /md/get-medication/{md_id}

∞ Used to update medications

    Endpoint: PUT /md/update-medication/{md_id}

∞ Used to delete medication

    Endpoint: DELETE /md/delete-medication/{md_id}


### Built With

    Java - Programming language
    Spring Boot - Framework for building web applications
    Maven - Dependency management
    H2 - In-memory database

### Author

    Damilola Daramola - https://github.com/daraxdray


