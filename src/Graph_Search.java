import java.lang.reflect.Array;
import java.util.*;

//Author: Zachary Himed

/*-----------------------------------------------------------------
//The famous missionary and cannibal searching problem is solved
//here using a Graph Search and Node structure
//The search algorithm used is the Breadth First Search algorithm
------------------------------------------------------------------*/
//This program is run with a predefined initial state of 3
// missionaries and 3 cannibals on the right side of the river
//The only thing left to do is run the program and find the correct
// sequence of actions to achieve the goal state which is printed in the console

class Node {

    // n.STATE: the state in the state space to which the node corresponds;
    private ArrayList<Integer> state=new ArrayList<>();

    // n.PARENT: the node in the search tree that generated this node;
    private final Node parent;

    // n.ACTION: the action that was applied to the parent to generate the node;
    private final String action;

    public Node(ArrayList<Integer> state) {
        this(state, null, null);
    }

    public Node(ArrayList<Integer> state, Node parent, String action) {
        this.state.addAll(state);
        this.parent = parent;
        this.action = action;
    }

    public ArrayList<Integer> getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public String getAction() {
        return action;
    }

    public void printState(){
        for(int i=0;i<state.size();i++){
            System.out.printf("%d ",state.get(i));
        }
        System.out.println();
    }


    public boolean isRootNode() {
        return parent == null;
    }

    @Override
    public String toString() {
        return "[parent=" + parent + ", action=" + action + ", state=" + getState() + "]";
    }
}


class Graph_Search {

    public Set<ArrayList<Integer>> explored = new HashSet<>();
    public Queue<Node> frontier= new LinkedList<>();

    //this will hold the sentence for each abbreviated action
    //ex. seq_actions.put("MML","Two missionaries to the left");
    HashMap<String,String> seq_actions=new HashMap<>();

    //The actions have been split into two categories
    String[] left_actions_arr= {"MML","MCL","CCL","CL","ML"};
    String[] right_actions_arr= {"MMR","MCR","CCR","CR","MR"};
    ArrayList<Node> children= new ArrayList<>();
    Node init,go;

    Graph_Search(Node initial, Node goal){
        this.init=initial;
        this.go=goal;
        seq_actions.put("MML","Two missionaries to the left");
        seq_actions.put("MCL","One missionary and one cannibal to the left");
        seq_actions.put("CCL","Two cannibals to the left");
        seq_actions.put("CL","One cannibal to the left");
        seq_actions.put("ML","One missionary to the left");
        seq_actions.put("MMR","Two missionaries the right");
        seq_actions.put("MCR","One missionary and one cannibal to the right");
        seq_actions.put("CCR","Two cannibals to the right");
        seq_actions.put("CR","One cannibal to the right");
        seq_actions.put("MR","One missionary to the right");

        BFS(initial,goal);

    }

    public ArrayList actions(Node parent){

        //we need to find out if the boat is on the left or right
        //if left, then we only can do valid actions to the right, vice versa
        ArrayList<Integer> state=parent.getState();
        ArrayList<Node> children= new ArrayList<>();
        ArrayList<Integer> temp=new ArrayList<>();


        if(left_or_right(state)=="right"){
            //iterates through all left actions below
            for(int i=0;i<left_actions_arr.length;i++) {
                //ArrayList<Integer> temp=new ArrayList<>(state);
                //temp=new ArrayList<>();
                temp.addAll(state);

                switch (left_actions_arr[i]) {
                    case "MML":
                        //ex: TWO MISSIONARIES LEFT
                        if(temp.get(6)<2){
                            break;
                        }else{
                            temp.set(6,temp.get(6)-2);
                            temp.set(0,temp.get(0)+2);
                            //move the boat to the left
                            temp.set(4,temp.get(4)-1);
                            temp.set(2,temp.get(2)+1);
                            if(isValid(temp)){
                                //create a child
                                Node child= new Node(temp,parent,"MML");
                                children.add(child);
                            }
                        }
                        break;
                    case"MCL":
                        if(temp.get(6)<1 || temp.get(5)<1){
                            break;
                        }else{
                            temp.set(6,temp.get(6)-1);
                            temp.set(5,temp.get(5)-1);
                            temp.set(0,temp.get(0)+1);
                            temp.set(1,temp.get(1)+1);
                            //move the boat to the left
                            temp.set(4,temp.get(4)-1);
                            temp.set(2,temp.get(2)+1);
                            if(isValid(temp)){
                                //create a child

                                Node child= new Node(temp,parent,"MCL");
                                children.add(child);
                            }
                        }
                        break;
                    case"CCL":
                        if(temp.get(5)<2){
                            break;
                        }else{
                            temp.set(5,temp.get(5)-2);
                            temp.set(1,temp.get(1)+2);
                            //move the boat to the left
                            temp.set(4,temp.get(4)-1);
                            temp.set(2,temp.get(2)+1);
                            if(isValid(temp)){
                                //create a child

                                Node child= new Node(temp,parent,"CCL");
                                children.add(child);
                            }
                        }
                        break;

                    case "CL":
                        if(temp.get(5)<1){
                            break;
                        }else{
                            temp.set(5,temp.get(5)-1);
                            temp.set(1,temp.get(1)+1);
                            //move the boat to the left
                            temp.set(4,temp.get(4)-1);
                            temp.set(2,temp.get(2)+1);
                            if(isValid(temp)){
                                //create a child

                                Node child= new Node(temp,parent,"CL");
                                children.add(child);
                            }
                        }
                        break;
                    case "ML":
                        if(temp.get(6)<1){
                            break;
                        }else{
                            temp.set(6,temp.get(6)-1);
                            temp.set(0,temp.get(0)+1);
                            //move the boat to the left
                            temp.set(4,temp.get(4)-1);
                            temp.set(2,temp.get(2)+1);
                            if(isValid(temp)){
                                //create a child

                                Node child= new Node(temp,parent,"ML");
                                children.add(child);
                            }
                        }
                        break;
                }

                temp.clear();
            }
        }else{
            //iterates through all right actions below
            for(int i=0;i<right_actions_arr.length;i++) {
                temp.addAll(state);
                switch (right_actions_arr[i]) {
                    case "MMR":
                        //ex: TWO MISSIONARIES RIGHT
                        if(temp.get(0)<2){
                            break;
                        }else{
                            temp.set(0,temp.get(0)-2);
                            temp.set(6,temp.get(6)+2);
                            //move the boat to the right
                            temp.set(4,temp.get(4)+1);
                            temp.set(2,temp.get(2)-1);
                            if(isValid(temp)){
                                //create a child
                                Node child= new Node(temp,parent,"MMR");
                                children.add(child);
                            }
                        }
                        break;
                    case "MCR":
                        if(temp.get(0)<1 || temp.get(1)<1){
                            break;
                        }else{
                            temp.set(0,temp.get(0)-1);
                            temp.set(1,temp.get(1)-1);
                            temp.set(6,temp.get(6)+1);
                            temp.set(5,temp.get(5)+1);
                            //move the boat to the right
                            temp.set(4,temp.get(4)+1);
                            temp.set(2,temp.get(2)-1);
                            if(isValid(temp)){
                                //create a child
                                Node child= new Node(temp,parent,"MCR");
                                children.add(child);
                            }
                        }
                        break;
                    case "CCR":
                        if(temp.get(1)<2){
                            break;
                        }else{
                            temp.set(1,temp.get(1)-2);
                            temp.set(5,temp.get(5)+2);
                            //move the boat to the right
                            temp.set(4,temp.get(4)+1);
                            temp.set(2,temp.get(2)-1);
                            if(isValid(temp)){
                                //create a child
                                Node child= new Node(temp,parent,"CCR");
                                children.add(child);
                            }
                        }
                        break;

                    case "CR":
                        if(temp.get(1)<1){
                            break;
                        }else{
                            temp.set(1,temp.get(1)-1);
                            temp.set(5,temp.get(5)+1);
                            //move the boat to the right
                            temp.set(4,temp.get(4)+1);
                            temp.set(2,temp.get(2)-1);
                            if(isValid(temp)){
                                //create a child
                                Node child= new Node(temp,parent,"CR");
                                children.add(child);
                            }
                        }
                        break;
                    case "MR":
                        if(temp.get(0)<1){
                            break;
                        }else{
                            temp.set(0,temp.get(0)-1);
                            temp.set(6,temp.get(6)+1);
                            //move the boat to the right
                            temp.set(4,temp.get(4)+1);
                            temp.set(2,temp.get(2)-1);
                            if(isValid(temp)){
                                //create a child
                                Node child= new Node(temp,parent,"MR");
                                children.add(child);
                            }
                        }
                        break;
                }
                temp.clear();
            }
        }

        return children;
    }

    public static String left_or_right(ArrayList<Integer> word){
        if(word.get(2)==1){
            return "left";
        }
        return "right";
    }

    public static boolean isValid(ArrayList<Integer> state){
        if(state.get(0)<state.get(1) && state.get(0)!=0){
            return false;
        }
        if(state.get(6)<state.get(5)&& state.get(6)!=0){
            return false;
        }
        return true;
    }

    public static boolean goal_test(Node state,Node goal){
        ArrayList<Integer> s= state.getState();
        ArrayList<Integer> g = goal.getState();
        for(int i=0;i<s.size();i++){
            if(s.get(i)!=g.get(i)){

                return false;
            }
        }
        return true;
    }


    public void result(Node child){
        ArrayList<String> solution=new ArrayList<>();
        while(!child.isRootNode()){
            solution.add(child.getAction());
            child=child.getParent();
        }

        System.out.println("\nHere are the actions in their order: \n");
        for(int i=solution.size()-1;i>=0;i--){
            System.out.println(seq_actions.get(solution.get(i)));
        }
    }


    public Node BFS(Node initial, Node goal){

        System.out.println("Searching for solution using BFS algorithm...\n");
//        *   node &lt;- a node with STATE = problem.INITIAL-STATE
        Node state=initial;
        Node solution=null;
//        *   if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
        if(goal_test(state,goal)){
            //return the solution
            System.out.println("We are already in the goal state. No action is required.");
            return state;
        }
        frontier.add(initial);

        boolean flag=true;

// *   loop do
        while(flag) {
// *      if EMPTY?(frontier) then return failure
            if(frontier.size()==0){
                return null;
            }
//          *      node &lt;- POP(frontier) // chooses the shallowest node in frontier
            Node parent = frontier.remove();
//            if (!explored.contains(parent.getState()))
//                continue;

//          *      add node.STATE to explored
            explored.add(parent.getState());
//          *      for each action in problem.ACTIONS(node.STATE) do
            children.addAll(actions(parent));

            for(int i=0;i<children.size();i++){

// *          child &lt;- CHILD-NODE(problem, node, action)
//                *          if child.STATE is not in explored or frontier then
                    if(!frontier.contains(children.get(i))&& !explored.contains(children.get(i).getState())){

// *                   if problem.GOAL-TEST(child.STATE) then return SOLUTION(child)
                        if(goal_test(children.get(i),goal)){
                            System.out.println("Solution was found");
                            System.out.println("We found a match between this state and the goal state");
                            result(children.get(i));
                            return children.get(i);
                        }

//                *     frontier &lt;- INSERT(child, frontier)
                        frontier.add(children.get(i));
                    }

            }
        }
        return null;
    }

    public static void main(String[] args){
        //this is the initial state which is expressed with an arraylist of integers
        //index 0 and 6 are for the missionaries
        //index 1 and 5 are for the cannibals
        //index 2 and 4 are for the location of the boat(right or left)
        //index 3 is for the river and will always stay constant
        ArrayList<Integer> INITIAL = new ArrayList<Integer>(Arrays.asList(0,0,0,1,1,3,3));

        //this is the goal state with 3 missionaries and 3 cannibals on the left side of the river
        ArrayList<Integer> GOAL=new ArrayList<Integer>(Arrays.asList(3,3,1,1,0,0,0));

        Node initial=new Node(INITIAL,null,null);
        Node goal=new Node(GOAL,null,null);

        Graph_Search instance = new Graph_Search(initial, goal);



    }
}
