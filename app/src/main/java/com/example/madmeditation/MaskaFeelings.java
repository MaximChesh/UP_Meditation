package com.example.madmeditation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MaskaFeelings implements Parcelable {
    private int Id;
    private String Title;
    private int Position;
    private String Image;

    protected MaskaFeelings(Parcel in) {
        Id = in.readInt();
        Title = in.readString();
        Image = in.readString();
        Position = in.readInt();
    }
    public MaskaFeelings(int id, String title, int position, String image){
        Id = id;
        Title = title;
        Position = position;
        Image = image;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Title);
        dest.writeString(Image);
        dest.writeInt(Position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MaskaFeelings> CREATOR = new Creator<MaskaFeelings>() {
        @Override
        public MaskaFeelings createFromParcel(Parcel in) {
            return new MaskaFeelings(in);
        }

        @Override
        public MaskaFeelings[] newArray(int size) {
            return new MaskaFeelings[size];
        }
    };


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}