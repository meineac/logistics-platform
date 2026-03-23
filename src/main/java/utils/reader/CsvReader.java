package utils.reader;

import models.LogisticsData;
import utils.parser.CsvParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CsvReader implements Reader {
    private final CsvParser parser;

    public CsvReader(CsvParser parser) {
        this.parser = parser;
    }

    @Override
    public LogisticsData read(String filePath) {
        try {
            var table = parseCsv(filePath);

            return parser.parseData(table);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }

    private List<List<String>> parseCsv(String filePath) throws IOException {
        try(var lines = Files.lines(Path.of(filePath))) {
            return lines
                    .skip(1)
                    .map(line -> line.split(";", -1))
                    .map(columns -> Arrays.stream(columns)
                            .map(String::strip)
                            .map(s -> s.isEmpty() ? null : s)
                            .toList())
                    .toList();
        }
    }
}
