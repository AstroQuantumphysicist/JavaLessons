import java.util.Objects;
import java.util.Optional;

import CustomTypes.CustomList;

/** Verwaltet Fahrzeuge und bietet grundlegende Fuhrparkoperationen an. */
public class Fuhrpark {
    private final CustomList<Fahrzeug> fahrzeuge = new CustomList<Fahrzeug>();

    /** Fügt ein Fahrzeug am Ende des Fuhrparks hinzu. */
    public void fuegeFahrzeugHinzu(Fahrzeug fahrzeug) {
        fahrzeuge.append(
                Objects.requireNonNull(fahrzeug, "Das Fahrzeug darf nicht null sein."));
    }

    /** Entfernt das übergebene Fahrzeug und meldet, ob es im Fuhrpark enthalten war. */
    public boolean entferneFahrzeug(Fahrzeug fahrzeug) {
        Fahrzeug zuEntfernendesFahrzeug = Objects.requireNonNull(
                fahrzeug,
                "Das Fahrzeug darf nicht null sein.");
        return fahrzeuge.remove(zuEntfernendesFahrzeug);
    }

    /** Sucht ohne Beachtung der Groß- und Kleinschreibung nach einem Kennzeichen. */
    public Optional<Fahrzeug> sucheNachKennzeichen(String kennzeichen) {
        String gesuchtesKennzeichen = Objects.requireNonNull(
                kennzeichen,
                "Das Kennzeichen darf nicht null sein.").trim();

        for (Fahrzeug fahrzeug : fahrzeuge) {
            if (fahrzeug.getKennzeichen().equalsIgnoreCase(gesuchtesKennzeichen)) {
                return Optional.of(fahrzeug);
            }
        }

        return Optional.empty();
    }

    /** Gibt die aktuelle Anzahl der Fahrzeuge zurück. */
    public int getAnzahlFahrzeuge() {
        return fahrzeuge.size();
    }

    /**
     * Gibt eine unabhängige Liste zurück; die enthaltenen Fahrzeuge bleiben dieselben Objekte.
     */
    public CustomList<Fahrzeug> getFahrzeuge() {
        return fahrzeuge.copy();
    }
}
