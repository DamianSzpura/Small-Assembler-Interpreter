package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TextInput {
    private String fullText;

    public Command command;
    public String firstExpression;
    public String secondExpression;

    TextInput(){
        this.command = new Command("Empty.");
        this.firstExpression = "Empty.";
        this.secondExpression = "Empty.";
    }

    boolean setText(String fullText){
        this.fullText = fullText;
        this.firstExpression = "Empty.";
        this.secondExpression = "Empty.";
        return exceptionHandling();
    }

    private boolean exceptionHandling(){
        int comma = countCommas();

        if(comma>1||comma<0) {
            return false;
        }
        else {
            workWithText(comma);
            return true;
        }

    }

    private int countCommas(){
        String pattern=",";
        Matcher matcher = Pattern.compile(pattern).matcher(this.fullText);

        if(matcher.find())
            return matcher.group(0).length();
        else
            return 0;
    }

    private void workWithText(int comma) {
        List<String> textList = new ArrayList();
        textList = groupText(textList, comma);

        switch(comma){
            case 0:
                setCommand(textList.get(0));
                setFirstExpression(textList.get(1));
                break;

            case 1:
                setCommand(textList.get(0));
                setFirstExpression(textList.get(1));
                setSecondExpression(textList.get(2));
                break;
        }
    }

    private List<String> groupText(List<String> textList, int comma) {
        String pattern = "[^ď»ż]\\w*[^\\s]";
        Matcher matcher = Pattern.compile(pattern).matcher(this.fullText);

        if(matcher.find()) {
            textList.add(matcher.group(0));
        }

        if(comma>0) {
            pattern = "(?<=\\s)(.*?)(?=\\,)";
            matcher = Pattern.compile(pattern).matcher(this.fullText);

            if (matcher.find()) {
                textList.add(matcher.group(0));
            }

            pattern = "(?<=\\,\\s)(.*)";
            matcher = Pattern.compile(pattern).matcher(this.fullText);

            if (matcher.find()) {
                textList.add(matcher.group(0));
            }
        }
        else
        {
            pattern = "(?<=\\s)(.*)";
            matcher = Pattern.compile(pattern).matcher(this.fullText);

            if (matcher.find()) {
                textList.add(matcher.group(0));
            }
        }

        return textList;
    }

    private void setCommand(String text){
        this.command.text = text;
    }

    private void setFirstExpression(String text) {
        this.firstExpression = text;
    }

    private void setSecondExpression(String text) {
        this.secondExpression = text;
    }
}
