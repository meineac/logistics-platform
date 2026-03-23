package utils.reader;

import models.LogisticsData;

public interface Reader {
    LogisticsData read(String filePath);
}
