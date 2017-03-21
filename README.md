# Dropwizard Hystrix Tracker [![Build Status](https://travis-ci.org/Tushar-Naik/dropwizard-hystrix-tracker.svg?branch=master)](https://travis-ci.org/Tushar-Naik/dropwizard-hystrix-tracker)
#### A Bundle that can be used to track API-Paths / Resources 

A precompiled bundle for tracking/monitoring your favourite API-Paths/Resources.<br>
There are 2 features here:
1. Client Restriction for certain API Paths within your Resource - using annotation ```@ClientRestriction```
2. Hystrix tracking of APIs - using annotation  ```@TrackPath```<br>
 
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
    public Response getResponse() {
        ...    
    }
    ...
}
```

## 2. Using Client Restriction Bundle
Depending on the ```ClientFilterConfig``` provided, <br>if a Resource Path is marked with the annotation
 ```@ClientRestriction```, <br>API will return a **STATUS:403 FORBIDDEN** if header in the incoming Request is not present,<br> or does not match the ones present in ```ClientFilterConfig.validClients```
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
    public Response getResponse() {
        ...    
    }
    ...
}
```
