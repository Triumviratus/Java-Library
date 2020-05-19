/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ARZavier
 */
public class DataPoint {
    
    static ArrayList<String> types = new ArrayList<>();
    /** 
    * The array variable assignment on line 17 refers 
    * to the list of all the different classes.
    */
    static int distinctAttributeValues = -1;
    
    int[] attributes; // The Features of the DataPoint
    int index; // The Identifier of the Point
    int type; // The Class of the Point
    
    /**
     * Builds the DataPoint from a string input.
     * @param data The line of data containing the attributes
     */
    
    public DataPoint(String data){
        String[] parse = data.split(",");
        attributes = new int[parse.length-2];
        /**
         * The variable assignment on line 35
         * cuts off the class and the index.
         */
        for (int i = 1; i < parse.length-1; i++){
            attributes[i-1] = (int) Double.parseDouble(parse[i]);
            /**
             * The variable assignment on line 35 stores the attribute values
             * and the if statement on lines 47 and 48 assumes that we have 
             * attribute values being linear without an aperture.
             */
            if (attributes[i+1] + 1 > distinctAttributeValues)
                distinctAttributeValues = attributes[i-1] + 1;
        }
        /**
         * The index is never utilized, so there is
         * no purpose in maintaining it.
         */
        index = 1;
        if (!types.contains(parse[parse.length-1]))
            types.add(parse[parse.length-1]);
        /**
         * For the if statement on lines 55 and 56, if
         * it is a unique type, add it to the list.
         */
        
        type = types.indexOf(parse[parse.length-1]);
        /**
         * On line 62, this assignment renders the type a number.
         */
        
    }
    
    /**
     * 
     * @return String representation of the DataPoint
     */
    
    @Override
    public String toString(){
        return index + ":" + Arrays.toString(attributes);
    }
    
    /**
     * Tests whether two DataPoints are equivalent.
     * @param b the DataPoint to compare to
     * @return true if they have the same features
     * and class, false otherwise
     */
    
    public boolean equals (DataPoint b){
        if (b.type != type)
            return false;
        
        for (int i = 0; i < attributes.length; i++){
            if(b.attributes[i] != attributes[i])
                return false;
        }
        return true;
    }
    
    /**
     * Resets the types array, so that it does not
     * grow from training old models.
     */
    
    public static void reset(){
        types = new ArrayList<>();
        distinctAttributeValues = -1;
    }
}
