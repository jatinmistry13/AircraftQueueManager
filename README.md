# Aircraft Queue Manager

Prerequisite:
Make sure you have MySQL installed. The application will create a schema `AC_QUEUE_MANAGER` with a table `SC_AIRCRAFT`. This table is used to persist the data in a MySQL datastore.

You will also have to update the `jdbc.xml` file in order to connect to the database as per your username and password. This file can be found in `src/main/resources/beans/jdbc.xml`. The current datasource settings look like this:
```
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql://localhost:3306/AC_QUEUE_MANAGER?createDatabaseIfNotExist=true&amp;autoReconnect=true&amp;useSSL=false&amp;serverTimezone=UTC"></property>
		<property name="username" value="YOUR_MYSQL_USERNAME"></property>
		<property name="password" value="YOUR_MYSQL_PASSWORD"></property>
		<property name="initialSize" value="10"></property>
		<property name="maxActive" value="50"></property>
		<property name="maxIdle" value="50"></property>
		<property name="minIdle" value="10"></property>
		<property name="maxWait" value="30000"></property>
		<property name="validationQuery" value="SELECT 1" />
		<property name="testWhileIdle" value="true"></property>
		<property name="minEvictableIdleTimeMillis" value='#{30 * 60 * 60 * 1000}'></property>
		<property name="timeBetweenEvictionRunsMillis" value='#{36 * 60 * 60 * 1000}'></property>
	</bean>
```
Make sure to change the `YOUR_MYSQL_USERNAME` and `YOUR_MYSQL_PASSWORD` with your configured mysql username and password


To compile the application:
`mvn clean install`

To run the application:
`mvn spring-boot:run`


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


In the event where the application crashes or is shutdown and has to start back up, the queue will be loaded with the entries from the DB and can continue from where it left before a crash/shutdown.



