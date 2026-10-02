# Fuhrparkverwaltung

## Aufgabe und Verhalten

Das Programm verwaltet Fahrzeuge mit Kennzeichen, Elektroantrieb, Farbe, Marke,
Türenzahl, Kilometerstand und Baujahr. `Main` legt acht Beispielautos an, gibt
den Fuhrpark aus und zeigt anschließend eine aufsteigende Sortierung nach
Baujahr. Die Sortierung verändert die Reihenfolge im Fuhrpark nicht.

## Klassen

- `Fahrzeug` speichert die Daten eines einzelnen Autos. Kilometerstand und Farbe
  können über Methoden geändert werden. Ungültige Zahlenwerte sowie leere
  Pflichttexte werden abgewiesen.
- `Fuhrpark` fügt Fahrzeuge hinzu, entfernt sie, sucht nach einem Kennzeichen
  und liefert die aktuelle Anzahl. Eine Suche liefert `Optional.empty()`, wenn
  kein Fahrzeug passt. `getFahrzeuge()` liefert eine separate Liste; das
  Hinzufügen oder Entfernen in dieser Kopie verändert den Fuhrpark nicht.
- `CustomTypes.CustomList<E>` ist die generische Listenimplementierung. Sie
  verwaltet ihre Elemente in einem dynamisch vergrößerbaren Speicher und kann
  mit einer erweiterten `for`-Schleife durchlaufen werden.
- `CustomTypes.CustomStack<E>` verwaltet Elemente mit Zugriff auf das oberste
  Element.
- `CustomTypes.CustomQueue<E>` verarbeitet Elemente in ihrer Einfügereihenfolge.
- `CustomTypes.CustomPair<K, V>` speichert ein unveränderbares Schlüssel-Wert-Paar.
- `FahrzeugSortierer` sortiert eine Kopie einer Liste mithilfe des
  Einfügesortierverfahrens.
- `Main` enthält die Beispieldaten und die Ausgaberoutine.

## Operationen der Liste

`CustomList<E>` bietet die üblichen Listenoperationen: `add` und `append`
fügen am Ende ein, `insert` fügt an einer Position ein, `extend` hängt die
Elemente einer weiteren Quelle an. `get` und `set` lesen beziehungsweise ändern
Elemente; `remove` entfernt den ersten passenden Wert, während `removeAt` und
`pop` ein Element über seinen Index entfernen und zurückgeben. Außerdem gibt es
`contains`, `indexOf`, `count`, `size`, `isEmpty`, `clear`, `reverse`, `sort`,
`copy`, `slice` und `toArray`. Der Konstruktor kann zusätzlich eine beliebige
`Iterable` als Anfangsinhalt übernehmen. `sort` verwendet entweder die
natürliche Elementreihenfolge oder einen übergebenen `Comparator`.

Bei `get`, `set`, `removeAt` und `pop` zählen negative Indizes vom Listenende;
`-1` bezeichnet das letzte Element. Einfügepositionen und Grenzen für `slice`
werden auf den gültigen Bereich begrenzt. Der Endindex von `slice` ist ausgeschlossen.
Die Liste erlaubt auch `null` als Element; für eine Sortierung nach natürlicher
Reihenfolge müssen die Elemente jedoch vergleichbar und ungleich `null` sein.
Iterationen erkennen strukturelle Änderungen, die während des Durchlaufens
vorgenommen werden.

## Weitere Datentypen

`CustomStack<E>` stellt `push`, `pop` und `peek` für den Zugriff auf das oberste
Element sowie `size`, `isEmpty` und `clear` bereit. `CustomQueue<E>` bietet
`enqueue`, `dequeue` und `peek`; ihre Iteration läuft vom ältesten bis zum
neuesten Element. Bei einem Zugriff auf einen leeren Stapel oder eine leere
Warteschlange wird `NoSuchElementException` ausgelöst.

`CustomPair<K, V>` speichert zwei Werte. Die Werte lassen sich über `getKey()`
und `getValue()` abrufen. Gleichheit und Hashcode berücksichtigen beide Werte,
sodass Paare direkt verglichen oder in hashbasierten Sammlungen verwendet werden
können.

```java
import CustomTypes.CustomPair;
import CustomTypes.CustomQueue;
import CustomTypes.CustomStack;

CustomStack<String> stapel = new CustomStack<String>();
stapel.push("oben");
String oberstesElement = stapel.pop();

CustomQueue<String> warteschlange = new CustomQueue<String>();
warteschlange.enqueue("zuerst");
String naechstesElement = warteschlange.dequeue();

CustomPair<String, Integer> paar = CustomPair.of("Fahrzeuge", 8);
int anzahlFahrzeuge = paar.getValue();
```

## Sortierkriterien

Die Sortierung ist aufsteigend. Als Sortierschlüssel kann jedes gespeicherte
Fahrzeugmerkmal gewählt werden:

| Kriterium | Bedeutung |
| --- | --- |
| `BAUJAHR` | Baujahr |
| `KILOMETERSTAND` | Kilometerstand |
| `KENNZEICHEN` | Kennzeichen |
| `ELEKTRO` | Antriebsart (`false` vor `true`) |
| `FARBE` | Farbe |
| `MARKE` | Marke |
| `ANZAHL_TUEREN` | Türenzahl |

Zeichenketten werden ohne Beachtung von Groß- und Kleinschreibung verglichen.
Bei gleichen Werten bleibt die Reihenfolge der Eingabeliste erhalten. Die
Sortiermethode prüft ihre Argumente und gibt eine neue Liste zurück.

Für eine Sortierung nach Marke wird das Kriterium beim Aufruf ausgetauscht:

```java
import CustomTypes.CustomList;

CustomList<Fahrzeug> nachMarke = FahrzeugSortierer.sortiereNach(
        fuhrpark.getFahrzeuge(),
        FahrzeugSortierer.Sortierkriterium.MARKE);
```

## Struktogramm für den Programmablauf

```text
┌──────────────────────────────────────────────────────────────┐
│ Fuhrpark erstellen                                           │
├──────────────────────────────────────────────────────────────┤
│ Acht Beispiel-Fahrzeuge anlegen                              │
├──────────────────────────────────────────────────────────────┤
│ Für jedes Beispiel-Fahrzeug                                  │
│ ┌──────────────────────────────────────────────────────────┐ │
│ │ Fahrzeug zum Fuhrpark hinzufügen                         │ │
│ └──────────────────────────────────────────────────────────┘ │
├──────────────────────────────────────────────────────────────┤
│ Unsortierte Liste ausgeben                                   │
│ ┌──────────────────────────────────────────────────────────┐ │
│ │ Für jedes Fahrzeug: Fahrzeugdaten ausgeben               │ │
│ └──────────────────────────────────────────────────────────┘ │
├──────────────────────────────────────────────────────────────┤
│ Kopie nach BAUJAHR sortieren                                 │
├──────────────────────────────────────────────────────────────┤
│ Sortierte Liste ausgeben                                     │
│ ┌──────────────────────────────────────────────────────────┐ │
│ │ Für jedes Fahrzeug: Fahrzeugdaten ausgeben               │ │
│ └──────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────┘
```

## Struktogramm für die Einfügesortierung

Der Sortierer hält die bisher bearbeiteten Fahrzeuge sortiert. Das nächste
Fahrzeug wird vor größere Werte geschoben und dann in die entstandene Lücke
eingefügt.

```text
┌──────────────────────────────────────────────────────────────┐
│ Für index = 1 bis Listenlänge - 1                            │
│ ┌──────────────────────────────────────────────────────────┐ │
│ │ schluessel = Fahrzeug an Position index                  │ │
│ │ vergleichsIndex = index - 1                              │ │
│ ├──────────────────────────────────────────────────────────┤ │
│ │ Solange vergleichsIndex >= 0 und                          │ │
│ │ Vergleich(Fahrzeug[vergleichsIndex], schluessel) > 0      │ │
│ │ ┌──────────────────────────────────────────────────────┐ │ │
│ │ │ Fahrzeug an Position vergleichsIndex + 1              │ │ │
│ │ │ durch Fahrzeug an Position vergleichsIndex ersetzen   │ │ │
│ │ │ vergleichsIndex um 1 verringern                       │ │ │
│ │ └──────────────────────────────────────────────────────┘ │ │
│ ├──────────────────────────────────────────────────────────┤ │
│ │ Fahrzeug[vergleichsIndex + 1] = schluessel               │ │
│ └──────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────┘
```

Der Vergleich verwendet das ausgewählte Sortierkriterium. Weil gleiche Werte
nicht aneinander vorbeigeschoben werden, ist die Sortierung stabil. Bei `n`
Fahrzeugen beträgt der Aufwand im ungünstigsten Fall O(n²); für die sortierte
Kopie wird O(n) zusätzlicher Speicherplatz benötigt.
