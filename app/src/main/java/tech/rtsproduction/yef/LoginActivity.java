package tech.rtsproduction.yef;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.style.CharacterStyle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class LoginActivity extends AppCompatActivity {

    ImageView logoView;
    Animation animation,animationLoginView;
    RelativeLayout loginView;
    Button login;
    TextInputLayout username,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.LoginButton);
        username = (TextInputLayout) findViewById(R.id.input_layout_userId);
        username.setHint(username.getHint().toString().toUpperCase());

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.LoginButton).requestFocusFromTouch();
            }
        });
        pass = (TextInputLayout) findViewById(R.id.input_layout_pass);
        pass.setHint(pass.getHint().toString().toUpperCase());
        loginView = (RelativeLayout) findViewById(R.id.loginView);
        logoView = (ImageView) findViewById(R.id.loginLogo);
        animation = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        animationLoginView = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        logoView.setAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginView.setVisibility(View.VISIBLE);
            }
        },1500);
    }

    public void onClickGoToSignUp(View view)
    {
        Intent intent = new Intent(LoginActivity.this,Donators_Page.class);
        startActivity(intent);
        finish();
    }
    public void onClickLogin(View view)
    {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
