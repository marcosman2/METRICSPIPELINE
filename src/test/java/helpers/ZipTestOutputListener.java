package helpers;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTestOutputListener implements ITestListener {
    @Override
    public void onFinish(ITestContext context) {

        String testOutputFolderPath = "test-output";
        String zipFileName = "Metrics_Tests_Reports.zip";

        try {
            zipFolder(testOutputFolderPath, zipFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipFolder(String sourceFolder, String zipFileName) throws IOException {
        File folderToZip = new File(sourceFolder);
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            zipFile(folderToZip, folderToZip.getName(), zipOut);
        }
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            for (File child : fileToZip.listFiles()) {
                zipFile(child, fileName + "/" + child.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zipOut.write(buffer, 0, bytesRead);
            }
        }
    }

}
