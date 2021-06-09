package hcmute.edu.vn.mssv18110251.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import hcmute.edu.vn.mssv18110251.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private AppCompatButton btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btnVerify = findViewById(R.id.btn_verify_reset_pass);

        btnVerify.setVisibility(View.VISIBLE);

    }
}