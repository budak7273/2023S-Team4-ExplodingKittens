package datasource;

import system.Card;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CardCSVParser {

    private File csvFile;
    private static final int MAX_CARD_COUNT = 101;

    public CardCSVParser(File csv) {
        this.csvFile = csv;
    }

    private Card createCardFromTypeName(String cardTypeName) {
        CardType cardType = CardType.valueOf(cardTypeName);
        return new Card(cardType);
    }

    public List<Card> generateListOfCards(boolean includePaw, boolean includePawless) {
        return generateListOfCardsWithVerification(includePaw, includePawless);
    }

    public List<Card> generateListOfCardsWithVerification(boolean includePaw, boolean includePawless) {
        List<Card> cardList = new ArrayList<>();
        int cardCount = 0;

        Scanner scanner = generateScanner();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");

            verifyPropertyLength(cardProperties);

            String cardTypeName = cardProperties[0];
            Boolean hasAPaw = Boolean.parseBoolean(cardProperties[1]);

            verifyCardType(cardTypeName);
            cardCount++;

            Card currentCard = createCardFromTypeName(cardTypeName);
            if (includePaw && hasAPaw) {
                cardList.add(currentCard);
            } else if (includePawless && !hasAPaw) {
                cardList.add(currentCard);
            }
        }
        verifyCardCount(cardCount);
        return cardList;
    }

    private Scanner generateScanner() {
        try {
            return new Scanner(csvFile, "UTF-8");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                    I18n.getMessage("CouldNotGenerateMessage"));
        }
    }

    private void verifyPropertyLength(String[] cardProperties) {
        if (cardProperties.length != 2) {
            throw new IllegalArgumentException(
                    I18n.getMessage("MissingDataMessage"));
        }
    }

    private void verifyCardCount(int cardCount) {
        if (cardCount != MAX_CARD_COUNT) {
            throw new IllegalArgumentException(I18n
                    .getMessage("BadNumberOfCardsMessage"));
        }
    }

    private void verifyCardType(String cardTypeName) {
        System.out.println(cardTypeName);
        Set<String> allCardTypes = Collections.unmodifiableSet(new HashSet<>(CardType.ENUM_VALUES));
        System.out.println(allCardTypes);
        if (!allCardTypes.contains(cardTypeName)) {
            throw new IllegalArgumentException(I18n
                    .getMessage("InvalidCardTypeMessage") + cardTypeName
                                               + I18n.getMessage("FoundInFileMessage"));
        }
    }
}
