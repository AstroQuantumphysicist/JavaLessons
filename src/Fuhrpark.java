import java.util.ArrayList;

public class Fuhrpark {
    private ArrayList<Fahrzeug> meine_autos;
    private int fahrzeugAnzahl;

    public Fuhrpark() {
        this.fahrzeugAnzahl = 0;
        this.meine_autos = new ArrayList<Fahrzeug>();
    }

    public void addCar(Fahrzeug car) {
        this.fahrzeugAnzahl += 1;
        meine_autos.add(car);
    }

    public void delCar(Fahrzeug car) {
        this.fahrzeugAnzahl -= 1;
        meine_autos.remove(car);
    }

    public Fahrzeug getCar(String kennzeichen) {
        for (Fahrzeug f: meine_autos) {
            if (f.getInfo("kz").equals(kennzeichen)) {
                return f;
            } else {
                IO.println("Fahrzeug nicht gefunden!");
            }
        }
        return null;
    }
}
