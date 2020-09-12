package com.example.cricketleague.models.Match;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cricketleague.Utils.DateTimeUtils;

import java.time.LocalDateTime;

public class MatchInfo implements Parcelable {

    private final LocalDateTime dateTime;
    private final String location;

    private final String homeTeamId;
    private final String awayTeamId;

    public MatchInfo(LocalDateTime dateTime, String location, String homeTeamId, String awayTeamId) {
        this.dateTime = dateTime;
        this.location = location;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    public String getLocation() {
        return location;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    protected MatchInfo(Parcel in) {
        dateTime = DateTimeUtils.getDateTimeFromString(in.readString());
        location = in.readString();
        homeTeamId = in.readString();
        awayTeamId = in.readString();
    }

    public static final Creator<MatchInfo> CREATOR = new Creator<MatchInfo>() {
        @Override
        public MatchInfo createFromParcel(Parcel in) {
            return new MatchInfo(in);
        }

        @Override
        public MatchInfo[] newArray(int size) {
            return new MatchInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dateTime.toString());
        parcel.writeString(location);
        parcel.writeString(homeTeamId);
        parcel.writeString(awayTeamId);
    }
}
