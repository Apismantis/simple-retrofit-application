package com.blueeagle.simple_retrofit_application.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class FeedResponse implements Parcelable {

    public FeedResponse() {
    }

    private int code;
    private String message;
    private List<Feed> data;

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

    public List<Feed> getData() {
        return data;
    }

    public void setData(List<Feed> data) {
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

    protected FeedResponse(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(Feed.CREATOR);
    }

    public static final Parcelable.Creator<FeedResponse> CREATOR = new Parcelable.Creator<FeedResponse>() {
        @Override
        public FeedResponse createFromParcel(Parcel source) {
            return new FeedResponse(source);
        }

        @Override
        public FeedResponse[] newArray(int size) {
            return new FeedResponse[size];
        }
    };
}

