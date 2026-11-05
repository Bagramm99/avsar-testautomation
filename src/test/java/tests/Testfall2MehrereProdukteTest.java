package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AmazonHomePage;
import pages.CartPage;
import pages.ProductPage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Testfall2MehrereProdukteTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void searchThreeProductsAndAddToCart() {
        AmazonHomePage startseite = new AmazonHomePage(driver);

        // Produkt 1
        startseite.open();
        searchAndAddToCart(startseite, "Adidas Herren Questar Flow Laufschuhe");

        // Produkt 2
        startseite.open();
        searchAndAddToCart(startseite, "Puma Tazon 6");

        // Produkt 3
        startseite.open();
        searchAndAddToCart(startseite, "Nike Air Max");

        // Warenkorb öffnen und prüfen, ob alle drei Produkte enthalten sind
        CartPage warenkorb = new CartPage(driver);
        warenkorb.open();

        System.out.println("Gefundene Titel im Warenkorb: " + warenkorb.getItemTitles());

        assertTrue(warenkorb.containsProduct("Questar"), "Adidas-Produkt fehlt im Warenkorb");
        assertTrue(warenkorb.containsProduct("Puma Tazon 6"), "Puma-Produkt fehlt im Warenkorb");
        assertTrue(warenkorb.containsProduct("Nike Air Max"), "Nike-Produkt fehlt im Warenkorb");
    }

    // sucht nach einem Produkt und legt das erste nicht-gesponserte Ergebnis in den Warenkorb
    private void searchAndAddToCart(AmazonHomePage startseite, String produktname) {
        SearchResultsPage ergebnisse = startseite.search(produktname);

        // Sollergebnis: mindestens 1 Treffer
        assertTrue(ergebnisse.getResultCount() >= 1, "Kein Ergebnis für " + produktname);

        ProductPage produkt = ergebnisse.openFirstNonSponsoredResult();
        produkt.selectAvailableVariants();
        produkt.addToCart();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}