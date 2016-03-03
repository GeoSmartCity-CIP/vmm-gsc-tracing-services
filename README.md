# vmm-gsc-tracing
Tracing the Flanders sewer network - Proof of Concept

## Status
* Working samples; Development on hold


## How to install
```bash
# clone repository
git clone https://github.com/GeoSmartCity-CIP/vmm-gsc-tracing.git

# to build the project and execute tests
mvn install
```

## Visualize sample trace results in QGis
*Instructions based on QGis Desktop v2.12*

Download [traceresult.txt](https://github.com/GeoSmartCity-CIP/vmm-gsc-tracing/blob/master/traceresult.txt) and [traceresult-reverse.txt](https://github.com/GeoSmartCity-CIP/vmm-gsc-tracing/blob/master/traceresult-reverse.txt)
Open QGis Desktop

Create a new project or add the layer to an existing project

Layer : Add layer : Add delimited text layer...

Change options:
* Custom delimiters: semicolon
* Record options: First record has field names
* Geometry: Well known text (WKT)

Lambert72 CRS (EPSG 31370)

## Modify to use your own data
set database options in 'src/main/resources/tracing.spring.xml'

map 'be.vmm.gsc.tracing.data.model.Koppelpunt' to your node table and columns

map 'be.vmm.gsc.tracing.data.model.Rioollink' to your edge table and columns


## Licence 
*vmm-gsc-tracing* is free software, and may be redistributed under the MIT-LICENSE.