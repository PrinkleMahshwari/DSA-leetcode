import java.util.Random;

class RandomizedSet {
    private int[] nums;
    private int size;
    private Random rand;

    // Custom Flat Primitive Hash Map (Open Addressing with Linear Probing)
    private int[] keys;
    private int[] vals;
    private int mask;
    private int mapSize;

    public RandomizedSet() {
        nums = new int[16];
        size = 0;
        rand = new Random();
        
        // Initialize Map
        int capacity = 16;
        keys = new int[capacity];
        vals = new int[capacity];
        mask = capacity - 1;
        mapSize = 0;
    }
    
    private void resizeMap() {
        int oldCapacity = keys.length;
        int newCapacity = oldCapacity << 1;
        int[] oldKeys = keys;
        int[] oldVals = vals;
        
        keys = new int[newCapacity];
        vals = new int[newCapacity];
        mask = newCapacity - 1;
        
        for (int i = 0; i < oldCapacity; i++) {
            if (oldVals[i] != 0) { // Slot is filled
                int valIdx = oldVals[i] - 1;
                int key = oldKeys[i];
                
                int idx = hash(key) & mask;
                while (vals[idx] != 0) {
                    idx = (idx + 1) & mask;
                }
                keys[idx] = key;
                vals[idx] = valIdx + 1;
            }
        }
    }

    private int hash(int key) {
        // High-quality avalanche mixer for integers
        key ^= key >>> 16;
        key *= 0x85ebca6b;
        key ^= key >>> 13;
        key *= 0xc2b2ae35;
        key ^= key >>> 16;
        return key;
    }
    
    public boolean insert(int val) {
        if (mapSize * 2 >= keys.length) {
            resizeMap();
        }
        
        int idx = hash(val) & mask;
        while (vals[idx] != 0) {
            if (keys[idx] == val) {
                return false; // Already exists
            }
            idx = (idx + 1) & mask;
        }
        
        // Expand the element tracker array if full
        if (size == nums.length) {
            int[] newNums = new int[nums.length << 1];
            System.arraycopy(nums, 0, newNums, 0, size);
            nums = newNums;
        }
        
        nums[size] = val;
        keys[idx] = val;
        vals[idx] = size + 1; // Use 1-based indexing to reserve 0 for empty slots
        size++;
        mapSize++;
        return true;
    }
    
    public boolean remove(int val) {
        int idx = hash(val) & mask;
        while (vals[idx] != 0) {
            if (keys[idx] == val) {
                int indexToRemove = vals[idx] - 1;
                
                // Clear the removed item out of the hash table
                vals[idx] = 0; 
                mapSize--;
                
                // Linear Probing fix: Rehash subsequent keys in the cluster
                int nextIdx = (idx + 1) & mask;
                while (vals[nextIdx] != 0) {
                    int k = keys[nextIdx];
                    int v = vals[nextIdx];
                    vals[nextIdx] = 0;
                    mapSize--;
                    
                    // Re-insert the valid sliding key
                    int reInsertIdx = hash(k) & mask;
                    while (vals[reInsertIdx] != 0) {
                        reInsertIdx = (reInsertIdx + 1) & mask;
                    }
                    keys[reInsertIdx] = k;
                    vals[reInsertIdx] = v;
                    mapSize++;
                    
                    nextIdx = (nextIdx + 1) & mask;
                }
                
                // Swap array contents internally
                int lastElement = nums[size - 1];
                if (indexToRemove != size - 1) {
                    nums[indexToRemove] = lastElement;
                    
                    // Update index of the swapped element in our map
                    int swapIdx = hash(lastElement) & mask;
                    while (vals[swapIdx] != 0) {
                        if (keys[swapIdx] == lastElement) {
                            vals[swapIdx] = indexToRemove + 1;
                            break;
                        }
                        swapIdx = (swapIdx + 1) & mask;
                    }
                }
                
                size--;
                return true;
            }
            idx = (idx + 1) & mask;
        }
        return false;
    }
    
    public int getRandom() {
        return nums[rand.nextInt(size)];
    }
}
