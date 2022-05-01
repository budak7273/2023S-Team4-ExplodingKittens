package system;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private Boolean alive = true;
    private ArrayList<Card> hand = new ArrayList<>();

    public User() {
        this.name = "";
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public User(final String playerName) {
        this.name = playerName;
    }

    public User(final String playerName,
                final boolean activeStatus,
                final ArrayList<Card> playerHand) {
        this.name = playerName;
        this.alive = activeStatus;
        this.hand = playerHand;
    }

    public String getName() {

        return this.name;
    }

    public List<Card> getHand() {

        return this.hand;
    }

    public void addCard(final Card drawnCard) {

        this.hand.add(drawnCard);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void die() {
        this.alive = false;
    }

    public boolean checkForSpecialEffectPotential() {
        int count = 0;
        for(Card card:this.hand){
            if(card.isCatCard()) count++;
        }

        if(count>=2) return true;

        return false;
    }

    public boolean verifyEffectForCardsSelected(final List<Integer> selected) {
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            throw new IllegalArgumentException(
                    "You cannot select cards when your hand is empty");
        }

        return false;
    }
}
