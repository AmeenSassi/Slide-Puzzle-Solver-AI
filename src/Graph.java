public class Graph {
    Graph parent;
    int[][] state;
    String action;
    int generated;

    public Graph(int[][] s, String a){
        this.state = s;
        this.action = a;
    }

    public Graph addChild(Graph g){
        Graph child = new Graph(g.state, g.action);
        child.parent = this;
        return child;
    }

    public Graph goToParent(){
        if(this.parent != null){
            Graph graph = this.parent;
            return graph;
        }
        return this;
    }
}
