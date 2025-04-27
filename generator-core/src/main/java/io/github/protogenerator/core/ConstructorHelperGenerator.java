package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;

public class ConstructorHelperGenerator {
    public String generateConstructorHelper(String protoPackage, String originalClassName, DescriptorProtos.DescriptorProto message) {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(protoPackage).append(";\n\n");
        sb.append("public final class ").append(originalClassName).append("Constructor {\n\n");

        sb.append("    private ").append(originalClassName).append("Constructor() {}\n\n");

        sb.append("    public static ").append(originalClassName).append(" from(");
        for (int i = 0; i < message.getFieldCount(); i++) {
            DescriptorProtos.FieldDescriptorProto field = message.getField(i);
            sb.append(JavaTypeMapper.map(field.getType())).append(" ").append(field.getName());
            if (i < message.getFieldCount() - 1) {
                sb.append(", ");
            }
        }
        sb.append(") {\n");

        sb.append("        return ").append(originalClassName).append(".newBuilder()\n");
        for (DescriptorProtos.FieldDescriptorProto field : message.getFieldList()) {
            sb.append("            .set").append(StringUtil.capitalize(field.getName())).append("(").append(field.getName()).append(")\n");
        }
        sb.append("            .build();\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }
}
