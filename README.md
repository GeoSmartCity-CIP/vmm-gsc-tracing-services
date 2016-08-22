# vmm-gsc-tracing
Tracing the Flanders sewer network

## Status
* Working implementation, up for review.

## Configuration
database properties have to be set in tracing.spring.xml
if there is a specific CORS url you can set it in  TracingController.java with the annotation @CrossOrigin("yourspecificUrl:134")

## How to install
```bash
# clone repository

# to build the project and execute tests
mvn install
```

##Rest API GET
your host and port /tracing/{true/false}/{id}
first parameter defines if you want to check up or downstream
second parameter is the id from where the tracing needs to start

## Licence 
*vmm-gsc-tracing-services* is free software, and may be redistributed under the MIT-LICENSE.