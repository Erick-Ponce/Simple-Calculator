/*
Author: Erick Ponce 
Program: DukeCalc 
Date: May 30, 2018
Description: Simple calculator to allow the user to input a formula and output 
the correct answer 
 */
package Homework2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class DukeCalc extends Application {

    //controls
    TextArea taFormula = new TextArea();
    TextArea taOutput = new TextArea();
    Button btn1 = new Button(" 1 ");
    Button btn2 = new Button(" 2 ");
    Button btn3 = new Button(" 3 ");
    Button btn4 = new Button(" 4 ");
    Button btn5 = new Button(" 5 ");
    Button btn6 = new Button(" 6 ");
    Button btn7 = new Button(" 7 ");
    Button btn8 = new Button(" 8 ");
    Button btn9 = new Button(" 9 ");
    Button btn0 = new Button("      0      ");
    //Control to leave a blank button for formatting purposes 
    Button btnBlank = new Button("    ");
    Button btnEqual = new Button(" = ");
    Button btnPlus = new Button("   +  ");
    Button btnSub = new Button("   -  ");
    Button btnDivide = new Button("   /   ");
    Button btnMulti = new Button("   *  ");
    //new button that performs modular division
    Button btnMod = new Button("  %  ");
    Button btnSave = new Button("Save");
    Button btnLoad = new Button("Load");
    Button btnClear = new Button("   Clear  ");
    Button btnExit = new Button("Exit");
    int calculation;

    @Override
    public void start(Stage primaryStage) {
        GridPane primaryPane = new GridPane();

        //add controls to pane
        primaryPane.add(taFormula, 0, 0, 4, 1);
        primaryPane.add(btn1, 0, 3);
        primaryPane.add(btn4, 0, 2);
        primaryPane.add(btn7, 0, 1);
        primaryPane.add(btn0, 0, 4, 3, 1);
        primaryPane.add(btn2, 1, 3);
        primaryPane.add(btn5, 1, 2);
        primaryPane.add(btn8, 1, 1);
        primaryPane.add(btn3, 2, 3);
        primaryPane.add(btn6, 2, 2);
        primaryPane.add(btn9, 2, 1);
        primaryPane.add(btnBlank, 2, 4);
        primaryPane.add(btnEqual, 2, 5);
        primaryPane.add(btnPlus, 3, 4);
        primaryPane.add(btnSub, 3, 3);
        primaryPane.add(btnDivide, 3, 1);
        primaryPane.add(btnMulti, 3, 2);
        primaryPane.add(btnMod, 3, 5);
        primaryPane.add(taOutput, 5, 0, 1, 6);
        primaryPane.add(btnSave, 5, 6);
        primaryPane.add(btnLoad, 5, 7);
        primaryPane.add(btnClear, 0, 5, 3, 1);
        primaryPane.add(btnExit, 0, 9, 3, 1);

        //resize the text areas
        taFormula.setMaxHeight(20);
        taFormula.setMaxWidth(146);
        taFormula.setEditable(false);
        taOutput.setMaxWidth(260);
        taOutput.setEditable(false);

        //set scene 
        Scene primaryScene = new Scene(primaryPane, 450, 270);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("DukeCalc v 0.1");
        primaryStage.show();

        //event handlers to display text to the text area
        btn1.setOnAction(e -> {
            taFormula.appendText("1");
        });
        btn2.setOnAction(e -> {
            taFormula.appendText("2");
        });
        btn3.setOnAction(e -> {
            taFormula.appendText("3");
        });
        btn4.setOnAction(e -> {
            taFormula.appendText("4");
        });
        btn5.setOnAction(e -> {
            taFormula.appendText("5");
        });
        btn6.setOnAction(e -> {
            taFormula.appendText("6");
        });
        btn7.setOnAction(e -> {
            taFormula.appendText("7");
        });
        btn8.setOnAction(e -> {
            taFormula.appendText("8");
        });
        btn9.setOnAction(e -> {
            taFormula.appendText("9");
        });
        btn0.setOnAction(e -> {
            taFormula.appendText("0");
        });
        btnPlus.setOnAction(e -> {
            taFormula.appendText("+");
        });
        btnSub.setOnAction(e -> {
            taFormula.appendText("-");
        });
        btnDivide.setOnAction(e -> {
            taFormula.appendText("/");
        });
        btnMulti.setOnAction(e -> {
            taFormula.appendText("*");
        });
        //new function to do modular division
        btnMod.setOnAction(e -> {
            taFormula.appendText("%");
        });
        btnEqual.setOnAction(e -> {
            try {
                String output = taFormula.getText();
                calculation = calculate(output);
                taOutput.appendText(taFormula.getText() + "=" + calculation + "\n");
                taFormula.clear();
            } catch (EmptyStackException i) {

                String output = taFormula.getText();
                //checks to see if the formula entered ends correctly or if the 
                //text area is empty 
                if (output.isEmpty()) {
                    Alert userPrompt = new Alert(Alert.AlertType.ERROR, "Please fill out completely!", ButtonType.CLOSE);
                    userPrompt.show();
                } else if (output.endsWith("+") || output.endsWith("-")
                        || output.endsWith("*") || output.endsWith("/") || output.endsWith("%")) {
                    //alerts the user that the formula entered needs to be re-entered
                    Alert userPrompt = new Alert(Alert.AlertType.ERROR, "Please fill out completely!", ButtonType.CLOSE);
                    userPrompt.show();
                }
                //clears the formula text area so that the formula can be re-entered
                taFormula.clear();
            }
        });

        btnSave.setOnAction(e -> {
            try {
                //writes the data from the text area to a binary file
                String output = taOutput.getText();
                DataOutputStream calcOutput = new DataOutputStream(new FileOutputStream(("ticker.dat")));
                while (true) {
                    calcOutput.writeUTF(taOutput.getText());

                    calcOutput.close();
                }

            } catch (IOException ioex) {
                System.out.println(ioex.toString());
            }
        });
        btnLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    //reads the data that was written to the binary file and appends the text
                    //to the text area
                    DataInputStream calcInput = new DataInputStream(new FileInputStream(("ticker.dat")));
                    taOutput.setText(calcInput.readUTF());
                    calcInput.close();
                } catch (IOException ioex) {
                    System.out.println(ioex.toString());
                }
            }
        });
        btnClear.setOnAction(e -> {
            taFormula.clear();
        });
        btnExit.setOnAction(e -> {
            System.exit(0);
        });
    }

    public static int calculate(String output) {
        Stack<Integer> intStack = new Stack<>();
        Stack<Character> charStack = new Stack<>();

        String blanks = "";
        //inserts blanks to allow for the calculation of a formula 
        for (int i = 0; i < output.length(); i++) {
            if (output.charAt(i) == '+' || output.charAt(i) == '-'
                    || output.charAt(i) == '*' || output.charAt(i) == '/' || output.charAt(i) == '%') {
                blanks += " " + output.charAt(i) + " ";
            } else {
                blanks += output.charAt(i);
            }
        }
        //split the array 
        String[] formula = blanks.split(" ");

        for (String s : formula) {
            if (s.length() == 0) {
            } else if (s.charAt(0) == '+' || s.charAt(0) == '-') {
                //searches for a + or - operator and if found performs the necessary calcualtion (until its not found)
                while (!charStack.isEmpty() && (charStack.peek() == '+' || charStack.peek() == '-')) {
                    char operator = charStack.pop();
                    int num1 = intStack.pop();
                    int num2 = intStack.pop();
                    if (operator == '+') {
                        intStack.push(num1 + num2);
                    } else if (operator == '-') {
                        intStack.push(num2 - num1);
                    }
                }
                charStack.push(s.charAt(0));

            } else if (s.charAt(0) == '*' || s.charAt(0) == '/') {
                //searches for a * or / operator and if found performs the necessary calculation (until its not found)
                while (!charStack.isEmpty() && (charStack.peek() == '*' || charStack.peek() == '/')) {
                    char operator = charStack.pop();
                    int num1 = intStack.pop();
                    int num2 = intStack.pop();
                    if (operator == '*') {
                        intStack.push(num1 * num2);
                    } else if (operator == '/') {
                        intStack.push(num2 / num1);
                    }
                }
                charStack.push(s.charAt(0));
            } else if (s.charAt(0) == '%') {
                //seaches for a % operator and if found performs the necessary calculation (until its not found) 
                while (!charStack.isEmpty() && charStack.peek() == '%') {
                    char operator = charStack.pop();
                    int num1 = intStack.pop();
                    int num2 = intStack.pop();
                    if (operator == '%') {
                        intStack.push(num2 % num1);
                    }
                }
                charStack.push(s.charAt(0));
            } else {
                intStack.push(new Integer(s));
            }
        }
        while (!charStack.isEmpty()) {
            //calculate the answer
            char operator = charStack.pop();
            int num1 = intStack.pop();
            int num2 = intStack.pop();
            switch (operator) {
                case '+':
                    intStack.push(num1 + num2);
                    break;
                case '-':
                    intStack.push(num2 - num1);
                    break;
                case '*':
                    intStack.push(num1 * num2);
                    break;
                case '/':
                    intStack.push(num2 / num1);
                    break;
                //new function to calculate the remainder of a division problem
                case '%':
                    intStack.push(num2 % num1);
                    break;
                default:
                    break;
            }
        }
        return intStack.pop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
