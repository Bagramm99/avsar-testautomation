# Schnupperaufgabe Test Automation – AVSAR-TE

Selenium-Tests mit Java und JUnit 5 für die zwei Testfälle aus der Aufgabe.

## Was wird gebraucht

- JDK 19 
- Maven
- Google Chrome

## Starten

Im Projektordner:

```bash
mvn test
```

Oder in IntelliJ: Rechtsklick auf die Testklasse → Run.


## Testfall 1

Sucht "Adidas Schuhe", prüft mindestens 5 Ergebnisse, öffnet das erste
nicht gesponserte Ergebnis, wählt Größe/Farbe (falls nötig) und legt das
Produkt in den Warenkorb.

## Testfall 2

Sucht nacheinander drei Produkte (Adidas, Puma, Nike) im selben Browser
und legt jedes in den Warenkorb. Am Ende wird geprüft, ob alle drei im
Warenkorb sind.

## Hinweis

Das Produkt "Adidas Herren Questar Flow Laufschuhe" gibt es bei Amazon
nicht mehr. Amazon zeigt stattdessen ein ähnliches Modell ("Questar 3").
Deshalb prüft der Test nur, ob "Questar" im Warenkorb vorkommt.

Falls "In den Warenkorb legen" fehlschlägt, wird automatisch ein
Screenshot der Seite gespeichert (Datei `fehler_add_to_cart.png` im
Projektordner). So lässt sich der Fehler leicht nachvollziehen.