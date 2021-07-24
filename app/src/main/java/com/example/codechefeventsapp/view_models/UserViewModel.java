package com.example.codechefeventsapp.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.models.Event;
import com.example.codechefeventsapp.data.models.User;
import com.example.codechefeventsapp.data.repositories.UserRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> user;
    private UserRepository repository;
    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new UserRepository(application);
        user = repository.getUser();
    }
    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public LiveData<List<User>> getUser() {
        return user;
    }
}
