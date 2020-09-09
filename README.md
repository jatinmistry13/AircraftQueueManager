# Aircraft Queue Manager

To compile the application:
`mvn clean install -DskipTests`

To run the application:
`mvn spring-boot:run`

Note:
Make sure you have MySQL installed. The application will create a schema `AC_QUEUE_MANAGER` with a table `SC_AIRCRAFT`. This table is used to persist the data in a MySQL datastore. 

This application has 3 REST endpoints for integrating with 3rd party UI:
1. To list the aircrafts in the Queue (the returned list is not in order as the queue holds the aircrafts in a heap. It would be an expensive operation to keep polling all the elements from the queue and then fill up the queue for listing in order of priority)
2. To add an aircraft to the queue (enqueue)
3. To remove an aircraft from the queue (dequeue)


Details:
1. Get a list of aircrafts in the Queue (not in order of their priority)

Endpoint: `http://localhost:8080/queues/`
Method: `GET`

Javascript fetch code to integrate with 3rd party UI:
```
fetch("http://localhost:8080/queues/", {
  "method": "GET"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

Shell script:
```
curl --request GET --url http://localhost:8080/queues/
```

2. Add an aircraft to the Queue:

Endpoint: `http://localhost:8080/queues/`
Method: `PUT`

Javascript fetch code to integrate with 3rd party UI:
```
fetch("http://localhost:8080/queues/", {
  "method": "GET"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

Shell script:
```
curl --request PUT \
  --url http://localhost:8080/queues/ \
  --header 'content-type: application/json' \
  --data '{
	"aircraftType": "Passenger",
	"aircraftSize": "Small",
	"aircraftName": "abc"
}'
```

3. Remove an aircraft from the queue

Endpoint: `http://localhost:8080/queues/`
Method: `DELETE`

Javascript fetch code to integrate with 3rd party UI:
```
fetch("http://localhost:8080/queues/", {
  "method": "DELETE"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

Shell script:
```
curl --request DELETE --url http://localhost:8080/queues/
```

