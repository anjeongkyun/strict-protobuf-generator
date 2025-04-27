package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;

public class JavaTypeMapper {
    public static String map(DescriptorProtos.FieldDescriptorProto.Type type) {
        return switch (type) {
            case TYPE_STRING -> "String";
            case TYPE_INT32 -> "int";
            case TYPE_INT64 -> "long";
            case TYPE_BOOL -> "boolean";
            case TYPE_FLOAT -> "float";
            case TYPE_DOUBLE -> "double";
            default -> "Object";
        };
    }
}
