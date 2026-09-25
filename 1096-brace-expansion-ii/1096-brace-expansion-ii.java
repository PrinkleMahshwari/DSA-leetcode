class Solution {

    private String expression;
    private int index;

    public List<String> braceExpansionII(String expression) {
        this.expression = expression;
        this.index = 0;

        Set<String> result = parseExpression();

        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);

        return answer;
    }

    // Parses unions separated by commas.
    private Set<String> parseExpression() {
        Set<String> result = parseTerm();

        while (index < expression.length()
                && expression.charAt(index) == ',') {

            index++; // skip ','

            Set<String> next = parseTerm();
            result.addAll(next);
        }

        return result;
    }

    // Parses concatenated parts.
    private Set<String> parseTerm() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < expression.length()
                && expression.charAt(index) != '}'
                && expression.charAt(index) != ',') {

            Set<String> next = parseFactor();

            result = combine(result, next);
        }

        return result;
    }

    // Parses either a single letter or a {...} expression.
    private Set<String> parseFactor() {

        if (expression.charAt(index) == '{') {

            index++; // skip '{'

            Set<String> result = parseExpression();

            index++; // skip '}'

            return result;
        }

        // Single lowercase letter
        Set<String> result = new HashSet<>();

        result.add(String.valueOf(expression.charAt(index)));

        index++;

        return result;
    }

    // Cartesian product used for concatenation.
    private Set<String> combine(Set<String> first, Set<String> second) {

        Set<String> result = new HashSet<>();

        for (String a : first) {
            for (String b : second) {
                result.add(a + b);
            }
        }

        return result;
    }
}