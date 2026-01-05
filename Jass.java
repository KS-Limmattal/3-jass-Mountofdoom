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
        switch (rank) {
            case ASS:
                return 11;
            case KOENIG:
                return 4;
            case OBER:
                return 3;
            case UNDER:
                return 21;
            case BANNER:
                return 10;
            case NEUN:
                return 20;
            case ACHT:
                return 8;
            case SIEBEN:
                return 0;
            default:
                return 0;
        }
    }
   
    
    
    public static Card selectCardToPlay(Deck playerHand, Card leadCard, Suit trumpf) {
        if (leadCard == null) {
            // Erste Karte - wähle zufällige Karte
            for (Card card : playerHand.getCards()) {
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
        for (Card card : playerHand.getCards()) {
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
        
        for (Card card : playerHand.getCards()) {
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
        for (Card card : playerHand.getCards()) {
            if (card != null) {
                return card;
            }
        }
        return null; // Keine Karten mehr
    }
    public static int getPlayerWithRosenBanner(Deck[] playerDecks) {
        Card rosenBanner = new Card(Suit.ROSEN, Rank.BANNER);
        for (int player = 0; player < playerDecks.length; player++) {
            for (Card card : playerDecks[player].getCards()) {
                if (card != null && card.equals(rosenBanner)) {
                    return player;
                }
            }
        }
        return -1; // Karte nicht gefunden
    }
    public static void playRound(Deck[] playerDecks, Suit trumpf, int startingPlayer) {
        Deck startingDeck = playerDecks[startingPlayer];
        Card leadCard = startingDeck.pop();
        System.out.println("Spieler " + (startingPlayer + 1) + " spielt: " + leadCard);
        Card highestCard = leadCard;
        int winningPlayer = startingPlayer;
        for (int i = 1; i < playerDecks.length; i++) {
            int currentPlayer = (startingPlayer + i) % playerDecks.length;
            Deck currentDeck = playerDecks[currentPlayer];
            Card playedCard = selectCardToPlay(currentDeck, leadCard, trumpf);
            if (playedCard != null) {
                currentDeck.pop(); // Karte aus dem Deck entfernen
                System.out.println("Spieler " + (currentPlayer + 1) + " spielt: " + playedCard);
                // Bestimme die höchste Karte
                if (playedCard.getSuit() == highestCard.getSuit()) {
                    if (playedCard.getRank().ordinal() > highestCard.getRank().ordinal()) {
                        highestCard = playedCard;
                        winningPlayer = currentPlayer;
                    }
                } else if (playedCard.getSuit() == trumpf && highestCard.getSuit() != trumpf) {
                    highestCard = playedCard;
                    winningPlayer = currentPlayer;
                }
            }
        }
        System.out.println("Spieler " + (winningPlayer + 1) + " gewinnt die Runde mit der Karte: " + highestCard);
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
        Deck deckPlayer1 = new Deck(cardsPlayer1);
        Deck deckPlayer2 = new Deck(cardsPlayer2);
        Deck deckPlayer3 = new Deck(cardsPlayer3);
        Deck deckPlayer4 = new Deck(cardsPlayer4);

        Deck[] playerDecks = {deckPlayer1, deckPlayer2, deckPlayer3, deckPlayer4};
        //System.out.println("Karten für Spieler 1:");
        //for (Card card : cardsPlayer1) {
        //    System.out.println(card);
        //}
        int pick = new Random().nextInt(Suit.values().length);
        Suit trumpf = Suit.values()[pick];
        System.out.println("Der Trumpf ist: " + trumpf);
        
        int startingPlayer = getPlayerWithRosenBanner(playerDecks);
        if (startingPlayer != -1) {
            System.out.println("Spieler " + (startingPlayer + 1) + " hat die ROSEN BANNER und beginnt.");
        } else {
            System.out.println("Keine Spieler hat die ROSEN BANNER.");
        }
        
        
        
    }
}
