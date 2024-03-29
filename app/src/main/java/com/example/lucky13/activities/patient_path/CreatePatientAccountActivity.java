package com.example.lucky13.activities.patient_path;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.service.PatientService;
import com.example.lucky13.utils.EmailVerificationSender;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Triple;

public class CreatePatientAccountActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "Addition of user";
    private static final int RC_SIGN_IN = 9001;

    public PatientService patientService;

    EditText mUsernameEditText,
            mEmailAddressEditText,
            mPasswordEditText,
            mConfPasswordEditText;

    AppCompatButton mSignUpButton,
            mSignUpGoogleButton;

    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account_create);;

        Intent incomingIntent = getIntent();
        String incomingFirstName = incomingIntent.getStringExtra("firstName");
        String incomingLastName = incomingIntent.getStringExtra("lastName");
        String incomingGender = incomingIntent.getStringExtra("gender");

        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        patientService = new PatientService();

        mUsernameEditText = findViewById(R.id.patientcreateUsernameTextEdit);
        mEmailAddressEditText = findViewById(R.id.patientCreateEmailTextEdit);
        mPasswordEditText = findViewById(R.id.patientCreatePasswordTextEdit);
        mConfPasswordEditText = findViewById(R.id.patientcreatePasswordConfirmTextEdit);

        mSignUpButton = findViewById(R.id.patientCreateSignInButton);
        mSignUpGoogleButton = (AppCompatButton) findViewById(R.id.patientCreateSignInWithGoogleButton);

        mSignUpGoogleButton.setOnClickListener(this);
        GoogleSignInOptions googlesignin = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googlesignin)
                .build();


        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsernameEditText.getText().toString().trim();
                String email = mEmailAddressEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String conf_password = mConfPasswordEditText.getText().toString().trim();

                if (!password.equals(conf_password)) {

                    mConfPasswordEditText.setError("Password confirmation does not match!");
                    return;
                }

                if (TextUtils.isEmpty(email)) {

                    mEmailAddressEditText.setError("Email is required");
                    return;
                } else if (TextUtils.isEmpty(password)) {

                    mPasswordEditText.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {

                    mPasswordEditText.setError("Password must be of at least 6 characters");
                    return;
                }
                if (username.length() < 6) {

                    mUsernameEditText.setError("Username must be of at least 6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    EmailVerificationSender.sendVerificationMail(fAuth);

                                    String uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                    patientService.addPatient(uid, incomingFirstName, incomingLastName, new Triple(0, 0, 0),
                                            email, 0, 0, incomingGender, new ArrayList<String>());

                                    Intent intent = new Intent(CreatePatientAccountActivity.this, PatientHomeScreenActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onClick(@NonNull View view) {
        if (view.getId() == R.id.patientCreateSignInWithGoogleButton)
            registerGoogle();
    }

    private void registerGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Intent incomingIntent = getIntent();
        String incomingFirstName = incomingIntent.getStringExtra("firstName");
        String incomingLastName = incomingIntent.getStringExtra("lastName");
        String incomingGender = incomingIntent.getStringExtra("gender");

        Log.d(TAG, "handleSignInResult" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            String email = account.getEmail();
            String idToken = account.getIdToken();
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);

            fAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                EmailVerificationSender.sendVerificationMail(fAuth);

                                Toast.makeText(CreatePatientAccountActivity.this, TAG + ": succeeded", Toast.LENGTH_SHORT).show();

                                String uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                patientService.addPatient(uid, incomingFirstName, incomingLastName, new Triple(0, 0, 0),
                                        email, 0, 0, incomingGender, new ArrayList<String>());
                            } else {
                                Toast.makeText(CreatePatientAccountActivity.this, TAG + ": failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed" + connectionResult);
    }
}