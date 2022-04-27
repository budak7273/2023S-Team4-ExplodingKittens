package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawDeck {
    private List<Card> cards;

    public DrawDeck() {
        cards = new ArrayList<>();
    }
    public int getDeckSize() {
        return cards.size();
    }

    public void addCard(final Card card) {
        cards.add(card);
    }

    public void drawCard(final User drawingUser) {
        if (cards.isEmpty()) {
            throw new RuntimeException("Draw deck is empty, the game was set up improperly.");
        }
        Card drawnCard = cards.remove(0);
        drawingUser.addCard(drawnCard);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void drawInitialCard(User drawer) {
        if (cards.isEmpty()) {
            throw new RuntimeException("Draw deck is empty, the game was set up improperly.");
        }

        // TODO: REMOVE this code! Exploding Kitten cards are already removed from the deck
        //  at the time hands are dealt (see https://www.explodingkittens.com/pages/rules-kittens-party)
        Card drawnCard = cards.remove(0);
        while (drawnCard.getName().equals("Exploding Kitten")) {
            cards.add(cards.size(), drawnCard);
            drawnCard = cards.remove(0);
        }
        drawer.addCard(drawnCard);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }
}