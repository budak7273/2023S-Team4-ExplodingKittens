package presentation;

import system.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NotificationPanel extends JPanel {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private ArrayList<Card> cardOrder = new ArrayList<>();
    private GamePlayer gamePlayer;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public NotificationPanel(GamePlayer player) {
        super();
        this.gamePlayer = player;
        constructBaseLayout();
    }

    private void constructBaseLayout() {
        this.removeAll();
        this.setLayout(new GridLayout(2, 1));

        contentPanel = new JPanel();
        ComponentOrientation orientation = ComponentOrientation.LEFT_TO_RIGHT;
        contentPanel.setComponentOrientation(orientation);
        this.add(contentPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel);
    }

    private void addExitButtonToLayout(String msg) {
        JButton exit = new JButton();
        exit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        exit.setBackground(Color.GRAY);
        exit.addActionListener(e -> removeAll());
        exit.setText(msg);
        buttonPanel.add(exit);
    }

    public void seeTheFuture(List<Card> future) {
        initializePane();
        addExitButtonToLayout("Done");

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            contentPanel.add(futureCard);
        }
        gamePlayer.updateDisplay();
    }

    public void alterTheFuture(List<Card> future) {
        initializePane();
        addExitButtonToLayout("Done");
        final Card[] selectedCard = {null};

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            futureCard.addActionListener(new ActionListener() {
                private Card card = topCard;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedCard[0] != null) {
                        int current = cardOrder.indexOf(card);
                        int swap = cardOrder.indexOf(selectedCard[0]);
                        cardOrder.set(swap, card);
                        cardOrder.set(current, selectedCard[0]);
                        selectedCard[0] = null;
                        alterTheFuture(new ArrayList<>(cardOrder));
                    } else {
                        selectedCard[0] = card;
                    }
                }
            });
            contentPanel.add(futureCard);
        }

        gamePlayer.updateDisplay();
    }

    public void notifyPlayers(String contentMessage, String doneMessage) {
        initializePane();
        addExitButtonToLayout(doneMessage);

        JLabel content = new JLabel("<html><center><br>"
                + contentMessage + "<br><br></center></html>");

        contentPanel.add(content);
        content.setOpaque(true);
        content.setBackground(Color.CYAN);

        gamePlayer.updateDisplay();
    }

    private void initializePane() {
        constructBaseLayout();
        cardOrder.clear();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        gamePlayer.updateDisplay();
        if (cardOrder.size() > 0) {
            gamePlayer.returnFutureCards(cardOrder);
            cardOrder.clear();
        }
    }
}
