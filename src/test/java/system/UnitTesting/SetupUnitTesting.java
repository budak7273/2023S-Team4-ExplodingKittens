package system.UnitTesting;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.Card;
import system.DrawDeck;
import system.Setup;
import system.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.easymock.EasyMock.eq;

public class SetupUnitTesting {
    private static final int FULL_SIZE = 107;
    private static final int PAW_ONLY_SIZE = 44;

    private static final int MAX_PLAYER_COUNT = 10;

    private static final int MAX_PAW_ONLY_COUNT = 3;
    private static final int MIN_NO_PAW_ONLY_COUNT = 4;
    private static final int MAX_NO_PAW_ONLY_COUNT = 7;
    private static final int MIN_ALL_COUNT = 8;

    private static final int INITIAL_HAND_SIZE = 7;

    @Test
    public void testCreateUsersFromEmptyList() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromNull() {
        Setup setup = new Setup(2);
        Executable executable = () -> setup.createUsers(null);
        Assertions.assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void testCreateUsersFromListOfSize1() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        names.add("name");
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromListOfSize2() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 2);
    }

    @Test
    public void testCreateUsersFromListOfSize10() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYER_COUNT; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == MAX_PLAYER_COUNT);
    }

    @Test
    public void testCreateUsersFromListOfSize11() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYER_COUNT + 1; i++) {
            names.add("name" + i);
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromListWithDuplicates() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("sameName");
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromEmptyFile() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/empty.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFileWithOneLine() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/oneline.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd2Players() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/fullfile.csv";

        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 1;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd3Players() {
        Setup setup = new Setup(MAX_PAW_ONLY_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd4Players() {
        Setup setup = new Setup(MIN_NO_PAW_ONLY_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = MAX_NO_PAW_ONLY_COUNT - MIN_NO_PAW_ONLY_COUNT;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd7Players() {
        Setup setup = new Setup(MAX_NO_PAW_ONLY_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd8Players() {
        Setup setup = new Setup(MIN_ALL_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 2;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd10Players() {
        Setup setup = new Setup(MAX_PLAYER_COUNT);
        String cardInfoFile = "/testingcsvs/fullfile.csv";
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromOneLineOfInvalidData() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/oneline_invalid.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFullFileWithOneLineOfInvalidData() {
        Setup setup = new Setup(2);
        String cardInfoFile = "/testingcsvs/fullfile_invalid.csv";
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testDistributeCards1User() {
        Queue<User> users = new LinkedList<>();
        users.add(new User());
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck drawDeck = new DrawDeck(cards);

        Setup setup = new Setup(1);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testDistributeCards2Users() {
        User player1 = EasyMock.createMock(User.class);
        User player2 = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        EasyMock.expect(drawDeck.drawCard(player1))
                .andReturn(false)
                .times(INITIAL_HAND_SIZE);
        EasyMock.expect(drawDeck.drawCard(player2))
                .andReturn(false)
                .times(INITIAL_HAND_SIZE);

        player1.addCard(eq(new Card(CardType.DEFUSE)));
        player2.addCard(eq(new Card(CardType.DEFUSE)));
        EasyMock.replay(player1, player2, drawDeck);

        Queue<User> users = new LinkedList<>();
        users.add(player1);
        users.add(player2);

        Setup setup = new Setup(2);
        setup.dealHands(users, drawDeck);

        EasyMock.verify(player1, player2, drawDeck);
    }

    @Test
    public void testDistributeCards10Users() {
        Queue<User> users = new LinkedList<>();
        for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
            users.add(EasyMock.createMock(User.class));
        }
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        for (User user : users) {
            EasyMock.expect(drawDeck.drawCard(user))
                    .andReturn(false)
                    .times(INITIAL_HAND_SIZE);
            user.addCard(eq(new Card(CardType.DEFUSE)));
        }

        for (User user : users) {
            EasyMock.replay(user);
        }
        EasyMock.replay(drawDeck);

        Setup setup = new Setup(MAX_PLAYER_COUNT);
        setup.dealHands(users, drawDeck);

        for (User user : users) {
            EasyMock.verify(user);
        }
        EasyMock.verify(drawDeck);
    }

    @Test
    public void testDistributeCards11Users() {
        Queue<User> users = new LinkedList<>();
        for (int i = 0; i < MAX_PLAYER_COUNT + 1; i++) {
            users.add(new User());
        }
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck drawDeck = new DrawDeck(cards);

        Setup setup = new Setup(MAX_PLAYER_COUNT + 1);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testShuffleExplodingKittensInDeck() {
        for (int i = 2; i <= MAX_PLAYER_COUNT; i++) {
            int playerCount = i;
            Setup setup = new Setup(playerCount);
            DrawDeck deck = EasyMock.createMock(DrawDeck.class);
            Card explodingCard = EasyMock.createMockBuilder(Card.class)
                                         .withConstructor(CardType.EXPLODING_KITTEN).createMock();
            EasyMock.expect(deck.shuffle()).andReturn(true);

            deck.addCardToTop(explodingCard);
            EasyMock.expectLastCall().times(playerCount - 1);
            EasyMock.replay(deck, explodingCard);

            setup.shuffleExplodingKittensInDeck(deck);

            EasyMock.verify(deck, explodingCard);
        }
    }
}
