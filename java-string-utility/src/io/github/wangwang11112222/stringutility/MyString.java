package io.github.wangwang11112222.stringutility;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;



public class MyString implements MyStringInterface {
    private String currentString;

    @Override
    public String getString() {
        return currentString;
    }


    @Override
    public void setString(String arg){
        if(arg.isEmpty()){
            throw new IllegalArgumentException("String Can Not Be Empty");
        }

        if(!arg.matches(".*[a-zA-Z0-9].*")){
            throw new IllegalArgumentException("String Must Contain at least one Character or Number");
        }
        currentString=arg;
    }
    @Override
    public int countAlphabeticWords(){
        if(currentString==null){
            throw new NullPointerException("String Can Not Be Null");
        }
        boolean check=false;
        int numWords=0;
        for(int i=0;i<currentString.length();i++){
            char c=currentString.charAt(i);
            if((!check) && ((c>=97 && c<=122)||(c>=65 && c<=90))){
                check=true;
                numWords++;
            }
            if(check && (c<65|| (c>90 && c<97)||c>122)) {
                check = false;
            }
        }
        return numWords;
    }
    @Override
    public String encrypt(int arg1, int arg2){

        HashMap<Integer, Character> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= 9; i++) {
                    map1.put(i, (char) ('0' + i));
                    map2.put((char) ('0' + i),i);
        }
        for (int i = 10; i <= 35; i++) {
                    map1.put(i, (char) ('A' + i - 10));
                    map2.put((char) ('A' + i - 10),i);
        }
        for (int i = 36; i <= 61; i++) {
                    map1.put(i, (char) ('a' + i - 36));
                    map2.put((char) ('a' + i - 36),i);
        }


        if(currentString==null){
            throw new NullPointerException("String Can Not Be Null");
        }
        else if(arg1%2==0 || arg1%31==0 || arg1<0 || arg2>61 || arg2<1 || arg2>=62){
            throw new IllegalArgumentException("The Argument is Illegal");
        }
        int value;
        for(int i=0;i<currentString.length();i++){
            char c=currentString.charAt(i);
            if(map2.containsKey(c)){
                value=map2.get(c);
                result.append(map1.get((value*arg1+arg2)%62));
            }else{
                result.append(c);
            }

        }

        return result.toString();
    }

    @Override
    public void convertDigitsToNamesInSubstring(int arg1, int arg2){
        HashMap<Character, String> map3 = new HashMap<>();

        map3.put('0', "Zero");
        map3.put('1', "One");
        map3.put('2', "Two");
        map3.put('3', "Three");
        map3.put('4', "Four");
        map3.put('5', "Five");
        map3.put('6', "Six");
        map3.put('7', "Seven");
        map3.put('8', "Eight");
        map3.put('9', "Nine");

        StringBuilder result = new StringBuilder();

        if(currentString==null){
            throw new NullPointerException("Current String Can Not Be Null");
        }else if(arg1<1 || arg1>arg2){
            throw new IllegalArgumentException("Argument Inputs are Illegal");
        }else if(arg2>currentString.length()){
            throw new MyIndexOutOfBoundsException("Out of Bound");
        }else{
            for(int i=0;i<currentString.length();i++){
                char c=currentString.charAt(i);
                if((i>=arg1-1 && i<=arg2-1) && map3.containsKey(c)){
                    result.append(map3.get(c));
                }else{
                    result.append(c);
                }
            }
            currentString=result.toString();
        }
    }


}