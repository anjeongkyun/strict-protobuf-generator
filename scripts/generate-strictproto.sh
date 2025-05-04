#!/bin/bash
set -e

PROTO_DIR=examples
OUT_DIR=/workspace/generated

mkdir -p ${OUT_DIR}
cd /workspace

for file in ${PROTO_DIR}/*.proto; do
  echo "Generating constructor for ${file}..."
  protoc \
    --experimental_allow_proto3_optional \
    --plugin=protoc-gen-strictproto=/workspace/scripts/protoc-gen-strictproto \
    --strictproto_out=${OUT_DIR} \
    --proto_path=${PROTO_DIR} \
    ${file}
done

#!/bin/bash
set -e

PROTO_DIR=examples
OUT_DIR=/workspace/generated

mkdir -p ${OUT_DIR}
cd /workspace

for file in ${PROTO_DIR}/*.proto; do
  protoc \
    --experimental_allow_proto3_optional \
    --plugin=protoc-gen-strictproto=/workspace/scripts/protoc-gen-strictproto \
    --strictproto_out=/workspace/generated \
    --proto_path=/workspace/examples \
    /workspace/examples/Payment.proto
done
