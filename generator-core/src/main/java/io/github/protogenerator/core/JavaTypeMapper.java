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
            case TYPE_ENUM -> field.getTypeName().substring(field.getTypeName().lastIndexOf('.') + 1); // Enum은 그대로 이름 사용
            case TYPE_MESSAGE -> field.getTypeName().substring(field.getTypeName().lastIndexOf('.') + 1); // 메시지도 이름 사용
            default -> "Object";
        };
    }
}
