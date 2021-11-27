package com.company.players;

import com.company.card.Card;
import com.company.deck.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User extends Player {

    private int money;
    private int bid;
    private boolean isSecondHandActive = false;
    private List<Card> secondHand = new ArrayList<>();

    public User() {
        this.username = "Player";
        this.money = 0;
        this.bid = 0;
        this.hand = new ArrayList<Card>();
        this.isSecondHandActive=false;
        this.secondHand = new ArrayList<Card>();
    }

    public User(String username, int money){
        this.username = username;
        this.money = money;
        this.bid = 0;
        this.hand = new ArrayList<Card>();
        this.isSecondHandActive=false;
        this.secondHand = new ArrayList<Card>();
    }

    @Override
    public void showInfo() {
        System.out.print(this.username+": ");
        for (Card card : this.hand) {
            String toPrint = card.getValue() + card.getColor() + " ";
            System.out.print(toPrint);
        }
        System.out.println("\n"+this.username+"'s bid: "+this.bid+"\n");
    }

    public void askBid() {
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Actual money: " + this.money);
            System.out.println("Next bid (only nums): ");
            this.bid = scanner.nextInt();
            if(this.bid <= this.money) {
                this.money -= this.bid;
                return;
            }else
                System.out.println("Wrong value - it has to be less than money you've got.");
        }while(true);
    }

    public boolean hasMove(Deck deck, List<Card> hand) {
        ArrayList<Integer> sums = this.sumOfCards(hand);
        int lastSum = sums.get(sums.size() - 1);//the last sum is the biggest (always <= 21)
        if (lastSum >= 21)//than stand
            return false;
        else
            return true;
    }

    public boolean askForMove(Deck deck, List<Card> hand){//true if stand
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option: ");
        System.out.println("Hit (write H)");
        System.out.println("Stand (write S)");
        String option = scanner.next();
        switch(option.charAt(0)){
            case 'h':
            case 'H':
                deck.hit(hand);
                return false;
            case 's':
            case 'S':
                return true;
            default:
                return askForMove(deck, hand);
        }
    }

    public void win(int points) {
        if(points == 21){
            this.money = this.money + (int)(2.5*this.bid);
        }else{
            this.money = this.money + 2*this.bid;
        }
    }

    public void draw(){
        this.money += this.bid;
    }

    @Override
    public void clearHand(){
        this.hand.clear();
        this.isSecondHandActive = false;
        this.secondHand.clear();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}
