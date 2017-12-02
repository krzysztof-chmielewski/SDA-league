package com.kchmielewski.sda.league.importing;

import com.google.common.io.Files;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
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

    String getImportDirectory() {
        return importDirectory;
    }

    String getProcessedDirectory() {
        return processedDirectory;
    }

    String getErrorDirectory() {
        return errorDirectory;
    }

    void copyFile(String fileName) {
        try {
            Files.copy(new File("src/test/resources/default/" + fileName + ".txt"),
                    new File("src/test/resources/import/" + fileName + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void waitForService(ScheduledExecutorService service) throws InterruptedException {
        Thread.sleep(50);
        service.shutdown();
    }
}