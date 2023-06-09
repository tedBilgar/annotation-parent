package com.urakanov.woller.generators;

import com.squareup.javapoet.*;
import io.jd.framework.BeanDefinition;
import io.jd.framework.BeanProvider;
import io.jd.framework.ScopeProvider;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.stream.Collectors;

import static javax.lang.model.element.Modifier.PUBLIC;

class ElementWriter {
    private final TypeElement definedClass;
    private final List<TypeMirror> constructorParameterTypes;
    private final ClassName definedClassName;

    ElementWriter(TypeElement definedClass, List<TypeMirror> constructorParameterTypes) {
        this.definedClass = definedClass;
        this.constructorParameterTypes = constructorParameterTypes;
        this.definedClassName = ClassName.get(definedClass);
    }

    public JavaFile createDefinition() {
        ParameterizedTypeName parameterizedBeanDefinition = ParameterizedTypeName.get(ClassName.get(BeanDefinition.class), definedClassName);
        var definitionSpec = TypeSpec.classBuilder("$%s$Definition".formatted(definedClassName.simpleName()))
                .addModifiers(PUBLIC)
                .addMethod(createMethodSpec())
                .addMethod(typeMethodSpec())
                .addField(scopeProvider())
                .build();
        return JavaFile.builder(definedClassName.packageName(), definitionSpec).build();
    }

    private MethodSpec typeMethodSpec() {
        var classTypeForDefinedTyped = ParameterizedTypeName.get(ClassName.get(Class.class), definedClassName);
        return MethodSpec.methodBuilder("type")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addStatement("return $T.class", definedClass)
                .returns(classTypeForDefinedTyped)
                .build();
    }

    private MethodSpec createMethodSpec() {
        return MethodSpec.methodBuilder("create")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(ParameterSpec.builder(BeanProvider.class, "beanProvider").build())
                .addStatement("return provider.apply(beanProvider)")
                .returns(definedClassName)
                .build();
    }

    private FieldSpec scopeProvider() {
        ParameterizedTypeName scopeProviderType = ParameterizedTypeName.get(ClassName.get(ScopeProvider.class), definedClassName);
        return FieldSpec.builder(scopeProviderType, "provider", Modifier.FINAL, Modifier.PRIVATE)
                .initializer(singletonScopeInitializer())
                .build();
    }

    private CodeBlock singletonScopeInitializer() {
        var typeNames = constructorParameterTypes.stream().map(TypeName::get).toList();
        var constructorParameters = typeNames.stream().map(__ -> "beanProvider.provide($T.class)")
                .collect(Collectors.joining(", "));
        return CodeBlock.builder()
                .add("ScopeProvider.singletonScope(")
                .add("beanProvider -> ")
                .add("new ")
                .add("$T", definedClassName)
                .add("(" + constructorParameters + ")", typeNames.toArray())
                .add(")")
                .build();
    }
}
