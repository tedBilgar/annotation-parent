package com.urakanov.woller.processors;

import com.google.auto.service.AutoService;
import com.urakanov.woller.annotations.Log;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes(
        "com.urakanov.woller.annotations.Log")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class LogProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements
                    = roundEnv.getElementsAnnotatedWith(annotation);

            LogProcessor logProcessor = new LogProcessor();
            processingEnv.getFiler()
        }

        return false;
    }
}
