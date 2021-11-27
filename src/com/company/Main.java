package com.company;

import com.company.game.Game;
import com.company.players.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String username="";
        String money="", numOfSets="", message;
        boolean correctMoney;
        System.out.println("Your username: ");
        username = scanner.next();
        do {
            System.out.println("Money You've got: ");
            money = scanner.next();
            message = "Write only numbers and without spaces!";
            correctMoney = isANumber(money, message);
        }while(!correctMoney);
        User user = new User(username, Integer.parseInt(money));
        do {
            System.out.println("How many card sets you want to play with (4-8): ");
            numOfSets = scanner.next();
            message = "Write number 4/5/6/7/8";
            correctMoney = isANumber(numOfSets, message);
        }while(!correctMoney || !(Integer.parseInt(numOfSets)>=4 && Integer.parseInt(numOfSets)<=8));
        Game game = new Game(user, Integer.parseInt(numOfSets));

        game.run();
    }

    public static boolean isANumber(String input, String message){
        try{
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException ex){
            System.out.println(message);
            return false;
        }
    }
}