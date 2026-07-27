var containsNearbyDuplicate = function(nums, k) {
    if (!nums || nums.length <= 1 || k <= 0) {
        return false;
    }

    const window = new Set();

    for (let i = 0; i < nums.length; i++) {
        // In JS, Set.has() checks for existence
        if (window.has(nums[i])) {
            return true;
        }
        
        window.add(nums[i]);

        // When the window index exceeds size k, drop the oldest element
        if (i >= k) {
            window.delete(nums[i - k]);
        }
    }

    return false;
};
