package com.example.cricketleague.models.Match;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cricketleague.models.player.Player;
import com.example.cricketleague.models.team.Team;

public class MatchSummary implements Parcelable {
    private String id;

    private final Team winner;
    private final Team looser;

    private final Player ManOfTheMatch;
    private final Player BowlerOfTheMatch;
    private final Player bestFielder;

    public MatchSummary(String id,Team winner, Team looser, Player manOfTheMatch, Player bowlerOfTheMatch, Player bestFielder) {
        this.id = id;
        this.winner = winner;
        this.looser = looser;
        ManOfTheMatch = manOfTheMatch;
        BowlerOfTheMatch = bowlerOfTheMatch;
        this.bestFielder = bestFielder;
    }

    public String getId() {
        return id;
    }

    public Team getWinner() {
        return winner;
    }

    public Team getLooser() {
        return looser;
    }

    public Player getManOfTheMatch() {
        return ManOfTheMatch;
    }

    public Player getBowlerOfTheMatch() {
        return BowlerOfTheMatch;
    }

    public Player getBestFielder() {
        return bestFielder;
    }

    protected MatchSummary(Parcel in) {
        winner = in.readParcelable(Team.class.getClassLoader());
        looser = in.readParcelable(Team.class.getClassLoader());
        ManOfTheMatch = in.readParcelable(Player.class.getClassLoader());
        BowlerOfTheMatch = in.readParcelable(Player.class.getClassLoader());
        bestFielder = in.readParcelable(Player.class.getClassLoader());
    }

    public static final Creator<MatchSummary> CREATOR = new Creator<MatchSummary>() {
        @Override
        public MatchSummary createFromParcel(Parcel in) {
            return new MatchSummary(in);
        }

        @Override
        public MatchSummary[] newArray(int size) {
            return new MatchSummary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(winner, i);
        parcel.writeParcelable(looser, i);
        parcel.writeParcelable(ManOfTheMatch, i);
        parcel.writeParcelable(BowlerOfTheMatch, i);
        parcel.writeParcelable(bestFielder, i);
    }
}
