package com.example.pantrypal;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Item implements Parcelable{
    /**
     * Id of item
     */
    private Integer i_Id;

    /**
     * Name of item
     */
    private String i_Name;

    /**
     * Expiration date of item
     */
    private String i_Date;

    /**
     * Category of item
     */
    private String i_Category;

    /**
     * Amount of item
     */
    private int i_Amount;

    /**
     * Location of item
     */
    private String i_Location;

    public Item(int id, String date){
        this.i_Id = Integer.valueOf(id);
        this.i_Name = Integer.toString(id);
        this.i_Date = date;
        this.i_Category = "Uncategorized";
        this.i_Amount = 1;
    }

    public Item(int id, String name, String date, String category, int amount, String location){
        this.i_Id = Integer.valueOf(id);;
        this.i_Name = name;
        this.i_Date = date;
        this.i_Category = category;
        this.i_Amount = amount;
        this.i_Location = location;
    }

    protected Item(Parcel in) {
        if (in.readByte() == 0) {
            i_Id = null;
        } else {
            i_Id = in.readInt();
        }
        i_Name = in.readString();
        i_Date = in.readString();
        i_Category = in.readString();
        i_Amount = in.readInt();
        i_Location = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public void setI_Id(int id){
        this.i_Id = Integer.valueOf(id);
    }

    public Integer getI_Id(){
        return this.i_Id;
    }

    public void setI_Name(String name){
        this.i_Name = name;
    }

    public String getI_Name(){
        return this.i_Name;
    }

    public void setI_Date(String date){
        this.i_Date = date;
    }

    public String getI_Date(){
        return this.i_Date;
    }

    public void setI_Category(String category){
        this.i_Category = category;
    }

    public String getI_Category(){
        return this.i_Category;
    }

    public void setI_Amount(int amount){
        this.i_Amount = amount;
    }

    public int getI_Amount(){
        return this.i_Amount;
    }

    public void setI_Location(String location) {
        this.i_Location = i_Location;
    }

    public String getI_Location() {
        return i_Location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (i_Id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(i_Id);
        }
        parcel.writeString(i_Name);
        parcel.writeString(i_Date);
        parcel.writeString(i_Category);
        parcel.writeInt(i_Amount);
        parcel.writeString(i_Location);
    }
}
