package com.company.players;

import com.company.card.Card;
import com.company.deck.Deck;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class User extends Player {

    private int money;
    private int bid;
    private boolean isSecondHandActive;
    private List<Card> secondHand;
    private boolean insurance;

    public User() {
        this.username = "Player";
        this.money = 0;
        this.bid = 0;
        this.hand = new ArrayList<>();
        this.isSecondHandActive=false;
        this.secondHand = new ArrayList<>();
        this.insurance = false;
    }

    public User(String username, int money){
        this.username = username;
        this.money = money;
        this.bid = 0;
        this.hand = new ArrayList<>();
        this.isSecondHandActive=false;
        this.secondHand = new ArrayList<>();
        this.insurance = false;
    }

    @Override
    public void showInfo(List<Card> hand) {
        if(!this.isSecondHandActive)
            System.out.print(this.username+"'s hand: ");
        else if(hand.equals(this.hand)){
            System.out.print(this.username+" main hand: ");
        }else{
            System.out.print(this.username+" second hand: ");
        }
        for (Card card : hand) {
            String toPrint = card.getValue() + card.getColor() + " ";
            System.out.print(toPrint);
        }
        System.out.println("\n"+this.username+"'s bid: "+this.bid);
        if(this.insurance){
            System.out.println("Insurance: "+((int)(0.5*this.bid)));
        }
        System.out.print("\n");
    }

    public void askBid() {
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Actual money: " + this.money);
            System.out.println("Next bid (write 0 if You want to go all in): ");
            try{
                this.bid = scanner.nextInt();
                if(this.bid == 0)
                    this.bid = this.money;
                if(this.bid <= this.money) {
                    this.money -= this.bid;
                    return;
                }else
                    System.out.println("Wrong value - it has to be less than money you've got.");
            }catch(InputMismatchException ex){
                System.out.println("Write only numbers and without spaces!!!");
            }
        }while(true);
    }

    public boolean hasMove(Deck deck, List<Card> hand) {
        ArrayList<Integer> sums = this.sumOfCards(hand);
        int lastSum = sums.get(sums.size() - 1);//the last sum is the biggest (always <= 21)
        //than stand
        return lastSum < 21;
    }

    public char askForMove(Deck deck, List<Card> hand, boolean croupierHasAce){
        boolean isSplit = this.isSplitAvailable();
        boolean isDouble = this.isDoubleAvailable();
        boolean isInsurance = this.isInsuranceAvailable(croupierHasAce);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option: ");
        System.out.println("Hit (write H)");
        System.out.println("Stand (write S)");
        if(isSplit)
            System.out.println("Split (write P)");
        if(isDouble)
            System.out.println("Double (write D)");
        if(isInsurance)
            System.out.println("Insurance (write I)");
        System.out.println("Write T to see card Counter (up to previous game)");
        String option = scanner.next();
        switch(option.charAt(0)){
            case 'h':
            case 'H':
                deck.hit(hand);
                return 'H';
            case 's':
            case 'S':
                return 'S';
            case 'p':
            case 'P':
                if(isSplit) {
                    this.splitCards(deck);
                    return 'P';
                }
                return askForMove(deck, hand, croupierHasAce);
            case 'd':
            case 'D':
                if(isDouble){
                    deck.hit(hand);
                    this.money -= this.bid;
                    this.bid *= 2;
                    return 'D';
                }
                return askForMove(deck, hand, croupierHasAce);
            case 'i':
            case 'I':
                if(this.isInsuranceAvailable(croupierHasAce)){
                    this.money = this.money - (int)(0.5*this.bid);
                    this.insurance = true;
                    return 'I';
                }
                return askForMove(deck, hand, croupierHasAce);
            case 't':
            case 'T':
                System.out.println("Card counter: "+deck.getCardCounter());
            default:
                return askForMove(deck, hand, croupierHasAce);
        }
    }

    public boolean isSplitAvailable(){
        return !this.isSecondHandActive && this.hand.size() == 2 && this.hand.get(0).getPoints() == this.hand.get(1).getPoints();
    }

    public boolean isDoubleAvailable(){
        return !this.isSecondHandActive && this.hand.size() == 2;
    }

    public boolean isInsuranceAvailable(boolean croupierHasAce){
        return croupierHasAce && this.hand.size() == 2;
    }

    public void splitCards(Deck deck){
        this.isSecondHandActive=true;
        this.secondHand.add(this.hand.get(1));
        this.hand.remove(1);
        deck.hit(this.hand);
        deck.hit(this.secondHand);
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
        this.insurance = false;
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

    public boolean isSecondHandActive() {
        return isSecondHandActive;
    }

    public List<Card> getSecondHand() {
        return secondHand;
    }

    public boolean isInsurance(){
        return this.insurance;
    }
}
