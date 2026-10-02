import java.util.Comparator;
import java.util.Objects;

import CustomTypes.CustomList;

/** Sortiert Fahrzeuglisten stabil nach einem frei wählbaren Fahrzeugmerkmal. */
public final class FahrzeugSortierer {
    /** Fahrzeugmerkmale, die als Sortierschlüssel verwendet werden können. */
    public enum Sortierkriterium {
        BAUJAHR,
        KILOMETERSTAND,
        KENNZEICHEN,
        ELEKTRO,
        FARBE,
        MARKE,
        ANZAHL_TUEREN
    }

    private FahrzeugSortierer() {
        // Die Klasse stellt ausschließlich statische Sortiermethoden bereit.
    }

    /**
     * Erstellt eine aufsteigend sortierte Kopie der übergebenen Fahrzeugliste.
     * Die Eingabeliste wird nicht verändert. Zeichenketten werden ohne
     * Beachtung der Groß- und Kleinschreibung verglichen; false steht vor true.
     *
     * @param fahrzeuge zu sortierende Fahrzeuge
     * @param kriterium Merkmal, nach dem sortiert wird
     * @return neue, sortierte Liste
     */
    public static CustomList<Fahrzeug> sortiereNach(
            CustomList<Fahrzeug> fahrzeuge,
            Sortierkriterium kriterium) {
        Objects.requireNonNull(fahrzeuge, "Die Fahrzeugliste darf nicht null sein.");
        for (Fahrzeug fahrzeug : fahrzeuge) {
            Objects.requireNonNull(
                    fahrzeug,
                    "Die Fahrzeugliste darf keine null-Elemente enthalten.");
        }

        Comparator<Fahrzeug> vergleich = erstelleVergleich(kriterium);
        CustomList<Fahrzeug> sortierteFahrzeuge = fahrzeuge.copy();

        // Einfügesortierung: Jedes Fahrzeug wird an seiner richtigen Stelle eingefügt.
        for (int index = 1; index < sortierteFahrzeuge.size(); index++) {
            Fahrzeug schluessel = sortierteFahrzeuge.get(index);
            int vergleichsIndex = index - 1;

            while (vergleichsIndex >= 0
                    && vergleich.compare(
                            sortierteFahrzeuge.get(vergleichsIndex),
                            schluessel) > 0) {
                sortierteFahrzeuge.set(
                        vergleichsIndex + 1,
                        sortierteFahrzeuge.get(vergleichsIndex));
                vergleichsIndex--;
            }

            sortierteFahrzeuge.set(vergleichsIndex + 1, schluessel);
        }

        return sortierteFahrzeuge;
    }

    private static Comparator<Fahrzeug> erstelleVergleich(Sortierkriterium kriterium) {
        Objects.requireNonNull(kriterium, "Ein Sortierkriterium muss ausgewählt sein.");

        switch (kriterium) {
            case BAUJAHR:
                return Comparator.comparingInt(Fahrzeug::getBaujahr);
            case KILOMETERSTAND:
                return Comparator.comparingInt(Fahrzeug::getKilometerstand);
            case KENNZEICHEN:
                return Comparator.comparing(
                        Fahrzeug::getKennzeichen,
                        String.CASE_INSENSITIVE_ORDER);
            case ELEKTRO:
                return Comparator.comparing(Fahrzeug::isElektro);
            case FARBE:
                return Comparator.comparing(Fahrzeug::getFarbe, String.CASE_INSENSITIVE_ORDER);
            case MARKE:
                return Comparator.comparing(Fahrzeug::getMarke, String.CASE_INSENSITIVE_ORDER);
            case ANZAHL_TUEREN:
                return Comparator.comparingInt(Fahrzeug::getAnzahlTueren);
            default:
                throw new IllegalArgumentException("Unbekanntes Sortierkriterium: " + kriterium);
        }
    }
}
