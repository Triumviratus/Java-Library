/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgabstract;

/**
 *
 * @author ARZavier
 */
public class Heap {
    
    public void sort (int arr[]){
        int n = arr.length;
        for (int i = (n/2)-1; i >= 0; i--){
            heapify(arr, n, i);
        }
        for (int i = n-1; i >= 0; i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }
    
    void heapify(int arr[], int n, int i){
        int element = i;
        int L = (2*i) + 1;
        int R = (2*i) + 2;
        
        if (L < n && arr[L] > arr[element]){
            element = L;
        }
        if (R < n && arr[R] > arr[element]){
            element = R;
        }
        if (element != i){
            int swap = arr[i];
            arr[i] = arr[element];
            arr[element] = swap;
            heapify(arr, n, element);
        }
    }
    
    void printArray(int arr[]){
        int n = arr.length;
        for (int i = 0; i < n; ++i){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}