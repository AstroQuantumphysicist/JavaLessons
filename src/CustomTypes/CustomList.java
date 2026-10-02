package CustomTypes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Eine generische, dynamisch wachsende Liste mit indiziertem Zugriff.
 * Negative Indizes zählen vom Ende der Liste; bei Einfügepositionen werden
 * Indizes außerhalb des Listenbereichs auf den gültigen Bereich begrenzt.
 *
 * @param <E> Typ der gespeicherten Elemente
 */
public class CustomList<E> implements Iterable<E> {
    private static final int ANFANGSKAPAZITAET = 8;

    private Object[] elemente = new Object[ANFANGSKAPAZITAET];
    private int anzahl;
    private int aenderungen;

    /** Erzeugt eine leere Liste. */
    public CustomList() {
    }

    /** Erzeugt eine Liste mit den Elementen der angegebenen Quelle. */
    public CustomList(Iterable<? extends E> quelle) {
        extend(quelle);
    }

    /** Fügt ein Element am Ende der Liste ein. */
    public boolean add(E element) {
        stelleKapazitaetSicher(anzahl + 1);
        elemente[anzahl++] = element;
        aenderungen++;
        return true;
    }

    /** Alias für {@link #add(Object)} mit einem sprechenden Namen für das Anhängen. */
    public void append(E element) {
        add(element);
    }

    /**
     * Fügt ein Element an der angegebenen Position ein.
     * Negative Positionen zählen vom Listenende; zu kleine oder zu große
     * Positionen werden auf den Listenanfang beziehungsweise das Listenende begrenzt.
     */
    public void insert(int index, E element) {
        int einfuegeIndex = normalisiereEinfuegeIndex(index);
        stelleKapazitaetSicher(anzahl + 1);
        System.arraycopy(
                elemente,
                einfuegeIndex,
                elemente,
                einfuegeIndex + 1,
                anzahl - einfuegeIndex);
        elemente[einfuegeIndex] = element;
        anzahl++;
        aenderungen++;
    }

    /** Fügt alle Elemente der Quelle in ihrer Reihenfolge am Listenende hinzu. */
    public void extend(Iterable<? extends E> quelle) {
        Objects.requireNonNull(quelle, "Die Quelle darf nicht null sein.");
        Iterable<? extends E> elementQuelle = quelle == this ? copy() : quelle;

        for (E element : elementQuelle) {
            add(element);
        }
    }

    /** Liefert das Element am angegebenen Index; negative Indizes zählen vom Ende. */
    public E get(int index) {
        return elementAm(normalisiereElementIndex(index));
    }

    /** Ersetzt das Element am angegebenen Index und gibt den alten Wert zurück. */
    public E set(int index, E element) {
        int elementIndex = normalisiereElementIndex(index);
        E vorherigesElement = elementAm(elementIndex);
        elemente[elementIndex] = element;
        return vorherigesElement;
    }

    /** Entfernt das erste Element, das dem angegebenen Wert entspricht. */
    public boolean remove(Object gesuchtesElement) {
        int index = indexOf(gesuchtesElement);
        if (index < 0) {
            return false;
        }

        entferneAnPosition(index);
        return true;
    }

    /** Entfernt das Element am angegebenen Index und gibt es zurück. */
    public E removeAt(int index) {
        return entferneAnPosition(normalisiereElementIndex(index));
    }

    /** Entfernt und liefert das letzte Element der Liste. */
    public E pop() {
        return removeAt(-1);
    }

    /** Entfernt und liefert das Element am angegebenen Index. */
    public E pop(int index) {
        return removeAt(index);
    }

    /** Gibt den Index des ersten passenden Elements oder -1 zurück. */
    public int indexOf(Object gesuchtesElement) {
        for (int index = 0; index < anzahl; index++) {
            if (Objects.equals(elemente[index], gesuchtesElement)) {
                return index;
            }
        }
        return -1;
    }

    /** Zählt, wie oft ein Wert in der Liste vorkommt. */
    public int count(Object gesuchtesElement) {
        int treffer = 0;
        for (int index = 0; index < anzahl; index++) {
            if (Objects.equals(elemente[index], gesuchtesElement)) {
                treffer++;
            }
        }
        return treffer;
    }

    /** Prüft, ob ein Element mit dem angegebenen Wert enthalten ist. */
    public boolean contains(Object gesuchtesElement) {
        return indexOf(gesuchtesElement) >= 0;
    }

    /** Gibt die Anzahl der gespeicherten Elemente zurück. */
    public int size() {
        return anzahl;
    }

    /** Prüft, ob die Liste keine Elemente enthält. */
    public boolean isEmpty() {
        return anzahl == 0;
    }

    /** Entfernt alle Elemente aus der Liste. */
    public void clear() {
        if (anzahl == 0) {
            return;
        }

        Arrays.fill(elemente, 0, anzahl, null);
        anzahl = 0;
        aenderungen++;
    }

    /** Kehrt die Reihenfolge der Elemente um. */
    public void reverse() {
        for (int links = 0, rechts = anzahl - 1; links < rechts; links++, rechts--) {
            Object zwischenspeicher = elemente[links];
            elemente[links] = elemente[rechts];
            elemente[rechts] = zwischenspeicher;
        }

        if (anzahl > 1) {
            aenderungen++;
        }
    }

    /** Sortiert die Liste stabil nach dem angegebenen Vergleich. */
    public void sort(Comparator<? super E> vergleich) {
        Objects.requireNonNull(vergleich, "Ein Vergleich muss angegeben sein.");
        if (anzahl < 2) {
            return;
        }

        aenderungen++;
        Arrays.sort(elemente, 0, anzahl, (links, rechts) ->
                vergleich.compare(elementAlsTyp(links), elementAlsTyp(rechts)));
    }

    /** Sortiert die Liste stabil nach der natürlichen Reihenfolge ihrer Elemente. */
    public void sort() {
        sort(this::vergleicheNatuerlich);
    }

    /**
     * Erstellt eine flache Kopie der Liste.
     * Die Reihenfolge bleibt erhalten; die Elemente werden nicht kopiert.
     */
    public CustomList<E> copy() {
        CustomList<E> kopie = new CustomList<E>();
        kopie.stelleKapazitaetSicher(anzahl);
        System.arraycopy(elemente, 0, kopie.elemente, 0, anzahl);
        kopie.anzahl = anzahl;
        return kopie;
    }

    /**
     * Liefert eine neue Liste aus dem Bereich von start (einschließlich) bis
     * ende (ausschließlich). Negative Grenzen zählen vom Ende und Grenzen
     * außerhalb der Liste werden auf den gültigen Bereich begrenzt.
     */
    public CustomList<E> slice(int start, int ende) {
        int startIndex = normalisiereGrenze(start);
        int endeIndex = normalisiereGrenze(ende);
        CustomList<E> ausschnitt = new CustomList<E>();

        for (int index = startIndex; index < endeIndex; index++) {
            ausschnitt.add(elementAm(index));
        }

        return ausschnitt;
    }

    /** Gibt eine neue Array-Kopie mit den Listenelementen zurück. */
    public Object[] toArray() {
        return Arrays.copyOf(elemente, anzahl);
    }

    /** Iteriert in Listenreihenfolge über die Elemente. */
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
                return elementAm(cursor++);
            }

            private void pruefeAufNebenlaeufigeAenderung() {
                if (erwarteteAenderungen != aenderungen) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    /** Gibt die Elemente in der üblichen Listenform zurück. */
    @Override
    public String toString() {
        StringBuilder darstellung = new StringBuilder("[");
        for (int index = 0; index < anzahl; index++) {
            if (index > 0) {
                darstellung.append(", ");
            }
            if (elemente[index] == this) {
                darstellung.append("(diese Liste)");
            } else {
                darstellung.append(elemente[index]);
            }
        }
        return darstellung.append(']').toString();
    }

    private E entferneAnPosition(int index) {
        E entferntesElement = elementAm(index);
        int verschiebbareElemente = anzahl - index - 1;
        if (verschiebbareElemente > 0) {
            System.arraycopy(elemente, index + 1, elemente, index, verschiebbareElemente);
        }

        elemente[--anzahl] = null;
        aenderungen++;
        return entferntesElement;
    }

    private void stelleKapazitaetSicher(int benoetigteKapazitaet) {
        if (benoetigteKapazitaet <= elemente.length) {
            return;
        }

        long neueKapazitaet = Math.max((long) elemente.length * 2, benoetigteKapazitaet);
        if (neueKapazitaet > Integer.MAX_VALUE) {
            throw new OutOfMemoryError("Die Liste kann nicht weiter vergrößert werden.");
        }
        elemente = Arrays.copyOf(elemente, (int) neueKapazitaet);
    }

    private int normalisiereElementIndex(int index) {
        long normalisierterIndex = index < 0 ? (long) anzahl + index : index;
        if (normalisierterIndex < 0 || normalisierterIndex >= anzahl) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Listenlänge: " + anzahl);
        }
        return (int) normalisierterIndex;
    }

    private int normalisiereEinfuegeIndex(int index) {
        long normalisierterIndex = index < 0 ? (long) anzahl + index : index;
        if (normalisierterIndex < 0) {
            return 0;
        }
        if (normalisierterIndex > anzahl) {
            return anzahl;
        }
        return (int) normalisierterIndex;
    }

    private int normalisiereGrenze(int index) {
        long normalisierterIndex = index < 0 ? (long) anzahl + index : index;
        if (normalisierterIndex < 0) {
            return 0;
        }
        if (normalisierterIndex > anzahl) {
            return anzahl;
        }
        return (int) normalisierterIndex;
    }

    @SuppressWarnings("unchecked")
    private E elementAm(int index) {
        return (E) elemente[index];
    }

    @SuppressWarnings("unchecked")
    private E elementAlsTyp(Object element) {
        return (E) element;
    }

    @SuppressWarnings("unchecked")
    private int vergleicheNatuerlich(E links, E rechts) {
        Comparable<? super E> vergleichbarerWert = (Comparable<? super E>) links;
        return vergleichbarerWert.compareTo(rechts);
    }
}
