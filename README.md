# Dropwizard Hystrix Tracker [![Build Status](https://travis-ci.org/Tushar-Naik/dropwizard-hystrix-tracker.svg?branch=master)](https://travis-ci.org/Tushar-Naik/dropwizard-hystrix-tracker)
####A Bundle that can be used to track API-Paths / Resources 

A precompiled bundle for all your favourite Resources/API Paths.<br>
There are 2 features here:
1. Client Restriction for certain API Paths within your Resource - using annotation ```@ClientRestriction```
2. Hystrix tracking of APIs - using annotation  ```@TrackPath```<br>
Prereq: Java 8.
 
## Usage
 
### Build instructions
  - Cloning source code:

        git clone github.com/Tushar-Naik/dropwizard-hystrix-tracker.git

  - Building code

        mvn install

### Maven Dependency
* Use the following maven dependency for path tracker:
```
<dependency>
    <groupId>io.dropwizard.tracker</groupId>
    <artifactId>dropwizard-hystrix-path-tracker</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
* Use the following maven dependency for client filter:
```
<dependency>
    <groupId>io.dropwizard.tracker</groupId>
    <artifactId>dropwizard-client-filter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 1. Using Path Tracker Bundle
Use this to track a particular API endpoint.<br>
Hystrix metrics will be emitted based on the name of the API.
#### Bootstrap
```java
    @Override
    public void initialize(final Bootstrap...) {
        bootstrap.addBundle(new TrackerBundle());
    }
```
#### Tracking your Resource
```java
    
@Path("/service/v1")
@Slf4j
public class MyResource{

    @Path("/api/{path}/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @TrackPath("/service/*")
    public void getResponse() {
        ...    
    }
    ...
```

## 2. Using Client Restriction Bundle
Depending on the ```ClientFilterConfig```, if a Resource Path is marked with the annotation
 ```@ClientRestriction```, API will return a **STATUS:403 FORBIDDEN** if a header to the incoming Request is not passed, or does not match the ones present in ```ClientFilterConfig```
#### Bootstrap
```java
    @Override
    public void initialize(final Bootstrap...) {
        bootstrap.addBundle(new ClientFilterBundle() {
                    public ClientFilterConfig getClientFilterConfig() {
                        return new ClientFilterConfig("X-My-Header", ImmutableSet.of("ValidClient1", "ValidClient2")));
                    }
                });
    }
```
#### Adding client filter for a resource 
```java
    
@Path("/service/v1")
@Slf4j
public class MyResource{

    @Path("/api/{path}/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ClientRestriction
    public void getResponse() {
        ...    
    }
    ...
```
