package tournamentP;


public class tournament {
    private Bucket[] buckets;
    private TowerPlace[] towerPlaces;
    private Data data;
    private ParticipantsTowerGenerator generator;
    private int sandPointsMultiplier;
    private int heightPointsMultiplier;
    private int bucketPortions = 3;
    private int amountOfParticipants = 20;

    tournament(){
        data = new Data();
        readData();
        generator = new ParticipantsTowerGenerator(buckets, towerPlaces, sandPointsMultiplier, heightPointsMultiplier, bucketPortions, amountOfParticipants);
    }

    private void readData(){
        buckets = data.getBuckets();
        towerPlaces = data.getTowerPlaces();
        sandPointsMultiplier = data.getSandMultiplier();
        heightPointsMultiplier = data.getHeightMultiplier();
    }

    public static void main(String[] args) {
        tournament tournament = new tournament();
    }

}