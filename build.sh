#!/usr/bin/env bash
set -e

./mvnw package && mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
