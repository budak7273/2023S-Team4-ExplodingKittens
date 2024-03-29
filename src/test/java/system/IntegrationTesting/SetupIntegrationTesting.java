package system.IntegrationTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.DrawDeck;
import system.Setup;
import system.User;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class SetupIntegrationTesting {
    private static final int FULL_SIZE = 107;
    private static final int PAW_ONLY_SIZE = 44;

    private static final int MAX_PLAYER_COUNT = 10;

    private static final int MAX_PAW_ONLY_COUNT = 3;
    private static final int MIN_NO_PAW_ONLY_COUNT = 4;
    private static final int MAX_NO_PAW_ONLY_COUNT = 7;
    private static final int MIN_ALL_COUNT = 8;

    private static final int INITIAL_HAND_SIZE = 7;

    @Test
    public void testCreateUsersFromEmptyListIntegrationTest() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromNullIntegrationTest() {
        Setup setup = new Setup(2);
        Executable executable = () -> setup.createUsers(null);
        Assertions.assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void testCreateUsersFromListOfSize1IntegrationTest() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        names.add("name");
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromListOfSize2IntegrationTest() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 2);
    }

    @Test
    public void testCreateUsersFromListOfSize10IntegrationTest() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYER_COUNT; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == MAX_PLAYER_COUNT);
    }

    @Test
    public void testCreateUsersFromListOfSize11IntegrationTest() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYER_COUNT + 1; i++) {
            names.add("name" + i);
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromListWithDuplicatesIntegrationTest() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("sameName");
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromEmptyFileIntegrationTest() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/empty.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFileWithOneLineIntegrationTest() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/oneline.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd2PlayersIntegrationTest() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 1;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd3PlayersIntegrationTest() {
        Setup setup = new Setup(MAX_PAW_ONLY_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd4PlayersIntegrationTest() {
        Setup setup = new Setup(MIN_NO_PAW_ONLY_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = MAX_NO_PAW_ONLY_COUNT - MIN_NO_PAW_ONLY_COUNT;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd7PlayersIntegrationTest() {
        Setup setup = new Setup(MAX_NO_PAW_ONLY_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd8PlayersIntegrationTest() {
        Setup setup = new Setup(MIN_ALL_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 2;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd10PlayersIntegrationTest() {
        Setup setup = new Setup(MAX_PLAYER_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromOneLineOfInvalidDataIntegrationTest() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/oneline_invalid.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testCreateDrawDeckFromFullFileWithOneLineOfInvalidDataIntegrationTest() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/fullfile_invalid.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testDistributeCards1UserIntegrationTest() {
        Queue<User> users = new LinkedList<>();
        users.add(new User());
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        Setup setup = new Setup(1);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testDistributeCards2UsersIntegrationTest() {
        User player1 = new User("player1");
        User player2 = new User("player2");
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        final int firstDeckSize = 45;
        Assertions.assertEquals(firstDeckSize,
                                drawDeck.getCardsAsList().size());

        Queue<User> users = new LinkedList<>();
        users.add(player1);
        users.add(player2);

        setup.dealHands(users, drawDeck);
        final int secondDeckSize = 31;
        Assertions.assertEquals(secondDeckSize,
                                drawDeck.getCardsAsList().size());
    }

    @Test
    public void testDistributeCards10UsersIntegrationTest() {
        Queue<User> users = new ArrayDeque<>();
        for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
            users.add(new User());
        }
        Setup setup = new Setup(MAX_PLAYER_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        final int firstDeckSize = 107;
        Assertions.assertEquals(firstDeckSize,
                                drawDeck.getCardsAsList().size());
        setup.dealHands(users, drawDeck);
        final int secondDeckSize = 37;
        Assertions.assertEquals(secondDeckSize,
                                drawDeck.getCardsAsList().size());
    }

    @Test
    public void testDistributeCards11UsersIntegrationTest() {
        Queue<User> users = new LinkedList<>();
        for (int i = 0; i < MAX_PLAYER_COUNT + 1; i++) {
            users.add(new User());
        }
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        Setup setup = new Setup(MAX_PLAYER_COUNT + 1);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }
}
