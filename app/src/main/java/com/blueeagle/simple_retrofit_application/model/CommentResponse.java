package com.blueeagle.simple_retrofit_application.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CommentResponse implements Parcelable {

    private int code;
    private String message;
    private List<Comment> data;

    public CommentResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
    }

    protected CommentResponse(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Parcelable.Creator<CommentResponse> CREATOR = new Parcelable.Creator<CommentResponse>() {
        @Override
        public CommentResponse createFromParcel(Parcel source) {
            return new CommentResponse(source);
        }

        @Override
        public CommentResponse[] newArray(int size) {
            return new CommentResponse[size];
        }
    };
}
