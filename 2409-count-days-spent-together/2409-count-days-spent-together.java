class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        // array of number of days
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 30};

        int aliceStart = getDay (arriveAlice, days);
        int aliceEnd = getDay (leaveAlice, days);

        int bobStart = getDay (arriveBob, days);
        int bobEnd = getDay (leaveBob, days);

        int start = Math.max (aliceStart, bobStart);
        int end = Math.min (aliceEnd, bobEnd);

        // if no overlapping day then return 0 else return end - start + 1
        if (start > end) return 0;

        return end - start + 1;
    }

    // helper function for finding day
    private int getDay (String date, int[] days) {
        int month = Integer.parseInt (date.substring (0, 2));
        int day = Integer.parseInt (date.substring (3, 5));

        for (int i = 0; i < month - 1; i++) day += days[i];

        return day;
    }
}