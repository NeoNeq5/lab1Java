package tournamentP;


import java.util.ArrayList;
import java.util.List;

public class Tower {
    public static class Segment {
        private double height;
        private double topRadius;
        private int bucketNumber;

        public Segment(double height, double topRadius, int bucketNumber) {
            this.height = height;
            this.topRadius = topRadius;
            this.bucketNumber = bucketNumber;
        }

        public double getHeight() {return height;}
        public double getTopRadius() {return topRadius;}
        public int getBucketNumber() {return bucketNumber;}

    }

    private List<Segment> segments;
    private double towerScore = 0;

    public Tower() {
        this.segments = new ArrayList<>();
    }

    public void addSegment(double height, double topRadius, int bucketNumber) {
        Segment newSegment = new Segment(height, topRadius, bucketNumber);
        segments.add(newSegment);
    }

    public void removeLastSegment() {
        if (!segments.isEmpty()) {
            segments.remove(segments.size() - 1);
        } else {
            System.out.println("No segments to remove.");
        }
    }

    public List<Segment> getSegments() {
        if(segments.isEmpty()){
            //System.out.println("No segments found");
            return segments;
        }
        //System.out.println("segments found");
        return segments;

    }

    public Segment getLastOne() {
        if (segments.size() >= 1) {
            return segments.get(segments.size() - 1);
        } else {
            System.out.println("Not enough segments to get the one before last segment");
            return null;
        }
    }

    public double getTowerScore(){
        return towerScore;
    }

    public void setTowerScore(double score){
        towerScore = score;
    }


}