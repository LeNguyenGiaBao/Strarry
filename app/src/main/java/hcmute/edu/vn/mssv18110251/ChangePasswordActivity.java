package hcmute.edu.vn.mssv18110251;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110251.Adapter.SharePreferenceClass;
import hcmute.edu.vn.mssv18110251.DAO.AccountDAO;
import hcmute.edu.vn.mssv18110251.Model.Account;

public class ChangePasswordActivity extends AppCompatActivity {

    Button btn_change_password;
    EditText password1, password2, password3;
    private SharePreferenceClass SharedPreferenceClass;
    private AccountDAO accountDAO;
    ImageView back_button, image_account;
    TextView name_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        accountDAO = new AccountDAO(this);
        accountDAO.open();

        Account account = (Account)SharedPreferenceClass.getInstance(getBaseContext()).get("account");

        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        password3 = findViewById(R.id.password3);

        btn_change_password = findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = account.getPassword();
                String pass2 = password1.getText().toString();
                String pass3 = password2.getText().toString();
                String pass4 = password3.getText().toString();
                Log.d("Pass1", account.getPassword());
                Log.d("Pass2", password1.getText().toString());
                Log.d("Pass3", password2.getText().toString());
                Log.d("Pass4", password3.getText().toString());
                if(pass1.equals(pass2) && pass3.equals(pass4)){
                    Log.d("Change Password", "Success");
                    account.setPassword(password2.getText().toString());
                    if(accountDAO.update(account)) {
                        Toast.makeText(getBaseContext(), "Change Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        image_account = findViewById(R.id.image_account);
        name_account = findViewById(R.id.name_account);
        set_info_account(account);

    }

    private void set_info_account(Account account) {
        name_account.setText(account.getName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(account.getImage(), 0, account.getImage().length);
        image_account.setImageBitmap(bitmap);
    }
}