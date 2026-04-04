package utils.loader;

import models.LogisticsData;
import utils.helper.EntityAssembler;

import java.io.File;
import java.io.IOException;

public abstract class DataLoaderTemplate implements DataLoader {
    protected final EntityAssembler assembler;

    public DataLoaderTemplate(EntityAssembler assembler) {
        this.assembler = assembler;
    }

    @Override
    public final LogisticsData load(String filePath) {
        validateFilePath(filePath);
        try {
            return processData(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read/parse file at: " + filePath, e);
        }
    }

    private void validateFilePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        if (!new File(filePath).exists()) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }
    }

    protected abstract LogisticsData processData(String filePath) throws IOException;
}
