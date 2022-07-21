package com.sparta.finalproject6.model;

import java.util.Objects;

public enum Rank {
    MASTER(600, null),
    DIAMOND(400, MASTER),
    GOLD(200, DIAMOND),
    SILVER(50, GOLD),
    BRONZE(0, SILVER);

    private final int nextPoint;
    private final Rank nextRank;

    Rank(int nextPoint, Rank nextRank) {
        this.nextPoint = nextPoint;
        this.nextRank = nextRank;
    }

    public static boolean availableRankUp(Rank rank, int totalPoint) {
        if(Objects.isNull(rank)) {
            return false;
        }

        if(Objects.isNull(rank.nextRank)) {
            return false;
        }

        return totalPoint >= rank.nextPoint;
    }

    static Rank getNextRank(int totalPoint) {
        if(totalPoint >= Rank.MASTER.nextPoint) {
            return MASTER;
        }

        if(totalPoint >= Rank.DIAMOND.nextPoint) {
            return DIAMOND.nextRank;
        }

        if(totalPoint >= Rank.GOLD.nextPoint) {
            return GOLD.nextRank;
        }

        if(totalPoint >= Rank.SILVER.nextPoint) {
            return SILVER.nextRank;
        }

        return BRONZE;
    }
}
