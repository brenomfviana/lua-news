package br.edu.ufrn.brenov.luanews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Views
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get views
        this.edtUsername = findViewById(R.id.edt_username);
        this.edtPassword = findViewById(R.id.edt_password);
    }

    public void login(View view) {
        // Get values
        String username = this.edtUsername.getText().toString();
        String password = this.edtPassword.getText().toString();
        // Check if the fields are empty
        if (username.equals("")) {
            edtUsername.setError("This field must be !");
            return;
        }
        if (password.equals("")) {
            edtPassword.setError("....");
            return;
        }
        // Check if the user is registered
        if (username.equals("admin") && password.equals("admin")) {
            Toast.makeText(this,"The user logged into the Lua News.",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Wrong username or password!",Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {
        Intent intent =  new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
