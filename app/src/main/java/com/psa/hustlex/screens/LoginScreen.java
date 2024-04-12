package com.psa.hustlex.screens;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.psa.hustlex.R;
import com.psa.hustlex.database.AppDatabase;
import com.psa.hustlex.database.RoomDAO;
import com.psa.hustlex.models.auth;

public class LoginScreen extends AppCompatActivity {

    private EditText user,pass;
    private Button login;
    private TextView register;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);
        login = findViewById(R.id.button);
        register = findViewById(R.id.register);

        appDatabase = AppDatabase.geAppdatabase(LoginScreen.this);

        RoomDAO roomDAO = appDatabase.getRoomDAO();
        auth temp = roomDAO.getLoggedInUser();
        if(temp!=null){
            Intent intent = new Intent(LoginScreen.this, ReminderScreen.class);
            startActivity(intent);
            finish();
        }


        login.setOnClickListener(v -> loginUser(user.getText().toString().trim(),pass.getText().toString().trim()));

        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
            finish();
        });

    }

    public void loginUser(String user,String pass){

        RoomDAO roomDAO = appDatabase.getRoomDAO();
        auth temp = roomDAO.getUserWithUsername(user);
        Toast.makeText(this, temp.getPassword(), Toast.LENGTH_SHORT).show();

            Toast.makeText(LoginScreen.this,"Invalid username",Toast.LENGTH_SHORT).show();


            if(temp.getPassword().equals(pass)){
                temp.setIsLoggedIn(1);
                roomDAO.Update(temp);
                AppDatabase.destroyInstance();
                Intent intent = new Intent(LoginScreen.this, ReminderScreen.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(LoginScreen.this,"Invalid Password",Toast.LENGTH_SHORT).show();
            }


    }
}
