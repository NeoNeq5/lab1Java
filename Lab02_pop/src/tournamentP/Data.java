package tournamentP;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Data {
    private Bucket[] buckets = new Bucket[0];
    private TowerPlace[] towerPlaces = new TowerPlace[0];
    private int heightPointsMultiplier;
    private int sandPointsMultiplier;
    Data(){
        readData();
        getData();
    }
    private void readData(){
        //buckets Reading
        try (Scanner fileWiaderka = new Scanner(new File("D:\\java sem3\\Lab02_pop\\src\\tournamentP\\wiaderka.txt"))){
            int numOfBuckets = 0;
            while (fileWiaderka.hasNextLine()) {
                String line = fileWiaderka.nextLine();
                numOfBuckets++;
            }
            buckets = new Bucket[numOfBuckets];
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try (Scanner fileWiaderka = new Scanner(new File("D:\\java sem3\\Lab02_pop\\src\\tournamentP\\wiaderka.txt"))){
            int numOfBuckets = 0;
            while (fileWiaderka.hasNextLine()) {
                String line = fileWiaderka.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        try {
                            int bucketNum = Integer.parseInt(parts[0].trim());
                            int radius = Integer.parseInt(parts[1].trim());
                            int volume = Integer.parseInt(parts[2].trim());

                            // Initialize a new Bucket object and assign it to the array
                            buckets[numOfBuckets] = new Bucket(bucketNum, radius, volume);
                            numOfBuckets++;
                        }
                        catch (NumberFormatException e) {
                            System.err.println("Błąd parsowania liczby: " + e.getMessage());
                        }
                    }
                    else {
                        System.err.println("Nieprawidłowy format danych w linii: " + line);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        //TowerPlaces
        try (Scanner fileMiejsca = new Scanner(new File("D:\\java sem3\\Lab02_pop\\src\\tournamentP\\miejsca.txt"))){
            int numOfPlaces = 0;
            while (fileMiejsca.hasNextLine()) {
                String line = fileMiejsca.nextLine();
                if (!line.trim().isEmpty()) {
                    numOfPlaces++;
                }
            }
            towerPlaces = new TowerPlace[numOfPlaces];
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try (Scanner fileMiejsca = new Scanner(new File("D:\\java sem3\\Lab02_pop\\src\\tournamentP\\miejsca.txt"))){
            int numOfPlaces = 0;
            while (fileMiejsca.hasNextLine()) {
                String line = fileMiejsca.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        try {
                            int towerPlaceNum = Integer.parseInt(parts[0].trim());
                            int towerPlaceRadius = Integer.parseInt(parts[1].trim());

                            // Initialize a new TowerPlace object and assign it to the array
                            towerPlaces[numOfPlaces] = new TowerPlace(towerPlaceNum, towerPlaceRadius);
                            numOfPlaces++;
                        }
                        catch (NumberFormatException e) {
                            System.err.println("Błąd parsowania liczby: " + e.getMessage());
                        }
                    }
                    else {
                        System.err.println("Nieprawidłowy format danych w linii: " + line);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getData(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("podaj mnożnik punktacji za wysokości wież:");
        heightPointsMultiplier = scanner.nextInt();
        System.out.println("podaj mnożnik punktacji za ilość pozostawionego piasku :");
        sandPointsMultiplier = scanner.nextInt();

    }

    public Bucket[] getBuckets(){
        return this.buckets;
    }
    public TowerPlace[] getTowerPlaces(){
        return this.towerPlaces;
    }
    public int getHeightMultiplier(){
        return this.heightPointsMultiplier;
    }
    public int getSandMultiplier(){
        return this.sandPointsMultiplier;
    }

}
