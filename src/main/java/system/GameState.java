package system;

import datasource.Messages;
import presentation.GamePlayer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameState {
    private final Queue<User> playerQueue;
    private final GamePlayer gamePlayer;
    private final DrawDeck drawDeck;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;

    public GameState(Queue<User> pq, GamePlayer gp,
                     DrawDeck deck) {
        Queue<User> pqCopy = new LinkedList<>();
        pqCopy.addAll(pq);
        this.playerQueue = pqCopy;
        this.gamePlayer = gp;
        this.drawDeck = deck;
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

        gamePlayer.updateUI();
    }

    public void drawFromBottom() {
        User currentUser = getUserForCurrentTurn();
        drawDeck.drawFromBottomForUser(currentUser);
        transitionToNextTurn();
    }

    public void shuffleDeck() {
        drawDeck.shuffle();
    }

    public void seeTheFuture() {
        List<Card> futureCards = drawDeck.drawThreeCardsFromTop();
        gamePlayer.displayFutureCards(futureCards);
    }

    public void returnFutureCards(List<Card> future) {
        for (int i = 2; i >= 0; i--) {
            Card replace = future.get(i);
            drawDeck.addCardToTop(replace);
        }
    }

    public User getUserForCurrentTurn() {
        return playerQueue.peek();
    }

    public int getDeckSizeForCurrentTurn() {
        return drawDeck.getDeckSize();
    }

    public void drawCardForCurrentTurn() {
        User currentPlayer = getUserForCurrentTurn();
        boolean result = drawDeck.drawCard(currentPlayer);
        if (result) {
            currentPlayer.attemptToDie();
        }
        transitionToNextTurn();
    }

    public Queue<User> getPlayerQueue() {
        Queue<User> toReturn = new LinkedList<>();
        toReturn.addAll(this.playerQueue);
        return toReturn;
    }

    public DrawDeck getDrawDeck() {
        return this.drawDeck;
    }

    public boolean tryToEndGame() {
        if (playerQueue.size() < 1) {
            String msg = Messages.getMessage(Messages.ILLEGAL_PLAYERS);
            throw new IllegalArgumentException(msg);
        }
        if (playerQueue.size() == 1) {
            gamePlayer.endGame();
            return true;
        }
        return false;
    }
}
