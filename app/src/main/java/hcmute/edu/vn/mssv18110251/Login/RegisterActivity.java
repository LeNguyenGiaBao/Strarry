package hcmute.edu.vn.mssv18110251.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.ProductManage;
import hcmute.edu.vn.mssv18110251.R;
import hcmute.edu.vn.mssv18110251.VerifyOTPActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView go_to_login, txt_email, txt_password, txt_password_2;
    Button btn_register;
    private AccountDAO accountDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountDAO = new AccountDAO(this);
        accountDAO.open();

        txt_email = findViewById(R.id.editTextEmail);
        txt_password = findViewById(R.id.editTextPassword);
        txt_password_2 = findViewById(R.id.editTextReplacePassword);

        go_to_login = findViewById(R.id.go_to_login);
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register = findViewById(R.id.RegisterButton);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String password2 = txt_password_2.getText().toString();

                if(!password.equals(password2)){
                    Toast.makeText(getBaseContext(), "Password is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(accountDAO.checkPhone(phoneNumber)!=null)
                {
                    createDialog("The phone number is existed !","Notification");
                    return;
                } else {
                    String phoneNumberToSend = phoneNumber.substring(1,phoneNumber.length());
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+84"+phoneNumberToSend)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(RegisterActivity.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(RegisterActivity.this, VerifyOTPActivity.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    intent.putExtra("password",password);
                                    intent.putExtra("VerificationResetId",VerificationId);
                                    startActivity(intent);
                                }

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    createDialog(e.getMessage(),"Notification");
                                }
                            }).build();
                    PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

                }
//                byte[] byteArray = new byte[0];
////                Toast.makeText(RegisterActivity.this, password, Toast.LENGTH_LONG).show();
//                if(password.equals(password2)){
//                    Account account = new Account("", password, "", "", 0, phone, byteArray);
//                    boolean success = accountDAO.addAccount(account);
//                    if(success) {
//                        Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                }
//                else{
//                    Toast.makeText(RegisterActivity.this, "False", Toast.LENGTH_LONG).show();
//                }
            }
        });

    }

    public void createDialog(String text, String title){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("OK",null)
                .setTitle(title)
                .setMessage(text)
                .create();
        alertDialog.show();
    }
}