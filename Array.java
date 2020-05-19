/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

// Ambrose Ryan Xavier

import java.util.Random;

/**
 *
 * @author ARZavier
 */
public class Array {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Random rand = new Random();
        int randNumber;
        int integerArray[] = new int[10];
        
        System.out.println("\nThe Integers In Order: ");
        for (int i = 0; i < 10; i++){
            randNumber = rand.nextInt(10) + 1;
            integerArray[i] = randNumber;
            System.out.println(integerArray[i]);
        }
        for (int i = integerArray.length + 1; i <= 10; i++){
            System.out.println(integerArray[i] + " ");
        }
        System.out.println("\nThe Integers In Reverse Order: ");
        for (int i = integerArray.length - 1; i >= 0; i--){
            System.out.println(integerArray[i] + " ");
        }
        
        double firstMax = integerArray[0];
        double secondMax = firstMax;
        
        for (int i = 1; i < integerArray.length; i++){
            if (integerArray[i] > firstMax){
                secondMax = firstMax;
                firstMax = integerArray[i];
            }
        }
        System.out.println("The second highest value in the array is: " + secondMax);
        
        int sum = 0;
        for (int i = 0; i < integerArray.length; i++){
            sum = sum + integerArray[i];
        }
        double average = ((double)sum)/integerArray.length;
        System.out.println("\nThe average is: " + average);
        
        for (int i = 0; i < integerArray.length; i++){
            for (int j = 0; j < integerArray[i]; j++){
                System.out.print("*");
            }
            System.out.println();
        }
        
        int intArray[] = new int[10];
        for (int i = 0; i < intArray.length; i++){
            for (int j = 0; j < intArray.length; j++){
                if (intArray[i] == integerArray[i]){
                    break;
                }
                else {
                    intArray[i] = integerArray[i];
                }
            }
        }
        
        for (int i = 0; i < intArray.length; i++){
            System.out.println(intArray[i]);
        }
        for (int i = 0; i < integerArray.length; i++){
            int count = 0;
            for (int j = 0; j < integerArray.length; j++){
                if (integerArray[i] == integerArray[j]){
                    count++;
                }
            }
            System.out.println("Frequency of " + integerArray[i] + " is " + count + ".\n");
        }
    }
}