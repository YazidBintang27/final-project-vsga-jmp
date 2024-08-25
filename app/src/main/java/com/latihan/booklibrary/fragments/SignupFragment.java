package com.latihan.booklibrary.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.latihan.booklibrary.R;
import com.latihan.booklibrary.database.DatabaseHelper;
import com.latihan.booklibrary.database.UserDataModel;
import com.latihan.booklibrary.databinding.FragmentSignupBinding;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private boolean isPasswordVisible = false;
    private DatabaseHelper db;
    private UserDataModel user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(getLayoutInflater());
        changeVisibility();
        signUp();
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void changeVisibility() {
        EditText etPassword = binding.etPassword;
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        int selection = etPassword.getSelectionEnd();
                        if (isPasswordVisible) {
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_24px, 0, R.drawable.visibility_off_24px, 0);
                        } else {
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_24px, 0, R.drawable.visibility_24px, 0);
                        }
                        etPassword.setSelection(selection);
                        isPasswordVisible = !isPasswordVisible;
                        return true;
                    }
                }
                return false;
            }
        });
    }
    private void signUp() {
        Button btnSignUp = binding.btnSubmit;
        DatabaseHelper db = new DatabaseHelper(getContext());

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.etUsername.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String confirmPassword = binding.etConfirmPassword.getText().toString();
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill out all form!", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        if (db.isEmailAvailable(email)) {
                            Toast.makeText(getContext(), "Email already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        user = new UserDataModel();
                        user.setUsername(username).setEmail(email).setPassword(password);
                        boolean isRegistered = db.addUser(user);
                        if (isRegistered) {
                            Toast.makeText(getContext(), "Sign Up Success!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Password do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}