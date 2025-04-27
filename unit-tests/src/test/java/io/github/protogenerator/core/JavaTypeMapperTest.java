package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTypeMapperTest {
    @ParameterizedTest(name = "maps proto type {0} to java type {1}")
    @CsvSource({
        "TYPE_STRING, String",
        "TYPE_INT32, int",
        "TYPE_INT64, long",
        "TYPE_BOOL, boolean",
        "TYPE_FLOAT, float",
        "TYPE_DOUBLE, double"
    })
    @DisplayName("returns correct Java type for supported Proto types")
    void sut_returns_correct_java_type_for_supported_proto_types(String protoTypeName, String expectedJavaType) {
        // Arrange
        DescriptorProtos.FieldDescriptorProto.Type protoType = DescriptorProtos.FieldDescriptorProto.Type.valueOf(protoTypeName);

        // Act
        String actual = JavaTypeMapper.map(protoType);

        // Assert
        assertEquals(expectedJavaType, actual);
    }

    @Test
    @DisplayName("returns Object for unsupported Proto type")
    void sut_returns_Object_for_unsupported_proto_type() {
        // Act
        String actual = JavaTypeMapper.map(DescriptorProtos.FieldDescriptorProto.Type.TYPE_GROUP);

        // Assert
        assertEquals("Object", actual);
    }
}
