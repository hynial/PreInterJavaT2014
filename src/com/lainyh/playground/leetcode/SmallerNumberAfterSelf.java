package com.lainyh.playground.leetcode;

import java.util.*;

/**
 * 树状数组，动态求解前n项和
 */
public class SmallerNumberAfterSelf {
    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) return Collections.emptyList();

//        int[] nums0 = new int[nums.length];
//        System.arraycopy(nums, 0, nums0, 0, nums.length);
        //Arrays.sort(nums0); // 2 sort methods.
        Set<Integer> uSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        for(int i = 0; i < nums.length; i++) {
            uSet.add(nums[i]);
        }

        Map<Integer, Integer> valueIndexMap = new HashMap<>();
        Iterator<Integer> iter = uSet.iterator();

        for(int i = 0; iter.hasNext();) {
            valueIndexMap.put(iter.next(), i++);
        }

        int[] dynamicSum = new int[uSet.size()];
        List<Integer> result = new ArrayList<>();

        for(int i = nums.length - 1; i > -1; i--) {
            int ind = valueIndexMap.get(nums[i]);
            dynamicSum[ind]++;
            int partSum = 0;
            for(int j = 0; j < ind; j++) {
                partSum += dynamicSum[j];
            }
            result.add(0, partSum);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] t = new int[] {5,2,6,1};
//        int[] t = new int[] {-1, -1};
//        int[] t = new int[] {-1};

        SmallerNumberAfterSelf smallerNumberAfterSelf = new SmallerNumberAfterSelf();
        List<Integer> r = smallerNumberAfterSelf.countSmaller(t);

        System.out.println(r);
    }
}
