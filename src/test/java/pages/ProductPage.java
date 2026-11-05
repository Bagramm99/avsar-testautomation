package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

public class ProductPage extends BasePage {

    private By productTitle = By.xpath("//*[@id='productTitle']");
    private By addToCartButton = By.xpath("//*[@id='add-to-cart-button']");

    // diese Elemente gibt es nur, wenn das Produkt überhaupt Varianten hat
    private By sizeDropdown = By.xpath("//*[@id='native_dropdown_selected_size_name']");
    private By sizeSwatches = By.xpath("//div[@id='variation_size_name']//li[not(contains(@class,'swatchUnavailable'))]//*[contains(@class,'a-button-text')]");
    private By colorSwatches = By.xpath("//div[@id='variation_color_name']//li[not(contains(@class,'swatchUnavailable'))]//*[contains(@class,'a-button-text')]");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // prüft, ob die Produktseite geladen wurde
    public boolean isLoaded() {
        return isDisplayed(productTitle, 10);
    }

    // wählt, falls vorhanden, eine verfügbare Größe und Farbe aus
    public void selectAvailableVariants() {
        selectFirstAvailableSwatch(sizeSwatches);
        selectFirstAvailableSwatch(colorSwatches);
        selectDropdownSizeIfPresent();
    }

    // klickt die erste verfügbare Option einer Swatch-Liste (beispielsweise Größen- oder Farbauswahl)
    private void selectFirstAvailableSwatch(By locator) {
        if (isDisplayed(locator, 3)) {
            List<WebElement> optionen = driver.findElements(locator);
            if (!optionen.isEmpty()) {
                optionen.get(0).click();
            }
        }
    }

    // manche Produkte haben statt Swatches ein Dropdown für die Größe
    private void selectDropdownSizeIfPresent() {
        if (isDisplayed(sizeDropdown, 3)) {
            Select dropdown = new Select(find(sizeDropdown));
            for (WebElement option : dropdown.getOptions()) {
                String value = option.getAttribute("value");
                if (value != null && !value.equals("-1") && !value.isEmpty()) {
                    dropdown.selectByVisibleText(option.getText());
                    break;
                }
            }
        }
    }

    // legt das Produkt in den Warenkorb;  es erstellt bei Fehler einen Screenshot zur Analyse
    public void addToCart() {
        try {
            click(addToCartButton);
        } catch (RuntimeException e) {
            screenshotErstellen("fehler_add_to_cart");
            throw e;
        }
    }

    // speichert einen Screenshot der aktuellen Seite im Projektordner
    private void screenshotErstellen(String dateiname) {
        try {
            File quelle = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File ziel = new File(dateiname + ".png");
            FileUtils.copyFile(quelle, ziel);
            System.out.println("Screenshot gespeichert unter: " + ziel.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println("Screenshot konnte nicht erstellt werden: " + ex.getMessage());
        }
    }
}