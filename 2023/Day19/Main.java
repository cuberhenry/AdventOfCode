import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 19: Aplenty";

    // A comparison to decide where to put an item
    private static class Rule {
        // The category being compared
        private int category;
        // The comparison
        private boolean lessThan;
        // The benchmark for the comparison
        private int rating;
        // The receiving workflow if the comparison succeeds
        private String result;

        // Parsing constructor
        public Rule(String rule){
            // Has a comparison
            if (rule.contains(":")){
                category = switch(rule.charAt(0)){
                    case 'x' -> 0;
                    case 'm' -> 1;
                    case 'a' -> 2;
                    default -> 3;
                };
                lessThan = rule.charAt(1) == '<';
                rating = Integer.parseInt(rule.substring(2,rule.indexOf(':')));
                result = rule.substring(rule.indexOf(':')+1);
            }else{
                // Set it to always pass
                category = 0;
                lessThan = false;
                rating = -1;
                result = rule;
            }
        }

        // Whether the comparison succeeds with the given item
        public boolean passes(int[] item){
            return item[category] != rating && (lessThan ^ (item[category] > rating));
        }

        // The receiving workflow
        public String getResult(){
            return result;
        }

        // Find the result on this rule for a range of all four values
        public State split(State state){
            // Get the ranges
            int[][] ranges = state.getRanges();
            // The whole range succeeds
            if (ranges[category][0] > rating && !lessThan
             || ranges[category][1] < rating && lessThan){
                state.setWorkflow(result);
                return null;
            }
            // The whole range fails
            if (ranges[category][0] >= rating && lessThan
             || ranges[category][1] <= rating && !lessThan){
                state.nextRule();
                return null;
            }

            // Copy the ranges to a new state
            int[][] upperRanges = new int[4][];
            for (int i=0; i<4; ++i){
                upperRanges[i] = ranges[i].clone();
            }

            // The lower range succeeds
            if (lessThan){
                upperRanges[category][0] = rating;
                ranges[category][1] = rating - 1;
                State newState = new State(upperRanges,state.getWorkflow(),state.getRuleIndex()+1);
                state.setWorkflow(result);
                return newState;
            }
            // The higher range succeeds
            ranges[category][1] = rating;
            upperRanges[category][0] = rating + 1;
            state.nextRule();
            return new State(upperRanges,result);
        }
    }

    // A list of ranges and the current rule
    private static class State {
        private int[][] ranges;
        private String workflow;
        private int ruleIndex;

        // Constructor at the beginning of workflow
        public State(int[][] r, String w){
            this(r,w,0);
        }

        // Constructor in a workflow
        public State(int[][] r, String w, int i){
            ranges = r;
            workflow = w;
            ruleIndex = i;
        }

        // Getters
        public int[][] getRanges(){
            return ranges;
        }

        public String getWorkflow(){
            return workflow;
        }

        public int getRuleIndex(){
            return ruleIndex;
        }

        // Set workflow, resetting index
        public void setWorkflow(String w){
            workflow = w;
            ruleIndex = 0;
        }

        // Increment index
        public void nextRule(){
            ++ruleIndex;
        }

        // The number of distinct combinations in these ranges
        public long count(){
            long count = 1;
            for (int i=0; i<4; ++i){
                count *= ranges[i][1] - ranges[i][0] + 1;
            }
            return count;
        }
    }

    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        String line = sc.nextLine();
        // The rules in each workflow
        HashMap<String,Rule[]> workflows = new HashMap<>();
        while (!line.isBlank()){
            // Get the workflow's name
            String name = line.substring(0,line.indexOf('{'));
            // Get the string for each rule
            String[] split = line.substring(line.indexOf('{')+1,line.length()-1).split(",");
            // Create the rules
            Rule[] rules = new Rule[split.length];
            for (int i=0; i<split.length; ++i){
                rules[i] = new Rule(split[i]);
            }
            // Add them to the map
            workflows.put(name,rules);
            line = sc.nextLine();
        }
        
        // Take in all the specific items
        while (sc.hasNext()){
            // Get the next item as an int array
            int[] item = Library.intSplit(sc.nextLine().substring(3),",.=|}");
            // Get the starting workflow
            Rule[] workflow = workflows.get("in");
            for (int i=0; i<workflow.length; ++i){
                // If the workflow passes
                if (workflow[i].passes(item)){
                    // Switch to the new workflow
                    String result = workflow[i].getResult();
                    if (workflows.containsKey(result)){
                        workflow = workflows.get(workflow[i].getResult());
                        i = -1;
                        continue;
                    }
                    // If approved, add all ratings
                    if (result.equals("A")){
                        part1 += Library.sum(item);
                    }
                    // Item checking is done
                    break;
                }
            }
        }

        // Create a queue for checking the whole range of ratings
        LinkedList<State> queue = new LinkedList<>();
        queue.add(new State(new int[][] {{1,4000},{1,4000},{1,4000},{1,4000}},"in"));

        // Continue until all ranges have been checked
        while (!queue.isEmpty()){
            // Get the next range
            State state = queue.remove();
            // Get the current rule for the range
            Rule rule = workflows.get(state.getWorkflow())[state.getRuleIndex()];
            // Split the range on the current rule
            State other = rule.split(state);

            // If the new state has been approved
            if (state.getWorkflow().equals("A")){
                // Add the number of rating combos
                part2 += state.count();
            }else if (!state.getWorkflow().equals("R")){
                // If not rejected, save it for later
                queue.add(state);
            }

            // If the range was split on the rule
            if (other != null){
                if (other.getWorkflow().equals("A")){
                    part2 += other.count();
                }else if (!other.getWorkflow().equals("R")){
                    queue.add(other);
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}