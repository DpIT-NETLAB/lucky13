package com.example.lucky13.activities.start_activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lucky13.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class WelcomeBackPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "WelcomeBack-Activity";
    private static final int RC_SIGN_IN = 9001;

    EditText mEmailEditText,
            mPasswordEditText;
    Button mSignInButton;
    SignInButton mSignInGoogleButton;

    GoogleApiClient mGoogleApiClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back_page);

        mEmailEditText = findViewById(R.id.WelcomeBackEmailEditText);
        mPasswordEditText = findViewById(R.id.WelcomeBackPasswordEditText);

        mSignInButton = findViewById(R.id.WelcomeBackSignInButton);
        mSignInGoogleButton = findViewById(R.id.WelcomeBackSignInGoogleButton);
        firebaseAuth = FirebaseAuth.getInstance();

        //google register button - auth client api key
        GoogleSignInOptions googleSignIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignIn)
                .build();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) == true) {

                    mEmailEditText.setError("Email is required");
                    return;

                }
                else if (TextUtils.isEmpty(password)) {

                    mPasswordEditText.setError("Password is required");
                    return;

                }
                if (password.length() < 6) {

                    mPasswordEditText.setError("Password must be of at least 6 characters");
                }

                final  String TAG = "Addition of user";
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful() == true) {

                            Toast.makeText(WelcomeBackPage.this, "User registered", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "The user has been successfully added!");

//                            TODO: here we add the main activity where people choose what the app will
//                                    do next, such as Symptom Quiz
//                            startActivity(new Intent(getApplicationContext(), .class));

                        } else {

                            Toast.makeText(WelcomeBackPage.this, "Email already taken", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "The user has not been added to the database!");
                        }
                    }
                });
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String idToken = account.getIdToken();
            Log.d(TAG, "idToken " + idToken);
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult > task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(WelcomeBackPage.this, "User registered", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "The user has been successfully added!");

                            } else {
                                Toast.makeText(WelcomeBackPage.this, "User not registered", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "The user has not been added to the database!");
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.WelcomeBackSignInGoogleButton)
            registerGoogle();
    }

    private void registerGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed" + connectionResult);
    }
}