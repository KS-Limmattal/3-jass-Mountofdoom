import java.util.Random;

/**
 * Diese Klasse ist die Hauptklasse und enthält die main-Methode
 * In der main-Methode soll eine neues (vollstänidges) Deck erzeugt
 * und gemischt werden. Dann sollen (als Testcode) von diesem Deck 30 Karten entfernt
 * werden und anschliessend das EICHELN ASS hinzugefügt werden
 * Danach sollen alle Karten auf der Konsole ausgegeben werden.
 */
public class Jass {
    
    // gibt den wert einer Trumpfkarte zurück 
    public static int getTrumpValue(Rank rank) {
        if (rank == Rank.UNDER) {
            return 21;
        } else if (rank == Rank.NEUN) {
            return 20;
        } else {
            return rank.ordinal();
        }
    }
   
    public static boolean isCardPlayable(Card card, Card leadCard, Suit trumpf) {
        // Erste Karte der Runde - immer spielbar
        if (leadCard == null) {
            return true;
        }
        Suit leadSuit = leadCard.getSuit();
        Suit cardSuit = card.getSuit();
        // Gleiche Farbe wie die erste Karte - immer spielbar
        if (cardSuit == leadSuit) {
            return true;
        }
        // Trumpfkarte - immer spielbar
        if (cardSuit == trumpf) {
            return true;
        }
        // Andere Farbe - nur spielbar wenn man die geforderte Farbe nicht hat
        return false;
    }
    
    
    public static Card selectCardToPlay(Card[] playerHand, Card leadCard, Suit trumpf) {
        if (leadCard == null) {
            // Erste Karte - wähle zufällige Karte
            for (Card card : playerHand) {
                if (card != null) {
                    return card;
                }
            }
            return null;
        }
        Suit leadSuit = leadCard.getSuit();
        Rank leadRank = leadCard.getRank();
        // Versuche gleiche Farbe mit höherem Rang zu spielen
        Card bestSameSuit = null;
        for (Card card : playerHand) {
            if (card != null && card.getSuit() == leadSuit) {
                if (bestSameSuit == null || card.getRank().ordinal() > leadRank.ordinal()) {
                    bestSameSuit = card;
                }
            }
        }
        if (bestSameSuit != null) {
            return bestSameSuit;
        } 
        // Keine gleiche Farbe - versuche höchste Trumpfkarte zu spielen
        Card bestTrumpCard = null;
        int bestTrumpValue = -1;
        
        // Prüfe ob leadCard eine Trumpfkarte ist
        int leadTrumpValue = -1;
        if (leadCard.getSuit() == trumpf) {
            leadTrumpValue = getTrumpValue(leadCard.getRank());
        }
        
        for (Card card : playerHand) {
            if (card != null && card.getSuit() == trumpf) {
                int cardTrumpValue = getTrumpValue(card.getRank());
                // Versuche höhere Trumpfkarte als leadCard zu spielen
                if (cardTrumpValue > leadTrumpValue && cardTrumpValue > bestTrumpValue) {
                    bestTrumpCard = card;
                    bestTrumpValue = cardTrumpValue;
                } else if (bestTrumpCard == null) {
                    // Falls keine höhere gefunden, nimm irgendeine Trumpfkarte
                    bestTrumpCard = card;
                    bestTrumpValue = cardTrumpValue;
                }
            }
        }
        if (bestTrumpCard != null) {
            return bestTrumpCard;
        }
        // Weder gleiche Farbe noch Trumpf - spiele zufällige Karte
        for (Card card : playerHand) {
            if (card != null) {
                return card;
            }
        }
        return null; // Keine Karten mehr
    }
    public static int getPlayerWithRosenBanner(Card[][] playerHands) {
        Card rosenBanner = new Card(Suit.ROSEN, Rank.BANNER);
        for (int player = 0; player < playerHands.length; player++) {
            for (Card card : playerHands[player]) {
                if (card != null && card.equals(rosenBanner)) {
                    return player;
                }
            }
        }
        return -1; // Karte nicht gefunden
    }
    public static void main(String[] args){
        /* 
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
            */
        Deck deck = new Deck(); // erstellt das Spieldeck
        for (int i = 0; i<5; i++){
            deck.shuffle();// mischelt das Deck mehrmals
        }
        
        Card[] cardsPlayer1 = new Card[9]; //initialisere die Kartenvariabelen für die Spieler
        Card[] cardsPlayer2 = new Card[9];
        Card[] cardsPlayer3 = new Card[9];
        Card[] cardsPlayer4 = new Card[9];
        for (int i = 0; i < 9; i++){
            cardsPlayer1[i] = deck.pop();
            cardsPlayer2[i] = deck.pop();
            cardsPlayer3[i] = deck.pop();
            cardsPlayer4[i] = deck.pop();
        }
        Card[][] playerHands = {cardsPlayer1, cardsPlayer2, cardsPlayer3, cardsPlayer4};
        //System.out.println("Karten für Spieler 1:");
        //for (Card card : cardsPlayer1) {
        //    System.out.println(card);
        //}
        int pick = new Random().nextInt(Suit.values().length);
        Suit trumpf = Suit.values()[pick];
        System.out.println("Der Trumpf ist: " + trumpf);
        
        int startingPlayer = getPlayerWithRosenBanner(playerHands);
        if (startingPlayer != -1) {
            System.out.println("Spieler " + (startingPlayer + 1) + " hat die ROSEN BANNER und beginnt.");
        } else {
            System.out.println("Keine Spieler hat die ROSEN BANNER.");
        }
        
        
        
    }
}
