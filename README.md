# vmm-gsc-tracing
Tracing the Flanders sewer network

## Status
* Working implementation.

## Configuration
database properties have to be set in tracing.spring.xml or with environment variable<br />
if there is a specific CORS url you can set it in  TracingController.java with the annotation @CrossOrigin("yourspecificUrl:1234")

## How to install
```bash
# clone repository

# to build the project and execute tests (standalone Spring boot application)
mvn install

# to build a war file deployable to any application server
mvn -f pom-war.xml install
```

## How to start
```bash
java -jar gsc-tracing-services.jar
```
Runs on port 8080 by default, you can change it with the VM option -Dserver.port=1234

or

Deploy the war artifact on an application server.

##Rest API GET
Request:
**/tracing/{upstream|downstream}/{id}**<br />
first parameter defines if you want to trace up- or downstream<br />
second parameter is the id from where the tracing needs to start

Response: GeoJSON with all traced links, in no partical order.

##Performance / info
**IdGraphBuilder**
Builds a graph with id's only, no objects with all properties.
After tracing we fetchAll entities by a collections of Integers (the ids).
This is useful to always fetch the latest data, but that fetching has it's limitations and is a performance hit.
Remember to build the directed graph both upstream and downstream.<br />

**RioollinkGraphBuilder**
RioollinkGraphBuilder is similar to IdGraphBuilder but it contains the full entity instead of the id.<br />
Initial data lookup is slower, but the tracing itself is almost instant.
Works best if the data is static. This the current implementation.

## Licence 
*vmm-gsc-tracing-services* is free software, and may be redistributed under the MIT-LICENSE.