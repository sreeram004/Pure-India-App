package com.example.admin.pureindia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText userName, password;
    String u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.nameTxt);
        password = (EditText) findViewById(R.id.passTxt);

        login = (Button) findViewById(R.id.logButton);



        userName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    userName.setText("");
                return false;

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u = String.valueOf(userName.getText());
                String p = String.valueOf(password.getText());


                if(p.equals("1234")){
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                    password.setText("");
                    changeScreen();
                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void changeScreen() {

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("username", u);
        startActivity(intent);
    }
}
