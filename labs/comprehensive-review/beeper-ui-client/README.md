# Beeper UI Client (generator)

Used to generate the client-side TypeScript types and service functions from the OpenAPI specification.

**DO NOT** modify any generated files!
Treat the generated package as a build artifact.
If you need to change the way the models or API functions work, _change the OpenAPI spec and regenerate_.

Ideally, this generator is paired with generating the implementation structure in the API service itself.
When done correctly, this allows type safety and versioning around an otherwise-unchecked boundary in AJAX: the lack of cross-HTTP schemas in JSON.

## Building and Publishing

1. Run the `generate_api.sh` script to generate the UI client package.

```
[richardallred@p1 beeper-ui-client]$ ./generate_api.sh
[main] INFO  o.o.codegen.DefaultGenerator - Generating with dryRun=false
[main] INFO  o.o.c.ignore.CodegenIgnoreProcessor - Output directory (/local/beeper-ui-client) does not exist, or is inaccessible. No file (.openapi-generator-ignore) will be evaluated.
[main] INFO  o.o.codegen.DefaultGenerator - OpenAPI Generator: typescript-fetch (client)
[main] INFO  o.o.codegen.DefaultGenerator - Generator 'typescript-fetch' is considered stable.
[main] INFO  o.o.c.l.AbstractTypeScriptClientCodegen - Hint: Environment variable 'TS_POST_PROCESS_FILE' (optional) not defined. E.g. to format the source code, please try 'export TS_POST_PROCESS_FILE="/usr/local/bin/prettier --write"' (Linux/Mac)
[main] INFO  o.o.c.l.AbstractTypeScriptClientCodegen - Note: To enable file post-processing, 'enablePostProcessFile' must be set to `true` (--enable-post-
...output omitted...
################################################################################
# Thanks for using OpenAPI Generator.                                          #
# Please consider donation to help us maintain this project 🙏                 #
# https://opencollective.com/openapi_generator/donate                          #
################################################################################
```

This script uses the OAS file `../beeper-oas.yaml` and the `generator-config.yaml` to create a new NPM package named `beeper-ui-client` (new subdirectory).
1. `cd beeper-ui-client/`

Inside the new `beeper-ui-client` subdirectory, build the tarball:

1. `npm install` to install dependencies
2. `npm build` to compile the TypeScript
3. `npm pack` to generate a publishable tarball

Login to Nexus as `admin` and take the tarball and upload to the `npm-hosted` repository using the nexus UI.  
![image](https://user-images.githubusercontent.com/392059/223172797-be3151dc-cae6-4023-ada1-6411d563a151.png)


## Consuming the API Client

The generated NPM package contains two important directorys: `src/apis/` and `src/models/`.

The `models` directory contains TypeScript interfaces that represent the types defined in the OpenAPI spec.
Ideally, these should be used within the UI to pass API-oriented data around.
They _must_ be used with the API functions.

The `apis` directory contains the generated functions for calling the API.
The models and available methods are all based on the OpenAPI spec.
