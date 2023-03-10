package system.IntegrationTesting;

import system.Setup;
import system.User;
import system.DrawDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.io.File;
import java.util.*;


public class SetupIntegrationTesting {
    private static final int FULL_SIZE = 101;
    private static final int PAW_ONLY_SIZE = 41;

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
        String path = "src/test/resources/empty.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFileWithOneLineIntegrationTest() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/oneline.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd2PlayersIntegrationTest() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);

        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 1;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }
    @Test
    public void testCreateDrawDeckFromFullFileAnd3PlayersIntegrationTest() {
        Setup setup = new Setup(MAX_PAW_ONLY_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd4PlayersIntegrationTest() {
        Setup setup = new Setup(MIN_NO_PAW_ONLY_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = MAX_NO_PAW_ONLY_COUNT - MIN_NO_PAW_ONLY_COUNT;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd7PlayersIntegrationTest() {
        Setup setup = new Setup(MAX_NO_PAW_ONLY_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd8PlayersIntegrationTest() {
        Setup setup = new Setup(MIN_ALL_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 2;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd10PlayersIntegrationTest() {
        Setup setup = new Setup(MAX_PLAYER_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromOneLineOfInvalidDataIntegrationTest() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/oneline_invalid.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testCreateDrawDeckFromFullFileWithOneLineOfInvalidDataIntegrationTest() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/fullfile_invalid.csv";
        File cardInfoFile = new File(path);
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
        String path = "src/test/resources/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(new File(path));
        final int firstDeckSize = 42;
        Assertions.assertEquals(firstDeckSize,
                drawDeck.getCardsAsList().size());

        Queue<User> users = new LinkedList<>();
        users.add(player1);
        users.add(player2);

        setup.dealHands(users, drawDeck);
        final int secondDeckSize = 28;
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
        String path = "src/test/resources/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(new File(path));
        final int firstDeckSize = 101;
        Assertions.assertEquals(firstDeckSize,
                drawDeck.getCardsAsList().size());
        setup.dealHands(users, drawDeck);
        final int secondDeckSize = 31;
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
