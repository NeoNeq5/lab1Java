package tournamentP;

public class TowerPart {
    private double partBegRadius;
    private double partEndRadius;
    private double sandVolume;
    private double partHeight;
    private double sandAngleOfRepose;
    TowerPart(double sandAngleOfRepose, double sandVolume, double partBegRadius){
        this.sandAngleOfRepose = 90 - sandAngleOfRepose;
        this.sandVolume = sandVolume;
        this.partBegRadius = partBegRadius;
        calculate();
    }

    private void calculate(){
        double angleRadians = Math.toRadians(sandAngleOfRepose);

        partEndRadius = Math.cbrt(partBegRadius * partBegRadius * partBegRadius - (3 * sandVolume * Math.tan(angleRadians)) / Math.PI);

        partHeight = (partBegRadius - partEndRadius) / Math.tan(angleRadians);

    }

    public double getPartEndRadius(){
        return this.partEndRadius;
    }

    public double getPartHeight(){
        return this.partHeight;
    }
}
