package codeDetector;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CodeDetectorTest {
    @Test
    public void can_tell_how_many_files_in_a_directory(){
        CodeDetector codeDetector = new CodeDetector();
        DetectorReport detectorReport = codeDetector.analyze("src/test/fixture");
        assertThat(detectorReport.getFileCount(), is(1L));
    }
}