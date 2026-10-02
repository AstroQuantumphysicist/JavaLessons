import java.util.Objects;

/** Repräsentiert ein Fahrzeug und seine veränderlichen Betriebsdaten. */
public class Fahrzeug {
    private static final int ERSTES_AUTOMOBIL_BAUJAHR = 1886;

    private final String kennzeichen;
    private final boolean elektro;
    private final String marke;
    private final int anzahlTueren;
    private final int baujahr;

    private String farbe;
    private int kilometerstand;

    /**
     * Erzeugt ein Fahrzeug.
     *
     * @param kilometerstand aktueller Kilometerstand, mindestens 0
     * @param kennzeichen amtliches Kennzeichen
     * @param elektro true, wenn das Fahrzeug elektrisch angetrieben wird
     * @param farbe aktuelle Fahrzeugfarbe
     * @param marke Fahrzeugmarke
     * @param anzahlTueren Anzahl der Türen, mindestens 0
     * @param baujahr Baujahr, mindestens 1886
     */
    public Fahrzeug(
            int kilometerstand,
            String kennzeichen,
            boolean elektro,
            String farbe,
            String marke,
            int anzahlTueren,
            int baujahr) {
        if (kilometerstand < 0) {
            throw new IllegalArgumentException("Der Kilometerstand darf nicht negativ sein.");
        }
        if (anzahlTueren < 0) {
            throw new IllegalArgumentException("Die Anzahl der Türen darf nicht negativ sein.");
        }
        if (baujahr < ERSTES_AUTOMOBIL_BAUJAHR) {
            throw new IllegalArgumentException(
                    "Das Baujahr muss mindestens " + ERSTES_AUTOMOBIL_BAUJAHR + " sein.");
        }

        this.kilometerstand = kilometerstand;
        this.kennzeichen = pruefeText(kennzeichen, "Kennzeichen");
        this.elektro = elektro;
        this.farbe = pruefeText(farbe, "Farbe");
        this.marke = pruefeText(marke, "Marke");
        this.anzahlTueren = anzahlTueren;
        this.baujahr = baujahr;
    }

    /** Erhöht den Kilometerstand um die gefahrene Strecke. */
    public void fahreKilometer(int gefahreneKilometer) {
        if (gefahreneKilometer < 0) {
            throw new IllegalArgumentException("Die gefahrene Strecke darf nicht negativ sein.");
        }

        kilometerstand = Math.addExact(kilometerstand, gefahreneKilometer);
    }

    /** Ändert die aktuelle Fahrzeugfarbe. */
    public void lackiereNeu(String neueFarbe) {
        farbe = pruefeText(neueFarbe, "Farbe");
    }

    public int getKilometerstand() {
        return kilometerstand;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public boolean isElektro() {
        return elektro;
    }

    public String getFarbe() {
        return farbe;
    }

    public String getMarke() {
        return marke;
    }

    public int getAnzahlTueren() {
        return anzahlTueren;
    }

    public int getBaujahr() {
        return baujahr;
    }

    /** Gibt alle Fahrzeugdaten kompakt und lesbar zurück. */
    @Override
    public String toString() {
        return String.format(
                "%s | %s | Baujahr %d | %d km | %s | %s | %d Türen",
                kennzeichen,
                marke,
                baujahr,
                kilometerstand,
                elektro ? "Elektro" : "Verbrenner",
                farbe,
                anzahlTueren);
    }

    private static String pruefeText(String wert, String feldname) {
        Objects.requireNonNull(wert, feldname + " darf nicht null sein.");
        if (wert.trim().isEmpty()) {
            throw new IllegalArgumentException(feldname + " darf nicht leer sein.");
        }
        return wert.trim();
    }
}
