package br.edu.ufrn.brenov.luanews.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import br.edu.ufrn.brenov.luanews.R;
import br.edu.ufrn.brenov.luanews.controller.database.auth.Auth;
import br.edu.ufrn.brenov.luanews.view.dialogs.RegistrationDialog;
import br.edu.ufrn.brenov.luanews.model.User;

public class MainActivity extends AppCompatActivity {

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
            edtUsername.setError("Enter the username to register!");
            return;
        } else if (password.equals("")) {
            edtPassword.setError("Enter the password to register!");
            return;
        } else {
            try {
                // Get registered user
                User user = Auth.getUser(this);
                // Check if the user was registered
                if (user == null) {
                    Toast.makeText(this,"No user has been registered\n!",Toast.LENGTH_SHORT).show();
                } else {
                    if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        startActivity(new Intent(this, HomeActivity.class));
                    } else {
                        Toast.makeText(this,"Wrong username or password!",Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Error in login file.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(this, "Error in login file.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public void register(View view) {
        try {
            // Get registered user
            User user = Auth.getUser(this);
            // Check if the user was registered
            if (user == null) {
                startActivity(new Intent(this, RegisterActivity.class));
            } else {
                RegistrationDialog.show(getSupportFragmentManager(), new RegistrationDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == DialogInterface.BUTTON_POSITIVE) {
                            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        }
                    }
                });
            }
        } catch (Exception e) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
