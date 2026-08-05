package io.github.wangwang11112222.sdpencryptor;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
import java.util.HashMap;



import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText value;
    private EditText arg1;
    private EditText arg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.textEncryptedID);
        value = (EditText) findViewById(R.id.entryTextID);
        arg1 = (EditText) findViewById(R.id.argInput1ID);
        arg2 = (EditText) findViewById(R.id.argInput2ID);
        arg1.setText("1");
        arg2.setText("2");



    }

    public void handleClick2 (View view){

        String currentString = value.getText().toString();
        int input1=-100;
        int input2=-100;
        boolean error=false;

        try {
            input1 = Integer.parseInt(arg1.getText().toString());
        } catch (NumberFormatException e) {
            arg1.setError("Invalid Arg Input 1");
            result.setText("");
            error=true;

        }

        try {
        input2 = Integer.parseInt(arg2.getText().toString());
        } catch (NumberFormatException e) {
            arg2.setError("Invalid Arg Input 2");
            result.setText("");
            error=true;

        }

        if(input1 %2==0 || input1 %31==0 || input1 <1 || input1 >61){
            arg1.setError("Invalid Arg Input 1");
            result.setText("");
            error=true;

        }



        if(!currentString.matches(".*[a-zA-Z0-9].*")||currentString.isEmpty()){
            value.setError("Invalid Entry Text");
            result.setText("");
            error=true;

        }



        if(input2 < 1 || input2 > 61){
            arg2.setError("Invalid Arg Input 2");
            result.setText("");
            error=true;

        }

        if(!error) {

            result.setText(encrypt(currentString, input1, input2));
        }

        return;

    }


    public String encrypt(String currentString, int arg1, int arg2){

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




}