package ch.model;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class OverviewParameters {
    private final IntegerProperty avgDaySymptoms;
    private final IntegerProperty avgNightSymptoms;
    private final IntegerProperty avgActiveLim;
    private final IntegerProperty avgRelivMed;
    private final DoubleProperty avgMorningPEF;
    private final DoubleProperty avgEveningPEF;
    private final DoubleProperty avgFEV1;
    private final StringProperty mostFrequentSymptom;

    /**
     * Default Constructor
     * Kan pt ikke benyttes. Den forårsager NullPointerException når den kaldes og kan ikke gennemskue en fiks...
     *
     */
    public OverviewParameters(){this(null, null, null, null, null, null, null, null);}


    public OverviewParameters(Integer avgDaySymptoms, Integer avgNightSymptoms, Integer avgActiveLim, Integer avgRelivMed, Double avgMorningPEF, Double avgEveningPEF, Double avgFEV1, String mostFrequentSymptom) {
        this.avgDaySymptoms = new SimpleIntegerProperty(avgDaySymptoms);
        this.avgNightSymptoms = new SimpleIntegerProperty(avgNightSymptoms);
        this.avgActiveLim = new SimpleIntegerProperty(avgActiveLim);
        this.avgRelivMed = new SimpleIntegerProperty(avgRelivMed);
        this.avgMorningPEF = new SimpleDoubleProperty(avgMorningPEF);
        this.avgEveningPEF = new SimpleDoubleProperty(avgEveningPEF);
        this.avgFEV1 = new SimpleDoubleProperty(avgFEV1);
        this.mostFrequentSymptom = new SimpleStringProperty(mostFrequentSymptom);
    }

    public IntegerProperty avgDaySymptomsProperty() {
        return avgDaySymptoms;
    }

    public Integer getAvgDaySymptoms(){
        return avgDaySymptoms.get();
    }

    public void setAvgDaySymptoms(Integer avgDaySymptoms){
        this.avgDaySymptoms.set(avgDaySymptoms);
    }

    public IntegerProperty avgNightSymptomsProperty() {
        return avgNightSymptoms;
    }

    public Integer getAvgNightSymptoms(){
        return avgNightSymptoms.get();
    }

    public void setAvgNightSymptoms(Integer avgNightSymptoms){
        this.avgNightSymptoms.set(avgNightSymptoms);
    }

    public IntegerProperty avgActiveLimProperty() {
        return avgActiveLim;
    }

    public Integer getAvgActiveLim(){
        return avgActiveLim.get();
    }

    public void setAvgActiveLim(Integer avgActiveLim){
        this.avgActiveLim.set(avgActiveLim);
    }

    public IntegerProperty avgRelivMedProperty() {
        return avgRelivMed;
    }

    public Integer getAvgRelivMed(){
        return avgRelivMed.get();
    }

    public void setAvgRelivMed(Integer avgRelivMed){
        this.avgRelivMed.set(avgRelivMed);
    }

    public DoubleProperty avgMorningPEFProperty() {
        return avgMorningPEF;
    }

    public Double getAvgMorningPEF(){
        return avgMorningPEF.get();
    }

    public void setAvgMorningPEF(Integer avgMorningPEF){
        this.avgMorningPEF.set(avgMorningPEF);
    }

    public DoubleProperty avgEveningPEFProperty() {
        return avgEveningPEF;
    }

    public Double getAvgEveningPEF(){
        return avgEveningPEF.get();
    }

    public void setAvgEveningPEF(Integer avgEveningPEF){
        this.avgEveningPEF.set(avgEveningPEF);
    }

    public StringProperty mostFrequentProperty(){return mostFrequentSymptom;}

    public String getMostFrequent(){
        return mostFrequentSymptom.get();
    }

    public void setMostFrequentSymptom(String mostFrequentSymptom){
        this.mostFrequentSymptom.set(mostFrequentSymptom);
    }

    public DoubleProperty getAvgFEV1() {
        return avgFEV1;
    }

    public void setAvgFEV1(Double avgFEV1) {
        this.avgFEV1.set(avgFEV1);
    }
}

