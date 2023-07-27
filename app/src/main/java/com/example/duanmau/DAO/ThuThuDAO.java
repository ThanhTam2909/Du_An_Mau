package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.ThuThu;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class ThuThuDAO {
    MyDbHelper myDbHelper;
    public ThuThuDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    //Hiển thị danh sách
    public ArrayList<ThuThu> getDSThuThu(){
        ArrayList<ThuThu> arrayList = new ArrayList<>();
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from ThuThu", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
        }do {
            arrayList.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
        }while (cursor.moveToNext());
        return arrayList;
    }

    //Them thu thư
    public boolean ThemThuThu(String ma, String ten, String mkhau, int soTK){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", ma);
        values.put("hoTen", ten);
        values.put("matKhau", mkhau);
        values.put("soTK", soTK);

        long check = database.insert("ThuThu", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }

    //Cập nhật thủ thư
    public boolean SuaThuThu(String ma, String ten, String mkhau, int soTK){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", ten);
        values.put("matKhau", mkhau);
        values.put("soTK", soTK);

        long check = database.update("ThuThu", values, "maTT=?", new String[]{ma});
        if (check == -1){
            return false;
        }
        return true;
    }

    public boolean xoaThuThu(String maTT){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PhieuMuon WHERE maTT=?", null);
        if (cursor.getCount() != 0){
            return false;
        }
        long check = database.delete("ThuThu", "maTT=?", new String[]{maTT});
        if (check == -1){
            return false;
        }return true;
    }

    //Hàm đăng nhập
    public boolean dangNhap(String maTT, String matKhau){
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?", new String[]{maTT, matKhau});
        if (cursor.getCount() != 0){
            return true;
        }
        return false;
    }

    //Cập nhật mật khẩu
    public boolean capNhapMK(String user, String mkhaucu, String mkhaumoi){
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?", new String[]{user, mkhaucu});
        if (cursor.getCount() > 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matKhau", mkhaumoi);
            long check = database.update("ThuThu", contentValues, "maTT=?", new String[]{user});
            if (check == -1){
                return false;
            }
            return true;
        }
        return false;
    }
}
