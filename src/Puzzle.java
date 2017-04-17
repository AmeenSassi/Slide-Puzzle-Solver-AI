public class Puzzle
{
    int n;
    int[][] start;
    int[][] goal;

    int x;
    int y;
    int generated;

    public void findPos() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (start[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }
    }
}
