function wordPattern(pattern, s) {
    const words = s.split(" ");
    if (pattern.length !== words.length) return false;

    const charLastSeen = new Int32Array(26);
    const wordLastSeen = new Map();

    for (let i = 0; i < pattern.length; i++) {
        const charIdx = pattern.charCodeAt(i) - 97;
        const word = words[i];

        const lastCharPos = charLastSeen[charIdx];
        const lastWordPos = wordLastSeen.get(word) || 0;

        if (lastCharPos !== lastWordPos) return false;

        charLastSeen[charIdx] = i + 1;
        wordLastSeen.set(word, i + 1);
    }
    return true;
}
