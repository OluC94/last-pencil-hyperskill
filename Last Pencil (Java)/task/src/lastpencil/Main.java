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
        String player1 = scanner.nextLine();

        while (isInvalidPlayerName(players, player1)){
            System.out.println("Choose between " + players[0] + " and " + players[1]);
            player1 = scanner.nextLine();
        }

        String player2 = Objects.equals(player1, players[0]) ? players[1] : players[0];

        String pencils = "|".repeat(Math.max(0, pencilNumber));

        System.out.println(pencils);

        boolean player1turn = true;

        while (!pencils.isEmpty()){
            String currPlayer = player1turn ? player1 : player2;
            System.out.println(currPlayer + "'s turn");

            String inputPencilChoice = scanner.nextLine();
            // input must be string, convert function to use sting

            do {
                while (!isValidPencilChoice(inputPencilChoice)) {
                    System.out.println("Possible values: '1', '2', '3'");
                    inputPencilChoice = scanner.nextLine();
                }

                while(isStringNonNumeric(inputPencilChoice)) {
                    System.out.println("Choice must be numeric");
                    inputPencilChoice = scanner.nextLine();
                }
            } while (!isValidPencilChoice(inputPencilChoice) || isStringNonNumeric(inputPencilChoice));

            int numTaken = Integer.parseInt(inputPencilChoice);

            int targetForRemoval = pencils.length() - numTaken;
            pencils=pencils.substring(0,targetForRemoval);
            System.out.println(pencils);

            player1turn = !player1turn;
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
            if (str.charAt(i) - '0' < 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPencilChoice(String choice){
        return Objects.equals(choice, "1") || Objects.equals(choice, "2") || Objects.equals(choice, "3");
    }
}
