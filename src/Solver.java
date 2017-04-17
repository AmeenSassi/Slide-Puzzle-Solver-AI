import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

public class Solver {

    // BackTracking Functions

    private static boolean isFinished(Puzzle puzzle){

        for (int i = 0; i < puzzle.n; i++)
        {
            for (int j = 0; j < puzzle.n; j++)
            {
                if (puzzle.start[i][j] != puzzle.goal[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    private static void backtrackingStart(){
        int moves = 0;
        Vector<int[][]> stateList = new Vector<>();
        Vector<String> actionList = new Vector<>();

        PuzzlePrep prep = new PuzzlePrep();
        Parser p = new Parser();
        Puzzle puzzle = p.parse();
        prep.printRules(puzzle);
        Rule Up = new Rule("Up", puzzle);
        Rule Down = new Rule("Down", puzzle);
        Rule Left = new Rule("Left", puzzle);
        Rule Right = new Rule("Right", puzzle);
        RuleSet rules = new RuleSet(Up, Down, Left, Right);
        boolean flag = isFinished(puzzle);
        if(flag)
            System.out.println("This Puzzle is Finished");
        else
            System.out.println("This Puzzle is Not Done");

        storeState(puzzle, stateList);
        if(backtracking(stateList, actionList, puzzle, rules, moves, 31)){
            printList(puzzle.n, stateList, actionList);
            System.out.println("Nodes Examined : " + puzzle.generated);
            System.out.println("Solution Length : " + (stateList.size() - 1));
        }
    }

    private static boolean backtracking(Vector<int[][]> state, Vector<String> action, Puzzle puzzle, RuleSet rules, int move, int max){
        move++;
        puzzle.generated++;
        if(checkValid(state, puzzle, move, max)) {
            if (!isFinished(puzzle)) {
                if (rules.left.isApplicable(puzzle)) {
                    rules.left.doAction(puzzle);
                    storeState(puzzle, state);
                    action.addElement("Left");
                    if(backtracking(state, action, puzzle, rules, move, max))
                    {
                        return true;
                    }
                    else {
                        state.remove(state.size() - 1);
                        action.remove(action.size() - 1);
                        rules.right.doAction(puzzle);
                    }
                }
                if (rules.right.isApplicable(puzzle)) {
                    rules.right.doAction(puzzle);
                    storeState(puzzle, state);
                    action.addElement("Right");
                    if (backtracking(state, action, puzzle, rules, move, max)) {
                        return true;
                    } else {
                        state.remove(state.size() - 1);
                        action.remove(action.size() - 1);
                        rules.left.doAction(puzzle);
                    }
                }
                if (rules.up.isApplicable(puzzle)) {
                    rules.up.doAction(puzzle);
                    storeState(puzzle, state);
                    action.addElement("Up");
                    if(backtracking(state, action, puzzle, rules, move, max))
                    {
                        return true;
                    }
                    else {
                        state.remove(state.size() - 1);
                        action.remove(action.size() - 1);
                        rules.down.doAction(puzzle);
                    }
                }
                if (rules.down.isApplicable(puzzle)) {
                    rules.down.doAction(puzzle);
                    storeState(puzzle, state);
                    action.addElement("Down");
                    if(backtracking(state, action, puzzle, rules, move, max))
                    {
                        return true;
                    }
                    else {
                        state.remove(state.size() - 1);
                        action.remove(action.size() - 1);
                        rules.up.doAction(puzzle);
                    }
                }

            }
            else {
                    return true;
            }
        }
        else{
            return false;
        }
        return false;
    }

    private static void iterativeStart(){
        int moves = 0;
        int max = 1;
        Vector<int[][]> stateList = new Vector<>();
        Vector<String> actionList = new Vector<>();

        PuzzlePrep prep = new PuzzlePrep();
        Parser p = new Parser();
        Puzzle puzzle = p.parse();
        prep.printRules(puzzle);
        Rule Up = new Rule("Up", puzzle);
        Rule Down = new Rule("Down", puzzle);
        Rule Left = new Rule("Left", puzzle);
        Rule Right = new Rule("Right", puzzle);
        RuleSet rules = new RuleSet(Up, Down, Left, Right);
        boolean flag = isFinished(puzzle);
        if(flag)
            System.out.println("This Puzzle is Finished");
        else
            System.out.println("This Puzzle is Not Done");

        if(iterative(stateList, actionList, puzzle, rules, moves, max)){
            printList(puzzle.n, stateList, actionList);
            System.out.println("Nodes Examined = " + puzzle.generated);
            System.out.println("Solution Length = " + (stateList.size() - 1));
        }
    }

    private static boolean iterative(Vector<int[][]> state, Vector<String> action, Puzzle puzzle, RuleSet rules, int move, int max) {
        storeState(puzzle, state);
        while (max < 31){
            if(backtracking(state, action, puzzle, rules, move, max))
                return true;
            max++;
        }
        return false;
    }

    private static boolean checkValid(Vector<int[][]> state, Puzzle puzzle, int move, int max){
        if( (move-1) > max)
            return false;
        else if (!checkCycle(state, puzzle)){
            return false;
        }
        return true;
    }

    private static boolean checkCycle(Vector<int[][]> state, Puzzle puzzle) {
        boolean flag;
        for (int i = 0; i < state.size() - 1; i++) {
            flag = false;
            for (int j = 0; j < puzzle.n; j++) {
                for (int k = 0; k < puzzle.n; k++) {
                    if (state.get(i)[j][k] != puzzle.start[j][k]) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private static void printList(int n, Vector<int[][]> state, Vector<String> action){
        for(int i = 0; i < state.size(); i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print("[" + state.get(i)[j][0]);
                for(int k = 1; k < n; k++)
                {
                    System.out.print("," + state.get(i)[j][k]);
                }
                System.out.println("]");
            }
            System.out.println("---------------------------");
        }
        System.out.print("Moves: " + action.get(0));
        for(int i = 1; i < action.size(); i++)
        {
            System.out.print(", " + action.get(i));
        }
        System.out.println("");
    }

    private static void storeState(Puzzle puzzle, Vector<int[][]> state){
        int[][] temp = new int[puzzle.n][puzzle.n];
        for(int i = 0; i < puzzle.n; i++)
        {
            for(int j = 0; j < puzzle.n; j++)
            {
                temp[i][j] = puzzle.start[i][j];
            }
        }
        state.addElement(temp);
    }

    // Graph Search Functions

    private static boolean graphSearch(Puzzle puzzle, RuleSet rules) {
        Graph G = new Graph(addState(puzzle), "Start");
        Queue<Graph> Open = new LinkedList<>();
        Queue<Graph> Closed = new LinkedList<>();
        Open.add(G);
        while(!Open.isEmpty()){
            Graph graph = Open.poll();
            Closed.add(graph);
            if(Compare2(graph.state, puzzle.goal, puzzle.n)){
                makeNPrintList(graph, puzzle.n, G.generated, Closed.size());
                return true;
            }
            else{
                puzzle = setPuzzleState(puzzle, graph.state);
                if(rules.left.isApplicable(puzzle)){
                    rules.left.doAction(puzzle);
                    G.generated++;
                    if(checkValid2(puzzle.start, Open, Closed, puzzle.n)){
                        Graph temp = new Graph(addState(puzzle), "Left");
                        Open.add(graph.addChild(temp));
                    }
                    rules.right.doAction(puzzle);
                }
                if(rules.right.isApplicable(puzzle)){
                    rules.right.doAction(puzzle);
                    G.generated++;
                    if(checkValid2(puzzle.start, Open, Closed, puzzle.n)) {
                        Graph temp = new Graph(addState(puzzle), "Right");
                        Open.add(graph.addChild(temp));
                    }
                    rules.left.doAction(puzzle);
                }
                if(rules.up.isApplicable(puzzle)){
                    rules.up.doAction(puzzle);
                    G.generated++;
                    if(checkValid2(puzzle.start, Open, Closed, puzzle.n)) {
                        Graph temp = new Graph(addState(puzzle), "Up");
                        Open.add(graph.addChild(temp));
                    }
                    rules.down.doAction(puzzle);
                }
                if(rules.down.isApplicable(puzzle)){
                    rules.down.doAction(puzzle);
                    G.generated++;
                    if(checkValid2(puzzle.start, Open, Closed, puzzle.n)) {
                        Graph temp = new Graph(addState(puzzle), "Down");
                        Open.add(graph.addChild(temp));
                    }
                    rules.up.doAction(puzzle);
                }
            }


        }
        return false;
    }

    private static void graphSearchStart() {
        PuzzlePrep prep = new PuzzlePrep();
        Parser p = new Parser();
        Puzzle puzzle = p.parse();
        prep.printRules(puzzle);
        Rule Up = new Rule("Up", puzzle);
        Rule Down = new Rule("Down", puzzle);
        Rule Left = new Rule("Left", puzzle);
        Rule Right = new Rule("Right", puzzle);
        RuleSet rules = new RuleSet(Up, Down, Left, Right);
        boolean flag = isFinished(puzzle);
        if(flag)
            System.out.println("This Puzzle is Finished");
        else
            System.out.println("This Puzzle is Not Done");

        if (graphSearch(puzzle, rules)) {
            System.out.println("This Puzzle was solved!");
        }
    }

    private static int[][] addState(Puzzle puzzle){
        int[][] tempStart = new int[puzzle.n][puzzle.n];
        for(int i = 0; i < puzzle.n; i++)
        {
            for(int j = 0; j < puzzle.n; j++)
            {
                tempStart[i][j] = puzzle.start[i][j];
            }
        }
        return tempStart;
    }

    private static void makeNPrintList(Graph graph, int size, int generated, int explored){
        Graph temp = graph;
        Stack<int[][]> printState = new Stack<>();
        Stack<String> printAction = new Stack<>();
        int solLength;
        while(!(temp.action.equals("Start"))){
            storeGraphState(temp.state, printState, size);
            printAction.push(temp.action);
            temp = temp.goToParent();
        }
        solLength = printState.size();

        int[][] tempState;
        while(!(printState.isEmpty())){
            tempState = printState.pop();
            for(int j = 0; j < size; j++)
            {
                System.out.print("[" + tempState[j][0]);
                for(int k = 1; k < size; k++)
                {
                    System.out.print("," + tempState[j][k]);
                }
                System.out.println("]");
            }
            System.out.println("---------------------------");
        }

        System.out.print("Moves: " + printAction.pop());
        while(!(printAction.isEmpty()))
        {
            System.out.print(", " + printAction.pop());
        }
        System.out.println(" ");

        System.out.println("Solution Length : " + solLength);
        System.out.println("Nodes Generated : " + generated);
        System.out.println("Nodes Explored : " + explored);
    }

    private static void storeGraphState(int[][] state, Stack<int[][]> list, int size){
        int[][] temp = new int[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                temp[i][j] = state[i][j];
            }
        }
        list.push(temp);
    }

    private static Puzzle setPuzzleState(Puzzle puzzle, int[][] state){
        for (int i = 0; i < puzzle.n; i++)
        {
            for (int j = 0; j < puzzle.n; j++)
            {
                puzzle.start[i][j] = state[i][j];
            }
        }

        return puzzle;
    }

    private static boolean Compare2(int[][] state, int[][] goal, int size){

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (state[i][j] != goal[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkValid2(int[][] state, Queue<Graph> open, Queue<Graph> closed, int size) {
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag;
        Graph graph;
        int qSize = open.size();
        for (int i = 0; i < qSize; i++){
            flag = false;
            graph = open.poll();
            open.add(graph);
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (state[j][k] != graph.state[j][k]) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                flag1 = false;
            }
        }

        qSize = closed.size();
        for (int i = 0; i < qSize; i++){
            flag = false;
            graph = closed.poll();
            closed.add(graph);
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (state[j][k] != graph.state[j][k]) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                flag2 = false;
            }
        }

        if (!flag1 || !flag2){
            return false;
        }
        return true;
    }

    // Algorithm A Functions

   /* private static boolean algorithmA(Puzzle puzzle, RuleSet rules){
        Graph G = new Graph(addState(puzzle), "Start");
        Queue<Graph> Open = new LinkedList<>();
        Queue<Graph> Closed = new LinkedList<>();
        Open.add(G);
        while(!Open.isEmpty()){
            Graph graph = Open.poll();
            Closed.add(graph);
            if(Compare2(graph.state, puzzle.goal, puzzle.n)) {
                makeNPrintList(graph, puzzle.n);
                return true;
            }
            return false;
    }

    private static void algorithmAStart(){
        int moves = 0;

        PuzzlePrep prep = new PuzzlePrep();
        Parser p = new Parser();
        Puzzle puzzle = p.parse();
        prep.printRules(puzzle);
        Rule Up = new Rule("Up", puzzle);
        Rule Down = new Rule("Down", puzzle);
        Rule Left = new Rule("Left", puzzle);
        Rule Right = new Rule("Right", puzzle);
        RuleSet rules = new RuleSet(Up, Down, Left, Right);
        boolean flag = isFinished(puzzle);
        if(flag)
            System.out.println("This Puzzle is Finished");
        else
            System.out.println("This Puzzle is Not Done");


    }*/

    public static void main(String[] args) {

        backtrackingStart();
        iterativeStart();
        graphSearchStart();
    }
}
