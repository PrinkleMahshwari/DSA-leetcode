class Solution:
    def longestRepeating(self, s: str, queryCharacters: str, queryIndices: list[int]) -> list[int]:
        n = len(s)
        k = len(queryIndices)
        tree_size = 4 * n

        # Flat parallel arrays to mimic Java's array cache speed locally in Python
        leftChar = [""] * tree_size
        rightChar = [""] * tree_size
        prefix = [0] * tree_size
        suffix = [0] * tree_size
        max_arr = [0] * tree_size
        length = [0] * tree_size
        leafNodeMap = [0] * n

        def build(node: int, left: int, right: int):
            length[node] = right - left + 1
            if left == right:
                leftChar[node] = s[left]
                rightChar[node] = s[left]
                prefix[node] = 1
                suffix[node] = 1
                max_arr[node] = 1
                leafNodeMap[left] = node
                return

            mid = left + (right - left) // 2
            leftChild = node << 1
            rightChild = leftChild | 1

            build(leftChild, left, mid)
            build(rightChild, mid + 1, right)

            leftChar[node] = leftChar[leftChild]
            rightChar[node] = rightChar[rightChild]

            aLen = length[leftChild]
            if prefix[leftChild] == aLen and rightChar[leftChild] == leftChar[rightChild]:
                prefix[node] = aLen + prefix[rightChild]
            else:
                prefix[node] = prefix[leftChild]

            bLen = length[rightChild]
            if suffix[rightChild] == bLen and rightChar[leftChild] == leftChar[rightChild]:
                suffix[node] = bLen + suffix[leftChild]
            else:
                suffix[node] = suffix[rightChild]

            maxVal = max_arr[leftChild] if max_arr[leftChild] > max_arr[rightChild] else max_arr[rightChild]
            if rightChar[leftChild] == leftChar[rightChild]:
                combo = suffix[leftChild] + prefix[rightChild]
                if combo > maxVal:
                    maxVal = combo
            max_arr[node] = maxVal

        build(1, 0, n - 1)

        answer = [0] * k
        for i in range(k):
            node = leafNodeMap[queryIndices[i]]
            ch = queryCharacters[i]

            leftChar[node] = ch
            rightChar[node] = ch

            node >>= 1
            while node > 0:
                leftChild = node << 1
                rightChild = leftChild | 1

                leftChar[node] = leftChar[leftChild]
                rightChar[node] = rightChar[rightChild]

                aLen = length[leftChild]
                if prefix[leftChild] == aLen and rightChar[leftChild] == leftChar[rightChild]:
                    prefix[node] = aLen + prefix[rightChild]
                else:
                    prefix[node] = prefix[leftChild]

                bLen = length[rightChild]
                if suffix[rightChild] == bLen and rightChar[leftChild] == leftChar[rightChild]:
                    suffix[node] = bLen + suffix[leftChild]
                else:
                    suffix[node] = suffix[rightChild]

                maxVal = max_arr[leftChild] if max_arr[leftChild] > max_arr[rightChild] else max_arr[rightChild]
                if rightChar[leftChild] == leftChar[rightChild]:
                    combo = suffix[leftChild] + prefix[rightChild]
                    if combo > maxVal:
                        maxVal = combo
                max_arr[node] = maxVal

                node >>= 1
                
            answer[i] = max_arr[1]

        return answer
