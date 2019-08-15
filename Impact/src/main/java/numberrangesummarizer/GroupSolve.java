package numberrangesummarizer;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.util.Scanner;
import java.util.List;

public class GroupSolve implements NumberRangeSummarizer {

    public Collection<Integer> collect(String input) {

        //String Validation
        if((input==null)){//string is null object
            System.out.println("___Error: String is Null___");
            return null;
        }
        else if(input.isEmpty()){//string is empty
            System.out.println("___Error: String is Empty___");
            return null;
        }
        else if(!digitCheck(input)){//check if string has digits and not any other illegal characters
            System.out.println("___Error: String contains illegal characters___");
            return null;
        }
        else if(!commaCheck(input)){//string not properly comma separated
            System.out.println("___Error: String is not properly comma separated___");
            return null;
        }
            input = input.trim();//remove Leading and trailing spaces
            input = (input.replaceAll("^,+", "")).replaceAll(",+$", "");//remove all leading and trailing commas
            Pattern pattern = Pattern.compile(",");
            List<Integer> output = pattern.splitAsStream(input).map(Integer::valueOf).collect(Collectors.toList());
            Collections.sort(output);
        return output;//a Collection of Integers
    }

    public String summarizeCollection(Collection<Integer> input) {

        if(input == null)
            return null;

        //Used to Iterate through the Collection
        Iterator<Integer> iterator = input.iterator();

        //Strings
        String output = "";
        String lower = "";//lower bound for sequence
        String upper = "";//upper bound for sequence
        String rangeStr = "";//stores upper and lower bound for sequential integers

        //Integers
        int cur = iterator.next();//current integer in Collection
        int nxt;//next integer in Collection
        int offset = 1;//measuring the distance between sequential numbers

        //Boolean: has a sequence been found
        boolean concat = false;

        //Loop while there are still more elements
        while(iterator.hasNext()){

            nxt = iterator.next();

            if((cur+offset) == nxt){
                concat = true;
                lower = Integer.toString(cur);//set lower bound
                upper = Integer.toString(nxt);//set upper bound
                rangeStr = lower+"-"+upper;
                offset++;
            }
            else if(!concat){
                //append current integer in the collection to string if the next integer is not in
                //sequence with current integer
                output = output+", "+cur;
                cur = nxt;
                offset = 1;//reset offset
            }
            else{
                //append rangeStr to output when the next integer is not in sequence with upper bound
                output = output+", "+rangeStr;
                concat = false;
                cur = nxt;
                offset = 1;//reset offset
            }

            //Final check for Stragglers
            if(!iterator.hasNext()){

                if(concat)//if final integer is in sequence append rangeStr to output
                    output = output +", "+ rangeStr;
                else//if final integer is not in sequence append cur
                    output = output +", "+ cur;
            }
        }//End Loop

        //Finally remove Initial Comma and Space from Output
        output = output.substring(2, output.length());

        return output;//a comma separated String
    }

    public boolean commaCheck(String input){

        boolean flag = false;
        String sect;//store section of string scanned

        //removing leading and trailing spaces from string
        input = input.trim();

        Scanner runThru = new Scanner(input);
        runThru = runThru.useDelimiter(",");
        while(runThru.hasNext()){
            sect = runThru.next();
            sect = sect.trim();//remove leading and trailing spaces around section of string
            flag = isNumeric(sect);
            if(!flag)
                break;
        }
        return flag;
    }

    public boolean digitCheck(String input){

        boolean flag = true;

        for(int i = 0; i<input.length();i++) {
            char c = input.charAt(i);
            if(Character.isDigit(c) || (c == ',') || (c == ' ')) {
                continue;
            }
            else {
                flag = false;//indicates that an illegal character was detected
                break;
            }
        }

        return flag;
    }

    public boolean isNumeric(String input) {
        try {

            int d = Integer.parseInt(input);
        }
        catch (NumberFormatException | NullPointerException nfe) {

            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        //Testing and object creation
        GroupSolve obj = new GroupSolve();

        //Pass String to collect method
        //Pass returned Collection of Integers straight to summarizeCollection method and print
        System.out.println("Result: "+obj.summarizeCollection(obj.collect("1,6,3,7,8,12,13,14,15,21,22,23,24,31")));
    }
}