package com.example.minicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.util.regex.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC,buttonBS;
    MaterialButton buttonDivide, buttonMultiply, buttonSubt, buttonAdd, buttonEquals, buttonMod;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        assignId(buttonC, R.id.button_c);
        assignId(buttonBS, R.id.button_open_bracket);
        assignId(buttonMod, R.id.button_close_bracket);
        assignId(button0, R.id.button_zero);
        assignId(button1, R.id.button_one);
        assignId(button2, R.id.button_two);
        assignId(button3, R.id.button_three);
        assignId(button4, R.id.button_four);
        assignId(button5, R.id.button_five);
        assignId(button6, R.id.button_six);
        assignId(button7, R.id.button_seven);
        assignId(button8, R.id.button_eight);
        assignId(button9, R.id.button_nine);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonSubt, R.id.button_subtract);
        assignId(buttonAdd, R.id.button_add);
        assignId(buttonEquals, R.id.button_equals);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_decimal);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    String checkRegex(String dataToCalculate) {
        char newOperator = dataToCalculate.charAt(dataToCalculate.length()-1);
        char lastChar = dataToCalculate.charAt(dataToCalculate.length()-2);
        String newDataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-2);
        if(lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '%' || lastChar == '.') {

            if(newOperator == lastChar) {
                dataToCalculate = newDataToCalculate + newOperator;
            } else if(newOperator == '+' || newOperator == '-' || newOperator == '*' || newOperator == '/' || newOperator == '%') {
                if(lastChar == '.') {
                    dataToCalculate = newDataToCalculate + lastChar;
                } else {
                    dataToCalculate = newDataToCalculate + newOperator;
                }
            } else if(newOperator == '.') {
                dataToCalculate = newDataToCalculate + lastChar + "0" + newOperator;
            }
        }  else {
            Pattern pattern = Pattern.compile("^(\\d+(\\.)?(\\d+)?)([\\+\\-\\*\\/\\%](\\d+(\\.)?(\\d+)?)?)*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(dataToCalculate);
            if (!matcher.find()) {
                dataToCalculate = newDataToCalculate + lastChar;
            }
        }
        return dataToCalculate;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        switch (buttonText) {
            case "AC":
                solutionTv.setText("0");
                resultTv.setText("0");
                return;
            case "=":
                resultTv.setText("" + MathExpressionSolver.solveExpression(dataToCalculate));
                return;
            case "C":
                solutionTv.setText("0");
                return;
        }

        char firstChar = dataToCalculate.charAt(0);

        if((firstChar == '0' || firstChar == '+' || firstChar == '-' || firstChar == '*' || firstChar == '/' || firstChar == '%') && !(buttonText.equals("x") || buttonText.equals(".")) && dataToCalculate.length()<2) {
            dataToCalculate = dataToCalculate.substring(1);
        }

        if(buttonText.equals("x")) {
            if(dataToCalculate.length()==1) {
                dataToCalculate = "0";
            } else {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        if(dataToCalculate.length()>2) {
            dataToCalculate = checkRegex(dataToCalculate);
        }

        solutionTv.setText(dataToCalculate);
    }

    String getResult(String data) {
        return "Calculated";
    }
}