package com.example.duanmau.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.Adapter.ThuThuAdapter;
import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.DTO.ThuThu;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThuThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThuThuFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ThuThu> arrayList;
    private FloatingActionButton actionButton;
    ThuThuDAO thuThuDAO;
    ThuThuAdapter thuThuAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public ThuThuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ThuThuFragment newInstance() {
        ThuThuFragment fragment = new ThuThuFragment();
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
        View view = inflater.inflate(R.layout.fragment_thu_thu, container, false);
        recyclerView = view.findViewById(R.id.recyclerQLTT);
        actionButton = view.findViewById(R.id.floataddTT);
        reloadTT();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemThuThu();
            }
        });
        return view;
    }

    private void dialogThemThuThu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thuthu, null);
        EditText edtma = view.findViewById(R.id.ed_tmaTT);
        EditText edtten = view.findViewById(R.id.ed_ttenTT);
        EditText edtmkhau = view.findViewById(R.id.ed_tmKhau);
        EditText edtstkhoan = view.findViewById(R.id.ed_tsotk);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ma = edtma.getText().toString();
                String ten = edtten.getText().toString();
                String mkhau = edtmkhau.getText().toString();
                int stkhoan = Integer.parseInt(edtstkhoan.getText().toString());

                boolean check = thuThuDAO.ThemThuThu(ma, ten, mkhau, stkhoan);
                if (check){
                    reloadTT();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    public void reloadTT(){
        thuThuDAO = new ThuThuDAO(getContext());
        arrayList = thuThuDAO.getDSThuThu();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        thuThuAdapter = new ThuThuAdapter(getContext(), arrayList, thuThuDAO);
        recyclerView.setAdapter(thuThuAdapter);
    }
}