package com.example.codechefeventsapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.activities.LoginActivity;
import com.example.codechefeventsapp.activities.MainActivity;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.User;
import com.example.codechefeventsapp.view_models.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

public class ProfileFragment extends Fragment {
    // https://codeforces.com/api/user.info?handles=
    private List<User> currentUser;
    private UserViewModel userViewModel;
    private GoogleSignInClient googleSignInClient;
    private TextView nameTV;
    private TextView userNameTV;
    private TextView followingsTV;
    private TextView followersTV;
    private TextView instituteTV;
    private ImageView profileIV;
    private String personName;
    private String personEmail;
    private Uri personPhoto;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Profile");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        setHasOptionsMenu(true);

        nameTV = view.findViewById(R.id.text_name);
        userNameTV = view.findViewById(R.id.text_username);
        followersTV = view.findViewById(R.id.text_followers);
        followingsTV = view.findViewById(R.id.text_followings);
        instituteTV = view.findViewById(R.id.text_institute);
        profileIV = view.findViewById(R.id.iv_profile);

        //GoogleSignInAccount account =getIntent().getParcelableExtra("ACCOUNT");
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            personName = acct.getDisplayName();
            //String personGivenName = acct.getGivenName();
            //String personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            //String personId = acct.getId();
            personPhoto = acct.getPhotoUrl();

            Utils.Constants.userEmail = personEmail;

            userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            userViewModel.getUser().observe(getActivity(), new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable @org.jetbrains.annotations.Nullable List<User> users) {
                    // Update RecyclerView
                    //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                    currentUser = users;
                    if(currentUser == null || currentUser.size() == 0){
                        saveUser();
                    }
                    else {
                        getUserData();
                    }
                }
            });
        }
        else {
            return null;
        }


        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), profileIV.getId());
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        profileIV.setImageDrawable(mDrawable);*/


        return view;
    }

    private void saveUser(){
        User user = new User(personEmail, personName, personEmail.substring(0,10));
        user.setProfileUrl(personPhoto.toString());
        MainActivity.currentUser = user;
        userViewModel.insert(user);
        Toast.makeText(getActivity(), "User added", Toast.LENGTH_SHORT).show();
    }

    private void getUserData(){
        User user = currentUser.get(0);
        MainActivity.currentUser = user;
        nameTV.setText(user.getFullName());
        userNameTV.setText(user.getUserName());
        followingsTV.setText(String.valueOf(user.getFollowings()) + "  followings");
        followersTV.setText(String.valueOf(user.getFollowers()) + "  followers");
        instituteTV.setText(user.getInstituteName());
        Glide.with(this).load(String.valueOf(personPhoto)).apply(RequestOptions.circleCropTransform()).into(profileIV);
        Toast.makeText(getActivity(), "getUserData()", Toast.LENGTH_SHORT).show();
    }

    private void openSettings(){

        //Put the value
        /*UpdateDetailsFragment ldf = new UpdateDetailsFragment();
        Bundle args = new Bundle();
        args.put
        ldf.setArguments(args);

//Inflate the fragment
        getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();
        // Create new fragment and transaction*/
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(getId(), UpdateDetailsFragment.class, null);

        // Commit the transaction
        transaction.commit();
    }

    private void signOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        googleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
        Log.d(TAG, "onCreateOptionsMenu");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile_setting:
                openSettings();
                return true;
            case R.id.action_sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}