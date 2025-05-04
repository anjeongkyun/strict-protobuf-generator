# strict-protobuf-generator

Generate Java constructor helpers for your Protocol Buffers (proto3) messages.

This plugin enables simple, type-safe construction of Protobuf messages by generating static factory methods based on message fields.

---

## Features

- ✅ Constructor generation based on Protobuf message fields
- ✅ Supports `proto3 optional` fields (null-safe)
- ✅ Auto-generates import statements for message and enum types
- ✅ Supports nested package structures
- ✅ Easy integration with `protoc` via plugin

---

## Installation
Using Gradle (Local Development)

```bash
git clone https://github.com/protobuf-utils/strict-protobuf-generator.git
cd strict-protobuf-generator
./gradlew :protoc-plugin:installDist
```
This will generate the plugin binaries at
```
protoc-plugin/build/install/protoc-plugin
```
Make sure you have Java 17+ and protobuf-compiler installed in your environment.

---

## Example Usage
Given the following example.proto

```protobuf
syntax = "proto3";

package example;

option java_package = "com.example";
option java_multiple_files = true;

message User {
  string id = 1;
  optional string email = 2;
}
```
Run bash
```bash
protoc \
  --plugin=protoc-gen-strictproto=./scripts/protoc-gen-strictproto \
  --strictproto_out=./gen \
  --proto_path=. \
  path/to/example.proto
```
The generated code will look like
```java
package com.example;

public final class UserConstructor {

    private UserConstructor() {}

    public static User from(String id, String email) {
        User.Builder builder = User.newBuilder();
        builder.setId(id);
        if (email != null) {
            builder.setEmail(email);
        }
        return builder.build();
    }
}
```

---

## Docker Support
You can use Docker to run the generator in isolation

```bash
docker compose build strictproto-dev
docker compose run --rm strictproto-dev bash scripts/generate-strictproto.sh
```
Make sure your .proto and scripts/ directory are available inside the container.


