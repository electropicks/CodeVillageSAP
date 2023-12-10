package org.codevillage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SourceFileChecker implements FileChecker{
    public SourceFileChecker(){

    }

    @Override
    public File[] getFilesToParse(File directory) {
        assert directory.isDirectory() : "Cannot recognize directory.";
        List<File> sourceFiles = new ArrayList<>();
        try {
            Files.walk(directory.toPath())
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> sourceFiles.add(path.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceFiles.toArray(new File[0]);
    }
}