package tech.rtsproduction.yef;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    ImageView logoView;
    Animation animation, animationLoginView;
    RelativeLayout loginView;
    Button login;
    TextInputLayout username, pass;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.LoginButton);
        username = findViewById(R.id.input_layout_userId);
        pass = findViewById(R.id.input_layout_pass);
        loginView = findViewById(R.id.loginView);
        logoView = findViewById(R.id.loginLogo);
        progressBar = findViewById(R.id.progressIndicator);
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);

        username.setHint(username.getHint().toString().toUpperCase());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.LoginButton).requestFocusFromTouch();
            }
        });

        pass.setHint(pass.getHint().toString().toUpperCase());

        animation = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        animationLoginView = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        logoView.setAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginView.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginSuccessful();
        }
    }


    public void onClickGoToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickLogin(View view) {
        if (!username.getEditText().getText().toString().isEmpty() && !pass.getEditText().getText().toString().isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(username.getEditText().getText().toString(), pass.getEditText().getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                        loginSuccessful();
                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(this, "Please Fill Crenditials", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginSuccessful() {
        Intent intent = new Intent(LoginActivity.this, DonatorActivity.class);
        startActivity(intent);
        finish();
    }


}
