package com.shlad.berserk.Sprites;

import java.util.ArrayList;

public class Requirement extends Object
{
    
    //I couldnt fit these in the game i think and i dont wanna go back and check the entire thing for them
    public static void main(String[] args)
    {
        ArrayList rawArray = new ArrayList();
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(0, 1);
        System.out.println(integers.get(0));
        System.out.println(integers.set(0, 2));
        integers.remove(0);
        for (int i = 0; i < 10; i++)
            System.out.println(integers.add(1));
        for (int i = integers.size() - 1; i >= 0; i--)
        {
            System.out.println(integers.remove(i));
        }
        int[] selectionSort = {4, 3, 2, 1, 0};
        int[] insertionSort = {4, 3, 2, 1, 0};
    
        Requirement requirement = new Requirement();
        requirement.selectionSort(selectionSort);
        requirement.insertionSort(insertionSort);
    
        int[][] rowAndColumnMajor = {{1, 2}, {3, 4}};
    
        for (int row = 0; row < rowAndColumnMajor.length; row++)
        {
            for (int col = 0; col < rowAndColumnMajor[0].length; col++)
            {
                System.out.println(rowAndColumnMajor[row][col]);
            }
        }
        for (int col = 0; col < rowAndColumnMajor[0].length; col++)
        {
            for (int row = 0; row < rowAndColumnMajor.length; row++)
            {
                System.out.println(rowAndColumnMajor[row][col]);
            }
            Requirement[][] requirements = new Requirement[1][1];
        }
        
        Object h = new Requirement();
        ArrayList<Object> u = new ArrayList<>();
        u.add(new Requirement());
        
        Object[] a = {new Requirement()};
    }
    
    public void selectionSort(int[] arr)
    {
        int n = arr.length;
        int count = 0;
        
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;
            
            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
            count++;
            
        }
        System.out.println(count);
    }
    
    void insertionSort(int[] arr)
    {
        int count = 0;
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            count++;
            arr[j + 1] = key;
        }
        System.out.println(count);
    }
}
