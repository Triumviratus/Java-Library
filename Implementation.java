/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author ARZavier
 */
public class Implementation {
    
    static int[][][] sums;
    static int attributes;
    static int types;
    static int total;
    
    static double[] totalAcc1 = new double[10];
    static double[] totalAcc2 = new double [10];
    
    static double[] totalF1 = new double[10];
    static double[] totalF2 = new double[10];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ReadWrite r = new ReadWrite();
        /**
         * The variable assignment on line 38
         * lists the files that will be opened.
         */
        String[] files = {"Cancer.data", "Glass.data", "Soybean.data", 
                          "Votes.data", "Iris.data"};
        
        for (String path : files){
            for (int i = 0; i < totalAcc1.length; i++){
                // Resets the Totals
                totalAcc1[i] = 0;
                totalAcc2[i] = 0;
                totalF1[i] = 0;
                totalF2[i] = 0;
            }
            String file = r.readEntireFile(path);
            System.out.println(path + '\n');
            /**
             * Line 50 reads the entire file as a string
             * Line 51 prints the file path to the console so 
             * that we can tell what the output corresponds to
             */
            
            DataPoint.reset();
            doEverything(file);
            
            System.out.println("Control Accuracy: " + 
                                Arrays.toString(totalAcc1));
            System.out.println("Average: " + avg(totalAcc1));
            System.out.print("Standard Deviation: " + 
                              Math.sqrt(variance(totalAcc1)));
            
            System.out.print("Control F-Scores: " + Arrays.toString(totalF1));
            System.out.println("Average: " + avg(totalF1));
            System.out.println("Standard Deviation: " + 
                                Math.sqrt(variance(totalF1)));
            
            System.out.println("Scrambled Accuracy: " + 
                                Arrays.toString(totalAcc2));
            System.out.println("Average: " + avg(totalAcc2));
            System.out.println("Standard Deviation: " + 
                                Math.sqrt(variance(totalAcc2)));
            
            System.out.println("Scrambled F-Scores: " + 
                                Arrays.toString(totalF2));
            System.out.println("Average: " + avg(totalF2));
            System.out.println("Standard Deviation: " + 
                                Math.sqrt(variance(totalF2)));
            System.out.println();
            
            System.out.println("T-Value Accuracy: " +
                                studentT(totalAcc1, totalAcc2));
            System.out.println("T-Value F-Score: " + 
                                studentT(totalF1, totalF2));
            
            System.out.println("******************************************");
        }
        
    }
    
    /**
     * This function is the driver for all the 
     * training and testing for each dataset.
     * @param file the file to be analyzed
     */
    
    private static void doEverything(String file){
        Random rand = new Random();
        String[] lines = file.split("\n"); // Reads each line of the file
        
        DataPoint[] rawData = new DataPoint[lines.length]; // The Data
        DataPoint[] pureData = new DataPoint[lines.length];
        /**
         * The assignment on line 106 stores a pure copy
         * that is never modified.
         */
        
        // Parses the Data
        for (int i = 0; i < lines.length; i++){
            rawData[i] = new DataPoint(lines[i]);
            pureData[i] = new DataPoint(lines[i]);
        }
        
        total = lines.length; // Total Number of Data Points
        
        attributes = rawData[0].attributes.length;
        types = DataPoint.types.size();
        /**
         * The assignment on line 120 is the number of different
         * attributes and the assignment on line 121 is the number
         * of different classes.
         */
        
        System.out.println("Different Types: " + 
                            DataPoint.distinctAttributeValues);
        
        DataPoint[][] data = fold(rawData);
        /**
         * The assignment on line 130 folds the data randomly
         * into 10 folds and the assignment on line 139 obtains
         * an array of the accuracy of each fold.
         */
        
        double[][] temp = trainTest(data);
        double[] accuracy = temp[0];
        double[] fScores = temp[1];
        
        for(int i = 0; i < accuracy.length; i++){
            // Adds the accuracy and f-score to the total values
            totalAcc1[i] += accuracy[i];
            totalF1[i] += fScores[i];
        }
        
        DataPoint[] rawCopy = rawData.clone();
        int numAttributesToShuffle = (int) (0.1 * attributes) + 1;
        
        /**
         * The assignment on line 148 attempts to clone the data (which
         * does not work as points but still points to old DataPoint object)
         * and the assignment on line 149 shuffles 10 percent of the data.
         */
        
        /**
         * The assignment on line 162 holds the columns that have been
         * shuffled so they do not undergo shuffling again.
         */
        
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < numAttributesToShuffle; i++) {
            int ran = rand.nextInt(attributes);
            while(index.contains(ran) || ran < 0 || ran >= attributes) {
                /**
                 * Ascertains that the random
                 * number can be shuffled.
                 */
                ran = rand.nextInt();
            }
            index.add(ran);
            rawCopy = shuffle(rawCopy, ran); // Shuffles the Attributes
        }
        
        DataPoint[][] dataScrambled = fold(rawCopy);
        temp = trainTest(dataScrambled);
        double[] accuracyScrambled = temp[0];
        double[] fScoresScrambled = temp[1];
        
        /**
         * The assignment on line 176 folds the scrambled data
         * and the assignment on line 177 represented the
         * accuracy of the scrambled data.
         */
        
        for (int i = 0; i < accuracyScrambled.length; i++) {
            // adds the accuracy and f-score to totals
            totalAcc2[i] += accuracyScrambled[i];
            totalF2[i] += fScoresScrambled[i];
        }
    }
    
    /**
     * Runs the training and testing of each fold
     * @param data the data to be trained on
     * @return the accuracy of each fold in an array with length 10
     */
    
    private static double[][] trainTest(DataPoint[][] data){
        double[] accuracy = new double[10];
        double[] fScore = new double[10];
        
        int testColumn = 0; // The column utilized for testing the data
        for (int fold = 0; fold < 10; fold++){
            // Loops for k-folds
            sums = new int[types][attributes + 1]
                    [DataPoint.distinctAttributeValues];
            for(int column = 0; column < data.length; column++){
                // Loops over training
                if(column != testColumn){
                    // If it is part of the training set
                    trainColumn(data[column]);
                }
            }
            accuracy[fold] = testAccuracy(data[testColumn]);
            fScore[fold] = testF(data[testColumn]);
            testColumn++;
        }
        return new double[][]{accuracy, fScore};
    }
    
    /**
     * This method, which was utilized to verify the
     * scramble method, tests whether two lists of
     * data are different.
     * @param x first list of data
     * @param y second list of data
     * @return true if the lists are different
     */
    
    private static boolean areDiff(DataPoint[] x, DataPoint[] y){
        for(int i = 0; i < x.length; i++){
            if(!x[i].equals(y[i]))
                return true;
        }
        return false;
    }
    
    /**
     * An implementation of the modern version of the
     * Fisher-Yates shuffle algorithm.
     * @param x array to shuffle
     * @param att which column to shuffle
     * @return the scrambled array (not necessary as the
     * algorithm modifies the DataPoint[] object)
     */
    
    private static DataPoint[] shuffle(DataPoint[] x, int att){
        Random rand = new Random();
        for (int i = x.length - 1; i > 0; i--){
            int j = rand.nextInt(i+1);
            /**
             * The assignment on line 252 swaps 
             * with a random element less than i.
             */
            int temp = x[i].attributes[att];
            x[i].attributes[att] = x[i].attributes[att];
            x[j].attributes[att] = temp;
        }
        return x; // Not Necessary
    }
    
    /**
     * Trains a specific column of data by calculating its sums
     * @param trainData the data to be trained on
     */
    
    private static void trainColumn(DataPoint[] trainData){
        int row = 0;
        DataPoint dataPoint = trainData[row++]; // Start at the Zeroth Row
        while(dataPoint != null){
            /**
             * Because there are null values at the lowermost portion
             * of the fold (the fold width is too large and is not
             * perfectly even).
             */
            for (int k = 0; k < attributes; k++){
                // Add up all of the true attributes
                sums[dataPoint.type][k][dataPoint.attributes[k]]++;
            }
            sums[dataPoint.type][attributes][0]++; // Increment Total
            dataPoint = trainData[row++]; // Increment datapoint
        }
    }
    
    /**
     * Tests the data on accuracy
     * @param testData the data to be tested
     * @return the accuracy of the model
     */
    
    private static double testAccuracy(DataPoint[] testData){
        int row = 0;
        DataPoint dataPoint = testData[row++]; // Initialize the datapoint
        int totalTest = 0; // Counter
        int correctClassification = 0; // Counter
        while(dataPoint != null){
            /**
             * We not that the last data point will be null (but 
             * this is not necessarily close to testData.length).
             */
            totalTest++; // Increment Total
            if(classify(dataPoint) == dataPoint.type){
                // If it is right, add to the total correct percentage
                correctClassification++;
            }
            dataPoint = testData[row++]; // increment datapoint
        }
        return (double) correctClassification / (double) totalTest;
    }
    
    /**
     * This calculates the F1 score of the testing data
     * @param testData the data to be tested
     * @return the F1 score
     */
    
    private static double testF(DataPoint[] testData){
        int row = 0;
        /**
         * Create a convolution matrix so that we can calculate the
         * rate of true positives, false positives, and false negatives, 
         * where the first index will be a true classification and the 
         * second index will be what the model classified.
         */
        int[][] classifications = new int[types][types];
        DataPoint dataPoint = testData[row++];
        while (dataPoint != null){
            /**
             * We know that the last data point will be null (but
             * this is not necessarily close to testData.length).
             */
            classifications[dataPoint.type][classify(dataPoint)]++;
            /**
             * Add the value to the matrix at the location where
             * we classified it.
             */
            dataPoint = testData[row++]; // Increment Datapoint
        }
        
        double precision = 0;
        double recall = 0;
        for (int perspective = 0; perspective < classifications.length;
                perspective++){
            // Iterates through the perspective
            int TP = 0; // True Positive
            int FP = 0; // False Positive
            int FN = 0; // False Negative
            for(int compare = 0; compare < classifications.length; compare++){
                if(compare != perspective){
                    FP += classifications[compare][perspective];
                    // Line 352 checks side to side for false positives
                    FN += classifications[perspective][compare];
                    // Line 354 checks up and down for false negatives
                }
                else {
                    TP += classifications[perspective][compare];
                    // Line 358 checks for the correct classifications
                }
            }
            
            if(TP + FP != 0 && TP + FN != 0){
                    // Ascertain that we have value to avoid NaN
                    precision += (double) TP / ((double) (TP + FP));
                    // Line 365 sums the precision
                    recall += (double) TP / ((double) (TP + FN));
                    // Line 367 sums the recall
                }
        }
        precision /= types;
        recall /= types;
        return 2 * (precision * recall) / (precision + recall);
    }
    
    /**
     * Utilizes the training model to classify an unknown point
     * @param x the data point to classify
     * @return the predicted class
     */
    
    private static int classify(DataPoint x){
        double maximum = 0;
        int maxarg = 0;
        for (int c = 0; c < types; c++){
            if (C(x, c) > maximum){
                // If it has a higher probability of being the new class
                maximum = C(x, c);
                maxarg = c;
            }
        }
        return maxarg;
    }
    
    /**
     * Returns the probability that the data point is of a given class
     * @param x the data point to be tested
     * @param c the class to which the data point might belong
     * @return the probability X is part of C
     */
    
    private static double C(DataPoint x, int c){
        double p = 1;
        for (int a = 0; a < attributes; a++){
            int value = x.attributes[a];
            p *= ((double) (sums[c][a][value] + 1) 
                    / (double) (sums[c][attributes][0] + types));
        }
        return (double) (sums[c][attributes][0])/(double)(total)*p;
    }
    
    /**
     * Folds the data into 10 relatively equal folds
     * @param points the data to be folded
     * @returns a folded list
     */
    
    public static DataPoint[][] fold(DataPoint[] points){
        DataPoint[][] data = new DataPoint[10][points.length];
        int[] counters = new int[10];
        /**
         * On line 420, the assignment is configured so that the elements
         * go into the array in order (i.e., all the null values are at
         * the end).
         */
        Random rand = new Random();
        for(int i = 0; i < 10; i++){
            // Ascertains that all folds have one DataPoint at minimum
            data[i][counters[i]++] = points[i];
        }
        for(int i = 10; i < points.length; i++){
            int random = rand.nextInt(10);
            data[random][counters[random]++] = points[i];
            /**
             * Line 433 places the points into folds 
             * in order so as to avoid null values.
             */
        }
        return data;
    }
    
    /**
     * Runs the studentT testAccuracy to testAccuracy differences
     * between two arrays.
     * @param x the first array
     * @param y the second array
     * @return the student T testAccuracy
     */
    
    public static double studentT(double[] x, double[] y){
        double xMean = avg(x);
        double xVar = variance(x);
        double yMean = avg(y);
        double yVar = variance(y);
        
        double numerator = xMean - yMean;
        double denominator = Math.sqrt(xVar/(x.length) + yVar/(y.length));
        
        return numerator/denominator;
    }
    
    /**
     * Calculates the variance of an array.
     * @param x the array
     * @return the variance
     */
    
    public static double variance(double[] x){
        double mean = avg(x);
        double sum = 0;
        
        for (int i = 0; i < x.length; i++){
            sum += (x[i] - mean) * (x[i] - mean);
        }
        double scalar = 1/(double)(x.length-1);
        return scalar * sum;
    }
    
    /**
     * Calculates the average of an array
     * @param x the array
     * @return the average
     */
    
    public static double avg(double[] x){
        double sum = 0;
        double total = 0;
        for (double y : x){
            sum += y;
            total++;
        }
        return sum/total;
    }
    
}