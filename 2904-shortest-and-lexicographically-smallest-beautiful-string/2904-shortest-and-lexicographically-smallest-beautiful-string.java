import java.util.*;

class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        List<Integer> ones = new ArrayList<>();

        // store positions of all 1s
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '1')
                ones.add(i);

        // Not enough 1s
        if (ones.size() < k)
            return  "";

        String answer = "";

        // try every possible starting 1
        for (int i = 0; i + k - 1 < ones.size(); i++) {
            int start = ones.get(i);
            int end = ones.get(i + k - 1);

            String candidate = s.substring(start, end + 1);

            if (answer.isEmpty()
                    || candidate.length() < answer.length()
                    || (candidate.length() == answer.length()
                        && candidate.compareTo(answer) < 0)) {
                            answer = candidate;
            }
        }   

        return answer;     
    }
}