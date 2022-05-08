package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.datechnologies.androidtest.login.dialog.LoginDialogFragment;
import com.datechnologies.androidtest.login.viewmodel.LoginViewModel;
import com.datechnologies.androidtest.network.rapptrlabs.result.Result;
import com.datechnologies.androidtest.network.rapptrlabs.result.ResultList;
import com.datechnologies.androidtest.network.rapptrlabs.result.SuccessResult;
import com.datechnologies.androidtest.util.AndroidUtil;
import com.datechnologies.androidtest.util.NetworkUtil;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity implements LoginDialogFragment.OnOkListener {

    private EditText mUsernameEt;
    private EditText mPasswordEt;
    private Button mLoginBtn;

    private LoginViewModel model;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameEt = findViewById(R.id.username_et);
        mPasswordEt = findViewById(R.id.password_et);
        mLoginBtn = findViewById(R.id.btn_login);
        model = AndroidUtil.createViewModel(LoginViewModel.class);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mLoginBtn.setOnClickListener(view -> {
            if(!NetworkUtil.hasNetworkConnectivity(this)){
                Toast.makeText(this, R.string.error_no_network, Toast.LENGTH_SHORT).show();
            } else {
                String username = mUsernameEt.getText().toString();
                String password = mPasswordEt.getText().toString();

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(this, R.string.error_missing_email, Toast.LENGTH_SHORT)
                            .show();
                } else if(TextUtils.isEmpty(password)){
                    Toast.makeText(this, R.string.error_missing_password, Toast.LENGTH_SHORT)
                            .show();
                } else {

                    model.authenticate(username, password).observe(this, result -> {
                        showLoginDialogFragment(result);
                    });
                }
            }
        });

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void onOkClick(){
        AndroidUtil.startActivityClearStack(this, MainActivity.class, null, null);
    }

    private void showLoginDialogFragment(LoginViewModel.LoginResult loginResult){
        LoginDialogFragment fragment = new LoginDialogFragment(loginResult);
        fragment.show(getSupportFragmentManager(), "login_dialog");
    }
}
