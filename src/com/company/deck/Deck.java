package com.company.deck;

import com.company.card.Card;
import com.company.players.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private int numOfSets = 0;
    private int cardCounter;
    private List<Card> cards = new ArrayList<>();
    private List<Card> thisRoundCards;

    public int numOfSets() {
        return numOfSets;
    }

    public void numOfSets(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Deck(int numOfSets){
        this.cardCounter = 0;
        this.thisRoundCards = new ArrayList<Card>();
        this.numOfSets(numOfSets);
        List<Card> listOfCards = createCardList(this.numOfSets());
        this.setCards(listOfCards);//full and shuffle deck
    }

    public List<Card> createCardList(int numOfSets){
        char[] colors = {'♥', '♦', '♣', '♠'};
        List<Card> deck = new ArrayList<>();
        for(int num = 0 ; num<numOfSets ; num++) {
            for (int i = 1; i <= 13; i++) {
                for (char color : colors) {
                    switch (i) {
                        case 1 -> deck.add(new Card(1, "A", color));
                        case 11 -> deck.add(new Card(10, "J", color));
                        case 12 -> deck.add(new Card(10, "Q", color));
                        case 13 -> deck.add(new Card(10, "K", color));
                        default -> deck.add(new Card(i, Integer.toString(i), color));
                    }
                }
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public void hit(List<Card> hand){
        Card card = this.cards.get(0);
        hand.add(card);
        this.thisRoundCards.add(card);
        this.cards.remove(0);
    }

    public void countCard(Card card){
        if(card.getPoints()>=2 && card.getPoints()<=6)
            this.cardCounter++;
        else if(card.getPoints() == 10 || card.getPoints() == 1)
            this.cardCounter--;
    }

    public int getCardCounter(){
        return this.cardCounter;
    }

    public void countCards(){
        for(Card card: this.thisRoundCards){
            this.countCard(card);
        }
        this.thisRoundCards.clear();
    }
}
