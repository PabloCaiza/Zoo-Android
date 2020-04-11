package com.pablo.zoologico.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
//serializamos la clase para poder trasferir un objero a octras activities
public class Product implements Parcelable {
    private String name;
    private Date  manufacture;
    private Date expire;
    private double cost;
    private byte[]image;

    public Product() {
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Product(String name, Date manufacture, Date expire, double cost, byte[] image) {
        this.name = name;
        this.manufacture = manufacture;
        this.expire = expire;
        this.cost = cost;
        this.image = image;
    }


    public Product(Parcel parcel){
        this.name=parcel.readString();
        this.manufacture=new Date(parcel.readLong());
        this.expire=new Date(parcel.readLong());
        this.cost=parcel.readDouble();
        byte[] aux=new byte[parcel.readInt()];
        parcel.readByteArray(aux);
        this.image=aux;
    }


    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getManufacture() {
        return manufacture;
    }

    public void setManufacture(Date manufacture) {
        this.manufacture = manufacture;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(manufacture.getTime());
        dest.writeLong(expire.getTime());
        dest.writeDouble(cost);
        dest.writeInt(image.length);
        dest.writeByteArray(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.cost, cost) != 0) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (manufacture != null ? !manufacture.equals(product.manufacture) : product.manufacture != null)
            return false;
        return expire != null ? expire.equals(product.expire) : product.expire == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (manufacture != null ? manufacture.hashCode() : 0);
        result = 31 * result + (expire != null ? expire.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
