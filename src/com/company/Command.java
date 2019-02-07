package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    public String text;

    Command(String text) {
        this.text = text;
    }

    public boolean Execute(String firstExpression, String secondExpression) {

        if (this.text.equals("mov")) {
            return (checkingCorrectDataNames(firstExpression,secondExpression) && movA(firstExpression, secondExpression));
        }
        else if(this.text.equals("push")) {
            return (checkingCorrectDataNames(firstExpression,secondExpression) && pushA(firstExpression));
        }
        else if (this.text.equals("int")) {
            return intA(firstExpression);
        }
        else if(this.text.equals("xor")) {
            return (checkingCorrectDataNames(firstExpression,secondExpression) && xorA(firstExpression, secondExpression));
        }
        else {
            return false;
        }
    }

    private boolean intA(String firstExpression) {
        String pattern = "0x80";
        Matcher firstMatcher = Pattern.compile(pattern).matcher(firstExpression);
        String firstExpressionEdit = "0";
        if(firstMatcher.find()){
           firstExpressionEdit = firstMatcher.group(0);
        }
        if (firstExpressionEdit.matches("0x80")) {
            try {
                System.out.println("Stack last value: " + Processor.stack.pop());
            }
            catch (Exception ex){
                System.out.println("Stack last value: ?");
            }
            return true;
        } else
            return false;
    }

    private boolean pushA(String firstExpression) {
        int editedFirstExpression = firstExpressionWork(firstExpression);

        Processor.stack.push(editedFirstExpression);
        return true;
    }

    private boolean xorA(String firstExpression, String secondExpression) {
        int editedFirstExpression = firstExpressionWork(firstExpression);
        int editedSecondExpression = 0;

        String pattern = "(?<=\\%)(...)";
        Matcher matcher = Pattern.compile(pattern).matcher(secondExpression);

        if(matcher.find()){
            editedSecondExpression = Integer.parseInt(processorDataToValues(matcher.group(0)));
            setData(matcher.group(0),(editedFirstExpression ^ editedSecondExpression));
            return true;
        }
        return false;
    }

    private void setData(String text, int i) {
        if (text.equals("eax"))
            Processor.eax = i;
        else if (text.equals("ebx"))
            Processor.ebx = i;
        else if (text.equals("ecx"))
            Processor.ecx = i;
        else if (text.equals("edx"))
            Processor.edx = i;
    }

    private boolean movA(String firstExpression, String secondExpression) {
        int editedFirstExpression = firstExpressionWork(firstExpression);

        String pattern = "(?<=\\%)(...)";
        Matcher matcher = Pattern.compile(pattern).matcher(secondExpression);

        if(matcher.find()){
            setData(matcher.group(0),editedFirstExpression);
            return true;
        }
        return false;
    }

    public int firstExpressionWork(String firstExpression) {
        List<String> textList = new ArrayList();
        String pattern = "(\\((.*)\\))|(?<=\\%)(...)|\\+|\\*|\\\\|\\-|\\d*";
        Matcher matcher = Pattern.compile(pattern).matcher(firstExpression);

        while (matcher.find()) {
            if(!(matcher.group(0).length()==0))
            textList.add(matcher.group(0));
        }
        textList = calculateOtherExpresions(textList);

        return calculateExpression(textList);


    }

    private List<String> calculateOtherExpresions(List<String> textList) {
        for (int i = 0; i < (textList.size()); i++) {
            String pattern = "((?<=\\()(.*)(?=\\)))";
            Matcher matcher = Pattern.compile(pattern).matcher(textList.get(i));

            if (matcher.find()) {
                textList.set(i, Integer.toString(firstExpressionWork(matcher.group(0))));
            }
        }
        return textList;

    }

    private int calculateExpression(List<String> textList) {
        for (int i = 0; i < textList.size(); i++) {
            textList.set(i, processorDataToValues(textList.get(i)));
        }

        for (int i = 1; i < (textList.size() - 1); i++) {
            if (textList.get(i).equals("*")) {
                textList.set(i - 1,Integer.toString(Integer.parseInt(textList.get(i - 1)) * Integer.parseInt(textList.get(i + 1))));
                textList.remove(i + 1);
                textList.remove(i);
            } else if (textList.get(i).equals("*")) {
                textList.set(i - 1,Integer.toString(Integer.parseInt(textList.get(i - 1)) / Integer.parseInt(textList.get(i + 1))));
                textList.remove(i + 1);
                textList.remove(i);
            }
        }
        int firstExpressionInt = 0 + Integer.parseInt(textList.get(0));
        for (int i = 1; i <= (textList.size() - 1); i++) {
            if (textList.get(i).equals("+")) {
                firstExpressionInt = firstExpressionInt + Integer.parseInt(textList.get(i + 1));

            } else if (textList.get(i).equals("-")) {
                firstExpressionInt = firstExpressionInt - Integer.parseInt(textList.get(i + 1));
            }
        }
        return firstExpressionInt;
    }

    private String processorDataToValues(String text) {
        if (text.equals("eax"))
            return Integer.toString(Processor.eax);
        else if (text.equals("ebx"))
            return Integer.toString(Processor.ebx);
        else if (text.equals("ecx"))
            return Integer.toString(Processor.ecx);
        else if (text.equals("edx"))
            return Integer.toString(Processor.edx);
        else
            return text;
    }

    private boolean checkingCorrectDataNames(String firstExpression, String secondExpression){
        String pattern = "x[\\w%()]+";
        Matcher firstMatcher = Pattern.compile(pattern).matcher(firstExpression);

        if(firstMatcher.find()){
            return false;
        }

        pattern = "x[\\w%()]+|(\\d)";
        Matcher secondMatcher = Pattern.compile(pattern).matcher(secondExpression);

        if(secondMatcher.find()){
            return false;
        }

        return true;
    }
}

