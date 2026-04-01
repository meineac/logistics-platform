package utils.reader;

import models.LogisticsData;

public interface DataLoader {
    LogisticsData load(String filePath);
}
