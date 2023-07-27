package com.example.duanmau.DTO;

public class ThuThu {
    String maTT, tenTT, matKhau;
    int soTaiKhoan;

    public ThuThu(String maTT, String tenTT, String matKhau, int soTaiKhoan) {
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.matKhau = matKhau;
        this.soTaiKhoan = soTaiKhoan;
    }

    public int getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(int soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

}
