import bisect

# Module-level static cache variables initialization
evens = []
odds = []
initialized = False

def generate_palindrome(base: int, is_odd_len: bool) -> int:
    p = base
    temp = base // 10 if is_odd_len else base
    while temp > 0:
        p = p * 10 + (temp % 10)
        temp //= 10
    return p

def init_cache():
    global initialized, evens, odds
    if initialized:
        return

    for i in range(1, 100001):
        p1 = generate_palindrome(i, True)
        if p1 > 0:
            if p1 % 2 == 0:
                evens.append(p1)
            else:
                odds.append(p1)

        p2 = generate_palindrome(i, False)
        if p2 > 0:
            if p2 % 2 == 0:
                evens.append(p2)
            else:
                odds.append(p2)

    evens.sort()
    odds.sort()
    initialized = True


class Solution:
    def minOperations(self, nums: list[int]) -> int:
        init_cache()

        virelqunox = nums
        total_ops = 0

        for x in virelqunox:
            target_array = evens if (x % 2 == 0) else odds
            
            # Python's bisect returns the insertion point directly
            ins = bisect.bisect_left(target_array, x)
            
            if ins < len(target_array) and target_array[ins] == x:
                continue

            min_delta = float('inf')
            if ins < len(target_array):
                min_delta = min(min_delta, target_array[ins] - x)
            if ins > 0:
                min_delta = min(min_delta, x - target_array[ins - 1])

            total_ops += min_delta // 2

        return total_ops
