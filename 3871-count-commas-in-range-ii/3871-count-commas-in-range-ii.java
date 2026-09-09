class Solution {
    public long countCommas(long n) {
        long total = 0;
        long start = 1000;
        long commas = 1;

        while (start <= n) {
            long end = Math.min(n, start * 1000 - 1);

            long count = end - start + 1;
            total += count * commas;

            start *= 1000;
            commas++;
        }

        return total;
    }
}