public class Fahrzeug {
    private int kilometerstand;
    private String kennzeichen;
    private boolean istElektro;
    private String farbe;
    private String marke;
    private int anzahlTueren;

    public Fahrzeug(int kms, String kz, boolean iE, String farb, String mark, int aT) {
        this.kilometerstand = kms;
        this.kennzeichen = kz;
        this.istElektro = iE;
        this.farbe = farb;
        this.marke = mark;
        this.anzahlTueren = aT;
    }

    public void incrementKilometerstand(int km) {
        this.kilometerstand += km;
        IO.println("Ein Stück weiter gefahren!");
    }

    public void neuLakieren (String neueFarbe) {
        this.farbe = neueFarbe;
        IO.println("Neu lakiert!");
    }

    public String getInfo (String infot) { // infot kann kms, kz, iE, farb, mark, aT haben
        if ("kms".equals(infot)) {
            IO.println(this.kilometerstand);
        } else if ("kz".equals(infot)) {
            IO.println(this.kennzeichen);
            return String.valueOf(this.kennzeichen);
        } else if ("iE".equals(infot)) {
            IO.println(this.istElektro);
        } else if ("farb".equals(infot)) {
            IO.println(this.farbe);
        } else if ("mark".equals(infot)) {
            IO.println(this.marke);
        } else if ("aT".equals(infot)) {
            IO.println(this.anzahlTueren);
        } else {
            IO.println("wähle zwischen kms, kz, iE, farb, mark, aT");
        }
        return infot;
    }

    private String getKennzeichen() {
        return this.kennzeichen;
    }
}
