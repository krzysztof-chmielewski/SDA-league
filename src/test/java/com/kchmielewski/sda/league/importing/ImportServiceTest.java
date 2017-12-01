package com.kchmielewski.sda.league.importing;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

@Ignore
public class ImportServiceTest {
    private final String directoryName = "src/test/resources/import/";
    private final String fileSuffix = "_COPY";

    @Before
    public void setUp() throws Exception {
        File directory = new File(directoryName);
        File[] files = directory.listFiles();
        if (files != null) {
            Stream.of(files).forEach(f -> {
                try {
                    Files.copy(f.toPath(), new File(directoryName + f.getName() + fileSuffix).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void name() throws Exception {

        File directory = new File(directoryName);
        File[] files = directory.listFiles();
        if (files != null) {
            Stream.of(files).forEach(f -> {
                if (!f.delete()) {
                    System.out.println("Could not delete " + f);
                }
            });
        }
    }


    @After
    public void tearDown() throws Exception {
        File directory = new File(directoryName);
        File[] files = directory.listFiles();
        if (files != null) {
            Stream.of(files).filter(f -> f.getName().endsWith(fileSuffix)).forEach(f -> {
                try {
                    Files.copy(f.toPath(), new File(directoryName + f.getName().replace(fileSuffix, "")).toPath());
                    if (!f.delete()) {
                        System.out.println("Could not delete " + f);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}