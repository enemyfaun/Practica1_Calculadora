package ipn.cecyt9.practica1_calculadora;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    double numero1, numero2, resultado;
    char signo;
    boolean thereIsResultAsNumber = false, signoOper = false, functionTrigo = false;

    public void onClickButtonNumber(View myView){
        TextView tv = findViewById(R.id.textView);
        Button button = (Button) myView;
        String resp = tv.getText().toString();
        String number = button.getText().toString();

        if((thereIsResultAsNumber && signo == ' ') || resp.equals("ERROR")) {
            onClickButtonAC(myView);
            tv.setText(number);
        } else if(!functionTrigo) {
            tv.setText(String.format("%s%s", resp, number));
        } else{
            resp = resp.substring(0, resp.length()-1);
            tv.setText(String.format("%s%s)", resp, number));
        }
    }
    
    public void onClickButtonOperation(View myView){
        TextView tv = findViewById(R.id.textView);
        Button button = (Button) myView;
        char signoButton = button.getText().charAt(0);
        try {
            if(signoOper || functionTrigo){
                signoOper = false;
                throw new Exception();
            }
            numero1 = Double.parseDouble(String.valueOf(tv.getText()));
            signo = signoButton;
            System.out.println(numero1);
            System.out.println(signo);
            onClickButtonNumber(myView);
            signoOper = true;

        } catch(Exception e){

            if(thereIsResultAsNumber) {
                numero1 = resultado;
                signo = signoButton;
                tv.setText(String.valueOf(numero1).replace(".0", ""));
                tv = findViewById(R.id.resultado);
                tv.setText("");
                onClickButtonNumber(myView);
                thereIsResultAsNumber = false;

            } else if((signoButton == '+' || signoButton == '-') && !functionTrigo) {

                onClickButtonNumber(myView);

            } else {
                tv.setText("ERROR");
                restart();
            }

        }

    }

    public void onClickButtonTrigonometry(View myView){
        onClickButtonAC(myView);
        TextView tv = findViewById(R.id.textView);
        Button button = (Button) myView;
        String oper = button.getText().toString();

        if(oper.equals("sin")){
            signo = 's';
            tv.setText("sin()");
        } else if(oper.equals("cos")){
            signo = 'c';
            tv.setText("cos()");
        }

        functionTrigo = true;
    }

    public void onClickButtonResult(View myView){
        TextView tv = findViewById(R.id.textView);
        String tvText = tv.getText().toString();
        String resultadoText = "";

        try {
            if(!functionTrigo) {
                System.out.println("signo "+signo);
                int position = tvText.indexOf(signo);
                System.out.println("posicion "+position);
                System.out.println(tvText);
                String number = tvText.substring(position+1, tvText.length());
                System.out.println("numero "+number);
                numero2 = Double.parseDouble(number);


                System.out.println("numero" + numero2);

                if (signo == '+')
                    resultado = numero1 + numero2;
                else if (signo == '-')
                    resultado = numero1 - numero2;
                else if (signo == '*')
                    resultado = numero1 * numero2;
                else if (signo == '/')
                    resultado = numero1 / numero2;
                else if (signo == '^')
                    resultado = Math.pow(numero1, numero2);
                else
                    resultado = numero2;

                System.out.println(resultado);


            } else{

                double number = Math.toRadians(Double.parseDouble(tvText.substring(4, tvText.length()-1)));
                if(signo == 's')
                    resultado = Math.sin(number);
                else if(signo == 'c')
                    resultado = Math.cos(number);

            }

            tv = findViewById(R.id.resultado);
            resultadoText = String.valueOf(resultado);
            int pos = resultadoText.indexOf(".0");

            if(pos == resultadoText.length()-2)
                resultadoText = String.valueOf(resultado).replace(".0", "");

            if (resultadoText.length() >= 10)
                resultadoText = resultadoText.substring(0, 11);

            tv.setText(resultadoText);
            thereIsResultAsNumber = true;
        } catch(Exception e){
            System.out.println(e);
            tv.setText("ERROR");
        }
        restart();
    }

    public void onClickButtonAC(View myView){
        TextView tv = findViewById(R.id.textView);
        tv.setText("");
        tv = findViewById(R.id.resultado);
        tv.setText("");
        thereIsResultAsNumber = false;
        restart();
    }

    public void onClickButtonDelete(View myView){
        TextView tv = findViewById(R.id.textView);
        try {
            String tvText = tv.getText().toString();
            if (!tvText.equals("ERROR")){
                if(functionTrigo){
                    int lenght = tvText.length();
                    if(tvText.charAt(lenght-2) != '(') {
                        tvText = tvText.substring(0, lenght-2) + ')';
                        tv.setText(tvText);
                    } else {
                        tv.setText("");
                        functionTrigo = false;
                    }

                } else if(!thereIsResultAsNumber) {
                    tvText = tvText.substring(0, tvText.length() - 1);
                    tv.setText(tvText);
                    tv = findViewById(R.id.resultado);
                    tv.setText("");
                }
            } else{
                tv.setText("");
                tv = findViewById(R.id.resultado);
                tv.setText("");
            }
        } catch(Exception e){

        }
    }

    public void restart(){
        numero1 = 0;
        numero2 = 0;
        signo = ' ';
        signoOper = false;
        functionTrigo = false;
    }
}