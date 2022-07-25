package com.example.lucky13.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationSender {

    public static void sendVerificationMail(FirebaseAuth firebaseAuth) {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String TAG = "**VERIFICATION-EMAIL**";

        try {
            assert firebaseUser != null;
            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Log.d(TAG, "Verification email successfully sent");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.d(TAG, "onFailure: Verification email not sent " + e.getMessage());
                }
            });
        } catch (AssertionError error) {

            Log.d(TAG, "onFailure: Assertion error: " + error.getMessage());
        }
    }
}
