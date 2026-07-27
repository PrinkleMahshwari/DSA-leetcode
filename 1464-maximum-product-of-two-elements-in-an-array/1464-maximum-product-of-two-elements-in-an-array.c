int maxProduct(int* nums, int numsSize) {
    int max1 = 0;
    int max2 = 0;

    for (int i = 0; i < numsSize; i++) {
        int num = nums[i];
        if (num > max1) {
            max2 = max1;
            max1 = num;
        } else if (num > max2) {
            max2 = num;
        }
    }

    return (max1 - 1) * (max2 - 1);
}