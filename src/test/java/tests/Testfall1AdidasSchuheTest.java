package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AmazonHomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Testfall1AdidasSchuheTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void searchAdidasShoesAndAddToCart() {
        AmazonHomePage startseite = new AmazonHomePage(driver);
        startseite.open();

        SearchResultsPage ergebnisse = startseite.search("Adidas Schuhe");

        // Sollergebnis: mindestens 5 Schuhe werden angezeigt
        assertTrue(ergebnisse.getResultCount() >= 5,
                "Es wurden weniger als 5 Ergebnisse angezeigt");

        ProductPage produkt = ergebnisse.openFirstNonSponsoredResult();

        // Sollergebnis: das Produkt wird erfolgreich aufgerufen
        assertTrue(produkt.isLoaded(), "Produktseite wurde nicht geladen");

        produkt.selectAvailableVariants();
        produkt.addToCart();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}