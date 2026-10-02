package CustomTypes;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Eine generische Warteschlange, die Elemente in Einfügereihenfolge verarbeitet. */
public class CustomQueue<E> implements Iterable<E> {
    private static final int ANFANGSKAPAZITAET = 8;

    private Object[] elemente = new Object[ANFANGSKAPAZITAET];
    private int anfang;
    private int anzahl;
    private int aenderungen;

    /** Fügt ein Element am Ende der Warteschlange hinzu. */
    public void enqueue(E element) {
        stelleKapazitaetSicher();
        int ende = (anfang + anzahl) % elemente.length;
        elemente[ende] = element;
        anzahl++;
        aenderungen++;
    }

    /** Entfernt das älteste Element und gibt es zurück. */
    public E dequeue() {
        pruefeNichtLeer();

        E erstesElement = elementAm(anfang);
        elemente[anfang] = null;
        anfang = (anfang + 1) % elemente.length;
        anzahl--;
        if (anzahl == 0) {
            anfang = 0;
        }
        aenderungen++;
        return erstesElement;
    }

    /** Gibt das älteste Element zurück, ohne es zu entfernen. */
    public E peek() {
        pruefeNichtLeer();
        return elementAm(anfang);
    }

    /** Gibt die Anzahl der gespeicherten Elemente zurück. */
    public int size() {
        return anzahl;
    }

    /** Prüft, ob die Warteschlange leer ist. */
    public boolean isEmpty() {
        return anzahl == 0;
    }

    /** Entfernt alle Elemente aus der Warteschlange. */
    public void clear() {
        if (anzahl == 0) {
            return;
        }

        Arrays.fill(elemente, null);
        anfang = 0;
        anzahl = 0;
        aenderungen++;
    }

    /** Iteriert vom ältesten bis zum zuletzt hinzugefügten Element. */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor;
            private final int erwarteteAenderungen = aenderungen;

            @Override
            public boolean hasNext() {
                pruefeAufNebenlaeufigeAenderung();
                return cursor < anzahl;
            }

            @Override
            public E next() {
                pruefeAufNebenlaeufigeAenderung();
                if (cursor >= anzahl) {
                    throw new NoSuchElementException();
                }

                int index = (anfang + cursor) % elemente.length;
                cursor++;
                return elementAm(index);
            }

            private void pruefeAufNebenlaeufigeAenderung() {
                if (erwarteteAenderungen != aenderungen) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    /** Gibt die Elemente in Entnahmereihenfolge zurück. */
    @Override
    public String toString() {
        StringBuilder darstellung = new StringBuilder("[");
        for (int index = 0; index < anzahl; index++) {
            if (index > 0) {
                darstellung.append(", ");
            }
            darstellung.append(elemente[(anfang + index) % elemente.length]);
        }
        return darstellung.append(']').toString();
    }

    private void pruefeNichtLeer() {
        if (isEmpty()) {
            throw new NoSuchElementException("Die Warteschlange ist leer.");
        }
    }

    private void stelleKapazitaetSicher() {
        if (anzahl < elemente.length) {
            return;
        }

        long neueKapazitaet = (long) elemente.length * 2;
        if (neueKapazitaet > Integer.MAX_VALUE) {
            throw new OutOfMemoryError("Die Warteschlange kann nicht weiter vergrößert werden.");
        }

        Object[] vergroesserteElemente = new Object[(int) neueKapazitaet];
        for (int index = 0; index < anzahl; index++) {
            vergroesserteElemente[index] = elemente[(anfang + index) % elemente.length];
        }
        elemente = vergroesserteElemente;
        anfang = 0;
    }

    @SuppressWarnings("unchecked")
    private E elementAm(int index) {
        return (E) elemente[index];
    }
}
