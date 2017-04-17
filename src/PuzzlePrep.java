 public class PuzzlePrep {

    public static boolean validate(Puzzle puzzle) {
        boolean flag;
        if(puzzle.n <= 1)
        {
            return false;
        }
        if(puzzle.start == null || puzzle.goal == null)
        {
            return false;
        }
        for(int i = 0; i < (puzzle.n*puzzle.n); i++)
        {
            flag = false;
            for(int j = 0; j < puzzle.n; j++)
            {
                for(int k = 0; k < puzzle.n; k++)
                {
                    if(puzzle.start[j][k] == i) {
                        flag = true;
                        j = puzzle.n;
                        k = puzzle.n;
                    }
                }
            }
            if(flag == false)
                return false;
        }
        for(int i = 0; i < (puzzle.n*puzzle.n); i++)
        {
            flag = false;
            for(int j = 0; j < puzzle.n; j++)
            {
                for(int k = 0; k < puzzle.n; k++)
                {
                    if(puzzle.goal[j][k] == i) {
                        flag = true;
                        j = puzzle.n;
                        k = puzzle.n;
                    }
                }
            }
            if(flag == false)
                return false;
        }
        return true;
    }

    public void printRules(Puzzle puzzle) {
        boolean flag;
        System.out.println("n : " + puzzle.n);
        System.out.println("Start : " );
        for(int i = 0; i < puzzle.n; i++)
        {
            System.out.print("[" + puzzle.start[i][0]);
            for(int j = 1; j < puzzle.n; j++)
            {

                System.out.print("," + puzzle.start[i][j]);
            }
            System.out.println("]");
        }
        System.out.println("Goal : " );
        for(int i = 0; i < puzzle.n; i++)
        {
            System.out.print("[" + puzzle.goal[i][0]);
            for(int j = 1; j < puzzle.n; j++)
            {

                System.out.print("," + puzzle.goal[i][j]);
            }
            System.out.println("]");
        }

        flag = validate(puzzle);
        if(flag == true)
            System.out.println("This Puzzle is valid");
        else
            System.out.println("This Puzzle is Invalid");
    }



}
