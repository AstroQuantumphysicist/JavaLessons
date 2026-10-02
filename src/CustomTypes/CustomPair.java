package CustomTypes;

import java.util.Objects;

/** Ein unveränderbares Wertepaar mit einem Schlüssel und einem Wert. */
public final class CustomPair<K, V> {
    private final K schluessel;
    private final V wert;

    /** Erzeugt ein Wertepaar. */
    public CustomPair(K schluessel, V wert) {
        this.schluessel = schluessel;
        this.wert = wert;
    }

    /** Erstellt ein Wertepaar mit dem angegebenen Schlüssel und Wert. */
    public static <K, V> CustomPair<K, V> of(K schluessel, V wert) {
        return new CustomPair<K, V>(schluessel, wert);
    }

    public K getKey() {
        return schluessel;
    }

    public V getValue() {
        return wert;
    }

    @Override
    public boolean equals(Object objekt) {
        if (this == objekt) {
            return true;
        }
        if (!(objekt instanceof CustomPair)) {
            return false;
        }

        CustomPair<?, ?> anderesPaar = (CustomPair<?, ?>) objekt;
        return Objects.equals(schluessel, anderesPaar.schluessel)
                && Objects.equals(wert, anderesPaar.wert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schluessel, wert);
    }

    @Override
    public String toString() {
        return "(" + schluessel + ", " + wert + ")";
    }
}
