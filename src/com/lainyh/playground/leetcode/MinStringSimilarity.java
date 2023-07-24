package com.lainyh.playground.leetcode;

public class MinStringSimilarity {
    private String t1 = "abc";
    private String t2 = "bca";
    public static void main(String[] args) {
        MinStringSimilarity minStringSimilarity = new MinStringSimilarity();
        int k = minStringSimilarity.kSimilarity(minStringSimilarity.t1, minStringSimilarity.t2);

        System.out.println(minStringSimilarity.t1);
        System.out.println(minStringSimilarity.t2);
        System.out.println(k);
    }

    public int kSimilarity(String s1, String s2) {
        if (s1.length() == 0) return 0;
        StringBuilder str1 = new StringBuilder(), str2 = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                str1.append(s1.charAt(i));
                str2.append(s2.charAt(i));
            }
        }

        if (str1.length() == 0) return 0;

        int ans = Integer.MAX_VALUE;
        String tmp = str2.toString();
        for (int i = 0; i < tmp.length(); i++) {
            if (str1.charAt(0) == tmp.charAt(i)) {
                String tmp2 = tmp;
                if ( i > 0) {
                    tmp2 = tmp.substring(1, i) + tmp.charAt(0) + ( i + 1 < tmp.length() ? tmp.substring(i + 1) : "");
                }
                int tmpValue = 1 + kSimilarity(str1.substring(1), tmp2);
                if (tmpValue < ans) {
                    ans = tmpValue;
                }
            }
        }

        return ans;
    }
}
