package com.lainyh.playground.leetcode.merge1;

import java.util.*;

/**
 * https://leetcode.cn/problems/count-of-smaller-numbers-after-self/
 * 正统解法带 # 号
 * 计算右侧小于当前元素的个数
 *
 * 树状数组(Binary Indexed Tree,Fenwick tree)，动态求解前n项和 （树状数组是一种数据结构，将时间复杂度控制在logN，该数据结构表现为一个中间状态（区间和的数据结构），通过维护它的部分和，来求解N项和）
 * 背景：修改了原数组的一个元素后，如何去更新其前n项和的数组？
 * 单点修改，并维护一个前n项和的数组
 * https://zhuanlan.zhihu.com/p/93795692
 * https://oi-wiki.org/ds/fenwick/
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
            // 没有用到树状数组，时间复杂度为N
            dynamicSum[ind]++;
            int partSum = 0;
            for(int j = 0; j < ind; j++) {
                partSum += dynamicSum[j];
            }
            result.add(0, partSum);
        }

        return result;
    }

    // # 正统
    public List<Integer> countSmaller2(int[] nums) {
        Set<Integer> discretization = new HashSet<>();
        for (int n : nums) {
            discretization.add(n);
        }

        int[] c = new int[discretization.size()];
        int start = 0;
        for (Integer n : discretization) {
            c[start++] = n;
        }

        Arrays.sort(c);

        int[] flags = new int[c.length];
        List<Integer> resultList = new ArrayList<>();
        for(int i = nums.length - 1; i >= 0; i--) {
            int index = Arrays.binarySearch(c, nums[i]) + 1;
            int ret = 0, pos0 = index - 1, pos = index;
            // 采用了树状数组flags，时间复杂度降到logN
            // 查询求和
            while (pos0 > 0) {
                ret += flags[pos0];
                pos0 -= lowBit(pos0);
            }
            resultList.add(ret);

            // 单点修改
            while (pos < c.length) {
                flags[pos] += 1;
                pos += lowBit(pos);
            }
        }

        Collections.reverse(resultList);
        return resultList;
    }

    private int lowBit(int x) {
        return x & -x; // 2^k , 二进制x的最后一个1的指数幂，k表示最后一个1后面的0的个数。树状数组的常用伎俩
    }

    // 归并排序计算逆序对
    private int[] ans;
    private int[] indexes; // 索引数组，作用：归并回去的时候，方便知道是哪个下标的元素
    public List<Integer> countSmaller3(int[] nums) {
        ans = new int[nums.length];
        indexes = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            indexes[i] = i;
        }

        mergeSort(nums, 0, nums.length - 1);

        // System.out.println(Arrays.toString(nums));

        return Arrays.stream(ans).boxed().toList();
    }

    private void mergeSort(int[] a, int l, int r){
        if (l >= r) {
            return;
        }

        int m = (l + r) >> 1;
        mergeSort(a, l, m);
        mergeSort(a, m + 1, r);

        // 归并排序的优化，如果索引数组有序，则不存在逆序关系，没有必要合并
        // 因为此法改变了源数组所以不通过indexes[m]来获取索引
        if (a[m] <= a[m + 1]) {
            return;
        }

        merge(a, l, m, r);
    }

    private void merge(int[] a, int l, int m, int r) {
        int i = l, j = m + 1;
        int[] temp = new int[a.length];
        int[] tempIndex = new int[a.length];
        for (int t = l; t <= r; t++) {
            temp[t] = a[t];
        }

        for (int ii = l; ii <= r; ii++) {
            tempIndex[ii] = indexes[ii];
        }

        for (int k = l; k <= r; k++) {
            if (i == m + 1) {
                indexes[k] = tempIndex[j];
                a[k] = temp[j++];
            } else if (j == r + 1) {
                indexes[k] = tempIndex[i];
                a[k] = temp[i++];
                ans[indexes[k]] += r - m;
            } else if (temp[i] <= temp[j]) {
                indexes[k] = tempIndex[i];
                a[k] = temp[i++];
                ans[indexes[k]] += j - m - 1;
            } else {
                indexes[k] = tempIndex[j];
                a[k] = temp[j++];
            }
        }

//        for (int k = l; k <= r; k++) {
//            if (i == m + 1) {
//                indexes[k] = tempIndex[j];
//                a[k] = temp[j++];
//            } else if (j == r + 1) {
//                indexes[k] = tempIndex[i];
//                a[k] = temp[i++];
//            } else if (temp[i] <= temp[j]) {
//                indexes[k] = tempIndex[i];
//                a[k] = temp[i++];
//            } else {
//                indexes[k] = tempIndex[j];
//                a[k] = temp[j++];
//                ans[indexes[k]] += m - i + 1;
//            }
//        }
    }

    public static void main(String[] args) {
        int[] t = new int[] {5,2,6,1};
//        int[] t = new int[] {-1, -1};
//        int[] t = new int[] {-1};

        SmallerNumberAfterSelf smallerNumberAfterSelf = new SmallerNumberAfterSelf();
        List<Integer> r = smallerNumberAfterSelf.countSmaller3(t);

        System.out.println(r);
    }
}
