package system.IntegrationTesting.FeatureTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GameWindow;
import system.GameManager;
import system.User;
import system.TestingUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class FeatureTwoTesting {

    private GameManager gameManager;

    @BeforeEach
    void runAsHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    void setUpInternals(Queue<User> users) {
        GameDesigner gameDesigner = new GameDesigner(users, TestingUtils.getFakeFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        gameManager = gameWindow.getGameManager();
    }

    @Test
    void testDisplayingCurrentPlayersName() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        setUpInternals(users);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test1");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test1");
    }

    @Test
    void testDisplayingCurrentPlayersNameWithLargePlayerCount() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        users.add(new User("test4", true, new ArrayList<>()));
        users.add(new User("test5", true, new ArrayList<>()));
        users.add(new User("test6", true, new ArrayList<>()));
        users.add(new User("test7", true, new ArrayList<>()));
        users.add(new User("test8", true, new ArrayList<>()));
        setUpInternals(users);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test1");
        for (int i = 2; i < gameManager.getPlayerQueue().size() + 1; i++) {
            gameManager.transitionToNextTurn();
            Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                    "test" + (i % (gameManager.getPlayerQueue().size() + 1)));
        }
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test1");
    }
}
