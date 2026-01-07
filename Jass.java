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
                return 13;
            case KOENIG:
                return 12;
            case OBER:
                return 11;
            case UNDER:
                return 21;
            case BANNER:
                return 10;
            case NEUN:
                return 20;
            case ACHT:
                return 8;
            case SIEBEN:
                return 7;
            default:
                return 0;
        }
    }

       public static int getTrumpValuePoints(Rank rank) {
        switch (rank) {
            case ASS:
                return 11;
            case KOENIG:
                return 4;
            case OBER:
                return 3;
            case UNDER:
                return 20;
            case BANNER:
                return 10;
            case NEUN:
                return 14;
            default:
                return 0;
        }
    }
        public static int getNonTrumpPoints(Rank rank) {
        switch (rank) {
            case ASS:
                return 11;
            case KOENIG:
                return 4;
            case OBER:
                return 3;
            case UNDER:
                return 2;
            case BANNER:
                return 10;
            default:
                return 0;
        }
    }
    
    
    public static Card selectCardToPlay(Deck playerHand, Card leadCard, Suit trumpf) {
        if (leadCard == null) {
            // Erste Karte - wähle erste verfügbare Karte
            for (Card card : playerHand.getCards()) {
                if (card != null) {
                    return card;
                }
            }
            return null;
        }
        
        // Hole gültige Karten mit der validCards Methode
        Card[] playedCards = {leadCard};
        Card[] valid = playerHand.validCards(trumpf, playedCards);
        
        if (valid.length == 0) {
            // Keine gültigen Karten - spiele beliebige Karte
            for (Card card : playerHand.getCards()) {
                if (card != null) {
                    return card;
                }
            }
            return null;
        }
        
        Suit leadSuit = leadCard.getSuit();
        
        // Filtere zuerst nach Karten mit gleicher Farbe wie leadCard
        Card bestSameSuit = null;
        for (Card card : valid) {
            if (card.getSuit() == leadSuit) {
                if (bestSameSuit == null || card.getRank().ordinal() > bestSameSuit.getRank().ordinal()) {
                    bestSameSuit = card;
                }
            }
        }
        
        if (bestSameSuit != null) {
            return bestSameSuit;
        }
        
        // Keine gleiche Farbe - wähle beste Trumpfkarte
        Card bestTrumpCard = null;
        int bestTrumpValue = -1;
        
        for (Card card : valid) {
            if (card.getSuit() == trumpf) {
                int trumpValue = getTrumpValue(card.getRank());
                if (trumpValue > bestTrumpValue) {
                    bestTrumpCard = card;
                    bestTrumpValue = trumpValue;
                }
            }
        }
        
        if (bestTrumpCard != null) {
            return bestTrumpCard;
        }
        
        // Fallback: spiele erste gültige Karte
        return valid[0];
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
                if (playedCard.getSuit() == highestCard.getSuit() && playedCard.getSuit() != trumpf) {
                    if (playedCard.getRank().ordinal() > highestCard.getRank().ordinal()) {
                        highestCard = playedCard;
                        winningPlayer = currentPlayer;
                    }
                } else if (playedCard.getSuit() == trumpf && highestCard.getSuit() != trumpf) {
                    highestCard = playedCard;
                    winningPlayer = currentPlayer;
                }
                else if (playedCard.getSuit() == trumpf && highestCard.getSuit() == trumpf) {
                    if (Jass.getTrumpValue(playedCard.getRank()) > Jass.getTrumpValue(highestCard.getRank())) {
                        highestCard = playedCard;
                        winningPlayer = currentPlayer;
                    }
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
            System.out.println("Spieler " + (startingPlayer + 1) + " hat den ROSEN BANNER und beginnt.");
        } else {
            System.out.println("Keine Spieler hat die ROSEN BANNER.");
        }
        playRound(playerDecks, trumpf, startingPlayer);
        
        
        
    }
}
