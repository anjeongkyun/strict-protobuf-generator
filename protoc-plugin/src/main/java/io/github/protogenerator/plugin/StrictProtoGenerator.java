package io.github.protogenerator.plugin;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.compiler.PluginProtos;
import io.github.protogenerator.core.ConstructorHelperGenerator;

import java.io.IOException;
import java.util.List;

public class StrictProtoGenerator {
    public static void main(String[] args) throws IOException {
        PluginProtos.CodeGeneratorRequest request = PluginProtos.CodeGeneratorRequest.parseFrom(System.in);
        PluginProtos.CodeGeneratorResponse response = process(request);
        response.writeTo(System.out);
    }

    public static PluginProtos.CodeGeneratorResponse process(PluginProtos.CodeGeneratorRequest request) {
        ConstructorHelperGenerator generator = new ConstructorHelperGenerator();
        PluginProtos.CodeGeneratorResponse.Builder responseBuilder = PluginProtos.CodeGeneratorResponse.newBuilder();

        List<DescriptorProtos.FileDescriptorProto> protoFiles = request.getProtoFileList();
        for (DescriptorProtos.FileDescriptorProto file : protoFiles) {
            String protoPackage = file.getPackage();

            for (DescriptorProtos.DescriptorProto message : file.getMessageTypeList()) {
                String originalClassName = message.getName();
                String generatedCode = generator.generateConstructorHelper(protoPackage, originalClassName, message);

                responseBuilder.addFile(
                    PluginProtos.CodeGeneratorResponse.File.newBuilder()
                        .setName(protoPackage.replace(".", "/") + "/" + originalClassName + "Constructor.java")
                        .setContent(generatedCode)
                        .build()
                );
            }
        }

        return responseBuilder.build();
    }
}
