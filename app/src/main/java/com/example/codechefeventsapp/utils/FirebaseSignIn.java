package com.example.codechefeventsapp.utils;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.codechefeventsapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseSignIn {

    public static int RC_SIGN_IN = 1;
    static FirebaseSignIn instance = null;
    private static Activity mActivity;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private OnFirebaseSignInListener listener;


    private FirebaseSignIn() {
        initGoogleSign();
    }

    public static FirebaseSignIn getInstance(Activity activity) {
        mActivity = activity;
        if (instance == null) instance = new FirebaseSignIn();
        return instance;
    }

    void initGoogleSign() {
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(mActivity, gso);
    }

    public void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            currentUser = auth.getCurrentUser();
                            listener.signInSuccess(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            listener.signInFailure();
                        }
                    }
                });
    }

    public void notifyOnActivityResult(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        Log.d(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.getId());
                        firebaseAuthWithGoogle(googleSignInAccount.getIdToken());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Google sign in failed", e);
                    }
                });
    }


    public void setOnFirebaseSignInListener(OnFirebaseSignInListener listener) {
        this.listener = listener;
    }

    public interface OnFirebaseSignInListener {
        void signInSuccess(FirebaseUser currentUser);

        void signInFailure();
    }


}
