package codeDetector;

import java.io.File;
import java.util.Arrays;

public class CodeDetector {
    public DetectorReport analyze(String path) {
        File file = new File(path);
        long fileCount = Arrays.stream(file.listFiles()).filter(File::isFile).count();
        DetectorReport detectorReport = new DetectorReport();
        detectorReport.setFileCount(fileCount);
        return detectorReport;
    }
}
