package hcmute.edu.vn.mssv18110251.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.R;

public class LoginActivity extends AppCompatActivity {

    TextView go_to_register, email, password;

    Button btn_login;
    private AccountDAO accountDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountDAO = new AccountDAO(this);
        accountDAO.open();

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        go_to_register = findViewById(R.id.go_to_register);
        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent_register);
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                Toast.makeText(LoginActivity.this, email_string, Toast.LENGTH_SHORT);
                Account account = accountDAO.checkAccount(email_string, password_string);

                if(account != null){
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT);
                }
            }
        });

    }
}