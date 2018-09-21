package com.example.jarfi.sispak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jarfi.sispak.Admin.adminmenu;
import com.example.jarfi.sispak.User.Menu.usermenu;

public class MainActivity extends AppCompatActivity {
    Button admin, user;
    EditText username, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin= findViewById(R.id.button);
        user= findViewById(R.id.button2);
        username = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usern();
            }
        });
    }

    private void usern(){
        Intent i = new Intent(getApplicationContext(), usermenu.class);
        startActivity(i);
    }
    private void admin(){
        String user=username.getText().toString().trim();
        String pas=pass.getText().toString().trim();
        if(user.equals("admin")&& pas.equals("admin"))
        {
            Intent i = new Intent(getApplicationContext(), adminmenu.class);
            startActivity(i);
            hapus();
        }
        else {
            Toast.makeText(this,"username and password salah!",Toast.LENGTH_LONG).show();
        }
    }

    private void hapus(){
        username.setText("");
        pass.setText("");
    }

}
