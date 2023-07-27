package com.example.duanmau.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DTO.PhieuMuon;
import com.example.duanmau.DTO.Sach;
import com.example.duanmau.DTO.ThanhVien;
import com.example.duanmau.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PhieuMuon> arrayList;
    PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> arrayList, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.arrayList = arrayList;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_maPM.setText("Mã PM: " +arrayList.get(position).getMaPM());
        holder.txt_MaTV.setText("Mã TV: " +arrayList.get(position).getMaTV());
        holder.txttenTV.setText("Tên TV: " +arrayList.get(position).getTenTV());
        holder.txtmaTT.setText("Mã TT: " +arrayList.get(position).getMaTT());
        holder.txttenTT.setText("Tên TT: " +arrayList.get(position).getTenTT());
        holder.txtmaSach.setText("Mã Sách: " +arrayList.get(position).getMaSach());
        holder.txttenSach.setText("Tên sách: " +arrayList.get(position).getTenSach());
        holder.txtNgay.setText("Ngày mượn: " +arrayList.get(position).getNgay());
        holder.txtTrangThai.setText("Trạng thái: " +arrayList.get(position).getTrangThai());
        if (arrayList.get(position).getTrangThai().equals("Da tra")){
            holder.btn_tra.setVisibility(View.INVISIBLE);
        }else if (arrayList.get(position).getTrangThai().equals("Chua tra")){
            holder.btn_tra.setVisibility(View.VISIBLE);
        }
        holder.txtTien.setText("Tiền thuê: " +arrayList.get(position).getTien());

        holder.btn_tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean ktra = phieuMuonDAO.thaydoiTThai(arrayList.get(holder.getAdapterPosition()).getMaPM());
                if (ktra){
                    arrayList.clear();
                    arrayList = phieuMuonDAO.getDSPMuon();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Thay đổi trạng thái thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
                }
        });

        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsuaPM(arrayList.get(holder.getAdapterPosition()));
            }
        });

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = phieuMuonDAO.xoaPMuon(arrayList.get(holder.getAdapterPosition()).getMaPM());
                        if (check == false){
                            Toast.makeText(context, "XÓa thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            loadData();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_maPM, txt_MaTV, txttenTV, txtmaTT, txttenTT, txtmaSach, txttenSach, txtNgay, txtTrangThai, txtTien;
        Button btn_tra;
        ImageView imgsua, imgxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maPM = itemView.findViewById(R.id.tv_maPM);
            txt_MaTV = itemView.findViewById(R.id.tv_maTV);
            txttenTV = itemView.findViewById(R.id.tv_tenTV);
            txtmaTT = itemView.findViewById(R.id.tv_maTT);
            txttenTT = itemView.findViewById(R.id.tv_tenTT);
            txtmaSach = itemView.findViewById(R.id.tv_maSach);
            txttenSach = itemView.findViewById(R.id.tv_tenSach);
            txtNgay = itemView.findViewById(R.id.tv_ngay);
            txtTrangThai = itemView.findViewById(R.id.tv_TThai);
            txtTien = itemView.findViewById(R.id.tv_tien);
            btn_tra = itemView.findViewById(R.id.btn_traSach);
            imgsua = itemView.findViewById(R.id.img_suaPM);
            imgxoa = itemView.findViewById(R.id.img_xoaPM);
        }
    }

    private void dialogsuaPM(PhieuMuon phieuMuon){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_phieumuon, null);
        TextView edt_maPM = view.findViewById(R.id.edsua_mapmuon);
        Spinner spnTVien = view.findViewById(R.id.spnsua_tvien);
        Spinner spnSach = view.findViewById(R.id.spnsua_sach);
        EditText edtngay = view.findViewById(R.id.edsua_ngay);
        EditText tthai = view.findViewById(R.id.edsua_tthai);
        EditText edtTien = view.findViewById(R.id.edsua_gthue);

        getDataTV(spnTVien);
        getDataSach(spnSach);
        builder.setView(view);
        edt_maPM.setText("Mã phiếu mượn: " +phieuMuon.getMaPM());
        edtngay.setText(phieuMuon.getNgay());
        tthai.setText(phieuMuon.getTrangThai());
        edtTien.setText(String.valueOf(phieuMuon.getTien()));

        builder.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maPM = phieuMuon.getMaPM();
                // Lấy mã thành viên
                HashMap<String, Object> hsMapTV = (HashMap<String, Object>) spnTVien.getSelectedItem();
                String maTV = (String) hsMapTV.get("maTV");
                //Lấy mã sách
                HashMap<String, Object> hashMapSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                String maSach = (String) hashMapSach.get("maSach");

                String ngay = edtngay.getText().toString();
                String trangThai = tthai.getText().toString();
                int tien = Integer.parseInt(edtTien.getText().toString());

                SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                String maTT = sharedPreferences.getString("maTT", "");

                PhieuMuon phieuMuon = new PhieuMuon(maPM, maTV, maTT, maSach, ngay, trangThai, tien);
                boolean ktra = phieuMuonDAO.SuaPMuon(phieuMuon);
                if (ktra){
                    loadData();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    private void suaPhieuMuon(String maPM, String maTV, String maSach, String trangThai,int tien){
//        //Lấy mã thủ thư
//
//    }

    private void getDataTV(Spinner spnTVien){
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        ArrayList<ThanhVien> list = thanhVienDAO.getDSTVien();
        ArrayList<HashMap<String, Object>> listHashMap = new ArrayList<>();
        for (ThanhVien tVien : list){
            HashMap<String, Object> hMap = new HashMap<>();
            hMap.put("maTV", tVien.getMaTV());
            hMap.put("TenTV", tVien.getTenTV());
            listHashMap.add(hMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHashMap, android.R.layout.simple_list_item_1, new String[]{"TenTV"}, new int[]{android.R.id.text1});
        spnTVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(context);
        ArrayList<Sach> list = sachDAO.getDSSach();
        ArrayList<HashMap<String, Object>> listHashMap = new ArrayList<>();
        for (Sach sach : list){
            HashMap<String, Object> hMap = new HashMap<>();
            hMap.put("maSach", sach.getMaSach());
            hMap.put("TenSach", sach.getTenSach());
            hMap.put("GiaThue", sach.getGia());
            listHashMap.add(hMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHashMap, android.R.layout.simple_list_item_1, new String[]{"TenSach"}, new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData(){
        arrayList.clear();
        arrayList = phieuMuonDAO.getDSPMuon();
        notifyDataSetChanged();
    }

}
