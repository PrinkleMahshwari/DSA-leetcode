class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int leftSum = 0;
        int rightSum = 0;

        int qLeft = 0;
        int qRight = 0;

        for (int i = 0; i < half; i++) {
            char c = num.charAt(i);

            if (c == '?')
                qLeft++;
            else
                leftSum += c - '0';
        }

        for (int i = half; i < n; i++) {
            char c = num.charAt(i);

            if (c == '?')
                qRight++;
            else
                rightSum += c - '0';
        }

        int diff = leftSum - rightSum;

        return 2 * diff != 9 * (qRight - qLeft);
    }
}