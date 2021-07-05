package com.example.codechefeventsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.adapters.ContestAdapter;
import com.example.codechefeventsapp.model.Contest_Model;

import java.util.ArrayList;

public class ContestFragment extends Fragment {
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<Contest_Model> dataHolder;

    public ContestFragment() {
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
        View view= inflater.inflate(R.layout.fragment_contest, container, false);
        recyclerView=view.findViewById(R.id.recyclerview_contest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataHolder=new ArrayList<>();

        Contest_Model ob1=new Contest_Model("July Challenge 2021","www.codechef.com","13:00","15:00","2 hours","Yes","Running",R.drawable.cc_icon);
        dataHolder.add(ob1);
        Contest_Model ob2=new Contest_Model("July Challenge 2021","www.codechef.com","13:00","15:00","2 hours","Yes","Running",R.drawable.cc_icon);
        dataHolder.add(ob2);
        Contest_Model ob3=new Contest_Model("July Challenge 2021","www.codechef.com","13:00","15:00","2 hours","Yes","Not Running",R.drawable.cc_icon);
        dataHolder.add(ob3);
        Contest_Model ob4=new Contest_Model("July Challenge 2021","www.codechef.com","13:00","15:00","2 hours","Yes","Running",R.drawable.cc_icon);
        dataHolder.add(ob4);
        Contest_Model ob5=new Contest_Model("July Challenge 2021","www.codechef.com","13:00","15:00","2 hours","Yes","Not Running",R.drawable.cc_icon);
        dataHolder.add(ob5);
        Contest_Model ob6=new Contest_Model("July Challenge 2021","www.codechef.com","13:00","15:00","2 hours","Yes","Running",R.drawable.cc_icon);
        dataHolder.add(ob6);


        recyclerView.setAdapter(new ContestAdapter(dataHolder));

        return view;
    }
}

