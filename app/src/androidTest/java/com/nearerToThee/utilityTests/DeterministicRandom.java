package com.nearerToThee.utilityTests;

import java.util.Random;

/**
 * Created by Betsy on 4/17/2016.
 */
public class DeterministicRandom extends Random {
    int sequentialNum = 0;

    public DeterministicRandom(){
        super();
    }

    public int nextInt(int max){
        return sequentialNum++;
    }
}
