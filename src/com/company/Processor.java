package com.company;

import java.util.List;
import java.util.Stack;

public class Processor {
    //---------------------//
    public static int eax, ebx, ecx, edx;
    public static Stack stack = new Stack();
    //---------------------//

    public static void printData(){
        StringBuilder procesorData = new StringBuilder();

        procesorData.append("//---------------------//\n");
        procesorData.append("Eax: ");
        procesorData.append(eax);
        procesorData.append("\n");
        procesorData.append("Ebx: ");
        procesorData.append(ebx);
        procesorData.append("\n");
        procesorData.append("Ecx: ");
        procesorData.append(ecx);
        procesorData.append("\n");
        procesorData.append("Edx: ");
        procesorData.append(edx);
        procesorData.append("\n");
        procesorData.append("Stack: ");
        procesorData.append(stack.size());
        procesorData.append(" elements.");
        procesorData.append("\n");
        procesorData.append("//---------------------//\n");

        System.out.println(procesorData);
    }
}
