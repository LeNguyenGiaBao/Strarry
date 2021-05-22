package hcmute.edu.vn.mssv18110251.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hcmute.edu.vn.mssv18110251.R;

public class LoginActivity extends AppCompatActivity {

    TextView go_to_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        go_to_register = findViewById(R.id.go_to_register);
        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent_register);
            }
        });

    }
}