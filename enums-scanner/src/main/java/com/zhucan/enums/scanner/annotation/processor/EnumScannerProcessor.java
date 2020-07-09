package com.zhucan.enums.scanner.annotation.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auto.service.AutoService;
import com.zhucan.enums.scanner.annotation.EnumScan;
import com.zhucan.enums.scanner.constant.EnumConstant;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author: zhuCan
 * @date: 2020/1/16 9:44
 * @description: 在系统编译的时候, 扫描所有枚举的class存入到文件中
 */
@AutoService(Processor.class)
public class EnumScannerProcessor extends AbstractProcessor {

  private Filer filer;
  private Messager messager;
  private Elements elementUtils;
  private ObjectMapper mapper;


  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    filer = processingEnv.getFiler();
    messager = processingEnvironment.getMessager();
    elementUtils = processingEnvironment.getElementUtils();
    mapper = new ObjectMapper();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(EnumScan.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    messager.printMessage(Diagnostic.Kind.NOTE, "Processor : " + getClass().getSimpleName());

    for (TypeElement annotation : annotations) {
      for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found @Log at " + element);
      }
    }

    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(EnumScan.class);

    List<String> clazz = new ArrayList<>();

    elements.forEach(x -> {
      PackageElement packageElement = elementUtils.getPackageOf(x);
      String packageName = packageElement.getQualifiedName().toString();
      // 被扫描的类名
      Name simpleName = x.getSimpleName();
      clazz.add(packageName + "." + simpleName);

    });

    // 枚举码表class写入文件
    try {
      if (!elements.isEmpty()) {
        FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", EnumConstant.ENUM_FILE_NAME);
        Writer writer = resource.openWriter();
        writer.write(mapper.writeValueAsString(clazz));
        writer.flush();
        writer.close();

      }
    } catch (Exception e) {
      e.printStackTrace();
      messager.printMessage(Diagnostic.Kind.ERROR, "mistake : " + e.getLocalizedMessage());
    }

    return false;
  }

}
