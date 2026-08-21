#include <limits.h>

long long gcd(long long a, long long b) {
    while (b != 0) {
        long long temp = a % b;
        a = b;
        b = temp;
    }
    return a;
}

long long lcm(long long a, long long b) {
    return (a / gcd(a, b)) * b;
}

long long count(long long x, int* coins, int coinsSize, int index, long long currentLcm, int selected) {
    long long result = 0;

    for (int i = index; i < coinsSize; i++) {
        long long newLcm = lcm(currentLcm, coins[i]);

        if (newLcm > x || newLcm < 0) continue; // handle implicit numeric overflow

        long long contribution = x / newLcm;

        if ((selected + 1) % 2 == 1) {
            result += contribution;
        } else {
            result -= contribution;
        }

        result += count(x, coins, coinsSize, i + 1, newLcm, selected + 1);
    }

    return result;
}

long long findKthSmallest(int* coins, int coinsSize, int k) {
    long long minCoin = LLONG_MAX;

    for (int i = 0; i < coinsSize; i++) {
        if (coins[i] < minCoin) {
            minCoin = coins[i];
        }
    }

    long long left = 1;
    long long right = minCoin * k;

    while (left < right) {
        long long mid = left + (right - left) / 2;

        if (count(mid, coins, coinsSize, 0, 1, 0) >= k) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }

    return left;
}
