package br.edu.ufrn.brenov.luanews.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.database.auth.Auth;
import br.edu.ufrn.brenov.luanews.domain.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Get views
        this.edtUsername = findViewById(R.id.edt_username);
        this.edtPassword = findViewById(R.id.edt_password);
        this.edtConfirmPass = findViewById(R.id.edt_confirmpass);
    }

    public void register(View view) {
        // Get values
        String username = this.edtUsername.getText().toString();
        String password = this.edtPassword.getText().toString();
        String confirmPass = this.edtConfirmPass.getText().toString();
        // Check if the fields are not empty
        if (username.equals("")) {
            edtUsername.setError("Enter the username to register!");
            return;
        } else if (password.equals("")) {
            edtPassword.setError("Enter the password to register!");
            return;
        } else if (confirmPass.equals("")) {
            edtConfirmPass.setError("Confirm the password to register!");
            return;
        } else {
            // Check if the passwords are the same
            if (password.equals(confirmPass)) {
                // Create user
                User user = new User(username, password);
                try {
                    Auth.register(user, this);
                    // Change screen
                    Intent intent =  new Intent(this, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(this, "Error in user registration.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(this, "Error in user registration.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    public void cancel(View view) {
        Intent intent =  new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
