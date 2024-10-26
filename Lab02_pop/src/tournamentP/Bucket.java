package tournamentP;

public class Bucket{
    int bucketNum;
    int radius;
    double volume;
    double changeableVolume;
    Bucket(int bucketNum, int radius, double volume){
        this.bucketNum = bucketNum;
        this.radius = radius;
        this.volume = volume;
        this.changeableVolume = volume;
    }

    public void setVolume(int bucketPortion) {
        this.changeableVolume = this.changeableVolume - ((double) 1 /bucketPortion)*this.volume;
       // System.out.println("Volume set to " + String.format("%.4f",this.changeableVolume));
    }
    public void resetBucket(){
        this.changeableVolume = volume;
    }
}