class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] count = new int[3];

        for (int stone : stones)
            count[stone % 3]++;
        
        // if there are no stones with remainder 1 or 2,
        // Alice can't avoid eventually losing
        if (count[1] == 0 && count[2] == 0)
            return false;
        
        // if count[0] is even, the winner depends on 
        // whether one remainder group has at least 2 more stone than other
        if (count[0] % 2 == 0)
            return count[1] > 0 && count[2] > 0;
        
        // count[0] is odd
        // Alice wins if one of the two remainder groups
        // has at least 2 more stones than other 
        return Math.abs(count[1] - count[2]) > 2;
    }
}