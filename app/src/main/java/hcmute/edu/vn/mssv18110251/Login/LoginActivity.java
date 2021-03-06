package hcmute.edu.vn.mssv18110251.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.Admin.AdminActivity;
import hcmute.edu.vn.mssv18110251.CategoryActivity;
import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.Database.DatabaseHelper;
import hcmute.edu.vn.mssv18110251.FaceRecognizeActivity;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.R;

public class LoginActivity extends AppCompatActivity {
//    insert into account (password_account, phone_account, role_account) values ('a', '0798725333', 0)
    TextView go_to_register, phone, password, forget_password;

    Button btn_login;
    private AccountDAO accountDAO;
    private SharePreferenceClass SharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Log.d("Login", "-----------------------------------------------------------------");

        accountDAO = new AccountDAO(this);
        accountDAO.open();

        phone = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        Account account1 = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
        if(account1!=null){
            if(account1.getRole()==0){
                if(account1.getImage()!=null)
                {
                    Log.d("Login", "-----------------------------------------------------------------");
                    Intent check_face = new Intent(getApplicationContext(), FaceRecognizeActivity.class);
                    Log.d("Login", "-----------------------------------------------------------------");
//                    check_face.putExtra("FACE", account1.getImage());
                    Log.d("Login", "-----------------------------------------------------------------");
                    startActivity(check_face);
                } else {
                    Intent homePage = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(homePage);
                }
            }
        }

        forget_password = findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetpass = new Intent(getBaseContext(), ForgetPasswordActivity.class);
                startActivity(forgetpass);
            }
        });

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
                String phoneNumber = phone.getText().toString();
                String password_string = password.getText().toString();
                Account account = accountDAO.checkAccount(phoneNumber, password_string);
                if(account != null){
                    SharedPreferenceClass.getInstance(getBaseContext()).set("account", account);
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

//                    Account account1 = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");
                    if(account.getRole()==0) {
                        Log.d("LOGIN", String.valueOf(account.getId()));
                        Intent homePage = new Intent(getApplicationContext(), CategoryActivity.class);
                        homePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homePage);
                    }
                    if(account.getRole()==1){
                        Intent admin_intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(admin_intent);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}