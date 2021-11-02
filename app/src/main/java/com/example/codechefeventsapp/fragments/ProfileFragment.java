package com.example.codechefeventsapp.fragments;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;
import static com.example.codechefeventsapp.utils.Utils.KEY_CF_HANDLE;
import static com.example.codechefeventsapp.utils.Utils.KEY_STAT_DATA_FETCHED_ONCE;
import static com.example.codechefeventsapp.utils.Utils.RC_SIGN_IN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.models.cf.CfContest;
import com.example.codechefeventsapp.firebase.FirebaseSignIn;
import com.example.codechefeventsapp.fragments.styling.LineChartStyling;
import com.example.codechefeventsapp.utils.Utils;
import com.example.codechefeventsapp.view_models.UserViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment implements FirebaseSignIn.OnFirebaseSignInListener {

    ConstraintLayout signInLayout;
    ConstraintLayout profileLayout;
    SignInButton signInButton;
    TextView user_name;
    ImageView user_image;
    PieChart pieChart;
    LineChart lineChart;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    UserViewModel userViewModel;
    LinearLayout handleLinearLayout;
    TextView cfHandle;
    String handle = "";
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        signInLayout = view.findViewById(R.id.signInLayout);
        profileLayout = view.findViewById(R.id.profileLayout);
        signInButton = view.findViewById(R.id.signInButton);

        pieChart = view.findViewById(R.id.piechart);
        lineChart = view.findViewById(R.id.ratingGraph);
        user_name = view.findViewById(R.id.user_name);
        user_image = view.findViewById(R.id.userImage);
        cfHandle = view.findViewById(R.id.cfHandle);
        handleLinearLayout = view.findViewById(R.id.cfLinearLayout);

        initGoogleSign();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        handle = sharedPreferences.getString(KEY_CF_HANDLE, "");
        initProfileLayout();
        initViewModel();
    }

    /*
        At the start of the fragment check if there exists a currentUser and updateUI accordingly
         */
    @Override
    public void onStart() {
        super.onStart();
        updateUI(FirebaseAuth.getInstance().getCurrentUser());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
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
    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(UserViewModel.class);

        if (!sharedPreferences.getBoolean(KEY_STAT_DATA_FETCHED_ONCE, false)) {
            userViewModel.getUserCfDetailsAndStore(handle);
        } else {
            sharedPreferences.edit()
                    .putBoolean(KEY_STAT_DATA_FETCHED_ONCE, true)
                    .apply();
        }

        userViewModel.getAllCfUserContest().observe(getViewLifecycleOwner(), new Observer<List<CfContest>>() {
            @Override
            public void onChanged(List<CfContest> cfContests) {
                Log.d(TAG, "onChanged: " + cfContests.size());
                updateGraph(cfContests);
            }
        });
    }

    void updateGraph(List<CfContest> cfContests) {
        if (cfContests == null || cfContests.size() == 0) return;
        ArrayList<Entry> ratings = new ArrayList<>();

        int maxHere = -2000000;
        int minHere = 2000000;
        int postion = 0;

        long minTime = cfContests.get(0).getRatingUpdateTimeSeconds();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (CfContest contest : cfContests) {
            maxHere = Math.max(maxHere, contest.getNewRating());
            minHere = Math.min(minHere, contest.getNewRating());
            // (Float)(contest.getRatingUpdateTimeSeconds() - minTime) / 1000)

            float x = Float.valueOf(contest.getRatingUpdateTimeSeconds() - minTime) / 1000;
            float y = Float.valueOf(contest.getNewRating());
            ratings.add(new Entry(x, y));
        }
        Collections.sort(ratings, new Utils.SortByEntryX());
        LineDataSet lineDataSet = new LineDataSet(ratings, "girishgarg");
        lineDataSet.setLineWidth(2F);
//        lineDataSet.setColor(R.color.colorAccent);
        lineDataSet.setDrawValues(false);
        styleRatingGraph(maxHere, minHere);
        lineChart.setVisibility(View.VISIBLE);
        lineChart.invalidate();
        dataSets.add(lineDataSet);
        lineChart.setData(new LineData(dataSets));
    }

    void styleRatingGraph(int maxHere, int minHere) {
        maxHere = (maxHere != -2000000) ? (maxHere + 200) : 2000;
        minHere = (minHere != 2000000) ? Math.min(minHere - 200, 1200) : 1200;
        lineChart.getAxisLeft().setAxisMaximum(maxHere);
        lineChart.getAxisLeft().setAxisMinimum(minHere);
        new LineChartStyling(lineChart, getContext()).styleIt();
    }


    private void updateUI(FirebaseUser user) {
        if (user == null) {
            signInLayout.setVisibility(View.VISIBLE);
            profileLayout.setVisibility(View.GONE);
        } else {
            signInLayout.setVisibility(View.GONE);
            initProfileLayout();
            profileLayout.setVisibility(View.VISIBLE);
        }
    }

    void initGoogleSign() {
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    void initProfileLayout() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        setHasOptionsMenu(true);
        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        Uri photoUrl = currentUser.getPhotoUrl();
        String uid = currentUser.getUid();
        String photoUrlString = photoUrl.toString().replace("s96-c", "s400-c");

        user_name.setText(name);
        Glide.with(this)
                .load(photoUrlString)
                .apply(RequestOptions.circleCropTransform())
                .into(user_image);

        handleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHandleDialog();
            }
        });

        if (handle != null && !handle.trim().isEmpty()) {
            cfHandle.setText(handle);
        } else {
            cfHandle.setText("Your Codeforces handle");
        }

    }

    private void showHandleDialog() {
        AlertDialog dialogAddURL;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog,
                (ViewGroup) getView().findViewById(R.id.layoutAddUrlContainer)
        );
        builder.setView(view);
        dialogAddURL = builder.create();


        if (dialogAddURL.getWindow() != null) {
            dialogAddURL.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        EditText inputHandle = view.findViewById(R.id.inputHandle);
        inputHandle.setText(handle);
        inputHandle.requestFocus();

        view.findViewById(R.id.textSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputHandle.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter handle", Toast.LENGTH_SHORT).show();
                } else {
                    String text = inputHandle.getText().toString();
                    if (text != null && !text.trim().isEmpty()) {
                        handle = text;
                        userViewModel.getUserCfDetailsAndStore(text);
                        cfHandle.setText(handle);
                        sharedPreferences.edit().putString(KEY_CF_HANDLE, handle).apply();
                    } else {
                        cfHandle.setText("Your Codeforces Handle");
                    }
                    dialogAddURL.dismiss();
                }
            }
        });

        view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddURL.dismiss();
            }
        });
        dialogAddURL.show();
    }


    public void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            currentUser = auth.getCurrentUser();
                            updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logOut();
                break;
        }
        return true;
    }

    void logOut() {
        FirebaseAuth.getInstance().signOut();
    }


    void initPieChart() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(508, "Accepted"));
        pieEntries.add(new PieEntry(25, "Partially solved"));
        pieEntries.add(new PieEntry(125, "FWEF"));
        pieEntries.add(new PieEntry(400, "AccepFDSted"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Label");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(15);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Submissions");
        pieChart.setCenterTextSize(20);

        pieChart.setDrawSliceText(false);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.rgb(47, 59, 73));
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(0);
        pieChart.setDrawRoundedSlices(true);
        pieChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.getLegend().setTextSize(12);
        pieChart.setEntryLabelColor(0);
        pieChart.setEntryLabelTextSize(20);
        pieChart.setDrawHoleEnabled(true);

    }

    void initRatingGraph() {
//        updateGraph(new ArrayList<>());
//        lineChart.setTouchEnabled(true);
//        lineChart.setPinchZoom(true);
////        LimitLine limitLine = new LimitLine(30f, "girishgarg");
////        limitLine.setLineColor(Color.WHITE);
//        lineChart.setDragEnabled(true);
//        lineChart.setScaleEnabled(true);
//
//        ArrayList<Entry> yVals = new ArrayList<>();
//        yVals.add(new Entry(0, 60));
//        yVals.add(new Entry(1, 70));
//        yVals.add(new Entry(2, 90));
//        yVals.add(new Entry(3, 40));
//        yVals.add(new Entry(4, 30));
//
//        ArrayList<Entry> yVals2 = new ArrayList<>();
//        yVals2.add(new Entry(0, 50));
//        yVals2.add(new Entry(1, 80));
//        yVals2.add(new Entry(2, 70));
//        yVals2.add(new Entry(3, 20));
//        yVals2.add(new Entry(4, 90));
//
//        LineDataSet set1 = new LineDataSet(yVals, "Data Set 1");
//        LineDataSet set2 = new LineDataSet(yVals2, "Data Set 2");
//        set1.setFillAlpha(110);
//        set2.setFillAlpha(110);
//        // lineChart.moveViewToAnimated(float xValue, float yValue, AxisDependency axis, long duration);
//        ArrayList<ILineDataSet> datasets = new ArrayList<>();
//        datasets.add(set1);
//        datasets.add(set2);
//        LineData lineData = new LineData(datasets);
//
////        lineData.setValueTextColors();
//        lineChart.setData(lineData);
//        lineChart.getLegend().setTextColor(Color.WHITE);
//        lineChart.getLegend().setTextSize(12);
//        lineChart.setAutoScaleMinMaxEnabled(true);
//        lineChart.setBorderColor(Color.WHITE);
//        lineChart.setGridBackgroundColor(Color.YELLOW);
//        lineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        lineChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//
//        lineChart.getLineData().setValueTextSize(0);
//        lineChart.getXAxis().setTextColor(Color.WHITE);
//        lineChart.getXAxis().setTextSize(12);
//        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        lineChart.getAxisLeft().setTextColor(Color.WHITE);
//        //lineChart.getLegend().setYOffset(0);
//        lineChart.getAxisRight().setDrawLabels(false);
//        lineChart.getAxisRight().setTextSize(12);
//        lineChart.getDescription().setText("");

    }

    @Override
    public void signInSuccess(FirebaseUser currentUser) {
        updateUI(currentUser);
    }

    @Override
    public void signInFailure() {

    }
}