package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        TextInput textToOperate = new TextInput();

        Processor.printData();

        Scanner input = new Scanner(System.in);

            while (input.hasNextLine()) {
                try {
                    if(textToOperate.setText(input.nextLine())) {
                        if (!textToOperate.command.Execute(textToOperate.firstExpression, textToOperate.secondExpression)) {
                            System.out.println("Error");
                        }
                    }
                }
                catch(Exception ex) {
                    System.out.println("Error " + ex.getMessage());
                }
            }
    }
}
