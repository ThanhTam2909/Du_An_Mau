package com.example.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.DTO.ThuThu;
import com.example.duanmau.R;

import java.util.ArrayList;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ViewHolder>{

    Context context;
    ArrayList<ThuThu> arrayList;
    ThuThuDAO thuThuDAO;

    public ThuThuAdapter(Context context, ArrayList<ThuThu> arrayList, ThuThuDAO thuThuDAO) {
        this.context = context;
        this.arrayList = arrayList;
        this.thuThuDAO = thuThuDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thu_thu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtma.setText("Mã thủ thư: "+arrayList.get(position).getMaTT());
        holder.txtten.setText("Tên thủ thư: "+arrayList.get(position).getTenTT());
        holder.txtmkhau.setText("Mật khẩu: "+arrayList.get(position).getMatKhau());
        holder.txtsoTK.setText("Số tài khoản: "+arrayList.get(position).getSoTaiKhoan());

        String taiKhoan = String.valueOf(arrayList.get(position).getSoTaiKhoan());
        if(taiKhoan.length() == 3){
            holder.txtsoTK.setTypeface(holder.txtsoTK.getTypeface(), Typeface.BOLD);
            holder.txtsoTK.setTextColor(Color.RED);
        }

        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuaTT(arrayList.get(holder.getAdapterPosition()));
            }
        });
        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = thuThuDAO.xoaThuThu(arrayList.get(holder.getAdapterPosition()).getMaTT());
                        if (check == false){
                            Toast.makeText(context, "Xóa thất bại vì thủ thư nằm trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        }else {
                            loadTT();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtma, txtten, txtmkhau, txtsoTK;
        ImageView imgsua, imgxoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtma = itemView.findViewById(R.id.tv_matt);
            txtten = itemView.findViewById(R.id.tv_tentt);
            txtmkhau = itemView.findViewById(R.id.tv_mkhautt);
            txtsoTK = itemView.findViewById(R.id.tv_sotk);
            imgsua = itemView.findViewById(R.id.img_suaTT);
            imgxoa = itemView.findViewById(R.id.img_xoaTT);
        }
    }

    private void dialogSuaTT(ThuThu thuThu){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_thuthu, null);
        TextView tvma = view.findViewById(R.id.tv_sua_maTT);
        EditText edts_ten = view.findViewById(R.id.ed_sua_tenTT);
        EditText edts_mkhau = view.findViewById(R.id.ed_sua_mKhau);
        EditText edts_stkhoan = view.findViewById(R.id.ed_sua_soTK);
        tvma.setText("Mã thủ thư: " +thuThu.getMaTT());
        edts_ten.setText(thuThu.getTenTT());
        edts_mkhau.setText(thuThu.getMatKhau());
        edts_stkhoan.setText(String.valueOf(thuThu.getSoTaiKhoan()));
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ma = thuThu.getMaTT();
                String ten = edts_ten.getText().toString();
                String mkhau = edts_mkhau.getText().toString();
                int stkhoan = Integer.parseInt(edts_stkhoan.getText().toString());
                boolean check = thuThuDAO.SuaThuThu(ma, ten, mkhau, stkhoan);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadTT();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private void loadTT(){
        arrayList.clear();
        arrayList = thuThuDAO.getDSThuThu();
        notifyDataSetChanged();
    }
}
