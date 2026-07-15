class Solution {
    public int gcdOfOddEvenSums(int n) {
        // sumOdd = n*n, sumEven = n*(n+1)
        // gcd(n*n, n*(n+1)) = n * gcd(n, n+1) = n * 1, since consecutive integers are always coprime
        return n;
    }
}