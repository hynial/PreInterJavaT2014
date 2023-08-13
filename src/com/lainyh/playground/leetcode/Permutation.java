package com.lainyh.playground.leetcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全排列
 * https://leetcode.cn/problems/permutations/
 */
public class Permutation {
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return null;
        } else if (nums.length == 1) {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(nums[0]);
            return Arrays.asList(tmp);
        }

        int [] subInts = new int[nums.length - 1];
        System.arraycopy(nums, 0, subInts, 0, nums.length - 1);
        List<List<Integer>> sub = permute(subInts);

        List<List<Integer>> resultList = new ArrayList<>();
        int dest = nums[nums.length - 1];
        for (List<Integer> l : sub) {
            for (int i = 0; i < l.size() + 1; i++) {
                List<Integer> tmp = new ArrayList<>(l);
                tmp.add(i, dest);
                resultList.add(tmp);
            }
        }

        return resultList;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 2, 3};

        Permutation permutation = new Permutation();
        List<List<Integer>> answers = permutation.permute(nums);

        for(List<Integer> l : answers) {
            System.out.println(l.stream().map(e -> e.toString()).collect(Collectors.joining(", ", "", "")));
        }

        System.out.println(answers.size());
    }
}
