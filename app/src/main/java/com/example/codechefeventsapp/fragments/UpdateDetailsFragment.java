package com.example.codechefeventsapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.activities.MainActivity;
import com.example.codechefeventsapp.data.models.User;
import com.example.codechefeventsapp.view_models.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class UpdateDetailsFragment extends Fragment {
    private UserViewModel userViewModel;
    static final int REQUEST_CAMERA = 1;
    private ImageView profileIV;
    private TextInputEditText fullNameET;
    private TextInputEditText userNameET;
    private TextInputEditText instituteNameET;
    private EditText handleCCET;
    private EditText handleCFET;
    private EditText handleHEET;
    public UpdateDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_details, container, false);
        getActivity().setTitle("Update Details");
        setHasOptionsMenu(true);

        profileIV = view.findViewById(R.id.iv_profile);
        fullNameET = view.findViewById(R.id.edit_text_name);
        userNameET = view.findViewById(R.id.edit_text_username);
        instituteNameET = view.findViewById(R.id.edit_text_institute_name);
        handleCCET = view.findViewById(R.id.handle_codechef);
        handleCFET = view.findViewById(R.id.handle_codeforces);
        handleHEET = view.findViewById(R.id.handle_hackerearth);

        User user = MainActivity.currentUser;
        Glide.with(this).load(String.valueOf(user.getProfileUrl())).apply(RequestOptions.circleCropTransform()).into(profileIV);
        fullNameET.setText(user.getFullName());
        userNameET.setText(user.getUserName());
        instituteNameET.setText(user.getInstituteName());
        handleCCET.setText(user.getCodeChefHandle());
        handleCFET.setText(user.getCodeforcesHandle());
        handleHEET.setText(user.getHackerEarthHandle());

        Button saveButton = view.findViewById(R.id.save_update);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update user data;
                user.setFullName(fullNameET.getText().toString().trim());
                user.setUserName(userNameET.getText().toString().trim());
                user.setInstituteName(instituteNameET.getText().toString().trim());
                user.setCodeChefHandle(handleCCET.getText().toString().trim());
                user.setCodeforcesHandle(handleCFET.getText().toString().trim());
                user.setHackerEarthHandle(handleHEET.getText().toString());

                userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
                userViewModel.update(user);
                Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });

        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, REQUEST_CAMERA);

                 */
            }
        });
        return view;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                profileIV.setImageBitmap(image);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBlob = stream.toByteArray();
            }
        }
    }

     */

    /*public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

     */
    public void goBack(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(getId(), ProfileFragment.class, null);
        transaction.commit();

    }
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_close, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_close:
                goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}