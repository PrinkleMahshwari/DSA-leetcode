function isAnagram(s, t) {
    if (s.length !== t.length) return false;
    
    // Fast Int32 typed array for direct cache-line lookups
    const charCounts = new Int32Array(26);
    const len = s.length;
    
    for (let i = 0; i < len; i++) {
        charCounts[s.charCodeAt(i) - 97]++;
        charCounts[t.charCodeAt(i) - 97]--;
    }
    
    for (let i = 0; i < 26; i++) {
        if (charCounts[i] !== 0) return false;
    }
    
    return true;
}
