package hcmute.edu.vn.mssv18110251.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.CategoryActivity;
import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.MainActivity;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.Model.Category;
import hcmute.edu.vn.mssv18110251.R;

public class LoginActivity extends AppCompatActivity {

    TextView go_to_register, email, password;

    Button btn_login;
    private AccountDAO accountDAO;
    private SharePreferenceClass SharedPreferenceClass;

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

        Account account1 = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
        if(account1!=null){
            if(account1.getRole()==0){
                Intent homePage = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(homePage);
            }
        }

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                Account account = accountDAO.checkAccount(email_string, password_string);
                if(account != null){
                    SharedPreferenceClass.getInstance(getBaseContext()).set("account", account);
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

                    Account account1 = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
                    if(account1.getRole()==0) {
                        Log.d("LOGIN", String.valueOf(account1.getId()));
                        Intent homePage = new Intent(getApplicationContext(), CategoryActivity.class);
                        startActivity(homePage);
                    }
                }
            }
        });

    }
}