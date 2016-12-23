package com.example;

/**
 * Created by Administrator on 2016/8/25.
 */
public class PopSequence {

    static int[] nums = {1, 4, 6, 5, 8, 3, 8, 2};
//14568382
//14563828
    //
//

    public static void main(String[] args) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }

            }
        }

        for (int n : nums)
            System.out.print(n + " ");
    }
}
