package com.example.mid_exam_ahmed_al_farani;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    private boolean backPressedOnce = false;
    private Snackbar exitSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ModeActivity.class);
            startActivity(intent);
        });

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {

                        if (backPressedOnce) {
                            if (exitSnackbar != null) {
                                exitSnackbar.dismiss();
                            }

                            finishAffinity();
                        } else {
                            backPressedOnce = true;

                            exitSnackbar = Snackbar.make(
                                    findViewById(R.id.main),
                                    "Press back again to exit",
                                    Snackbar.LENGTH_INDEFINITE
                            );

                            exitSnackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(
                                        Snackbar snackbar, int event) {
                                    backPressedOnce = false;
                                }
                            });

                            exitSnackbar.show();
                        }
                    }
                });
    }

    private void validateInputs() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        boolean validEmail =
                Patterns.EMAIL_ADDRESS.matcher(email).matches();

        boolean validPassword = password.length() >= 6;

        btnLogin.setEnabled(validEmail && validPassword);
    }
}
