function canConstruct(ransomNote, magazine) {
    if (ransomNote.length > magazine.length) return false;
    const counts = new Int32Array(26);

    for (let i = 0; i < magazine.length; i++) {
        counts[magazine.charCodeAt(i) - 97]++;
    }

    for (let i = 0; i < ransomNote.length; i++) {
        const index = ransomNote.charCodeAt(i) - 97;
        counts[index]--;
        if (counts[index] < 0) return false;
    }
    return true;
}
