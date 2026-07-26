function isIsomorphic(s, t) {
    const len = s.length;
    const mapS = new Int32Array(256);
    const mapT = new Int32Array(256);

    for (let i = 0; i < len; i++) {
        const charS = s.charCodeAt(i);
        const charT = t.charCodeAt(i);

        if (mapS[charS] !== mapT[charT]) return false;

        mapS[charS] = i + 1;
        mapT[charT] = i + 1;
    }
    return true;
}
