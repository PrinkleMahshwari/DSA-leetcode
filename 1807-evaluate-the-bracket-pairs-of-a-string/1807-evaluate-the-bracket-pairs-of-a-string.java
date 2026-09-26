class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        
        Map<String, String> map = new HashMap<>();

        // store key-value pairs
        for (List<String> pair : knowledge) map.put(pair.get(0), pair.get(1));

        StringBuilder result = new StringBuilder();

        int i = 0;

        while (i < s.length()) {

            // normal character
            if (s.charAt(i) != '(') {
                result.append(s.charAt(i));
                i++;
                continue;
            }

            // find closing bracket
            int j = i + 1;
            while (s.charAt(j) != ')') j++;

            // extract key
            String key = s.substring(i + 1, j);

            // replace with value or '?'
            result.append(map.getOrDefault(key, "?"));

            // move after ')'
            i = j + 1;
        }

        return result.toString();
    }
}