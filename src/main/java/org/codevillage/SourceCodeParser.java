package org.codevillage;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.IOException;

public class SourceCodeParser implements Parser {
  static ArrayList<JavaEntity> entities;

  public SourceCodeParser() {
    this.entities = new ArrayList<>();
  };

  @Override
  public ArrayList<JavaEntity> parseSourceFiles(File directory) throws IOException {
    ParserConfiguration configuration = StaticJavaParser.getConfiguration();
    configuration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
    SourceFileChecker fileChecker = new SourceFileChecker();
    JavaEntityFactory entityFactory = new JavaEntityFactory();
    // validate java files as source code
    File[] sourceCodeFiles = fileChecker.getFilesToParse(directory);
    for (File file : sourceCodeFiles) {
      JavaEntity entity = entityFactory.createEntityFromFile(file);
      if (entity != null) {
        entities.add(entity);
      }
      else {
        System.out.println(file.getName() + " is not a valid java file");
      }
    }
    return entities;
  }

}
