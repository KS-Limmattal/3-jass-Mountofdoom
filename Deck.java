import java.util.Random;
import java.util.Arrays;

/**
 * Diese Klasse repräsentiert einen Kartenstapel mit einer variablen Anzahl
 * Karten
 * Sie soll ein Array cards von Typ Card als Instanzvariable haben,
 * - einen Konstruktor Deck(Card[] cards), welches eine Instanz bestehend aus
 * den gegebenen Karten kreiert,
 * - einen Konstruktor Deck(), welcher ein vollständiges Kartenset (4x9 Karten)
 * erzeugt,
 * - einen (trivialen) Getter getCards()
 * - eine Methode addCard(Card card), welche zum Deck eine Karte hinzufügt,
 * falls diese noch nicht im Deck enthalten ist und andernfalls eine Warnung auf
 * der Konsole ausgibt
 * - eine Methode pop(), welche die letzte Karte im Array aus dem Deck entfernt,
 * sofern Karten vorhanden sind
 * - eine Methode shuffle(), welche die Karten im Array durchmischt
 * 
 * Tipps:
 * - Um ein Array zu redimensionieren, verwende den Befehl "Arrays.copyOf" aus
 * java.util.Arrays
 * - Um eine zufällige Ganzzahl in einem gegebenen Bereich zu erzeugen, verwende
 * "rnd.nextInt", wobei "rnd" eine Instanz der Klasse "Random" aus
 * "java.util.Random" bezeichnet
 *
 */
public class Deck {
    private int numberOfCards;
    private Card[] cards;
    Deck(Card[] cards){    //Konstruktor mit gegebenen Karten 
        this.cards = cards;
        this.numberOfCards = cards.length;
    }
    Deck(){
        this.numberOfCards = 36; // Konstruktor für volles Deck
        this.cards = new Card[numberOfCards];
        int index = 0;
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                this.cards[index] = new Card(suit, rank);
                index++;
            }
        }
        if (index != numberOfCards){ //wurde das vollständige Deck korrekt erzeugt?
            System.out.println("Fehler beim Erzeugen des Decks!");
        }

    }
    public Card[] getCards(){
        return this.cards;
    }
    public void addCard(Card card){ //Karte zum Deck hinzufügen
        for (Card c : this.cards){ //Überprüfen, ob Karte schon im Deck ist
            if (c != null && c.equals(card)){
                System.out.println("Warnung: Karte bereits im Deck vorhanden!");
                return;
            }
        }
        //Karte hinzufügen
        this.cards = Arrays.copyOf(this.cards, this.numberOfCards + 1);
        this.cards[this.numberOfCards] = card;
        this.numberOfCards++;
    }
    public Card pop(){ //letzte Karte entfernen
        if (this.numberOfCards == 0){
            System.out.println("Warnung: Keine Karten im Deck zum Entfernen!");
            return null;
        }
        Card removed = this.cards[this.numberOfCards - 1];
        this.cards = Arrays.copyOf(this.cards, this.numberOfCards - 1);
        this.numberOfCards--;
        return removed;
    }
    public void shuffle(){ // Karten durchmischen
        Random rnd = new Random();
        for (int i = 0; i < this.numberOfCards; i++){      
            int j = rnd.nextInt(this.numberOfCards);
            //Karten an Position i und j tauschen
            Card temp = this.cards[i];
            this.cards[i] = this.cards[j];
            this.cards[j] = temp;
        }
        }
}


    

