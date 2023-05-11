#!/bin/bash
cp ../beeper-oas.yaml beeper.yaml
podman run --rm -v "${PWD}:/local:z" openapitools/openapi-generator-cli:v5.4.0 generate \
    -i /local/beeper.yaml \
    -g typescript-fetch \
    -o /local/beeper-ui-client \
    -c /local/generator-config.yaml
rm beeper.yaml
