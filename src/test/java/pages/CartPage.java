package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private By cartIcon = By.xpath("//*[@id='nav-cart']");
    private By cartItemTitles = By.xpath("//span[contains(@class,'sc-product-title')] | //div[contains(@class,'sc-product-title')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // öffnet den Warenkorb über das Warenkorb-Icon
    public void open() {
        click(cartIcon);
    }

    /**
     * Es prüft, ob im Warenkorb ein Produkt enthalten ist, dessen Titel alle Wörter
     * des übergebenen Suchbegriffs enthält (Reihenfolge im Titel spielt keine Rolle,
     * z.B. wegen zusätzlicher Wörter wie "Herren" zwischen Marke und Modellname)
     */
    public boolean containsProduct(String suchbegriff) {
        List<WebElement> titel = findAll(cartItemTitles);
        String[] woerter = suchbegriff.toLowerCase().split(" ");

        for (WebElement element : titel) {
            String text = element.getText().toLowerCase();
            boolean alleWoerterEnthalten = true;

            for (String wort : woerter) {
                if (!text.contains(wort)) {
                    alleWoerterEnthalten = false;
                    break;
                }
            }

            if (alleWoerterEnthalten) {
                return true;
            }
        }
        return false;
    }

    // gibt alle Produkttitel zurück, die aktuell im Warenkorb stehen
    public List<String> getItemTitles() {
        List<WebElement> elemente = findAll(cartItemTitles);
        List<String> titel = new ArrayList<>();
        for (WebElement element : elemente) {
            titel.add(element.getText());
        }
        return titel;
    }
}