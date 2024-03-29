package system.UnitTesting;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawDeckUnitTesting {
    @Test
    public void testGetCards() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        assertTrue(deck.getCardsAsList().isEmpty());
    }

    @Test
    public void testDrawCardFromEmptyDrawDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        Executable executable = () -> deck.drawCard(new User());
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawCardFromNonEmptyDrawDeck() {
        User user = new User();

        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCardToTop(new Card(CardType.ATTACK));
        boolean drewCard = deck.drawCard(user);
        Assertions.assertFalse(drewCard);

        assertTrue(deck.getCardsAsList().isEmpty());
        assertTrue(!user.getHand().isEmpty());
    }

    @Test
    public void testShuffleOnEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        boolean shuffled = deck.shuffle();
        Assertions.assertEquals(0, deck.getDeckSize());
        Assertions.assertTrue(shuffled);
    }

    @Test
    public void testShuffleOnDeckOfOneCard() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        Card card = new Card(CardType.ATTACK);
        deck.addCardToTop(card);
        boolean shuffled = deck.shuffle();

        Assertions.assertEquals(1, deck.getDeckSize());
        Assertions.assertTrue(shuffled);
    }

    @Test
    public void testShuffleOnDeckOfMultipleCards() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        Card card1 = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ATTACK);
        deck.addCardToTop(card1);
        deck.addCardToTop(card2);
        deck.shuffle();

        Assertions.assertEquals(2, deck.getDeckSize());
        Assertions.assertTrue(deck.getCardsAsList().contains(card1));
        Assertions.assertTrue(deck.getCardsAsList().contains(card2));
    }

    @Test
    public void testDrawFromBottomForUserWithEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        User user = new User();

        Executable executable = () -> deck.drawFromBottomForUser(user);

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawFromBottomForUserWithNonEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        Card bottomCard = new Card(CardType.ALTER_THE_FUTURE);
        deck.addCardToTop(bottomCard);
        Card topCard = new Card(CardType.ATTACK);
        deck.addCardToTop(topCard);

        User user = EasyMock.createMock(User.class);
        user.addCard(bottomCard);
        EasyMock.expectLastCall();

        boolean wasDrawn = deck.drawFromBottomForUser(user);
        Assertions.assertFalse(wasDrawn);

        Assertions.assertFalse(deck.getCardsAsList().contains(bottomCard));
        Assertions.assertTrue(deck.getCardsAsList().contains(topCard));
    }

    @Test
    public void testGetTopOfDeckWithEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        Executable executable = deck::drawThreeCardsFromTop;

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testGetBottomOfDeckWithEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        Executable executable = deck::drawThreeCardsFromBottom;

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testGetTopOfDeckWithOneCard() {
        Card topCard = EasyMock.createMock(Card.class);
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCardToTop(topCard);
        EasyMock.replay(topCard);

        List<Card> future = deck.drawThreeCardsFromTop();

        Assertions.assertEquals(1, future.size());
        Assertions.assertEquals(topCard, future.get(0));
        Assertions.assertEquals(1, deck.getDeckSize());
    }

    @Test
    public void testGetTopOfDeckWithThreeCards() {
        Card first = EasyMock.createMock(Card.class);
        Card second = EasyMock.createMock(Card.class);
        Card third = EasyMock.createMock(Card.class);
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCardToTop(third);
        deck.addCardToTop(second);
        deck.addCardToTop(first);

        EasyMock.replay(first, second, third);

        List<Card> future = deck.drawThreeCardsFromTop();

        final int expectedFutureSize = 3;
        Assertions.assertEquals(expectedFutureSize, future.size());
        Assertions.assertEquals(first, future.get(0));
        Assertions.assertEquals(second, future.get(1));
        Assertions.assertEquals(third, future.get(2));
        Assertions.assertEquals(expectedFutureSize, deck.getDeckSize());
    }

    @Test
    public void testGetTopOfDeckWithFourCards() {
        Card first = EasyMock.createMock(Card.class);
        Card second = EasyMock.createMock(Card.class);
        Card third = EasyMock.createMock(Card.class);
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCardToTop(new Card(CardType.ATTACK));
        deck.addCardToTop(third);
        deck.addCardToTop(second);
        deck.addCardToTop(first);

        EasyMock.replay(first, second, third);

        List<Card> future = deck.drawThreeCardsFromTop();

        final int expectedFutureSize = 3;
        final int expectedDeckSize = 4;
        Assertions.assertEquals(expectedFutureSize, future.size());
        Assertions.assertEquals(first, future.get(0));
        Assertions.assertEquals(second, future.get(1));
        Assertions.assertEquals(third, future.get(2));
        Assertions.assertEquals(expectedDeckSize, deck.getDeckSize());
    }

    @Test
    public void testAddCardToTop() {
        Card second = EasyMock.createMockBuilder(Card.class)
                .withConstructor(CardType.class)
                .withArgs(CardType.ATTACK).createMock();
        Card first = EasyMock.createMockBuilder(Card.class)
                .withConstructor(CardType.class)
                .withArgs(CardType.ALTER_THE_FUTURE).createMock();
        EasyMock.replay(second, first);

        DrawDeck deck = new DrawDeck(new ArrayList<>());

        deck.addCardToTop(second);
        deck.addCardToTop(first);

        List<Card> orderedCards = deck.getCardsAsList();

        Assertions.assertEquals(2, orderedCards.size());
        Assertions.assertEquals(first, orderedCards.get(0));
        Assertions.assertEquals(second, orderedCards.get(1));
    }

    @Test
    public void testDrawCardWithExplodingKitten() {
        Card kitten = new Card(CardType.EXPLODING_KITTEN);

        User user = EasyMock.createMock(User.class);
        EasyMock.replay(user);

        LinkedList<Card> initialDeck = new LinkedList<>();
        initialDeck.add(kitten);

        DrawDeck deck = new DrawDeck(initialDeck);
        assertTrue(deck.drawCard(user));
        assertTrue(deck.getCardsAsList().isEmpty());
        EasyMock.verify(user);
    }

    @Test
    public void testDrawBottomCardWithExplodingKitten() {
        Card kitten = new Card(CardType.EXPLODING_KITTEN);

        User user = EasyMock.createMock(User.class);
        EasyMock.replay(user);

        LinkedList<Card> initialDeck = new LinkedList<>();
        initialDeck.add(kitten);

        DrawDeck deck = new DrawDeck(initialDeck);
        assertTrue(deck.drawFromBottomForUser(user));
        assertTrue(deck.getCardsAsList().isEmpty());
        EasyMock.verify(user);
    }
    @Test
    public void testAddExplodingKitten() {
        Card kitten = new Card(CardType.EXPLODING_KITTEN);

        User user = EasyMock.createMock(User.class);
        EasyMock.replay(user);

        LinkedList<Card> initialDeck = new LinkedList<>();
        initialDeck.add(kitten);

        DrawDeck deck = new DrawDeck(initialDeck);
        assertTrue(deck.drawCard(user));
        deck.addExplodingKittenAtLocation(0);
        assertTrue(deck.getCardsAsList().size() == 1);
        EasyMock.verify(user);
    }

    @Test
    public void testAddExplodingKittenWithTwoCards() {
        Card kitten = new Card(CardType.EXPLODING_KITTEN);
        Card attack = new Card(CardType.ATTACK);

        User user = EasyMock.createMock(User.class);
        EasyMock.replay(user);

        LinkedList<Card> initialDeck = new LinkedList<>();
        initialDeck.add(kitten);
        initialDeck.add(attack);

        DrawDeck deck = new DrawDeck(initialDeck);
        assertTrue(deck.drawCard(user));
        deck.addExplodingKittenAtLocation(1);
        assertTrue(deck.getCardsAsList().size() == 2);
        EasyMock.verify(user);
    }

}
