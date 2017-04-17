public class Rule {

    String name;
    int badX;
    int badY;

    Rule(String Name, Puzzle puzzle) {
        name = Name;
        if (name.equals("Up"))
        {
            badX = 0;
        }
        if (name.equals("Down"))
        {
            badX = puzzle.n - 1;
        }
        if (name.equals("Left"))
        {
            badY = 0;
        }
        if (name.equals("Right"))
        {
            badY = puzzle.n - 1;
        }
    }

    boolean isApplicable(Puzzle puzzle) {
       puzzle.findPos();
       if (name.equals("Up"))
       {
           if (puzzle.x == badX)
           {
              return false;
           }
       }
       if (name.equals("Down"))
       {
           if (puzzle.x == badX)
           {
                return false;
           }
       }
       if (name.equals("Left"))
       {
           if (puzzle.y == badY)
           {
                return false;
           }
       }
       if (name.equals("Right"))
       {
           if (puzzle.y == badY)
           {
                return false;
           }
       }
       return true;
    }

    Puzzle doAction(Puzzle puzzle) {
        int temp;
        puzzle.findPos();
        if (name.equals("Up"))
        {
            temp = puzzle.start[puzzle.x - 1][puzzle.y];
            puzzle.start[puzzle.x - 1][puzzle.y] = 0;
            puzzle.start[puzzle.x][puzzle.y] = temp;
        }
        if (name.equals("Down"))
        {
            temp = puzzle.start[puzzle.x + 1][puzzle.y];
            puzzle.start[puzzle.x + 1][puzzle.y] = 0;
            puzzle.start[puzzle.x][puzzle.y] = temp;
        }
        if (name.equals("Left"))
        {
            temp = puzzle.start[puzzle.x][puzzle.y - 1];
            puzzle.start[puzzle.x][puzzle.y - 1] = 0;
            puzzle.start[puzzle.x][puzzle.y] = temp;
        }
        if (name.equals("Right"))
        {
            temp = puzzle.start[puzzle.x][puzzle.y + 1];
            puzzle.start[puzzle.x][puzzle.y + 1] = 0;
            puzzle.start[puzzle.x][puzzle.y] = temp;
        }

        return puzzle;
    }
}