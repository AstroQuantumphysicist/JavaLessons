package CustomTypes;

import java.util.NoSuchElementException;

/** Eine generische Ablage, bei der stets das zuletzt abgelegte Element entnommen wird. */
public class CustomStack<E> {
    private final CustomList<E> elemente = new CustomList<E>();

    /** Legt ein Element oben auf den Stapel. */
    public void push(E element) {
        elemente.append(element);
    }

    /** Nimmt das oberste Element herunter und gibt es zurück. */
    public E pop() {
        pruefeNichtLeer();
        return elemente.pop();
    }

    /** Gibt das oberste Element zurück, ohne es zu entfernen. */
    public E peek() {
        pruefeNichtLeer();
        return elemente.get(-1);
    }

    /** Gibt die Anzahl der gespeicherten Elemente zurück. */
    public int size() {
        return elemente.size();
    }

    /** Prüft, ob der Stapel leer ist. */
    public boolean isEmpty() {
        return elemente.isEmpty();
    }

    /** Entfernt alle Elemente. */
    public void clear() {
        elemente.clear();
    }

    private void pruefeNichtLeer() {
        if (elemente.isEmpty()) {
            throw new NoSuchElementException("Der Stapel ist leer.");
        }
    }
}
