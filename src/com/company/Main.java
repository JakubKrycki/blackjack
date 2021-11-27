package com.company;

import com.company.game.Game;
import com.company.players.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String username="";
        int money=0, numOfSets=0;

        System.out.println("Your username: ");
        username = scanner.next();
        System.out.println("Money You've got: ");
        money = scanner.nextInt();

        User user = new User(username, money);
        do {
            System.out.println("How many card sets you want to play with (4-8): ");
            numOfSets = scanner.nextInt();
        }while(!(numOfSets>=4 && numOfSets<=8));
        Game game = new Game(user, numOfSets);

        game.run();
    }
}
