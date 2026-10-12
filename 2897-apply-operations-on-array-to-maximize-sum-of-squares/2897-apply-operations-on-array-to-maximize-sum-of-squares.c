int maxSum(int* nums, int numsSize, int k) {
    int bits[31] = {0};
    long long mod = 1000000007LL;

    // Count how many numbers have each bit set
    for (int i = 0; i < numsSize; i++) {
        for (int b = 0; b < 31; b++) {
            if ((nums[i] & (1 << b)) != 0) {
                bits[b]++;
            }
        }
    }

    long long result = 0;

    // Greedily build the largest k numbers
    for (int i = 0; i < k; i++) {
        long long num = 0;

        for (int b = 0; b < 31; b++) {
            if (bits[b] > 0) {
                num |= (1LL << b);
                bits[b]--;
            }
        }

        result = (result + num * num) % mod;
    }

    return (int)result;
}
