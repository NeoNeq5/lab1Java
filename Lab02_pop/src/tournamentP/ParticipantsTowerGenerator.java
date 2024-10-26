package tournamentP;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ParticipantsTowerGenerator {
    private int amountOfTowers;
    private int amountBuckets;
    private int bucketPortions;
    private Bucket[] buckets;
    private TowerPlace[] towerPlaces;
    private Tower[] tower;
    private Tower[] towerToCompare;
    private int amountOfParticipants;
    private int sandPointsMultiplier;
    private int heightPointsMultiplier;

    ParticipantsTowerGenerator(Bucket[] buckets, TowerPlace[] towerPlaces, int sandPointsMultiplier, int heightPointsMultiplier, int bucketPortions, int amountOfParticipants){
        this.bucketPortions = bucketPortions;
        this.sandPointsMultiplier = sandPointsMultiplier;
        this.heightPointsMultiplier = heightPointsMultiplier;
        this.amountOfParticipants = amountOfParticipants;
        this.buckets = buckets;
        this.towerPlaces = towerPlaces;

        amountOfTowers = towerPlaces.length;
        amountBuckets = buckets.length;
        this.tower = new Tower[amountOfTowers];
        for(int i=0; i<amountOfTowers; i++){
            tower[i] = new Tower();
        }
        if(amountOfTowers > 3){
            initializeTowers();
            findBestGen();
        }
        else{
            System.out.println("Too few towers, try with more than 3");
        }
    }

    private void initializeTowers(){
        int counter = 0;
        while(counter < amountOfTowers){
            Random rand = new Random();
            int amountOfBuckets = rand.nextInt(amountBuckets * bucketPortions/amountOfTowers);
            for(int i=0; i < amountOfBuckets; i++){
                boolean found = false;
                while(!found){
                    int randomBucket = rand.nextInt(amountBuckets);
                    if(buckets[randomBucket].changeableVolume >= ((double) 1 / bucketPortions) * buckets[randomBucket].volume){
                        TowerPart towerPart;
                        if(tower[counter % amountOfTowers].getSegments().isEmpty())
                        {
                            towerPart = new TowerPart(buckets[randomBucket].radius, buckets[randomBucket].volume, towerPlaces[counter % amountOfTowers].towerPlaceRadius);
                        }
                        else{
                            towerPart = new TowerPart(buckets[randomBucket].radius, buckets[randomBucket].volume, tower[counter % amountOfTowers].getLastOne().getTopRadius());
                        }
                        if(towerPart.getPartEndRadius()>0){
                            found = true;
                            tower[counter % amountOfTowers].addSegment(towerPart.getPartHeight(), towerPart.getPartEndRadius(), randomBucket);
                            buckets[randomBucket].setVolume(bucketPortions);
                        }
                        else {
                            //System.out.println("segment base radius is to small " + towerPart.getPartEndRadius());
                            found = true;
                        }
                    }
                }
            }
            counter++;
        }
    }

    private void findBestGen(){
        double previousGenScore = 0;
        while(amountOfParticipants > 0){

            //last gen sort
            genSort();

            // reset buckets
            for(int i = 0; i < buckets.length; i++){
                buckets[i].resetBucket();
            }

            //creating new gen
            towerToCompare = new Tower[amountOfTowers];
            for (int i = 0; i < amountOfTowers; i++) {
                towerToCompare[i] = new Tower();
            }
            createNewGen();
            double currentGenScore = getTowersScores(towerToCompare);

            //change current gen to previous
            if(currentGenScore > previousGenScore){
                tower = towerToCompare;
                previousGenScore = currentGenScore;
            }

            amountOfParticipants--;
        }
        for(int i = 0; i < amountOfTowers; i++){
            double towerHeight = 0;
            for(int j = 0; j < tower[i].getSegments().size(); j++){
                towerHeight += tower[i].getSegments().get(j).getHeight();
            }
            System.out.println("wysokość wieży " + i + " = " + towerHeight);
        }
        System.out.println("Wynik najlepszego uczestnika: " + previousGenScore);
    }

    private void genSort(){
        for(int i = 0; i < amountOfTowers; i++){
            double towerPoints;
            double towerHeight = 0;
            double amountOfSand = 0;
            for(int j = 0; j < tower[i].getSegments().size(); j++){
                towerHeight += tower[i].getSegments().get(j).getHeight();
                amountOfSand +=  buckets[tower[i].getSegments().get(j).getBucketNumber()].volume*((double) 1 /bucketPortions);
            }
            towerPoints = towerHeight*heightPointsMultiplier - amountOfSand*sandPointsMultiplier;
            tower[i].setTowerScore(towerPoints);
        }
        Arrays.sort(tower, new Comparator<Tower>() {
            @Override
            public int compare(Tower t1, Tower t2) {
                return Double.compare(t2.getTowerScore(), t1.getTowerScore());
            }
        });
    }

    private void createNewGen(){
        int bestPartOfPreviousGen;
        if(amountOfTowers >= 8){
            bestPartOfPreviousGen = amountOfTowers/4;
        }else{
            bestPartOfPreviousGen = amountOfTowers/2;
        }

        for(int i = 0; i < bestPartOfPreviousGen; i++){
            towerToCompare[i] = tower[i];
        }
        for(int i = bestPartOfPreviousGen; i < amountOfTowers; i++){
            Random rand = new Random();
            int firstParentNum = rand.nextInt(bestPartOfPreviousGen);
            int secondParentNum = rand.nextInt(bestPartOfPreviousGen);
            while(firstParentNum == secondParentNum){
                secondParentNum = rand.nextInt(bestPartOfPreviousGen);
        }
            Tower firstParent = tower[firstParentNum];
            Tower secondParent = tower[secondParentNum];
            boolean lastSegmentfilled = true;
            int segmentsCounter = 0;
            while(lastSegmentfilled){
                if(rand.nextInt(5) == 1){
                    if(towerToCompare[i].getSegments().size()<amountBuckets * bucketPortions/amountOfTowers){
                        int randomBucket;
                        do{
                            randomBucket = rand.nextInt(amountBuckets);
                        }while(buckets[randomBucket].changeableVolume < 0);

                        setParentSegment(i, randomBucket);
                    }
                } else {
                    int selectedVariant = rand.nextInt(2);
                    if(selectedVariant == 0){
                        if(firstParent.getSegments().size() > segmentsCounter){
                            if(buckets[firstParent.getSegments().get(segmentsCounter).getBucketNumber()].volume > 0){
                                setParentSegment(i, firstParent.getSegments().get(segmentsCounter).getBucketNumber());
                            } else {
                                setParentSegment(i, secondParent.getSegments().get(segmentsCounter).getBucketNumber());
                            }

                        } else {
                            lastSegmentfilled = false;
                        }

                    } else if (selectedVariant == 1) {
                        if(secondParent.getSegments().size() > segmentsCounter){
                            if(buckets[secondParent.getSegments().get(segmentsCounter).getBucketNumber()].volume > 0){
                                setParentSegment(i, secondParent.getSegments().get(segmentsCounter).getBucketNumber());
                            } else {
                                setParentSegment(i, firstParent.getSegments().get(segmentsCounter).getBucketNumber());
                            }

                        } else {
                            lastSegmentfilled = false;
                        }
                    }
                    segmentsCounter++;
                }
            }
            if (rand.nextInt(10) == 1) {
                if (rand.nextBoolean()) {
                    int randomBucket;
                    do {
                        randomBucket = rand.nextInt(amountBuckets);
                    } while (buckets[randomBucket].changeableVolume < (1.0 / bucketPortions) * buckets[randomBucket].volume);

                    setParentSegment(i, randomBucket);
                } else {
                    if (!towerToCompare[i].getSegments().isEmpty()) {
                        towerToCompare[i].removeLastSegment();
                    }
                }
            }
        }
    }


    private void setParentSegment(int childNum , int bucketNum){
        boolean found = false;
//        System.out.println("searched for parent");
        while(!found) {
            if (buckets[bucketNum].changeableVolume >= ( (double) 1 / bucketPortions) * buckets[bucketNum].volume) {
                TowerPart towerPart;
                if (towerToCompare[childNum].getSegments().isEmpty()) {
                    towerPart = new TowerPart(buckets[bucketNum].radius, buckets[bucketNum].volume, towerPlaces[childNum].towerPlaceRadius);
                } else {
                    towerPart = new TowerPart(buckets[bucketNum].radius, buckets[bucketNum].volume, towerToCompare[childNum].getLastOne().getTopRadius());
                }
                if (towerPart.getPartEndRadius() > 0) {
                    found = true;
                    towerToCompare[childNum].addSegment(towerPart.getPartHeight(), towerPart.getPartEndRadius(), bucketNum);
                    buckets[bucketNum].setVolume(bucketPortions);
                }
                else{
                    found = true;
                }
            }
            else {
                int emptiness = 0;
                for (Bucket bucket : buckets) {
                    if((int)bucket.changeableVolume == 0)
                        emptiness++;
                }
                if (emptiness == amountBuckets) {
                    found = true;
                }
                Random rand = new Random();
                bucketNum = rand.nextInt(amountBuckets);
            }
        }
    }

    private double getTowersScores(Tower[] tower1){
        double heighSum = 0;
        double sandSum = 0;

        for(int i = 0; i < amountOfTowers; i++){
            for(int j = 0; j < tower1[i].getSegments().size(); j++){
                heighSum = heighSum + tower1[i].getSegments().get(j).getHeight();
            }
        }

        for (Bucket bucket : buckets) {
            sandSum = sandSum + bucket.changeableVolume;
        }
        return (heighSum/amountOfTowers)*heightPointsMultiplier + sandSum*sandPointsMultiplier;
    }
}
