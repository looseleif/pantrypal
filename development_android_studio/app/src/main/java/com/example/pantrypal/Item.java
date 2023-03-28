package com.example.pantrypal;

public class Item {
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

    public Item(int id, String date){
        this.i_Id = Integer.valueOf(id);
        this.i_Name = Integer.toString(id);
        this.i_Date = date;
        this.i_Category = "Uncategorized";
        this.i_Amount = 1;
    }

    public Item(int id, String name, String date, String category, int amount){
        this.i_Id = Integer.valueOf(id);;
        this.i_Name = name;
        this.i_Date = date;
        this.i_Category = category;
        this.i_Amount = amount;
    }

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
}
