package com.sparta.finalproject6.model;

import java.util.Objects;

public enum Grade {
    MASTER(Integer.MAX_VALUE, null),
    DIAMOND(600, MASTER),
    GOLD(400, DIAMOND),
    SILVER(200, GOLD),
    BRONZE(50, SILVER),

    NORMAL(10, BRONZE);

    private final int nextPoint;
    private final Grade nextGrade;

    Grade(int nextPoint, Grade nextGrade) {
        this.nextPoint = nextPoint;
        this.nextGrade = nextGrade;
    }

    public static boolean availableGradeUp(Grade grade, int totalPoint) {
        if(Objects.isNull(grade)) {
            return false;
        }

        if(Objects.isNull(grade.nextGrade)) {
            return false;
        }

        return totalPoint >= grade.nextPoint;
    }

    static Grade getNextGrade(int totalPoint) {
        if(totalPoint >= Grade.MASTER.nextPoint) {
            return MASTER;
        }

        if(totalPoint >= Grade.DIAMOND.nextPoint) {
            return DIAMOND.nextGrade;
        }

        if(totalPoint >= Grade.GOLD.nextPoint) {
            return GOLD.nextGrade;
        }

        if(totalPoint >= Grade.SILVER.nextPoint) {
            return SILVER.nextGrade;
        }

        if(totalPoint >= Grade.BRONZE.nextPoint) {
            return BRONZE.nextGrade;
        }

        if(totalPoint >= Grade.NORMAL.nextPoint) {
            return NORMAL.nextGrade;
        }

        return NORMAL;
    }
}
