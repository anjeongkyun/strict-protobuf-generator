package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorHelperGeneratorTest {

    @Test
    @DisplayName("returns valid constructor helper code for typical message")
    void sut_returns_valid_constructor_helper_code() {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("Person")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("name")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                .setNumber(1)
                .build())
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("age")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                .setNumber(2)
                .build())
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.example",
            "Person",
            message
        );

        // Assert
        assertAll(
            () -> assertTrue(generated.contains("package io.github.protogenerator.example;")),
            () -> assertTrue(generated.contains("public static Person from(String name, int age)")),
            () -> assertTrue(generated.contains(".setName(name)")),
            () -> assertTrue(generated.contains(".setAge(age)"))
        );
    }

    @Test
    @DisplayName("returns empty constructor when message has no fields")
    void sut_returns_empty_constructor_for_empty_message() {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("EmptyMessage")
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.empty",
            "EmptyMessage",
            message
        );

        // Assert
        assertTrue(generated.contains("public static EmptyMessage from()"));
        assertFalse(generated.contains(".set")); // 필드가 없으니까 set 메소드가 없어야 정상
    }

    @ParameterizedTest(name = "maps proto type {0} to java type {1}")
    @CsvSource({
        "TYPE_STRING, String",
        "TYPE_INT32, int",
        "TYPE_INT64, long",
        "TYPE_BOOL, boolean",
        "TYPE_FLOAT, float",
        "TYPE_DOUBLE, double"
    })
    void sut_maps_proto_types_to_java_types(String protoTypeName, String expectedJavaType) {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.FieldDescriptorProto.Type protoType = DescriptorProtos.FieldDescriptorProto.Type.valueOf(protoTypeName);
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("SampleMessage")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("field")
                .setType(protoType)
                .setNumber(1)
                .build())
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.types",
            "SampleMessage",
            message
        );

        // Assert
        assertTrue(generated.contains(expectedJavaType + " field"));
        assertTrue(generated.contains(".setField(field)"));
    }
}
