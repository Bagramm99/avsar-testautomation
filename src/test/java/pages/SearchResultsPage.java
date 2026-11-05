package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage {

    private By resultItems = By.xpath("//div[@data-component-type='s-search-result']");
    private By sponsoredLabel = By.xpath(".//span[contains(text(),'Gesponsert')]");
    private By titleLink = By.xpath(".//h2//a | .//a[.//h2]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    // gibt zurück, wie viele Suchergebnisse aktuell angezeigt werden
    public int getResultCount() {
        return findAll(resultItems).size();
    }

    // öffnet das erste Suchergebnis, das nicht als "Gesponsert" markiert ist
    public ProductPage openFirstNonSponsoredResult() {
        List<WebElement> ergebnisse = findAll(resultItems);

        for (WebElement ergebnis : ergebnisse) {
            // gesponserte Treffer haben ein "Gesponsert"-Label, das prüfen wir hier
            boolean istGesponsert = !ergebnis.findElements(sponsoredLabel).isEmpty();

            if (!istGesponsert) {
                WebElement link = ergebnis.findElement(titleLink);
                link.click();
                return new ProductPage(driver);
            }
        }

        // sollte eigentlich nie vorkommen, aber zur Sicherheit
        throw new RuntimeException("Kein nicht-gesponsertes Ergebnis gefunden");
    }
}