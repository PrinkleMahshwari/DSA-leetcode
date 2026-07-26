function groupAnagrams(strs) {
    const map = new Map();
    for (let i = 0; i < strs.length; i++) {
        const str = strs[i];
        // Fast sorting strategy using native array conversions
        const key = str.split('').sort().join('');
        if (!map.has(key)) map.set(key, []);
        map.get(key).push(str);
    }
    return Array.from(map.values());
}
