package system;

import datasource.Messages;
import presentation.GameDesigner;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameState {
    private final Queue<User> playerQueue;
    private final GameDesigner gameDesigner;
    private final DrawDeck drawDeck;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;

    public GameState(final Queue<User> pq, final GameDesigner gd,
                     final List<Card> deck) {
        Queue<User> pqCopy = new LinkedList<>();
        pqCopy.addAll(pq);
        this.playerQueue = pqCopy;
        this.gameDesigner = gd;
        DrawDeck deckCopy = new DrawDeck();
        for (Card card: deck) {
            deckCopy.addCard(card);
        }
        this.drawDeck = deckCopy;
    }

    public void transitionToNextTurn() {
        if (playerQueue.size() < MIN_PLAYERS
                || playerQueue.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException(
                    Messages.getMessage(Messages.ILLEGAL_PLAYERS));
        }
        User userForCurrentTurn = playerQueue.poll();
        if (userForCurrentTurn.isAlive()) {
            playerQueue.add(userForCurrentTurn);
        }
        while (!getUserForCurrentTurn().isAlive()) {
            playerQueue.poll();
        }

        gameDesigner.updateGamePlayer();
    }

    public void drawFromBottom() {
        User currentUser = getUserForCurrentTurn();
        drawDeck.drawFromBottomForUser(currentUser);
        transitionToNextTurn();
    }

    public User getUserForCurrentTurn() {
        return playerQueue.peek();
    }

    public int getDeckSizeForCurrentTurn() {
        return drawDeck.getDeckSize();
    }

    public void drawCardForCurrentTurn() {
        drawDeck.drawCard(getUserForCurrentTurn());
        transitionToNextTurn();
    }

    public Queue<User> getPlayerQueue() {
        Queue<User> toReturn = new LinkedList<>();
        toReturn.addAll(this.playerQueue);
        return toReturn;
    }

}
