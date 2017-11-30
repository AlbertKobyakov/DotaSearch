package com.example.albert.dotasearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoundUser implements Parcelable {
    @SerializedName("account_id")
    @Expose
    private long accountId;
    @SerializedName("avatarfull")
    @Expose
    private String avatarfull;
    @SerializedName("personaname")
    @Expose
    private String personaname;
    @SerializedName("last_match_time")
    @Expose
    private Object lastMatchTime;
    @SerializedName("similarity")
    @Expose
    private double similarity;

    public FoundUser()
    {

    }

    private FoundUser(Parcel in) {
        accountId = in.readLong();
        avatarfull = in.readString();
        personaname = in.readString();
        lastMatchTime = in.readString();
        similarity = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(accountId);
        dest.writeString(avatarfull);
        dest.writeString(personaname);
        dest.writeString(lastMatchTime+"");
        dest.writeDouble(similarity);
    }

    public static final Parcelable.Creator<FoundUser> CREATOR = new Parcelable.Creator<FoundUser>() {
        public FoundUser createFromParcel(Parcel in) {
            return new FoundUser(in);
        }

        public FoundUser[] newArray(int size) {
            return new FoundUser[size];
        }
    };

    public long getAccountId() {
        return accountId;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public String getPersonaname() {
        return personaname;
    }

    public Object getLastMatchTime() {
        return lastMatchTime;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public void setLastMatchTime(Object lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
