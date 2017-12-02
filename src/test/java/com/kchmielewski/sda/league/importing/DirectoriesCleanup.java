package com.kchmielewski.sda.league.importing;

import org.junit.After;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class DirectoriesCleanup {
    private final String importDirectory = "src/test/resources/import/";
    private final String processedDirectory = "src/test/resources/processed";
    private final String errorDirectory = "src/test/resources/error";
    private final List<String> directoriesToClear = Arrays.asList(importDirectory, processedDirectory, errorDirectory);

    @After
    public void tearDown() throws Exception {
        for (String directoryName : directoriesToClear) {
            File directory = new File(directoryName);
            File[] files = directory.listFiles();
            if (files != null) {
                Stream.of(files).forEach(f -> {
                    if (!f.delete()) {
                        throw new RuntimeException("There was a problem deleting file: " + f);
                    }
                });
            }
        }
    }
}