class Solution:
    def isRectangleOverlap(self, rec1: list[int], rec2: list[int]) -> bool:
        overlap_width = min(rec1[2], rec2[2]) - max(rec1[0], rec2[0])
        overlap_height = min(rec1[3], rec2[3]) - max(rec1[1], rec2[1])

        return overlap_width > 0 and overlap_height > 0
