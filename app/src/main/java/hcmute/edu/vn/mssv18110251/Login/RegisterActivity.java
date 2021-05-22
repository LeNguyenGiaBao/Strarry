package hcmute.edu.vn.mssv18110251.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hcmute.edu.vn.mssv18110251.R;

public class RegisterActivity extends AppCompatActivity {

    TextView go_to_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        go_to_login = findViewById(R.id.go_to_login);
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
            }
        });
    }
}