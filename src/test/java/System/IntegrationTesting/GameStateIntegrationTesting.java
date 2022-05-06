package system.IntegrationTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import presentation.GameDesigner;
import presentation.GamePlayer;
import system.User;
import system.DrawDeck;
import system.GameState;



public class GameStateIntegrationTesting {

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1UserIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        GamePlayer board = new GamePlayer();
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        pq.add(new User());
        GameState gameState = new GameState(pq, board, deck);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);

        GameDesigner gameboard = new GameDesigner(pq);
        gameboard.initializeGameState();
        GamePlayer gamePlayer = gameboard.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

    }

    @Test
    public void testTransitionToNextTurnWithQueueOf10UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        for (int i = 0; i < MAX_USER_COUNT - 2; i++) {
            pq.add(new User());
        }
        GameDesigner gameboard = new GameDesigner(pq);
        gameboard.initializeGameState();
        GamePlayer gamePlayer = gameboard.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

    }

    @Test
    public void testTransitionToNextTurnWithQueueOf11UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        for (int i = 0; i < MAX_USER_COUNT + 1; i++) {
            pq.add(new User());
        }
        GameDesigner gameboard = new GameDesigner(pq);
        Executable executable1 = () -> gameboard.initializeGameState();
        Assertions.assertThrows(IllegalArgumentException.class, executable1);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsersIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);

        GameDesigner gameboard = new GameDesigner(pq);
        gameboard.initializeGameState();
        GamePlayer gamePlayer = gameboard.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();

        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        expected.add(user1);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void
    testTransitionToNextAliveWithThreeUsersFirstDeadIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);

        GameDesigner gameboard = new GameDesigner(pq);
        gameboard.initializeGameState();
        GamePlayer gamePlayer = gameboard.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();

        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void
    testTransitionToNextAliveWithThreeUsersTwoDeadIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        user2.die();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);

        GameDesigner gameboard = new GameDesigner(pq);
        gameboard.initializeGameState();
        GamePlayer gamePlayer = gameboard.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();

        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void
    testTransitionToNextAliveWithTenUsersAndU1U2U4DeadIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        Queue<User> expected = new LinkedList<User>();

        for (int i = 0; i < MAX_USER_COUNT; i++) {
            User user = new User();
            if (i == 0 || i == 1 || i == ARBITRARY_USER_ID_TO_KILL) {
                user.die();
            }
            pq.add(user);
            if (i != 0 && i != 1) {
                expected.add(user);
            }
        }
        GameDesigner gameboard = new GameDesigner(pq);
        gameboard.initializeGameState();
        GamePlayer gamePlayer = gameboard.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();

        gameState.transitionToNextTurn();

        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void testDrawFromBottomIntegrationTest() {
        List<String> playerUsernames = new ArrayList<>();
        playerUsernames.add("Player1ForIntegrationTest");
        playerUsernames.add("Player2ForIntegrationTest");
        playerUsernames.add("Player3ForIntegrationTest");
        playerUsernames.add("Player4ForIntegrationTest");
        playerUsernames.add("Player5ForIntegrationTest");

        GameDesigner gameboard = new GameDesigner();
        gameboard.initializeGameState(playerUsernames);
        GamePlayer gamePlayer = gameboard.getGamePlayer();

        GameState gameState = gamePlayer.getGameState();
        DrawDeck drawDeck = gameState.getDrawDeck();
        int beforeCount = gameState.getDeckSizeForCurrentTurn();
        User currentUser = gameState.getUserForCurrentTurn();
        drawDeck.drawFromBottomForUser(currentUser);

        gamePlayer.updateUI();
        Assertions.assertEquals(
                beforeCount - 1, gameState.getDeckSizeForCurrentTurn());
        gameState.drawFromBottom();
        Assertions.assertEquals(
                beforeCount - 2, gameState.getDeckSizeForCurrentTurn());

    }

    @Test
    public void testShuffleDeckIntegrationTest() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = new GamePlayer();
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        drawDeck.shuffle();

        gameState.shuffleDeck();
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());
    }
}