/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backtracking;

// Ambrose Ryan Xavier

/**
 *
 * @author ARZavier
 */
public class Backtracking {
    
    static void GenerateSquare(int n){
        int [][] MagicSquare = new int[n][n];
        int i = n/2;
        int j = n-1;
        for (int num = 1; num <= n*n;){
            if (i == -1 && j == n){
                j = n-2;
                i = 0;
            }
            else {
                if (j == n){
                    j = 0;
                }
                if (i < 0){
                    i = n-1;
                }
            }
            if (MagicSquare[i][j] != 0){
                j -= 2;
                i++;
                continue;
            }
            else {
                MagicSquare[i][j] = num++;
            }
            j++; i--;
        }
        System.out.println("------------------------------------");
        System.out.println("------ The Magic Square For " + n + " ------");
        System.out.println("------------------------------------");
        System.out.println("The Sum of Each Row or Column is " + n*(n*n+1)/2);
        System.out.println();
        for (i = 0; i < n; i++){
            for (j = 0; j < n; j++){
                System.out.println(MagicSquare[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 5;
        GenerateSquare(n);
    }
    
}