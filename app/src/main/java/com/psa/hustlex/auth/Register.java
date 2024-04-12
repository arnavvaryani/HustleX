package com.psa.hustlex.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.psa.hustlex.R;
import com.psa.hustlex.database.AppDatabase;
import com.psa.hustlex.database.RoomDAO;
import com.psa.hustlex.screens.MainActivity;


public class Register extends AppCompatActivity {

    private EditText user,pass;
    private Button register;
    private TextView login;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);
        register = findViewById(R.id.button);
        login = findViewById(R.id.login);

        appDatabase = AppDatabase.geAppdatabase(Register.this);

        login.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        register.setOnClickListener(v -> registerUser(user.getText().toString().trim(),pass.getText().toString().trim()));

    }


    public void registerUser(String user,String pass){

        RoomDAO roomDAO = appDatabase.getRoomDAO();
        UsernamePassword temp = new UsernamePassword();
        temp.setUsername(user);
        temp.setPassword(pass);
        temp.setIsLoggedIn(0);

        UsernamePassword temp2 = roomDAO.getUserWithUsername(user);
        if(temp2==null) {
            roomDAO.Insert(temp);
            Toast.makeText(Register.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
            AppDatabase.destroyInstance();
            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(Register.this,"User Already Registered",Toast.LENGTH_SHORT).show();

    }
}
