package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;

public class JavaTypeMapper {
    public static String map(DescriptorProtos.FieldDescriptorProto field) {
        boolean isOptional = field.hasProto3Optional();
        return switch (field.getType()) {
            case TYPE_STRING -> "String";
            case TYPE_INT32 -> isOptional ? "Integer" : "int";
            case TYPE_INT64 -> isOptional ? "Long" : "long";
            case TYPE_BOOL -> isOptional ? "Boolean" : "boolean";
            case TYPE_FLOAT -> isOptional ? "Float" : "float";
            case TYPE_DOUBLE -> isOptional ? "Double" : "double";
            case TYPE_ENUM, TYPE_MESSAGE -> field.getTypeName().substring(field.getTypeName().lastIndexOf('.') + 1);
            default -> "Object";
        };
    }

    public static boolean isNullable(DescriptorProtos.FieldDescriptorProto field) {
        return field.hasProto3Optional();
    }
}
