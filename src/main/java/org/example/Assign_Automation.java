package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class Assign_Automation {

    public static void main(String[] args) {
        // Setup WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Define constants
        String email = "vikram0812+assignment@proton.me";
        String password = "Assignment@2024";
        String videoPath = "/Users/sonikumari/Downloads/SurajAssignment.mp4"; // Replace with your file path
        String downloadPath = "/Users/sonikumari/Documents/AssignmentsDownload"; // Replace with your download folder path

        try {
            // Step 1: Login
            driver.manage().window().maximize();
            driver.get("https://app.quso.ai/auth/login-with-email");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='e.g johndoe@gmail.com']")));
            emailField.sendKeys(email);

            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter your password']")));
            passwordField.sendKeys(password);

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='login-with-email-btn']")));
            loginButton.click();

            // Step 2: Navigate to "AI Clip"
            WebElement aiClipTile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='AI Clips']")));
            aiClipTile.click();

            // Step 3: Upload a Video
            WebElement fileUploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
            fileUploadInput.sendKeys(videoPath);

            // Step 4: Generate Clips
            WebElement generateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='generate-ai-clips-button']")));
            generateButton.click();

            // Step 5: Navigate to the Project
            WebElement projectTile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='home-sidebar-nav-btn-All projects']")));
            projectTile.click();

            // Step 6: Modify Clip Name (Using new renamed XPath for "Rename Project")
            WebElement clipNameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='rename-project']")));
            clipNameField.clear();
            clipNameField.sendKeys("Assignmentignment12");
            clipNameField.submit(); // Ensure the change is confirmed (optional based on the form)

            // Step 7: Edit the Clip
            WebElement viralTemplateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='edit-clip-review-page-go-to-editor']")));
            viralTemplateButton.click();

            // Step 8: Trim the Clip
            WebElement trimButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='marker playhead']")));
            trimButton.click();

            WebElement trimSlider = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='range']")));
            trimSlider.sendKeys("-5"); // Reduce duration by 5 seconds

            WebElement saveTrimButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Save Trim']")));
            saveTrimButton.click();

            // Step 9: Download the Edited Video
            WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Download']")));
            downloadButton.click();

            System.out.println("Step 9 completed: Edited video downloaded.");

            // Step 10: Wait for the file to download (polling approach to check file download)
            waitForFileDownload(downloadPath, "edited_video.mp4");

            // Step 11: Re-Upload the Downloaded Video
            WebElement uploadButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
            uploadButton.sendKeys(downloadPath + "/edited_video.mp4");

            WebElement modernTemplateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Modern']")));
            modernTemplateButton.click();

            // Step 12: Final Download
            WebElement finalDownloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Download']")));
            finalDownloadButton.click();

            System.out.println("Automation completed successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    // Helper method to wait for file download to complete
    private static void waitForFileDownload(String downloadPath, String fileName) {
        File file = new File(downloadPath + "/" + fileName);
        int waitTime = 0;
        while (!file.exists() && waitTime < 60) {  // Wait up to 60 seconds for the file
            try {
                Thread.sleep(1000); // Wait for 1 second before checking again
                waitTime++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (file.exists()) {
            System.out.println("File downloaded: " + file.getAbsolutePath());
        } else {
            System.out.println("File download timed out.");
        }
    }
}
