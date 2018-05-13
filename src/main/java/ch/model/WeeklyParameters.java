package ch.model;

import java.util.ArrayList;
import java.util.List;

public class WeeklyParameters {
    private List<List<Integer>> ugeListeDagSymptomer;
    private List<List<Integer>> ugeListeNatSymptomer;
    private List<Integer> ugeListeAktivitet;
    private List<Integer> ugeListeAnfaldsMed;
    private List<List<Double>> pctListeDagSymptomer;
    private List<List<Double>> pctListeNatSymptomer;
    private List<Double> pctPeriodeDagSymptom;
    private List<Double> pctPeriodeNatSymptom;

    public List<List<Integer>> getUgeListeDagSymptomer() {
        return ugeListeDagSymptomer;
    }

    public void setUgeListeDagSymptomer(List<List<Integer>> ugeListeDagSymptomer) {
        this.ugeListeDagSymptomer = ugeListeDagSymptomer;
    }

    public List<List<Integer>> getUgeListeNatSymptomer() {
        return ugeListeNatSymptomer;
    }

    public void setUgeListeNatSymptomer(List<List<Integer>> ugeListeNatSymptomer) {
        this.ugeListeNatSymptomer = ugeListeNatSymptomer;
    }

    public List<Integer> getUgeListeAktivitet(){
        return ugeListeAktivitet;
    }

    public void setUgeListeAktivitet(List<Integer> ugeListeAktivitet){
        this.ugeListeAktivitet = ugeListeAktivitet;
    }

    public List<Integer> getUgeListeAnfaldsMed(){
        return ugeListeAnfaldsMed;
    }

    public void setUgeListeAnfaldsMed(List<Integer> ugeListeAnfaldsMed){
        this.ugeListeAnfaldsMed = ugeListeAnfaldsMed;
    }

    public List<List<Double>> getPctListeDagSymptomer(){
        return pctListeDagSymptomer;
    }

    public void setPctListeDagSymptomer(List<List<Double>> pctListeDagSymptomer){
        this.pctListeDagSymptomer = pctListeDagSymptomer;
    }

    public List<List<Double>> getPctListeNatSymptomer() {
        return pctListeNatSymptomer;
    }

    public void setPctListeNatSymptomer(List<List<Double>> pctListeNatSymptomer) {
        this.pctListeNatSymptomer = pctListeNatSymptomer;
    }

    public List<Double> getPctPeriodeDagSymptom() {
        return pctPeriodeDagSymptom;
    }

    public void setPctPeriodeDagSymptom(List<Double> pctPeriodeDagSympmtom) {
        this.pctPeriodeDagSymptom = pctPeriodeDagSympmtom;
    }

    public List<Double> getPctPeriodeNatSymptom() {
        return pctPeriodeNatSymptom;
    }

    public void setPctPeriodeNatSymptom(List<Double> pctPeriodeNatSympmtom) {
        this.pctPeriodeNatSymptom = pctPeriodeNatSympmtom;
    }
}
