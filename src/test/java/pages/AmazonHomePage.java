package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonHomePage extends BasePage {

    private By searchBox = By.xpath("//*[@id='twotabsearchtextbox']");
    private By searchButton = By.xpath("//*[@id='nav-search-submit-button']");

    // Cookie-Banner erscheint nicht immer (z.B. schon akzeptiert), deswegen kein normales click()
    private By cookieAcceptButton = By.xpath("//*[@id='sp-cc-accept']");

    public AmazonHomePage(WebDriver driver) {
        super(driver);
    }

    // öffnet die Amazon-Startseite und akzeptiert den Cookie-Banner, falls er erscheint
    public void open() {
        driver.get("https://www.amazon.de");

        if (isDisplayed(cookieAcceptButton, 5)) {
            click(cookieAcceptButton);
        }
    }

    // gibt den Suchbegriff ein, startet die Suche und gibt die Ergebnisseite zurück
    public SearchResultsPage search(String suchbegriff) {
        WebElement box = find(searchBox);
        box.clear();
        box.sendKeys(suchbegriff);
        click(searchButton);
        return new SearchResultsPage(driver);
    }
}