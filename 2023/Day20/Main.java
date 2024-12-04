import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 20: Pulse Propagation";

    // A parent class for communication modules
    private static abstract class Module {
        // The name of the modules
        private String name;
        // The number of each type of pulse this module has sent
        private long lows = 0;
        private long highs = 0;
        // The list of modules that this module sends signals to
        private String[] outputs;

        // Parsing constructor
        public Module(String line){
            name = line.substring(1,3);
            outputs = line.substring(7).split(", ");
        }

        // [from, pulse, to]
        public abstract ArrayList<String[]> receive(String[] pulse);

        // Getters
        public String getName(){
            return name;
        }

        public long lows(){
            return lows;
        }

        public long highs(){
            return highs;
        }

        // Send each of the modules in output a signal
        public ArrayList<String[]> output(boolean value){
            ArrayList<String[]> output = new ArrayList<>();
            for (String out : outputs){
                output.add(new String[] {name,value ? "high" : "low",out});
                if (value){
                    ++highs;
                }else{
                    ++lows;
                }
            }
            return output;
        }

        // Whether one of the outputs is the given target
        public boolean hasOutput(String target){
            for (String output : outputs){
                if (output.equals(target)){
                    return true;
                }
            }
            return false;
        }
    }

    // A module that stores a boolean value
    private static class FlipFlop extends Module {
        // The flip-flop's value
        private boolean value = false;

        // Parser constructor
        public FlipFlop(String line){
            super(line);
        }

        // Receive a pulse
        public ArrayList<String[]> receive(String[] pulse){
            // Ignore high pulses
            if (pulse[1].equals("low")){
                // Flip the value
                value = !value;
                // Send it
                return output(value);
            }
            // Return an empty list
            return new ArrayList<String[]>();
        }
    }

    // A NAND module
    private static class Conjunction extends Module {
        // The list of inputs and their most recent values
        private HashMap<String,Boolean> inputs = new HashMap<>();

        // Parser constructor
        public Conjunction(String line){
            super(line);
        }

        // Add the input with a default false
        public void addInput(String input){
            inputs.put(input,false);
        }

        // Output true if one input is false
        public boolean getOutput(){
            return inputs.containsValue(false);
        }

        // Receive a signal
        public ArrayList<String[]> receive(String[] pulse){
            inputs.put(pulse[0],pulse[1].equals("high"));
            return output(getOutput());
        }
    }

    public static void main(String args[]) {
        // Get the list of modules
        String[] input = Library.getStringArray(args);
        // Modules by their names
        HashMap<String,Module> modules = new HashMap<>();
        // Outputs of the broadcaster
        String[] broadcaster = null;

        // The module that sends a signal to rx
        String target = "";

        // Loop through each module
        for (String module : input){
            // Save the outputs for the broadcaster module
            if (module.charAt(0) == 'b'){
                broadcaster = module.substring(15).split(", ");
            }else{
                // Get the name of the module
                String name = module.substring(1,3);
                // Create and add the module
                if (module.charAt(0) == '%'){
                    modules.put(name,new FlipFlop(module));
                }else{
                    modules.put(name,new Conjunction(module));
                }
                // Save the module that sends a signal to rx
                if (modules.get(name).hasOutput("rx")){
                    target = name;
                }
            }
        }

        // Add conjunctions' inputs
        for (Module module : modules.values()){
            if (module instanceof Conjunction mod){
                for (Module m : modules.values()){
                    if (m.hasOutput(module.getName())){
                        mod.addInput(m.getName());
                    }
                }
            }
        }

        // The modules that populate the target
        HashSet<String> flags = new HashSet<>();
        for (String module : modules.keySet()){
            if (modules.get(module).hasOutput(target)){
                flags.add(module);
            }
        }
        // The numbers that determine each flag
        HashSet<Integer> numbers = new HashSet<>();

        // The answer to the problem
        long part1 = 0;
        long part2 = 1;

        // Continue until both answers are filled in
        for (int i=0; part1 == 0 || part2 == 1; ++i){
            // Save the results at 1000 button pressess
            if (i == 1000){
                // Gather the highs and lows from each module
                int highs = 0;
                int lows = 1000 * (broadcaster.length + 1);
                for (Module module : modules.values()){
                    lows += module.lows();
                    highs += module.highs();
                }
                part1 = lows * highs;
            }

            // Find the number of presses to send rx a high pulse
            if (numbers.size() == flags.size()){
                for (int num : numbers){
                    part2 = Library.LCM(part2,num);
                }
            }

            // Create a queue of pulses to simulate
            LinkedList<String[]> queue = new LinkedList<>();
            // Add each initial module
            for (String name : broadcaster){
                queue.add(new String[] {"broadcaster","low",name});
            }

            // Continue until all pulses have propogated
            while (!queue.isEmpty()){
                // Get the pulse
                String[] line = queue.remove();
                String name = line[2];

                // Skip if the pulse is going nowhere
                if (!modules.containsKey(name)){
                    continue;
                }

                // Simulate this pulse
                Module module = modules.get(name);
                queue.addAll(module.receive(line));

                // If the module is the last one in a number, add the number
                if (flags.contains(module.getName()) && ((Conjunction)module).getOutput()){
                    numbers.add(i+1);
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}