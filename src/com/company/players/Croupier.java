package com.company.players;

import com.company.card.Card;
import com.company.deck.Deck;

import java.util.ArrayList;
import java.util.List;

public class Croupier extends Player {

    private boolean onlyFirstCard;

    public Croupier() {
        this.username = "Player";
        this.hand = new ArrayList<Card>();
        this.onlyFirstCard = true;
    }

    public boolean isOnlyFirstCard() {
        return onlyFirstCard;
    }

    public void setOnlyFirstCard(boolean onlyFirstCard) {
        this.onlyFirstCard = onlyFirstCard;
    }

    public int makeMove(Deck deck, List<Card> hand){
        do {
            ArrayList<Integer> sums = this.sumOfCards(hand);
            int lastSum = sums.get(sums.size() - 1);//the last sum is the biggest (always <= 21)

            if (lastSum >= 17)//than stand
                return lastSum;

            deck.hit(hand);
        }while(true);//hit until sum >= 17
    }

    @Override
    public void showInfo(){
        System.out.print("Croupier: ");
        if(this.onlyFirstCard){
            Card first = this.hand.get(0);
            String toPrint = first.getValue()+first.getColor()+" "+"XX";
            System.out.print(toPrint);
        }else {
            for (Card card : this.hand) {
                String toPrint = card.getValue() + card.getColor() + " ";
                System.out.print(toPrint);
            }
        }
        System.out.print("\n");
    }
}
