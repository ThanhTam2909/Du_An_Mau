package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanmau.Adapter.ThongKeAdapter;
import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.DTO.Sach;
import com.example.duanmau.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Top_10_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top_10_Fragment extends Fragment {

    RecyclerView recyclerView;
    ThongKeDAO top10_dao;
    ThongKeAdapter top10_adapter;
    ArrayList<Sach> arrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters

    public Top_10_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Top_10_Fragment newInstance() {
        Top_10_Fragment fragment = new Top_10_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_10_, container, false);
        recyclerView = view.findViewById(R.id.recyclerTop);
        top10_dao = new ThongKeDAO(getContext());
        arrayList = top10_dao.top10();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        top10_adapter = new ThongKeAdapter(getContext(), arrayList);
        recyclerView.setAdapter(top10_adapter);
        return view;
    }
}