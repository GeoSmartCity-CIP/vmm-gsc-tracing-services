# vmm-gsc-tracing
Tracing the Flanders sewer network

## Status
* Working implementation, up for review.

## Configuration
database properties have to be set in tracing.spring.xml<br />
if there is a specific CORS url you can set it in  TracingController.java with the annotation @CrossOrigin("yourspecificUrl:1234")

## How to install
```bash
# clone repository

# to build the project and execute tests
mvn install
```

## How to start
Run the main method in AppConfiguration.java<br />
*default port = 8080, you can change it by setting the VM option -Dserver.port=1234*


##Rest API GET
your host and port /tracing/{true/false}/{id}<br />
first parameter defines if you want to check up or downstream<br />
second parameter is the id from where the tracing needs to start

##Performance / info
**IdGraphBuilder**
Current implementation builds and traces an DirectedGraph<Integer, Integer> where the second Integer is the feature Id.
After tracing we fetchAll entities by a collections of Integers (the ids).

**RioollinkGraphBuilder**
RioollinkGraphBuilder is similar to IdGraphBuilder but it contains the entity instead of the id.<br />
If you use RioollinkGraphBuilder the initial query will be slower but that will be the only one.<br />
If the data is static you can opt to enable caching.

If scaling becomes an issue it's advised to initialize the tracing graph at the start of the application.<br />
This will make the graph static.

## Licence 
*vmm-gsc-tracing-services* is free software, and may be redistributed under the MIT-LICENSE.