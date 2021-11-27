package com.company.game;

import com.company.deck.Deck;
import com.company.players.Croupier;
import com.company.players.User;

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

    public void showInfo(){
        this.croupier.showInfo();
        this.user.showInfo();
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
                System.out.println(this.user.getUsername()+" win");
                this.user.win(userPoints);
            }else if(userPoints == croupierPoints){
                System.out.println("Draw");
                this.user.draw();
            }else{
                System.out.println(this.user.getUsername()+" loose");
            }
        }else{
            System.out.println(this.user.getUsername()+" loose");
        }
    }

    public void run(){
        while(this.user.getMoney()>0) {
            this.user.askBid();
            this.giveCards();
            boolean hasAMove, stand;
            do {
                this.showInfo();
                stand = this.user.askForMove(deck, this.user.getHand());
                hasAMove = this.user.hasMove(deck, this.user.getHand());
            }while(hasAMove && !stand);
            this.croupier.setOnlyFirstCard(false);
            int userPoints = this.user.sumHand(this.user.getHand());
            int croupierPoints = this.croupier.makeMove(this.deck, this.croupier.getHand());
            this.showInfo();
            this.checkResult(userPoints, croupierPoints);
            this.clearHands();
        }
        System.out.println("No money left");
        //TODO split - make class from hand with Cards and bid for this hand
        //TODO cardcounter
        //TODO exceptions handling
        //TODO double
    }


}
