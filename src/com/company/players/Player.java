package com.company.players;

import com.company.card.Card;
import com.company.deck.Deck;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    protected String username = "";
    protected List<Card> hand = new ArrayList<>();

    public abstract void showInfo(List<Card> hand);

    public int numOfAces(){
        int num=0;
        for(Card card: this.hand) {
            if (card.getPoints() == 1) {
                num++;
            }
        }
        return num;
    }

    public int sumHand(List<Card> hand){
        ArrayList<Integer> sums = this.sumOfCards(hand);
        return sums.get(sums.size()-1);
    }

    public ArrayList<Integer> sumOfCards(List<Card> hand){
        int sum=0;
        int aces = numOfAces();
        ArrayList<Integer> sums = new ArrayList<>();
        for(Card card: hand){
            sum+=card.getPoints();
        }
        sums.add(sum);
        for(int i=1 ; i<= aces ; i++){
            if(sum+(i*10)<=21)
                sums.add(sum+(i*10));
            else
                break;
        }
        return sums;
    }

    public void clearHand(){
        this.hand.clear();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
}
