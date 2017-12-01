package com.kchmielewski.sda.league.importing;

import com.google.common.io.Files;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class FileCleanupTest {
    private final String importDirectory = "src/test/resources/import/";
    private final String processedDirectory = "src/test/resources/processed";
    private final String errorDirectory = "src/test/resources/error";


    @After
    public void tearDown() throws Exception {
        for (String directoryName : Arrays.asList(errorDirectory, processedDirectory)) {

            File directory = new File(directoryName);
            File[] files = directory.listFiles();
            if (files != null) {
                Stream.of(files).forEach(f -> {
                    try {
                        Files.move(f, new File(importDirectory + f.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}