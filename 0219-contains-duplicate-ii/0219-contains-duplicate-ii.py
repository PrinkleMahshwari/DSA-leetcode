class Solution:
    def containsNearbyDuplicate(self, nums: list[int], k: int) -> bool:
        if not nums or len(nums) <= 1 or k <= 0:
            return False
            
        window = set()
        
        for i, num in enumerate(nums):
            # O(1) membership lookup check
            if num in window:
                return True
                
            window.add(num)
            
            # Keep the set size bounded to at most k elements
            if i >= k:
                window.remove(nums[i - k])
                
        return False
