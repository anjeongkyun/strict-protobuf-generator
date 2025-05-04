package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorHelperGeneratorTest {
    @Test
    @DisplayName("sut_returns_valid_constructor_helper_code_for_typical_message")
    void sut_returns_valid_constructor_helper_code_for_typical_message() {
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
    @DisplayName("sut_returns_constructor_with_nullable_optional_fields")
    void sut_returns_constructor_with_nullable_optional_fields() {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("OptionalMessage")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("optionalField")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                .setProto3Optional(true)
                .setNumber(1)
                .build())
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.optional",
            "OptionalMessage",
            message
        );

        // Assert
        assertAll(
            () -> assertTrue(generated.contains("Integer optionalField")),
            () -> assertTrue(generated.contains("if (optionalField != null)")),
            () -> assertTrue(generated.contains(".setOptionalField(optionalField)"))
        );
    }

    @Test
    @DisplayName("sut_returns_constructor_handling_enum_types")
    void sut_returns_constructor_handling_enum_types() {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("EnumMessage")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("status")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_ENUM)
                .setTypeName(".io.github.protogenerator.example.PaymentStatus")
                .setNumber(1)
                .build())
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.enums",
            "EnumMessage",
            message
        );

        // Assert
        assertAll(
            () -> assertTrue(generated.contains("PaymentStatus status")),
            () -> assertTrue(generated.contains(".setStatus(status)"))
        );
    }

    @Test
    @DisplayName("sut_returns_constructor_handling_message_types")
    void sut_returns_constructor_handling_message_types() {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("NestedMessage")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("amount")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE)
                .setTypeName(".io.github.protogenerator.example.Amount")
                .setNumber(1)
                .build())
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.nested",
            "NestedMessage",
            message
        );

        // Assert
        assertAll(
            () -> assertTrue(generated.contains("Amount amount")),
            () -> assertTrue(generated.contains(".setAmount(amount)"))
        );
    }

    @Test
    @DisplayName("sut_returns_empty_constructor_for_empty_message")
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
        assertAll(
            () -> assertTrue(generated.contains("public static EmptyMessage from()")),
            () -> assertFalse(generated.contains(".set"))
        );
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
    @DisplayName("sut_maps_proto_types_to_java_types_correctly")
    void sut_maps_proto_types_to_java_types_correctly(String protoTypeName, String expectedJavaType) {
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
        assertAll(
            () -> assertTrue(generated.contains(expectedJavaType + " field")),
            () -> assertTrue(generated.contains(".setField(field)"))
        );
    }

    @Test
    @DisplayName("sut_skips_setting_non_optional_reference_fields_when_null")
    void sut_skips_setting_non_optional_reference_fields_when_null() {
        // Arrange
        var sut = new ConstructorHelperGenerator();
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("MixedMessage")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("text")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                .setNumber(1)
                .setProto3Optional(true)
                .build())
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("status")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_ENUM)
                .setTypeName(".io.github.protogenerator.example.Status")
                .setNumber(2)
                .setProto3Optional(true)
                .build())
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("amount")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE)
                .setTypeName(".io.github.protogenerator.example.Amount")
                .setNumber(3)
                .setProto3Optional(true)
                .build())
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("count")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                .setNumber(4)
                .build())
            .build();

        // Act
        String generated = sut.generateConstructorHelper(
            "io.github.protogenerator.example",
            "MixedMessage",
            message
        );

        // Assert
        assertAll(
            () -> assertTrue(generated.contains("if (text != null)")),
            () -> assertTrue(generated.contains("if (status != null)")),
            () -> assertTrue(generated.contains("if (amount != null)")),
            () -> assertTrue(generated.contains("builder.setCount(count)")),
            () -> assertFalse(generated.contains("builder.setText(text);") && !generated.contains("if"))
        );
    }
}
