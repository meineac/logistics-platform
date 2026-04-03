package utils.loader;

import models.LogisticsData;

public interface DataLoader {
    LogisticsData load(String filePath);
}
