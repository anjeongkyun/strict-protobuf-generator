package io.github.protogenerator.core;

import com.google.protobuf.DescriptorProtos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstructorHelperGenerator {
    public String generateConstructorHelper(String protoPackage, String originalClassName, DescriptorProtos.DescriptorProto descriptorProto) {
        StringBuilder builder = new StringBuilder();
        String packageDecl = protoPackage.isEmpty() ? "" : "package " + protoPackage + ";\n\n";

        builder.append(packageDecl);
        builder.append(generateImports(descriptorProto, protoPackage));
        builder.append("public final class ")
            .append(originalClassName)
            .append("Constructor {\n\n")
            .append("    private ")
            .append(originalClassName)
            .append("Constructor() {}\n\n")
            .append("    public static ")
            .append(originalClassName)
            .append(" from(");

        for (int i = 0; i < descriptorProto.getFieldCount(); i++) {
            DescriptorProtos.FieldDescriptorProto field = descriptorProto.getField(i);
            if (i > 0) builder.append(", ");
            builder.append(JavaTypeMapper.map(field)).append(" ").append(field.getName());
        }

        builder.append(") {\n")
            .append("        ")
            .append(originalClassName)
            .append(".Builder builder = ")
            .append(originalClassName)
            .append(".newBuilder();\n");

        for (DescriptorProtos.FieldDescriptorProto field : descriptorProto.getFieldList()) {
            String fieldName = field.getName();
            String setter = "set" + StringUtil.capitalize(fieldName);
            if (field.hasProto3Optional()) {
                builder.append("        if (")
                    .append(fieldName)
                    .append(" != null) {\n")
                    .append("            builder.")
                    .append(setter)
                    .append("(")
                    .append(fieldName)
                    .append(");\n")
                    .append("        }\n");
            } else {
                builder.append("        builder.")
                    .append(setter)
                    .append("(")
                    .append(fieldName)
                    .append(");\n");
            }
        }

        builder.append("        return builder.build();\n")
            .append("    }\n")
            .append("}\n");

        return builder.toString();
    }

    private String generateImports(DescriptorProtos.DescriptorProto descriptorProto, String packageName) {
        Set<String> imports = new HashSet<>();

        for (DescriptorProtos.FieldDescriptorProto field : descriptorProto.getFieldList()) {
            DescriptorProtos.FieldDescriptorProto.Type type = field.getType();
            if (type == DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE ||
                type == DescriptorProtos.FieldDescriptorProto.Type.TYPE_ENUM) {

                String fieldType = JavaTypeMapper.map(field);

                if (!List.of("int", "long", "float", "double", "boolean", "String", "Object").contains(fieldType)) {
                    imports.add("import " + packageName + "." + fieldType + ";");
                }
            }
        }

        return imports.stream()
            .sorted()
            .collect(Collectors.joining("\n")) + "\n\n";
    }
}
