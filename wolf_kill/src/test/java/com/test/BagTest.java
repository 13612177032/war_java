package com.test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liangchaolei on 2017/8/15.
 */
public class BagTest {
    public static void main(String[] args) {
        Bag bag=new Bag(14);
        Set<Thing> sets=new HashSet<Thing>();
        sets.add(new Thing(1,1));
        sets.add(new Thing(12,16));
        sets.add(new Thing(2,5));
        sets.add(new Thing(6,8));
        sets.add(new Thing(3,3));
        sets.add(new Thing(4,2));
        sets.add(new Thing(5,11));
        sets.add(new Thing(12,13));

        for (Thing t:sets) {
            bag.push(t);
        }
    }


    static class Bag{
        Set<Thing> set=new HashSet<Thing>();

        int maxWeight;

        int nowWight;

        int nowValue;

        public Bag(int maxWeight) {
            this.maxWeight = maxWeight;
        }

        public boolean push(Thing thing){
            if(nowWight+thing.weight<maxWeight) return realPush(thing);

            return false;
        }

        private boolean realPush(Thing thing) {
            nowWight+=thing.weight;
            nowValue+=thing.value;
            set.add(thing);
            return true;
        }

    }
    static class Thing{
        int weight;
        int value;

        double valueRate;

        public Thing(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        public double getValueRate() {
            return value/weight;
        }
    }


}
