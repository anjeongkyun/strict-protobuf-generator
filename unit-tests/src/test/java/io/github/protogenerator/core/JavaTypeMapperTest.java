package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTypeMapperTest {
    @ParameterizedTest(name = "maps proto type {0} with optional={1} to java type {2}")
    @CsvSource({
        "TYPE_STRING,false,String",
        "TYPE_STRING,true,String",
        "TYPE_INT32,false,int",
        "TYPE_INT32,true,Integer",
        "TYPE_INT64,false,long",
        "TYPE_INT64,true,Long",
        "TYPE_BOOL,false,boolean",
        "TYPE_BOOL,true,Boolean",
        "TYPE_FLOAT,false,float",
        "TYPE_FLOAT,true,Float",
        "TYPE_DOUBLE,false,double",
        "TYPE_DOUBLE,true,Double"
    })
    @DisplayName("returns correct Java type considering optional flag")
    void sut_returns_correct_java_type_considering_optional_flag(String protoTypeName, boolean isOptional, String expectedJavaType) {
        // Arrange
        DescriptorProtos.FieldDescriptorProto.Type protoType = DescriptorProtos.FieldDescriptorProto.Type.valueOf(protoTypeName);
        DescriptorProtos.FieldDescriptorProto.Builder fieldBuilder = DescriptorProtos.FieldDescriptorProto.newBuilder()
            .setType(protoType)
            .setName("testField");

        if (isOptional) {
            fieldBuilder.setProto3Optional(true);
        }

        DescriptorProtos.FieldDescriptorProto field = fieldBuilder.build();

        // Act
        String actual = JavaTypeMapper.map(field);

        // Assert
        assertEquals(expectedJavaType, actual);
    }

    @Test
    @DisplayName("returns Object for unsupported Proto type")
    void sut_returns_Object_for_unsupported_proto_type() {
        // Arrange
        DescriptorProtos.FieldDescriptorProto field = DescriptorProtos.FieldDescriptorProto.newBuilder()
            .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_GROUP)
            .setName("testField")
            .build();

        // Act
        String actual = JavaTypeMapper.map(field);

        // Assert
        assertEquals("Object", actual);
    }
}
