
package system.IntegrationTesting;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import datasource.CardType;
import system.DrawDeck;
import system.User;
import system.Card;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DrawDeckIntegrationTesting {
    @Test

    public void testGetCardsIntegrationTest() {
        ArrayList<Card> cards = new ArrayList<>();

        DrawDeck deck = new DrawDeck(cards);
        assertTrue(deck.getCards().isEmpty());
    }

    @Test

    public void testDrawCardFromEmptyDrawDeckIntegrationTest() {

        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        Executable executable = () -> deck.drawCard(new User());
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test

    public void testDrawCardFromNonEmptyDrawDeckIntegrationTest() {
        User user = new User();
        ArrayList<Card> cards = new ArrayList<>();


        DrawDeck deck = new DrawDeck(cards);

        deck.addCard(new Card(CardType.ATTACK));
        deck.drawCard(user);

        assertTrue(deck.getCards().isEmpty());
        assertTrue(!user.getHand().isEmpty());
    }

    @Test

    public void testDrawInitialCardFromEmptyDrawDeckIntegrationTest() {
        User player = new User("TestPlayer");
        ArrayList<Card> cards = new ArrayList<>();


        DrawDeck deck = new DrawDeck(cards);
        Executable executable = () -> deck.drawInitialCard(player);

        Assertions.assertThrows(RuntimeException.class, executable);

    }

    @Test

    public void testDrawInitialCardFromNonEmptyDrawDeckIntegrationTest() {
        User player = new User("TestPlayer");
        Card drawnCard = new Card(CardType.ATTACK);
        Card explodeCard = new Card(CardType.EXPLODING_KITTEN);
        player.addCard(drawnCard);

        ArrayList<Card> cards = new ArrayList<>();


        DrawDeck deck = new DrawDeck(cards);
        deck.addCard(explodeCard);
        deck.addCard(drawnCard);

        deck.drawInitialCard(player);
        Assertions.assertEquals(1, deck.getDeckSize());

    }

    @Test

    public void testShuffleOnEmptyDeckIntegrationTest() {

        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        deck.shuffle();
        Assertions.assertEquals(0, deck.getDeckSize());
    }

    @Test

    public void testShuffleOnDeckOfOneCardIntegrationTest() {

        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);

        Card card = new Card(CardType.ATTACK);
        deck.addCard(card);
        deck.shuffle();

        Assertions.assertEquals(1, deck.getDeckSize());
        Assertions.assertEquals(card, deck.getCards().get(0));
    }

    @Test

    public void testShuffleOnDeckOfMultipleCardsIntegrationTest() {

        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);

        Card card1 = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ATTACK);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.shuffle();

        Assertions.assertEquals(2, deck.getDeckSize());
        Assertions.assertTrue(deck.getCards().contains(card1));
        Assertions.assertTrue(deck.getCards().contains(card2));
    }

    @Test

    public void testDrawFromBottomForUserWithEmptyDeckIntegrationTest() {

        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        User user = new User();

        Executable executable = () -> deck.drawFromBottomForUser(user);

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test

    public void testDrawFromBottomForUserWithNonEmptyDeckIntegrationTest() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        deck.addCard(new Card(CardType.ATTACK));
        Card bottomCard = new Card(CardType.ALTER_THE_FUTURE);
        deck.addCard(bottomCard);


        User user = new User("TestPlayer");
        user.addCard(bottomCard);


        deck.drawFromBottomForUser(user);

        Assertions.assertFalse(deck.getCards().contains(bottomCard));
    }
}
