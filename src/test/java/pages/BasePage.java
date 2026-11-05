package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        for (int versuch = 0; versuch < 2; versuch++) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                // Element neu suchen und im zweiten Durchlauf erneut klicken
            } catch (TimeoutException e) {
                // Klick per JavaScript versuchen, falls z.B. ein Overlay den normalen Klick blockiert
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return;
            }
        }
    }

    protected boolean isDisplayed(By locator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}