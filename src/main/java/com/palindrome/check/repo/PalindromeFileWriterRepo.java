package com.palindrome.check.repo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Slf4j
public class PalindromeFileWriterRepo implements PalindromeRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(PalindromeFileWriterRepo.class);
    private static final String fileStorageName = "palindromes.db";
    private Path fileStorePath;

    public PalindromeFileWriterRepo(@Value("${file.store.dir:/tmp}") String storageDirectory) {
        this.fileStorePath = Paths.get(storageDirectory, fileStorageName);
        if (Files.notExists(this.fileStorePath)) {
            try {
                Files.createFile(this.fileStorePath);
            } catch (IOException e) {
                LOGGER.error("Unable to create file for storage {} got an error {}", this.fileStorePath.toString(), e);
            }
        }
    }

    @Override
    public void persistPalindromeCheckResult(String inputString, boolean result) {
        LOGGER.info("fileStorePath is {}", fileStorePath);
        persistDataToFile(this.fileStorePath, inputString + "::" + String.valueOf(result));
    }

    @Override
    public List<Pair<String, Boolean>> getAllPalindromeChecks() {
        if (Files.notExists(this.fileStorePath)) {
            LOGGER.error("File {} doesnt exist ", this.fileStorePath);
        }
        try (Stream<String> lines = Files.lines(this.fileStorePath)) {
            return lines.map(line -> {
                        String[] data = line.split("::");
                        return Pair.of(data[0], Boolean.parseBoolean(data[1]));
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Exception while retrieving from file {}", this.fileStorePath, e);
            return Collections.emptyList();
        }
    }

    private void persistDataToFile(Path path, String data) {
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            LOGGER.error("Unable to persist data {} to file {} got an error {}", data, path, e);
        }
    }

}
