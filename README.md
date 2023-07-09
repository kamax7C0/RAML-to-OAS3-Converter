# RAML 1.0 to OAS 3.0 Converter

This project uses the [AMF library (AML Modeling Framework)](https://github.com/aml-org/amf) to convert API specifications written in RAML (RESTful API Modeling Language) to OAS 3.0 (OpenAPI Specification 3.0). The AMF library was chosen for this purpose because it is capable of accurately transforming a RAML model into an OAS model, closely mimicking MuleSoft's own conversion process.

## How the Converter Works

The `RamlToOas` class performs the conversion operation, following these steps:

1. The application initializes the AMF library.

2. A temporary directory is created (or cleaned if it already exists).

3. A zip file, containing the RAML API project, is extracted from the resources directory to the temporary directory using the `net.lingala.zip4j.ZipFile` class.

4. The main RAML file from the extracted project is parsed into an AMF model using the `RAMLConfiguration` class.

5. The parsed AMF model is then transformed into OAS 3.0 format using the `OASConfiguration` class. The transformation process uses a compatibility pipeline to ensure the transformation conforms to the OAS 3.0 specification.

6. Finally, the OAS 3.0 formatted API specification is written to an output file.

## Configuration Options for AMF OAS 3.0 Output

When generating the OAS 3.0 output, the AMF library provides several configuration options:

- **Compact**: Output the specification without any extra annotations or comments.
- **Flattened**: Generate a simplified version of the model without links to other parts of the model.
- **Source maps**: Include source maps to track elements in the specification back to their original position in the source file.
- **Pretty-print**: Add line breaks and indentation to make the output more human-readable.
- **Documentation rendering**: Include all information necessary to generate complete documentation when rendering the specification.

These options can be set through the `RenderOptions` class when initializing the AMF Client:

```java
RenderOptions options = new RenderOptions().withCompactUris().withSourceMaps(); // etc.
```

## How to Use

To use this converter, place the RAML API project zip file in the `root` directory of the project, and then run the `RamlToOas` class. The converted OAS 3.0 specification will be output to the `output.json` file.
### ** Note ** 
Currently it's hardcoded to use the included sample poc-stockmanagement-api.zip file.  

## Dependencies

This project uses the following libraries:

- AMF for parsing and transforming API specifications.
- net.lingala.zip4j for handling zip files.
- Apache Commons IO for handling temporary directories and files.
- SLF4J for logging operations.

