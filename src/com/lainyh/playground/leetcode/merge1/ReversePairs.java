package com.lainyh.playground.leetcode.merge1;

import java.util.Arrays;

// https://leetcode.cn/problems/shu-zu-zhong-de-ni-xu-dui-lcof/solution/
public class ReversePairs {
    private int[] tmp;
    public int reversePairs(int[] nums) {
        tmp = new int[nums.length];
        return mergeSort(nums, 0, nums.length - 1);
    }
    private int mergeSort(int[] nums, int l, int r) {
        // 终止条件
        if (l >= r) return 0;
        // 递归划分
        int m = (l + r) / 2;
        int res = mergeSort(nums, l, m) + mergeSort(nums, m + 1, r);
        // 合并阶段
        int i = l, j = m + 1;
        for (int k = l; k <= r; k++)
            tmp[k] = nums[k];
        for (int k = l; k <= r; k++) {
            if (i == m + 1)
                nums[k] = tmp[j++];
            else if (j == r + 1 || tmp[i] <= tmp[j])
                nums[k] = tmp[i++];
            else {
                nums[k] = tmp[j++];
                res += m - i + 1; // 统计逆序对
            }
        }
        return res;
    }

    // 树状数组的解法
    public int reversePairs2(int[] nums) {
        int[] tmp = new int[nums.length];
        int[] tree = new int[nums.length + 1];

        System.arraycopy(nums, 0, tmp, 0, nums.length);
        Arrays.sort(tmp);

        for(int i = 0; i < nums.length; i++) {
            nums[i] = Arrays.binarySearch(tmp, nums[i]) + 1;
        }

        int ans = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            int pos = nums[i] - 1;
            while (pos > 0) {
                ans += tree[pos];
                pos -= pos & -pos;
            }

            pos = nums[i];
            while (pos <= nums.length) {
                tree[pos]++;
                pos += pos & -pos;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        ReversePairs reversePairs = new ReversePairs();

        int[] nums = new int[]{7,3,2,6,0,1,5,4}; // 17

        System.out.println(reversePairs.reversePairs2(nums));
    }
}
