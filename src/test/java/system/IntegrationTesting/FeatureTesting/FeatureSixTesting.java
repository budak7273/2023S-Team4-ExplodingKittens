package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.GameManager;
import system.User;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FeatureSixTesting {

    @Test
    public void testUserPassesTurn() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        User currentUser = gameManager.getUserForCurrentTurn();
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        gameManager.drawCardForCurrentTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize + 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize - 1);
    }

    @Test
    public void testUserPlaysCard() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        User currentUser = gameManager.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.SKIP));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameManager);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize);
    }
}
