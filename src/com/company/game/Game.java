package com.company.game;

import com.company.card.Card;
import com.company.deck.Deck;
import com.company.players.Croupier;
import com.company.players.User;

import java.util.ArrayList;
import java.util.List;

public class Game {

    Croupier croupier;
    User user;
    Deck deck;

    public Game() {
        this.croupier = new Croupier();
        this.user = new User();
        this.deck = null;
    }

    public Game(User user, int numOfSets) {
        this.croupier = new Croupier();
        this.user = user;
        this.deck = new Deck(numOfSets);
    }

    public void showInfo(List<Card> playerHand){
        this.croupier.showInfo(this.croupier.getHand());
        this.user.showInfo(playerHand);
    }

    public void giveCards(){
        this.deck.hit(this.user.getHand());
        this.deck.hit(this.user.getHand());
        this.deck.hit(this.croupier.getHand());
        this.deck.hit(this.croupier.getHand());
    }

    public void clearHands(){
        this.user.clearHand();
        this.croupier.clearHand();
        this.croupier.setOnlyFirstCard(true);
    }

    public void checkResult(int userPoints, int croupierPoints){
        if(userPoints <= 21){
            if(userPoints > croupierPoints || croupierPoints > 21){
                System.out.println(this.user.getUsername()+" wins");
                this.user.win(userPoints);
            }else if(userPoints == croupierPoints){
                System.out.println("Draw");
                this.user.draw();
            }else{
                System.out.println(this.user.getUsername()+" looses");
            }
        }else{
            System.out.println(this.user.getUsername()+" looses");
        }
    }

    public void userRound(List<Card> hand){
        boolean hasAMove;
        char move;
        do {//moves on main hand
            System.out.println("-------------------------------");
            this.showInfo(hand);
            move = this.user.askForMove(deck, hand);
            hasAMove = this.user.hasMove(deck, hand);
        }while(hasAMove && move != 'S' && move != 'D');
        if(!hasAMove || move == 'D') {
            System.out.println("-------------------------------");
            this.showInfo(hand);
        }
    }

    public void compareUserToCroupier(List<Card> hand, int croupierPoints){
        int userPoints = this.user.sumHand(hand);
        this.showInfo(hand);
        this.checkResult(userPoints, croupierPoints);
    }

    public void run(){
        while(this.user.getMoney()>0) {
            this.user.askBid();
            this.giveCards();
            this.userRound(this.user.getHand());
            if(this.user.isSecondHandActive()){//moves if split on the second hand
                this.user.setMoney(this.user.getMoney()-this.user.getBid());
                this.userRound(this.user.getSecondHand());
            }
            this.croupier.setOnlyFirstCard(false);
            System.out.println("-------------------------------");
            int croupierPoints = this.croupier.makeMove(this.deck, this.croupier.getHand());
            this.compareUserToCroupier(this.user.getHand(), croupierPoints);
            if(this.user.isSecondHandActive()) {
                this.compareUserToCroupier(this.user.getSecondHand(), croupierPoints);
            }
            this.deck.countCards();
            this.clearHands();
        }
        System.out.println("No money left");
        //TODO insurance
    }


}
