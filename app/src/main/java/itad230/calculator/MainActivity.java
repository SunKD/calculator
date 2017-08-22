package itad230.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String Tag = "MainActivity";
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bAdd, bSub, bMulti, bDivide, bDecimal, bEqual, bC, bCE, bDel, bPlusMinus;

    private TextView display;
    private TextView opeCode;
    private double num1 = Double.NaN;
    private double num2;

    private final char addition = '+';
    private final char subtract = '-';
    private final char divide = '/';
    private final char multiply = '*';
    private final char equal = '=';
    private char current_action;

    private String saveInput;
    private String saveOutput;
    private boolean accumulated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.d(Tag, "App Created");

        b0 = (Button) findViewById(R.id.zero);
        b1 = (Button) findViewById(R.id.one);
        b2 = (Button) findViewById(R.id.two);
        b3 = (Button) findViewById(R.id.three);
        b4 = (Button) findViewById(R.id.four);
        b5 = (Button) findViewById(R.id.five);
        b6 = (Button) findViewById(R.id.six);
        b7 = (Button) findViewById(R.id.seven);
        b8 = (Button) findViewById(R.id.eight);
        b9 = (Button) findViewById(R.id.nine);

        bAdd = (Button) findViewById(R.id.add);
        bSub = (Button) findViewById(R.id.sub);
        bMulti = (Button) findViewById(R.id.multiply);
        bDivide = (Button) findViewById(R.id.divide);
        bDecimal = (Button) findViewById(R.id.decimal);
        bEqual = (Button) findViewById(R.id.equal);
        bC = (Button) findViewById(R.id.c);
        bCE = (Button) findViewById(R.id.ce);
        bDel = (Button) findViewById(R.id.del);
        bPlusMinus = (Button) findViewById(R.id.plusminus);

        display = (TextView) findViewById(R.id.input2);
        opeCode = (TextView) findViewById(R.id.log);

        b0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("0");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printNum("1");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("2");
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("3");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("4");
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printNum("5");
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("6");
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("7");
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printNum("8");
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                printNum("9");
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                calculate(addition);
            }
        });

        bSub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(subtract);
            }
        });

        bMulti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(multiply);

            }
        });

        bDivide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(divide);
            }
        });

        bEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(equal);
                //setting the value and current action to default
                num1 = Double.NaN;
                current_action = '0';
            }
        });

        //printing decimal
        bDecimal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String puct = display.getText().toString();
                //if the current input contains the decimal, ignore it
                if(puct.contains("."))
                    display.setText(display.getText() +"");
                else
                    display.setText(display.getText() + ".");
            }
        });

        //clear all the values and set to default
        bC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    display.setText("0");
                    opeCode.setText("Operation Code: ");
                    num1 = Double.NaN;
                    num2 = 0;
                    current_action = '0';
                    accumulated = false;
            }
        });

        //clearing only the current input
        bCE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    display.setText("0");
            }
        });

        //delete each digit
        bDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(display.getText().toString() == "0"||
                        display.getText().toString().length() == 0){
                    display.setText(display.getText() + "0");
                }
                else {
                    String value = display.getText().toString();
                    display.setText(value.substring(0, value.length() - 1));
                }
            }
        });

        //to change the value negative or positive
        bPlusMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (display.getText().toString() == "0")
                    display.setText(display.getText().toString());
                else{
                    double value = Double.parseDouble(display.getText().toString());
                    value = -value;
                    display.setText(""+value);
                }
            }
        });
    }

    //printing numbers
    private void printNum(String button){
        //if number keeps accumlating
        if(accumulated){
            accumulated = false;
            clear();
            display.setText(display.getText() + button);
        }
        else{
            check0();
            display.setText(display.getText() + button);
        }
    }

    //if the input is set to 0(default case)
    private void check0(){
        if(display.getText().toString().equals("0"))
            clear();
    }

    //to clear the current input screen
    private void clear(){
        display.setText("");
    }

    //calculate with num1 and num2 with saved current action
    private void calculate(){
        if(!Double.isNaN(num1)){
            num2 = Double.parseDouble(display.getText().toString());
            display.setText("");
            if(current_action == addition)
                num1 = this.num1 + num2;
            else if(current_action == subtract)
                num1 = this.num1 -num2;
            else if(current_action == multiply)
                num1 = this.num1 * num2;
            else if(current_action == divide)
                num1 = this.num1 / num2;

            accumulated = true;
        }
        else{
            num1 = Double.parseDouble(display.getText().toString());
            accumulated = true;
        }
    }

    //to simplify each operator
    private void calculate(char operator) {
        calculate();
        current_action = operator;
        clear();
        display.setText("" + num1);
        opeCode.setText("Operation Code: ");
        opeCode.setText(opeCode.getText() + String.valueOf(operator) + ",  Accum: " + num1);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(Tag, "Data saved");

        //storing the input box
        saveInput = display.getText().toString();
        savedInstanceState.putString("input", saveInput);

        //storing the output box
        saveOutput = opeCode.getText().toString();
        savedInstanceState.putString("output", saveOutput);

        //storing the first value to calculate
        savedInstanceState.putDouble("saveNum1", num1);

        //storing whether its accumulated
        savedInstanceState.putBoolean("saveAccum", accumulated);

        //storing the current action
        savedInstanceState.putChar("saveAction", current_action);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Tag, "App Restored");

        String savedInput = savedInstanceState.getString("input");
        display.setText(savedInput); //display the saved input
        String savedOutput = savedInstanceState.getString("output");
        opeCode.setText(savedOutput); //display saved output

        double savedNum1 = savedInstanceState.getDouble("saveNum1");
        num1 = savedNum1;

        boolean savedAccum = savedInstanceState.getBoolean("saveAccum");
        accumulated = savedAccum;

        char savedAction = savedInstanceState.getChar("saveAction");
        current_action = savedAction;
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(Tag, "App Started");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(Tag, "App Resumed");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(Tag, "App Paused");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(Tag, "App Stopped");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(Tag, "App Destroyed");
    }
}
