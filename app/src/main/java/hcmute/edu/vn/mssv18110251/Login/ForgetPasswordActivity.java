package hcmute.edu.vn.mssv18110251.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import hcmute.edu.vn.mssv18110251.CategoryActivity;
import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;
import hcmute.edu.vn.mssv18110251.R;
import hcmute.edu.vn.mssv18110251.VerifyOTPActivity;

public class ForgetPasswordActivity extends AppCompatActivity {

    private AppCompatButton btnVerify;
    LinearLayout layout_number_verify;
    TextInputLayout textInputPassword, textInputReplacePassword;
    TextView send_phone_number, txt_email, check_code;
    AccountDAO accountDAO;
    private EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    String Verify_Code, phoneNumber;
    Account account;
    EditText txt_password, txt_password_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        accountDAO = new AccountDAO(this);
        accountDAO.open();

        btnVerify = findViewById(R.id.btn_verify_reset_pass);
        layout_number_verify = findViewById(R.id.layout_number_verify);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputReplacePassword = findViewById(R.id.textInputReplacePassword);
        send_phone_number = findViewById(R.id.send_phone_number);
        txt_email = findViewById(R.id.editTextEmail);
        txt_password = findViewById(R.id.editTextPassword);
        txt_password_2 = findViewById(R.id.editTextReplacePassword);
        check_code = findViewById(R.id.check_code);
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        setupOTPInputs();


        btnVerify.setVisibility(View.GONE);
        layout_number_verify.setVisibility(View.GONE);
        textInputPassword.setVisibility(View.GONE);
        textInputReplacePassword.setVisibility(View.GONE);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "TEST", Toast.LENGTH_SHORT).show();
            }
        });

        send_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = txt_email.getText().toString();
                account = accountDAO.checkPhone(phoneNumber);
                if(account==null)
                {
                    createDialog("Can't find this phone number","Notification");
                    return;
                } else {
//                    Toast.makeText(getBaseContext(), "TEST", Toast.LENGTH_SHORT).show();
                    layout_number_verify.setVisibility(View.VISIBLE);

                    String phoneNumberToSend = phoneNumber.substring(1,phoneNumber.length());
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumberToSend)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(ForgetPasswordActivity.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Verify_Code = VerificationId;
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
            }
        });

        check_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCode1.getText().toString().trim().isEmpty()
                        ||inputCode2.getText().toString().trim().isEmpty()
                        ||inputCode3.getText().toString().trim().isEmpty()
                        ||inputCode4.getText().toString().trim().isEmpty()
                        ||inputCode5.getText().toString().trim().isEmpty()
                        ||inputCode6.getText().toString().trim().isEmpty()){
                    createDialog("Please enter the OTP valid !","Notification");
                    return;
                }
                String code = inputCode1.getText().toString() + inputCode2.getText().toString()
                        + inputCode3.getText().toString() + inputCode4.getText().toString()
                        +inputCode5.getText().toString() + inputCode6.getText().toString();
                if(Verify_Code!=null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            Verify_Code,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        btnVerify.setVisibility(View.VISIBLE);
                                        textInputPassword.setVisibility(View.VISIBLE);
                                        textInputReplacePassword.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        createDialog("The OTP is not correct !","Notification");
                                    }
                                }
                            });
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txt_password.getText().toString();
                String password2 = txt_password_2.getText().toString();
                if(password.equals(password2)){
                    account.setPassword(password);
                    if(accountDAO.update(account)){
                        Toast.makeText(getBaseContext(), "Change successfully", Toast.LENGTH_SHORT).show();
                        Log.d("ForgetPasswordActivity", "Update successfully");
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Password is invalid", Toast.LENGTH_SHORT).show();
                }
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

    //Tu dong chuyen qua EditText tiep theo
    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}