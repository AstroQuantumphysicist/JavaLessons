import CustomTypes.CustomList;

/** Startet das Beispiel und zeigt den Fuhrpark vor und nach der Sortierung. */
public class Main {
    public static void main(String[] args) {
        Fuhrpark fuhrpark = erstelleBeispielfuhrpark();
        CustomList<Fahrzeug> fahrzeuge = fuhrpark.getFahrzeuge();

        druckeFahrzeuge(
                "Fuhrpark mit " + fuhrpark.getAnzahlFahrzeuge() + " Fahrzeugen:",
                fahrzeuge);

        CustomList<Fahrzeug> nachBaujahr = FahrzeugSortierer.sortiereNach(
                fahrzeuge,
                FahrzeugSortierer.Sortierkriterium.BAUJAHR);

        druckeFahrzeuge("Fuhrpark aufsteigend nach Baujahr sortiert:", nachBaujahr);
    }

    private static Fuhrpark erstelleBeispielfuhrpark() {
        Fuhrpark fuhrpark = new Fuhrpark();
        Fahrzeug[] beispielFahrzeuge = {
            new Fahrzeug(42000, "B-AB 1840", false, "Blau", "Volkswagen", 5, 2018),
            new Fahrzeug(18500, "M-CD 2021", true, "Weiß", "Tesla", 5, 2021),
            new Fahrzeug(76000, "HH-EF 2015", false, "Schwarz", "BMW", 4, 2015),
            new Fahrzeug(12000, "K-GH 2023", true, "Rot", "Hyundai", 5, 2023),
            new Fahrzeug(93500, "F-JK 2012", false, "Silber", "Mercedes-Benz", 5, 2012),
            new Fahrzeug(34000, "D-LM 2019", false, "Grün", "Skoda", 5, 2019),
            new Fahrzeug(27000, "S-NP 2022", true, "Grau", "BYD", 5, 2022),
            new Fahrzeug(108000, "N-QR 2010", false, "Rot", "Opel", 3, 2010)
        };

        for (Fahrzeug fahrzeug : beispielFahrzeuge) {
            fuhrpark.fuegeFahrzeugHinzu(fahrzeug);
        }

        return fuhrpark;
    }

    private static void druckeFahrzeuge(
            String ueberschrift,
            CustomList<Fahrzeug> fahrzeuge) {
        System.out.println(ueberschrift);
        for (Fahrzeug fahrzeug : fahrzeuge) {
            System.out.println(fahrzeug);
        }
        System.out.println();
    }
}
