package com.example.codechefeventsapp.fragments;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.codechefeventsapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
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
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    ConstraintLayout signInLayout;
    ConstraintLayout profileLayout;
    SignInButton signInButton;

    private GoogleSignInClient googleSignInClient;
    public static int RC_SIGN_IN = 1;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    TextView user_name;
    ImageView user_image;
    PieChart pieChart;
    LineChart lineChart;


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
        signInLayout = view.findViewById(R.id.signInLayout);
        profileLayout = view.findViewById(R.id.profileLayout);
        signInButton = view.findViewById(R.id.signInButton);

        pieChart = view.findViewById(R.id.piechart);
        lineChart = view.findViewById(R.id.ratingGraph);
        user_name = view.findViewById(R.id.user_name);
        user_image = view.findViewById(R.id.userImage);

        initGoogleSign();
        initProfileLayout();

        return view;
    }

    /*
    At the start of the fragment check if there exists a currentUser and updateUI accordingly
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
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

    private void updateUI(FirebaseUser user) {
        if (user == null) return;
        signInLayout.setVisibility(View.GONE);
        initProfileLayout();
        profileLayout.setVisibility(View.VISIBLE);
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
        String photoUrlString= photoUrl.toString().replace("s96-c", "s400-c");

        user_name.setText(name);
        Glide.with(this)
                .load(photoUrlString)
                .apply(RequestOptions.circleCropTransform())
                .into(user_image);

        initPieChart();
        initRatingGraph();
    }

    private void signIn() {
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
//        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
//        LimitLine limitLine = new LimitLine(30f, "girishgarg");
//        limitLine.setLineColor(Color.WHITE);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        ArrayList<Entry> yVals = new ArrayList<>();
        yVals.add(new Entry(0, 60));
        yVals.add(new Entry(1, 70));
        yVals.add(new Entry(2, 90));
        yVals.add(new Entry(3, 40));
        yVals.add(new Entry(4, 30));

        ArrayList<Entry> yVals2 = new ArrayList<>();
        yVals2.add(new Entry(0, 50));
        yVals2.add(new Entry(1, 80));
        yVals2.add(new Entry(2, 70));
        yVals2.add(new Entry(3, 20));
        yVals2.add(new Entry(4, 90));

        LineDataSet set1 = new LineDataSet(yVals, "Data Set 1");
        LineDataSet set2 = new LineDataSet(yVals2, "Data Set 2");
        set1.setFillAlpha(110);
        set2.setFillAlpha(110);
       // lineChart.moveViewToAnimated(float xValue, float yValue, AxisDependency axis, long duration);
        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        datasets.add(set1);
        datasets.add(set2);
        LineData lineData = new LineData(datasets);

//        lineData.setValueTextColors();
        lineChart.setData(lineData);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextSize(12);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setBorderColor(Color.WHITE);
        lineChart.setGridBackgroundColor(Color.YELLOW);
        lineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lineChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        lineChart.getLineData().setValueTextSize(0);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextSize(12);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        //lineChart.getLegend().setYOffset(0);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setTextSize(12);
        lineChart.getDescription().setText("");

    }

}