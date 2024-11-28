import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 13: Distress Signal";

    private static class Packet {
        private boolean isInt;
        private int value;
        private ArrayList<Packet> sublist = new ArrayList<>();

        public Packet(){}
        public Packet(Packet child){
            isInt = false;
            sublist.add(child);
        }

        public boolean isInt(){
            return isInt;
        }

        public int size(){
            return sublist.size();
        }

        public Packet getSubPacket(int index){
            return sublist.get(index);
        }

        public int parse(String s, int index){
            int end = index;
            while (end < s.length() && Character.isDigit(s.charAt(end))){
                ++end;
            }

            if (end != index){
                isInt = true;
                value = Integer.parseInt(s.substring(index,end));
                return end;
            }
            isInt = false;
            if (s.charAt(++index) == ']'){
                return ++index;
            }

            Packet packet = new Packet();
            index = packet.parse(s,index);
            sublist.add(packet);
            while (s.charAt(index++) == ','){
                packet = new Packet();
                index = packet.parse(s,index);
                sublist.add(packet);
            }

            return index;
        }

        public int compare(Packet other){
            // Both integers
            if (isInt && other.isInt()){
                return Integer.compare(value,other.value);
            }
            // One is integer
            if (isInt){
                return new Packet(this).compare(other);
            }
            if (other.isInt()){
                return compare(new Packet(other));
            }

            // Both lists
            for (int i=0; i<Math.min(size(),other.size()); ++i){
                int comp = getSubPacket(i).compare(other.getSubPacket(i));
                if (comp != 0){
                    return comp;
                }
            }
            return Integer.compare(size(),other.size());
        }
    }

    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The packets that will be sorted
        ArrayList<Packet> packets = new ArrayList<>();
        // The index of the current pair of packets
        int index = 1;
        // The value to be returned
        int part1 = 0;

        // While there are more packets
        while (sc.hasNext()){
            // Read in two packets, ignoring whitespace
            Packet one = new Packet();
            one.parse(sc.nextLine(),0);
            Packet two = new Packet();
            two.parse(sc.nextLine(),0);

            // See if they're in the right order
            if (one.compare(two) != 1){
                part1 += index;
            }

            // Add the packets
            packets.add(one);
            packets.add(two);

            // Increase the pair index
            ++index;

            // Skip empty lines
            if (sc.hasNext()){
                sc.nextLine();
            }
        }

        // Add the two dividers
        Packet two = new Packet();
        two.parse("[[2]]",0);
        packets.add(two);
        Packet six = new Packet();
        six.parse("[[6]]",0);
        packets.add(six);
        
        // Sort the packets
        packets.sort((a,b) -> {
            return a.compare(b);
        });

        // The indices of the divider packets
        int part2 = (packets.indexOf(two)+1) * (packets.indexOf(six)+1);

        // Print the answer
        Library.print(part1,part2,name);
    }
}