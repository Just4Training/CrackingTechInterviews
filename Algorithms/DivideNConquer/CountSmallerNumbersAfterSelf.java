package Algorithms.DivideNConquer;
/**
 * Given an integer array nums, return an integer array counts where counts[i] is the number of smaller elements to the right of nums[i].
 * 
 * Example 1:
 * Input: nums = [5,2,6,1]
 * Output: [2,1,1,0]
 * Explanation:
 * To the right of 5 there are 2 smaller elements (2 and 1).
 * To the right of 2 there is only 1 smaller element (1).
 * To the right of 6 there is 1 smaller element (1).
 * To the right of 1 there is 0 smaller element.
 * 
 * Example 2:
 * Input: nums = [-1]
 * Output: [0]
 * 
 * Example 3:
 * Input: nums = [-1,-1]
 * Output: [0,0]
 */
import java.util.ArrayList;
import java.util.List;

public class CountSmallerNumbersAfterSelf {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();

        int[] count = new int[nums.length];
        int[] indexes = new int[nums.length];

        for(int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        mergeSort(nums, indexes, 0, nums.length, count);

        return res;
    }

    private void mergeSort(int[] nums, int[] indexes, int l, int r, int[] count) {
        if(l + 1 >= r) {
            return;
        }

        int mid = l + (r - l) / 2;

        mergeSort(nums, indexes, l, mid, count);
        mergeSort(nums, indexes, mid, r, count);

        merge(nums, indexes, l, r, count);
    }

    private void merge(int[] nums, int[] indexes, int l, int r, int[] count) {
        int mid = l + (r - l) / 2;
        int[] temp = new int[r - l];
        int[] tempIdx = new int[r - l];

        int i = l;
        int j = mid;
        int k = 0;

        while(k < r - l) {
            if(i < mid) {
                if(j < r && nums[j] < nums[i]) {
                    tempIdx[k] = indexes[j];
                    temp[k] = nums[j];
                    k++;
                    j++;
                } else {
                    count[indexes[i]] += j - mid;
                    tempIdx[k] = indexes[i];
                    temp[k] = nums[i];
                    k++;
                    i++;
                }
            } else {
                tempIdx[k] = indexes[j];
                temp[k] = nums[j];
                k++;
                j++;
            }

            System.arraycopy(tempIdx, 0, indexes, l, r - l);
            System.arraycopy(temp, 0, nums, l, r - l);
        }
    }
}
