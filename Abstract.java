/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgabstract;

// Ambrose Ryan Xavier

/**
 *
 * @author ARZavier
 */
public class Abstract {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int arr1[] = {12,45,21,5,21,10,3,55,15};
        Heap h = new Heap();
        h.sort(arr1);
        System.out.println("Sorted Array");
        h.printArray(arr1);
        int arr2[] = {12,45,21,5,21,10,3};
        h.sort(arr2);
        System.out.println("Sorted Array");
        h.printArray(arr2);
        int arr3[] = {12,45,21,5,21,10,3,32,6};
        h.sort(arr3);
        System.out.println("Sorted Array");
        h.printArray(arr3);
        int arr4[] = {12,45,21,3,32,6};
        h.sort(arr4);
        System.out.println("Sorted Array");
        h.printArray(arr4);
    }
    
}