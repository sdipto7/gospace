# GoSpace - Journey Beyond Limits

Welcome to GoSpace, where cosmic adventures await! Discover and book exhilarating space trips to distant celestial destinations effortlessly. Embark on unforgettable journeys to explore the wonders of the universe. With GoSpace, your gateway to the stars, the thrill of space travel is just a click away. Let your imagination soar beyond the cosmos!

### Run the application anywhere(images are available on dockerhub)
  - ensure docker is installed in the target environment
  - use the provided docker-compose.yml file
  - pull the images and start the containers with:
```
docker compose up --detach
```

### APIs:
#### Spacecraft Service URLs using API gateway:
- Get Requests:
  - http://localhost:8765/api/spacecraft/{id}
  - http://localhost:8765/api/spacecraft/all

- Post Request:
  - http://localhost:8765/api/spacecraft

- Put Request:
  - http://localhost:8765/api/spacecraft

- Delete Request:
  - http://localhost:8765/api/spacecraft/{id}

#### Exploration Service URLs using API gateway:
- Get Requests:
  - http://localhost:8765/api/destination/{id}
  - http://localhost:8765/api/destination/all
  - http://localhost:8765/api/destination/all/{type}

- Post Request:
  - http://localhost:8765/api/destination

- Put Request:
    - http://localhost:8765/api/destination

- Delete Request:
  - http://localhost:8765/api/destination/{id}

#### SpaceTrip Service URLs using API gateway:
- Get Requests:
  - http://localhost:8765/api/spacetrip/{id}
  - http://localhost:8765/api/spacetrip/details/{id}
  - http://localhost:8765/api/spacetrip/available-trips

- Post Request:
  - http://localhost:8765/api/spacetrip

- Put Request:
  - http://localhost:8765/api/spacetrip

- Delete Request:
  - http://localhost:8765/api/spacetrip/{id}

#### Booking Service URLs using API gateway:
- Get Requests:
  - http://localhost:8765/api/booking/{id}
  - http://localhost:8765/api/booking?referenceNumber=79676f06-7e03-4d91-9175-d24dc2bd68c1
  - http://localhost:8765/api/booking/all
  - http://localhost:8765/api/booking/all/{status}

- Post Request:
  - http://localhost:8765/api/booking

- Put Requests:
  - http://localhost:8765/api/booking
  - http://localhost:8765/api/booking/payment

- Delete Request:
  - http://localhost:8765/api/booking/{id}

### Local environment setup for development of this project
  - clone the repository into your local machine
  - ensure docker is installed in your local machine
  - prior to running the services, ensure that the four required MySQL containers are running, each corresponding to a specific microservice
  - start the mysql containers with the correct database configurations for each microservice by the following commands - 

#### MySQL Container for SpaceTrip Service:
```
docker run --detach --name spacetrip_mysql_container --env MYSQL_DATABASE=spacetrip_schema --env MYSQL_USER=admin --env MYSQL_PASSWORD=1234 --env MYSQL_ROOT_PASSWORD=1234 --publish 3306:3306 mysql:8-oracle
```

#### MySQL Container for Spacecraft Service:
```
docker run --detach --name spacecraft_mysql_container --env MYSQL_DATABASE=spacecraft_schema --env MYSQL_USER=admin --env MYSQL_PASSWORD=1234 --env MYSQL_ROOT_PASSWORD=1234 --publish 3307:3306 mysql:8-oracle
```

#### MySQL Container for Exploration Service:
```
docker run --detach --name exploration_mysql_container --env MYSQL_DATABASE=exploration_schema --env MYSQL_USER=admin --env MYSQL_PASSWORD=1234 --env MYSQL_ROOT_PASSWORD=1234 --publish 3308:3306 mysql:8-oracle
```

#### MySQL Container for Booking Service:
```
docker run --detach --name booking_mysql_container --env MYSQL_DATABASE=booking_schema --env MYSQL_USER=admin --env MYSQL_PASSWORD=1234 --env MYSQL_ROOT_PASSWORD=1234 --publish 3309:3306 mysql:8-oracle
```

### Build the images of the microservices
  - navigate to the parent repository(gospace), where the pom.xml is located, containing the docker image build configuration
  - build the image using maven plugin with:
```
mvn spring-boot:build-image -DskipTests
```

### Push the docker images
```
docker push sdipto7/gospace-spacecraft:0.0.1-SNAPSHOT
docker push sdipto7/gospace-exploration:0.0.1-SNAPSHOT
docker push sdipto7/gospace-spacetrip:0.0.1-SNAPSHOT
docker push sdipto7/gospace-booking:0.0.1-SNAPSHOT
docker push sdipto7/gospace-api-gateway:0.0.1-SNAPSHOT
docker push sdipto7/gospace-naming-server:0.0.1-SNAPSHOT
```
