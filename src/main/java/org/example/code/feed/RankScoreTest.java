package org.example.code.feed;

import org.joda.time.DateTime;

/**
 * @author chenxuegui
 * @since 2024/1/18
 */
public class RankScoreTest {

    public static Long baseTime = new DateTime().minusDays(100).getMillis();

    public static void main(String[] args) {
        System.out.println(feedScore(0,0,0,new DateTime().getMillis()));
    }

    public static double feedScore(double likeNum,double commentNum,double viewNum, Long time){
        double W1 =30* (likeNum-3)/297;
        double W2 =60* (commentNum-3) /97;
        double W3 =10* (viewNum-10) /490;
        double minAgo = (time - baseTime)/ (1000D*60);
        double T = Math.pow( 2.718,-0.005*(minAgo/60));
        double score =T* (W1+W2+W3);
        return score;
    }
}
