package co.tranlequyen.palacepetz.Data.model;

public class Pet {
    String name;
    String gia;
    String soLuong;

    public Pet(String name, String gia, String soLuong) {
        this.name = name;
        this.gia = gia;
        this.soLuong = soLuong;
    }
    public Pet(){
        this.name = null;
        this.gia = null;
        this.soLuong = null;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

}

