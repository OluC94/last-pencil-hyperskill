import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.*;
import java.util.regex.Pattern;

public class LastPencilTest extends StageTest<String> {

    public LastPencilTest() {
        test_data = new Object[][]{
                {5, 1, new int[]{2, 1}, 2,
                        new String[]{"4", "a", "0", "-1", "_", "|", "|||||"}, 2},
                {20, 1, new int[]{3, 2, 3, 1, 2, 3, 3, 2}, 1,
                        new String[]{"4", "a", "0", "-1", "_", "|", "|||||"}, 2},
                {30, 1, new int[]{3, 2, 3, 1, 2, 3, 3, 3, 2, 3, 3}, 2,
                        new String[]{"4", "a", "0", "-1", "_", "|", "|||||"}, 1},
                {5, 2, new int[]{2, 1}, 2,
                        new String[]{"4", "a", "0", "-1", "_", "|", "|||||"}, 1},
                {20, 2, new int[]{3, 2, 3, 1, 2, 3, 3, 2}, 1,
                        new String[]{"4", "a", "0", "-1", "_", "|", "|||||"}, 1},
                {30, 2, new int[]{3, 2, 3, 1, 2, 3, 3, 3, 2, 3, 3}, 2,
                        new String[]{"4", "a", "0", "-1", "_", "|", "|||||"}, 2},
        };
        data = new String[]{"a", "_", "test", "|", "|||||", " ", "-", "two", "10g", "k5", "-0.2", "0.3"};
    }

    @DynamicTest
    CheckResult checkOutput() { //all comments correspond to 3 parts of tests
        TestedProgram main = new TestedProgram();
        String output = main.start().toLowerCase();
        String[] lines = output.strip().split("\\n");
        if (lines.length != 1 || !Pattern.matches("^(how many pencils would you like to use)\\??:?$", lines[0])) {
            return CheckResult.wrong("When the game starts, it should output only one line asking the user about the " +
                    "amount of pencils they would like to use with the \"How many pencils would you like to use\" string.");
        }

        String output2 = main.execute("1").replaceAll(" ", "");


        String whoWillBeRegex = "^(whowillbethefirst).*";
        String playerChecking = ".*\\([a-zA-Z_0-9]+,[a-zA-Z_0-9]+\\):?";

        if (output2.split("\\n").length != 1) {
            return CheckResult.wrong("When the user replies with the amount of pencils, the game should print 1 non-empty "
                    + "line asking who will be the first player.\n"
                    + output2.split("\\n").length + " lines were found in the output.");
        }
        output2 = output2.toLowerCase().strip();

        if (!Pattern.matches(whoWillBeRegex, output2))
            return CheckResult.wrong("Discrepancy with the task, pay attention to the line " +
                    "\"Who will be the first\"");
        if (!Pattern.matches(playerChecking, output2))
            return CheckResult.wrong("Discrepancy with the task, output example" +
                    " \"Who will be the first (Name1, Name2):\"");
        return CheckResult.correct();
    }

    String[] data;

    @DynamicTest(data = "data")
    CheckResult checkNumericAmount(String inp) {
        TestedProgram main = new TestedProgram();
        main.start();
        String output = main.execute(inp).toLowerCase();

        if (!output.contains("number of pencils") || !output.contains("numeric")) {
            return CheckResult.wrong("When the user provides the number of pencils as a non-numeric sequence, the game should " + "inform the user that their input is incorrect and prompt the user for input again" + " with the \"The number of pencils should be numeric\" string.");
        }
        for (int i = 1; i < 5; i++) {
            if (!output.contains("number of pencils") || !output.contains("numeric")) {
                return CheckResult.wrong("When the user provides the number of pencils as a non-numeric sequence, the game should " + "inform the user that their input is incorrect and prompt the user for input again" + " with the \"The number of pencils should be numeric\" string.");
            }

        }
        return CheckResult.correct();
    }


    @DynamicTest
    CheckResult checkNotZeroAmount() { //the test matches the name (checking positive)
        TestedProgram main = new TestedProgram();
        main.start();
        String output = main.execute("0").toLowerCase();

        if (!output.contains("number of pencils") || !output.contains("positive")) {
            return CheckResult.wrong("When the user provides \"0\" as a number of pencils, the game should " + "inform the user that their input is incorrect and prompt the user for input again" + " with the \"The number of pencils should be positive\" string.");
        }
        for (int i = 1; i < 5; i++) {
            output = main.execute("0").toLowerCase();
            if (!output.contains("number of pencils") || !output.contains("positive")) {
                return CheckResult.wrong("When the user provides \"0\" as a number of pencils, the game should " + "inform the user that their input is incorrect and prompt the user for input again" + " with the \"The number of pencils should be positive\" string.");
            }
        }
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult checkBothAmount() {
        TestedProgram main = new TestedProgram();
        main.start();

        String output = main.execute("0").toLowerCase(); // again, task checking "The number of pencils should be positive"
        if (!output.contains("number of pencils") || !output.contains("positive")) {
            return CheckResult.wrong("When the user provides \"0\" as a number of pencils," +
                    " the game should inform the user that their input is incorrect and prompt " +
                    "the user for input again with the \"The number of pencils should be positive\" string.");
        }

        output = main.execute("a").toLowerCase();// task checking "The number of pencils should be numeric"
        if (!output.contains("number of pencils") || !output.contains("numeric")) {
            return CheckResult.wrong("When the user provides the number of pencils as " +
                    "a non-numeric sequence, the game should inform the user that their " +
                    "input is incorrect and prompt the user for input again with the " +
                    "\"The number of pencils should be numeric\" string.");
        }

        output = main.execute("0").toLowerCase(); // checking positive in loop
        if (!output.contains("number of pencils") || !output.contains("positive")) {
            return CheckResult.wrong("When the user provides \"0\" as a number of pencils," +
                    " the game should inform the user that their input is incorrect and" +
                    " prompt the user for input again with the \"The number of pencils " +
                    "should be positive\" string.");
        }
        String output2 = main.execute("1").replaceAll(" ", "").strip();
        if (!Pattern.matches(".*\\([a-zA-Z_0-9]+,[a-zA-Z_0-9]+\\):?", output2)) { // checking task conditions after correct pencils choice "who will be the first player"
            return CheckResult.wrong("When the user inputs the number of pencils correctly," +
                    " the game should ask who will be the first player ending with the \"(\"Name1\", \"Name2\")\" string.");
        }

        return CheckResult.correct();
    }

    Object[][] test_data;// more extensive array for tests


    @DynamicTest(data = "test_data")
    CheckResult checkResult(int amount, int first, int[] moves, int last, String[] incorrect, int winner) {
// amount - number of pencils, first - player's turn, moves - number of operations, last - who was the last, incorrect - chars arrays for testing, winner - this winner ^_^
        TestedProgram main = new TestedProgram();
        main.start();
        String output = main.execute(String.valueOf(amount));
        output = output.replace(" ", "");

        if (!output.toLowerCase().contains("who") || !output.toLowerCase().contains("first")) { //Checking who first
            return CheckResult.wrong("The game should ask the user to input the player that goes first.");
        }
        // Getting names
        String leftName = output.substring(output.lastIndexOf('(') + 1, output.lastIndexOf(','));
        String rightName = output.substring(output.lastIndexOf(',') + 1, output.lastIndexOf(')'));
        // Checking names
        if (leftName.equals(rightName)) { //Checking player name1 != name2
            return CheckResult.wrong("The names of the players must be different," +
                    " lines were found in the output: Name1 - \""
                    + leftName + "\" Name2 - \"" + rightName + "\".");
        }
        String prevPlayer, nextPlayer;
        if (first == 1) {
            prevPlayer = leftName;
            nextPlayer = rightName;
        } else {
            prevPlayer = rightName;
            nextPlayer = leftName;
        }

        String output2 = main.execute(leftName + rightName).toLowerCase();

        String format = String.format("When the user provides a name that is not '%s' or '%s'," +
                " the game should inform the user that their input is incorrect " +
                "and prompt the user for input again with the " +
                "\"Choose between '%s' and '%s'\" string.", leftName, rightName, leftName, rightName);
        if (!output2.contains("choose between") || !output2.contains(leftName.toLowerCase()) || !output2.contains(rightName.toLowerCase())) {
            return CheckResult.wrong(format); // Checking task "choose between"
        }
        for (int j = 1; j < 5; j++) { //Checking for loop stability
            output2 = main.execute(leftName + rightName).toLowerCase();
            if (!output2.contains("choose between") || !output2.contains(leftName.toLowerCase()) || !output2.contains(rightName.toLowerCase())) {
                return CheckResult.wrong(format);
            }
        }
        //nonempty lines checking
        String output3 = main.execute(prevPlayer).toLowerCase();
        String[] lines = output3.strip().split("\\r?\\n");
        List<String> linesNonEmpty = Arrays.stream(lines).filter(s -> s.length() != 0).toList();
        if (linesNonEmpty.size() != 2) { // checking a start task, program should print 2 non-empty lines
            return CheckResult.wrong("When the player provides the initial game conditions, your program should print 2 non-empty lines:\n"
                    + "one with with vertical bar symbols representing the number of pencils, "
                    + "the other with the \"*NameX* turn\" " +
                    "string.\n" + String.format("%d lines were found in the output.", linesNonEmpty.size()));
        }
        //checking lines with pencils
        String[] checkPencils = Arrays.stream(lines).filter(s -> s.contains("|")).toArray(String[]::new);

        if (checkPencils.length == 0) { // checking initial game
            return CheckResult.wrong("When the player provides the initial game conditions, your program should print one line with several vertical bar symbols ('|') representing pencils.");
        }
        if (checkPencils.length > 1) { // checking if lines with pencils > 1
            return CheckResult.wrong("When the player provides the game initial conditions, your program should print only one line with several vertical bar symbols ('|') representing pencils.");
        }
        if (checkPencils[0].chars().distinct().count() != 1) { // checking any chars except |
            return CheckResult.wrong("The line with pencils should not contain any symbols other than the '|' symbol.");
        }


        if (checkPencils[0].length() != amount) { // checking matching the entered characters and the characters on the output
            return CheckResult.wrong("The line with pencils should contain as many '|' symbols as the player provided.");
        }


        if (!lines[1].matches(prevPlayer + "|.+turn!?$")) { //checking player and task "turn"
            return CheckResult.wrong("When the player provides the correct initial game conditions"
                    + " there should be a line in the output for "
                    + prevPlayer + "'s turn that contains " + "\""
                    + prevPlayer + "\" and \"turn\" substrings if '"
                    + prevPlayer + "' is the " + "first player.");
        }


        int onTable = amount;
//start checking task  possible values 1 2 3
        for (var i : moves) {
            for (String j : incorrect) {
                output = main.execute(j).toLowerCase();
                if (!output.contains("possible values") || !output.contains("1") || !output.contains("2") || !output.contains("3")) { // checking "Possible values: '1', '2', '3'" and re-entry requirement
                    return CheckResult.wrong("If the player enters values different from '1', '2', or '3'," + " the game should inform the user that " +
                            "their input is incorrect and prompt the user for input again" + " with the \"Possible values: '1', '2', '3'\" string.");
                }
                if (!output.toLowerCase().contains(prevPlayer.toLowerCase()) && output.toLowerCase().contains(nextPlayer.toLowerCase())) {// check in case the request for re-entry has not been received
                    return CheckResult.wrong("When " + prevPlayer + " provides values different from '1', '2', or '3'," + " you need to prompt "
                            + prevPlayer + " for input again.\n" + "However, the " + nextPlayer + "'s name was found in your output.");
                }
            }

            onTable -= i;
            output = main.execute(String.valueOf(i)).toLowerCase();
        }// nonempty checking
        lines = output.trim().split("\n");
        linesNonEmpty = List.of(Arrays.stream(lines).toArray(String[]::new));


        if (linesNonEmpty.size() != 2) {// checking after player choice "if != 2 nonempty lines"
            return CheckResult.wrong("When one of the players enters the number of pencils they want to remove," + " the program should print 2 non-empty lines.");
        }

        checkPencils = Arrays.stream(lines).filter(s -> s.contains("|")).toArray(String[]::new);
        if (checkPencils.length == 0) {//checking pencils in lines
            return CheckResult.wrong("When one of the players enters the amount of pencils they want to remove, your program should print one line with vertical bar symbols ('|') representing pencils.");
        }
        if (checkPencils.length > 1) {//checking if lines with pencils > 1 after player choice
            return CheckResult.wrong("When one of the players enters the amount of pencils they want to remove" + ", your program should print only one line with vertical bar symbols ('|') representing pencils.");
        }
        if (checkPencils[0].chars().distinct().count() != 1) {//checking any chars except |
            return CheckResult.wrong("The line with pencils should not contain any symbols other than the '|' symbol.");
        }
        if (checkPencils[0].length() != onTable) {//checking pencils in lines after player choice
            return CheckResult.wrong("When one of the players enters the amount of pencils they want to remove, " + "the line with pencils should contain as many '|' symbols as there are pencils left.");
        }

        if (!lines[1].matches(nextPlayer + "|.+turn!?$")) { // add regex checking player and turn
            return CheckResult.wrong(String.format("When %s enters the amount of pencils they want to remove"
                    + " there should be a line in output that contains \"%s turn\".", prevPlayer, nextPlayer));
        }

        output = main.execute(Integer.toString(last + 1)).toLowerCase();
        if (!output.contains("too many") || !output.contains("pencils")) { // checking condition if player choice pencils  "1 > | < 3"
            return CheckResult.wrong("If the player enters the number of pencils that is greater than the current " + "number of pencils on the table, the game should inform the user that " + "their input is incorrect and prompt the user for input again " + "with the \"too many pencils\" string.");
        }

        output = main.execute(String.valueOf(last)).toLowerCase();
        lines = output.trim().split("\\n");
//start getWinner checking
        linesNonEmpty = List.of(Arrays.stream(lines).filter(s -> s.length() != 0).toArray(String[]::new));
        String winnerName = "";
        if (winner == 1) {
            winnerName = leftName;
        }
        if (winner == 2) {
            winnerName = rightName;
        }
        if (linesNonEmpty.size() != 1 || !linesNonEmpty.get(0).toLowerCase().contains(winnerName.toLowerCase()) || (!linesNonEmpty.get(0).contains("win") && !linesNonEmpty.get(0).contains("won"))) {
            if (linesNonEmpty.size() >= 1) {// checking if winner choices not correct
                if (!linesNonEmpty.get(0).toLowerCase().contains(winnerName.toLowerCase()) && (linesNonEmpty.get(0).contains("win") || linesNonEmpty.get(0).contains("won"))) {
                    return CheckResult.wrong("Make sure you determined the winner of the game correctly.\n" + "The player who takes the last pencil loses the game.");
                }
            }//checking correct writing lines with winner
            return CheckResult.wrong("When the last pencil is taken, the program should print one line that informs " + "who is the winner in this game with \"*Name*\" and \"win\"/\"won\" strings.");
        }
        if (!main.isFinished()) {//checking output after the game is finished
            return CheckResult.wrong("Your program should not request anything when there are no pencils left.");
        }
        return CheckResult.correct();
    }
}
