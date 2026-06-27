class Solution {
    public String convert(String s, int numRows) {
        ArrayList<StringBuilder> rows = new ArrayList<>();

        // if row is 1 then return string
        if (numRows == 1) return s;

        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder()); // adds an empty StringBuilder to rows
        }

        // initialize current row and going down so we can change direction
        int currentRow = 0;
        boolean goingDown = false;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            rows.get(currentRow).append(ch);

            // flip direction at boundaries
            if (currentRow == 0 || currentRow == numRows - 1) {
                // change direction
                goingDown = !goingDown;
            }

            //  move row
            if (goingDown) {
                currentRow++; // move  down
            } else {
                currentRow--; // going up
            }
        }

        // store in result StringBuilder
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        // convert back to string
        return result.toString();
    }
}