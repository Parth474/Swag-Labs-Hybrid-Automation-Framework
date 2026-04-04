package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotUtil {

    public static String takeScreenShot(WebDriver driver, String testName, String screenshotDir) {

        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String path =
                screenshotDir + "/" + testName + "_" + timestamp + ".png";

        try {

            File src =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File dest = new File(path);

            FileUtils.copyFile(src, dest);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}