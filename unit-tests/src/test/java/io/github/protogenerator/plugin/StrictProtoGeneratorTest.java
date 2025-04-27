package io.github.protogenerator.plugin;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.compiler.PluginProtos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StrictProtoGeneratorTest {
    @Test
    @DisplayName("returns empty response when proto file has no messages")
    void sut_returns_empty_response_when_no_messages_exist() {
        // Arrange
        DescriptorProtos.FileDescriptorProto file = DescriptorProtos.FileDescriptorProto.newBuilder()
            .setName("empty.proto")
            .setPackage("io.github.protogenerator.example")
            .build();

        PluginProtos.CodeGeneratorRequest request = PluginProtos.CodeGeneratorRequest.newBuilder()
            .addProtoFile(file)
            .build();

        // Act
        PluginProtos.CodeGeneratorResponse actual = StrictProtoGenerator.process(request);

        // Assert
        assertEquals(0, actual.getFileCount());
    }

    @Test
    @DisplayName("returns constructor helpers for multiple proto files")
    void sut_returns_constructor_helpers_for_multiple_proto_files() {
        // Arrange
        DescriptorProtos.DescriptorProto message1 = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("Person")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("name")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                .build())
            .build();

        DescriptorProtos.DescriptorProto message2 = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("Company")
            .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName("companyName")
                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                .build())
            .build();

        DescriptorProtos.FileDescriptorProto file1 = DescriptorProtos.FileDescriptorProto.newBuilder()
            .setName("person.proto")
            .setPackage("io.github.protogenerator.people")
            .addMessageType(message1)
            .build();

        DescriptorProtos.FileDescriptorProto file2 = DescriptorProtos.FileDescriptorProto.newBuilder()
            .setName("company.proto")
            .setPackage("io.github.protogenerator.business")
            .addMessageType(message2)
            .build();

        PluginProtos.CodeGeneratorRequest request = PluginProtos.CodeGeneratorRequest.newBuilder()
            .addProtoFile(file1)
            .addProtoFile(file2)
            .build();

        // Act
        PluginProtos.CodeGeneratorResponse actual = StrictProtoGenerator.process(request);

        // Assert
        assertEquals(2, actual.getFileCount());
    }

    @Test
    @DisplayName("returns helper with default package when proto package is missing")
    void sut_returns_helper_with_default_package_when_proto_package_missing() {
        // Arrange
        DescriptorProtos.DescriptorProto message = DescriptorProtos.DescriptorProto.newBuilder()
            .setName("OrphanMessage")
            .build();

        DescriptorProtos.FileDescriptorProto file = DescriptorProtos.FileDescriptorProto.newBuilder()
            .setName("orphan.proto")
            .addMessageType(message)
            .build();

        PluginProtos.CodeGeneratorRequest request = PluginProtos.CodeGeneratorRequest.newBuilder()
            .addProtoFile(file)
            .build();

        // Act
        PluginProtos.CodeGeneratorResponse actual = StrictProtoGenerator.process(request);

        // Assert
        assertEquals(1, actual.getFileCount());
        PluginProtos.CodeGeneratorResponse.File generatedFile = actual.getFile(0);
        assertTrue(generatedFile.getName().endsWith("OrphanMessageConstructor.java"));
    }
}
