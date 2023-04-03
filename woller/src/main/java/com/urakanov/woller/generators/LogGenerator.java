package com.urakanov.woller.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.Date;

public class LogGenerator {
    public LogGenerator() {
    }

    public void generateCode(String className, Filer filer) throws IOException {
        JavaFileObject sourceFile = filer.createSourceFile(className + "Logger");

        filer.

        TypeSpec person = TypeSpec
                .classBuilder("Person")
                .addModifiers(Modifier.PUBLIC)
                .addField("test")
                .addMethod(MethodSpec
                        .methodBuilder("getName")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class)
                        .addStatement("return this.name")
                        .build())
                .addMethod(MethodSpec
                        .methodBuilder("setName")
                        .addParameter(String.class, "name")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class)
                        .addStatement("this.name = name")
                        .build())
                .addMethod(sumOfTen)
                .build();

        JavaFile javaFile = JavaFile
                .builder("com.baeldung.javapoet.person", person)
                .indent("    ")
                .addStaticImport(Date.class, "UTC")
                .addStaticImport(ClassName.get("java.time", "ZonedDateTime"), "*")
                .build();

        JavaFile.builder(filer.get)

        javaFile.w


    }
}
