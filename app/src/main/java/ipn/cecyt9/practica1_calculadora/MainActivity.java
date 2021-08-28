package ipn.cecyt9.practica1_calculadora;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }
    double numero1, numero2, resultado;
    char signo;
    boolean thereIsResultAsNumber = false;

    public void onClickButtonNumber(View myView){
        TextView tv = findViewById(R.id.textView);
        Button button = (Button) myView;
        if(thereIsResultAsNumber && signo == ' ')
            onClickButtonAC(myView);
        String resp = tv.getText().toString();
        String number = button.getText().toString();
        tv.setText(String.format("%s%s", resp, number));
    }
    
    public void onClickButtonOperation(View myView){
        TextView tv = findViewById(R.id.textView);
        Button button = (Button) myView;
        signo = button.getText().charAt(0);

        try {
            numero1 = Double.parseDouble(tv.getText().toString());
            System.out.println(numero1);
            System.out.println(signo);
            onClickButtonNumber(myView);
        } catch(Exception e){
            if(thereIsResultAsNumber) {
                numero1 = resultado;
                tv.setText(String.valueOf(numero1).replace(".0", ""));
                tv = findViewById(R.id.resultado);
                tv.setText("");
                onClickButtonNumber(myView);
            } else {
                tv.setText("ERROR");
                restart();
            }
        }
    }

    public void onClickButtonResult(View myView){
        TextView tv = findViewById(R.id.textView);

        try {
            String sobrante = String.valueOf(numero1).replace(".0", "") + signo;
            System.out.println(sobrante);
            System.out.println(tv.getText().toString());
            String number = tv.getText().toString().replace(sobrante, "");
            System.out.println(number);
            numero2 = Double.parseDouble(number);
            System.out.println(numero2);

            if (signo == '+')
                resultado = numero1 + numero2;
            else if (signo == '-')
                resultado = numero1 - numero2;
            else if (signo == '*')
                resultado = numero1 * numero2;
            else if (signo == '/')
                resultado = numero1 / numero2;
            else
                resultado = Double.parseDouble(tv.getText().toString());

            System.out.println(resultado);
            tv = findViewById(R.id.resultado);
            String resultadoText = String.valueOf(resultado).replace(".0", "");
            if(resultadoText.length() >= 10)
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
                if(!thereIsResultAsNumber) {
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
    }
}