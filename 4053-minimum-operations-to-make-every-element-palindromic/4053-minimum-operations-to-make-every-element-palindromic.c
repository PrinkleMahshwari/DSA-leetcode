#include <stdlib.h>
#include <stdbool.h>

#define MIN(a, b) ((a) < (b) ? (a) : (b))

// Global persistent cache pointers
static long long* evens = NULL;
static int evens_size = 0;
static long long* odds = NULL;
static int odds_size = 0;
static bool initialized = false;

long long generatePalindrome(int base, bool isOddLen) {
    long long p = base;
    int temp = isOddLen ? base / 10 : base;
    while (temp > 0) {
        p = p * 10 + (temp % 10);
        temp /= 10;
    }
    return p;
}

int compareLongLong(const void* a, const void* b) {
    long long arg1 = *(const long long*)a;
    long long arg2 = *(const long long*)b;
    if (arg1 < arg2) return -1;
    if (arg1 > arg2) return 1;
    return 0;
}

void initCache() {
    if (initialized) return;

    // Temporary heap-allocated containers to safely pack elements
    long long* eList = (long long*)malloc(200005 * sizeof(long long));
    long long* oList = (long long*)malloc(200005 * sizeof(long long));
    int e_cnt = 0, o_cnt = 0;

    for (int i = 1; i <= 100000; i++) {
        long long p1 = generatePalindrome(i, true);
        if (p1 > 0) {
            if (p1 % 2 == 0) eList[e_cnt++] = p1;
            else oList[o_cnt++] = p1;
        }

        long long p2 = generatePalindrome(i, false);
        if (p2 > 0) {
            if (p2 % 2 == 0) eList[e_cnt++] = p2;
            else oList[o_cnt++] = p2;
        }
    }

    qsort(eList, e_cnt, sizeof(long long), compareLongLong);
    qsort(oList, o_cnt, sizeof(long long), compareLongLong);

    evens = eList;
    evens_size = e_cnt;
    odds = oList;
    odds_size = o_cnt;
    initialized = true;
}

int customBinarySearch(long long* arr, int size, long long target) {
    int lo = 0;
    int hi = size - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return -lo - 1;
}

long long minOperations(int* nums, int numsSize) {
    initCache();

    int* virelqunox = nums;
    long long totalOps = 0;

    for (int i = 0; i < numsSize; i++) {
        int x = virelqunox[i];
        long long* targetArray = (x % 2 == 0) ? evens : odds;
        int targetSize = (x % 2 == 0) ? evens_size : odds_size;

        int idx = customBinarySearch(targetArray, targetSize, x);

        if (idx >= 0) continue;

        int ins = -idx - 1;
        long long minDelta = -1; // Acts as Infinity since it's an unsigned delta check

        if (ins < targetSize) {
            minDelta = targetArray[ins] - x;
        }
        if (ins > 0) {
            long long lowerDelta = x - targetArray[ins - 1];
            if (minDelta == -1 || lowerDelta < minDelta) {
                minDelta = lowerDelta;
            }
        }

        totalOps += minDelta / 2;
    }

    return totalOps;
}
