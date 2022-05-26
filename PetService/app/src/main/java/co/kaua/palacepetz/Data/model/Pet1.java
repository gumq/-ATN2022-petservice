package co.kaua.palacepetz.Data.model;

public class Pet1 {
    String name;
    int hinh;
    String gia;
    String chiTiet;
    public Pet1(String name, int hinh, String gia, String chiTiet) {
        this.name = name;
        this.hinh = hinh;
        this.gia = gia;
        this.chiTiet = chiTiet;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHinh() {
        return hinh;
    }
    public void setHinh(int hinh) {
        this.hinh = hinh;
    }
    public String getGia() {
        return gia;
    }
    public void setGia(String gia) {
        this.gia = gia;
    }
    public String getChiTiet() {
        return chiTiet;
    }
    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }
}
