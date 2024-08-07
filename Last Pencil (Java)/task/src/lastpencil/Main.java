package lastpencil;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many pencils would you like to use?");

        int pencilNumber;

        String initialPencilInput = scanner.nextLine();

        while (initialPencilInput.isEmpty()){
            System.out.println("The number of pencils should be numeric");
            initialPencilInput = scanner.nextLine();
        }

            do {
                while (isStringNonNumeric(initialPencilInput)){
                    System.out.println("The number of pencils should be numeric");
                    initialPencilInput = scanner.nextLine();
                }

                while (isStringNegative(initialPencilInput)){
                    System.out.println("The number of pencils should be positive");
                    initialPencilInput = scanner.nextLine();
                }


            } while (isStringNonNumeric(initialPencilInput) || isStringNegative(initialPencilInput));

            pencilNumber = Integer.parseInt(initialPencilInput);


        System.out.println("Who will be the first (Dante, Vergil)");
        String[] players = "Dante, Vergil".split(", ");
        String firstPlayer = scanner.nextLine();

        while (isInvalidPlayerName(players, firstPlayer)){
            System.out.println("Choose between " + players[0] + " and " + players[1]);
            firstPlayer = scanner.nextLine();
        }

        String secondPlayer = Objects.equals(firstPlayer, players[0]) ? players[1] : players[0];

        String pencils = "|".repeat(Math.max(0, pencilNumber));


        // currPlayer = first player
        // if cuuPlater is Vergil, it is the bot's turn
        boolean humanTurn = Objects.equals(firstPlayer, "Dante");
        String currPlayer = firstPlayer;

        while (!pencils.isEmpty()) {

            System.out.println(pencils);

            System.out.println(currPlayer + "'s turn");

            String inputPencilChoice;

            if (Objects.equals(currPlayer, "Dante")) {
                inputPencilChoice = scanner.nextLine();
                do {
                    while (isInvalidPencilChoice(inputPencilChoice)) {
                        System.out.println("Possible values: '1', '2', '3'");
                        inputPencilChoice = scanner.nextLine();
                    }

                    while (isStringNonNumeric(inputPencilChoice)) {
                        System.out.println("Choice must be numeric");
                        inputPencilChoice = scanner.nextLine();
                    }

                    while (isChoiceTooLarge(pencils.length(), inputPencilChoice)) {
                        System.out.println("Too many pencils were taken");
                        inputPencilChoice = scanner.nextLine();
                    }

                } while (isInvalidPencilChoice(inputPencilChoice) || isStringNonNumeric(inputPencilChoice));

            } else {
                int currentSize = pencils.length();

                if (botTakesThree(currentSize)){
                    inputPencilChoice = "3";
                } else if (botTakesTwo(currentSize)) {
                    inputPencilChoice = "2";
                } else if (botTakesOne(currentSize) || currentSize == 1) {
                    inputPencilChoice = "1";
                } else {
                    String[] botChoices = {"1", "2", "3"};
                    int randIndex = (int) (Math.random() * botChoices.length);
                    inputPencilChoice = botChoices[randIndex];
                }
                System.out.println(inputPencilChoice);
            }

            int numTaken = Integer.parseInt(inputPencilChoice);

            int targetForRemoval = pencils.length() - numTaken;
            pencils=pencils.substring(0,targetForRemoval);

            if (pencils.isEmpty()){
                String winner = Objects.equals(currPlayer, secondPlayer) ? firstPlayer : secondPlayer;
                System.out.println(winner + " won");
            }
            humanTurn = !humanTurn;

            if (currPlayer.equals(firstPlayer)){
                currPlayer = secondPlayer;
            } else {
                currPlayer = firstPlayer;
            }
        }
    }

    public static boolean isInvalidPlayerName(String[] list, String str) {
        for (String s : list) {
            if (s.equals(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStringNonNumeric(String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean isStringNegative(String str){
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) - '0' < 1 && i < 1) {
                System.out.println(str.charAt(i) + " + " + (str.charAt(i) - '0'));
                return true;
            }
        }
        return false;
    }

    public static boolean isInvalidPencilChoice(String choice){
        return !Objects.equals(choice, "1") && !Objects.equals(choice, "2") && !Objects.equals(choice, "3");
    }

    public static boolean isChoiceTooLarge(int total, String choice){
        int numChoice = Integer.parseInt(choice);
        return numChoice > total;
    }

    public static boolean botTakesThree(int stackLength){
        return stackLength % 4 == 0;
    }

    public static boolean botTakesTwo(int stackLength){
        return stackLength % 4 == 3;
    }

    public static boolean botTakesOne(int stackLength){
        return stackLength % 4 == 2;
    }

}
