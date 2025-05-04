#!/bin/bash
set -e

PROTO_DIR=examples
OUT_DIR=/workspace/generated-java

mkdir -p ${OUT_DIR}
cd /workspace

for file in ${PROTO_DIR}/*.proto; do
  protoc \
    --experimental_allow_proto3_optional \
    --proto_path=/workspace \
    --java_out=${OUT_DIR} \
    ${file}
done
