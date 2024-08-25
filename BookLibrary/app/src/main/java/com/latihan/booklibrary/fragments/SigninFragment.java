package com.latihan.booklibrary.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.latihan.booklibrary.MainActivity;
import com.latihan.booklibrary.R;
import com.latihan.booklibrary.database.DatabaseHelper;
import com.latihan.booklibrary.databinding.FragmentSigninBinding;

public class SigninFragment extends Fragment {

    private FragmentSigninBinding binding;
    private boolean isPasswordVisible = false;
    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSigninBinding.inflate(getLayoutInflater());
        changeVisibility();
        signIn();
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

    private void signIn() {
        Button btnSignIn = binding.btnSubmit;
        db = new DatabaseHelper(getContext());
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                boolean isSignin = db.isUserAvailable(email, password);
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill up all form!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isSignin) {
                        Toast.makeText(getContext(), "Sign in success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Sign in failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}