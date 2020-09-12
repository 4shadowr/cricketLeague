package com.example.cricketleague.models.Match;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Match implements Parcelable {

    private final String id;
    private final MatchInfo matchInfo;
    private final MatchSummary matchSummary;

    public Match(String id, MatchInfo matchInfo, MatchSummary matchSummary) {
        this.id = id;
        this.matchInfo = matchInfo;
        this.matchSummary = matchSummary;
    }

    public String getId() {
        return id;
    }

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public MatchSummary getMatchSummary() {
        return matchSummary;
    }

    protected Match(Parcel in) {
        id = in.readString();
        matchInfo = in.readParcelable(MatchInfo.class.getClassLoader());
        matchSummary = in.readParcelable(MatchSummary.class.getClassLoader());
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeParcelable(matchInfo, i);
        parcel.writeParcelable(matchSummary, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id.equals(match.id) &&
                matchInfo.equals(match.matchInfo) &&
                matchSummary.equals(match.matchSummary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matchInfo, matchSummary);
    }
}
