/**
 * Diese Klasse ist die Hauptklasse und enthält die main-Methode
 * In der main-Methode soll eine neues (vollstänidges) Deck erzeugt
 * und gemischt werden. Dann sollen (als Testcode) von diesem Deck 30 Karten entfernt
 * werden und anschliessend das EICHELN ASS hinzugefügt werden
 * Danach sollen alle Karten auf der Konsole ausgegeben werden.
 */
public class Jass {
    public static void main(String[] args){
        Deck deck = new Deck(); //erstell ein neues Deck
        deck.shuffle(); // mischelt das Deck
        System.out.println(deck.getCards().length);
        for (int i = 0; i < 30; i++){ // entfernt die letzten 3o Karten
            deck.pop(); //  
            
        }
        deck.addCard(new Card(Suit.EICHELN, Rank.ASS)); // fügt das Eicheln Ass hinzu
        for (Card card : deck.getCards()){
            System.out.println(card); //drück die verbleibenden Karten
        }
    }
}
