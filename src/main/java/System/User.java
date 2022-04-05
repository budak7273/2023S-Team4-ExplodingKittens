package System;


import java.util.ArrayList;

public class User {
    String name;
    Boolean alive;
    ArrayList<Card> hand;

    public User(){
        this.name = "";
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public User(String name, boolean alive, ArrayList<Card> hand) {
        this.name = name;
        this.alive = alive;
        this.hand = hand;
    }
}
