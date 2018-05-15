package ch.controller;

import ch.MainApp;
import ch.db_And_FHIR.FhirControl;
import ch.db_And_FHIR.dbControl;
import ch.model.EncapsulatedParameters;
import ch.model.OverviewParameters;
import ch.model.WeeklyParameters;
import org.hl7.fhir.dstu3.model.IntegerType;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Quantity;
import org.hl7.fhir.exceptions.FHIRException;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculatedParametersCtrl {

    public CalculatedParametersCtrl() {
    }



    //FhirControl FhirClass = FhirControl.getInstance();

    public EncapsulatedParameters buildCalculatedParameters(Integer patientIdentifier, LocalDate startDate, LocalDate endDate){
        List<Observation> FhirObservations;// = new ArrayList<>();
        List<Observation> fev1Liste = new ArrayList<>();
        List<Observation> dagSymptomListe = new ArrayList<>();
        List<Observation> natSymptomListe = new ArrayList<>();
        List<Observation> anfaldsMedListe = new ArrayList<>();
        List<Observation> triggerListe = new ArrayList<>();
        List<Observation> aktivitetsListe = new ArrayList<>();
        List<Observation> pefMorgenListe = new ArrayList<>();
        List<Observation> pefAftenListe = new ArrayList<>();
        List<Observation> dagSHoste = new ArrayList<>();
        List<Observation> dagSHvaesen = new ArrayList<>();
        List<Observation> dagSSlim = new ArrayList<>();
        List<Observation> dagSTryk = new ArrayList<>();
        List<Observation> dagSAande = new ArrayList<>();
        List<Observation> triggerAktiv = new ArrayList<>();
        List<Observation> triggerAllergi = new ArrayList<>();
        List<Observation> triggerStoev = new ArrayList<>();
        List<Observation> triggerUkendt = new ArrayList<>();
        List<Observation> natSHoste = new ArrayList<>();
        List<Observation> natSOpvaagning = new ArrayList<>();
        List<Observation> natSTraethed = new ArrayList<>();

        // Sætter de to instans variable til null, da vi aldrig skal bibeholde noget data hvis denne metode kaldes igen.

        // INDDEL listerne i intervaller af 7 dage IKKE kalender uger.
        WeeklyParameters WeekParam = new WeeklyParameters();
        EncapsulatedParameters encapsulatedParameters = new EncapsulatedParameters();
        //OVParam = null;
        //WeekParam = null;


        Long avgWeekRounder = (ChronoUnit.DAYS.between(startDate, endDate))/7;
        Integer avgWeekRounderInt = Math.round(avgWeekRounder.intValue());
        //System.out.println(avgWeekRounderInt);
        // Henter instansen af FhirControl, dette er IKKE at lave et nyt objekt.
        FhirControl FhirClass = FhirControl.getInstance();

        FhirObservations = FhirClass.getFHIRObservations(patientIdentifier.toString(), startDate, endDate);

        /**
         *
         */
        dbControl dbClass = dbControl.getInstance();
        fev1Liste = dbClass.buildFEV(patientIdentifier-20);
        List<Observation> pctFev = new ArrayList<>();
        pctFev = pctAfPEV1(fev1Liste);

        Double avgFev = gnmsnit(fev1Liste);

        /*for (int j = 0; j <FhirObservations.size(); j++){
    System.out.println(FhirObservations.get(j).getCode().getCoding().get(0).getCode());
}*/
        /**
         * Inddeler FhirObservationerne i liste efter deres koder
         */
        while (!FhirObservations.isEmpty()){
            if (FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Behov for anfalds medicin")){
                anfaldsMedListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);
            }
            else if(FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Nat Symptom")){
                natSymptomListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);
            }else if (FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Dag Symptom")){
                dagSymptomListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);
            }
            else if (FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Triggers")){
                triggerListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);

            }
            else if (FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Aktivitetsbegraensning")){
                aktivitetsListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);
            }
            else if (FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Morgen måling")){
                pefMorgenListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);
            }
            else if (FhirObservations.get(0).getCode().getCoding().get(0).getCode().equals("Aften måling")){
                pefAftenListe.add(FhirObservations.get(0));
                FhirObservations.remove(0);
            }
        }
        //System.out.println("DagsSymptom size = " + dagSymptomListe.size());
        OverviewParameters OVParam;

        /**
         * Inddeler yderligere listerne "NatSymptomer", "DagSymptomer" og "Triggers", i deres respektive underkomponenter vha. deres values.
         * Dette gøres ved nye lister. Ye I know, lister, lister, lister...
         */
        for (int j = 0; j<natSymptomListe.size(); j++){
            if (natSymptomListe.get(j).getValue().toString().equals("Hoste")){
                natSHoste.add(natSymptomListe.get(j));
            }
            else if  (natSymptomListe.get(j).getValue().toString().equals("Opvågning")){
                natSOpvaagning.add(natSymptomListe.get(j));
            }
            else if  (natSymptomListe.get(j).getValue().toString().equals("Træthed")){
                natSTraethed.add(natSymptomListe.get(j));
            }
        }
        for (int j = 0; j<triggerListe.size(); j++){
            if (triggerListe.get(j).getValue().toString().equals("Aktivitet")){
                triggerAktiv.add(triggerListe.get(j));
            }
            else if  (triggerListe.get(j).getValue().toString().equals("Allergi")){
                triggerAllergi.add(triggerListe.get(j));
            }
            else if  (triggerListe.get(j).getValue().toString().equals("Støv")){
                triggerStoev.add(triggerListe.get(j));
            }
            else if  (triggerListe.get(j).getValue().toString().equals("Ukendt")){
                triggerUkendt.add(triggerListe.get(j));
            }
        }
        for (int j = 0; j<dagSymptomListe.size(); j++){
            if (dagSymptomListe.get(j).getValue().toString().equals("Hvaesen")){
                dagSHvaesen.add(dagSymptomListe.get(j));
            }
            else if (dagSymptomListe.get(j).getValue().toString().equals("Hosten")){
                dagSHoste.add(dagSymptomListe.get(j));
            }
            else if  (dagSymptomListe.get(j).getValue().toString().equals("Hoste m. slim")){
                dagSSlim.add(dagSymptomListe.get(j));
            }
            else if  (dagSymptomListe.get(j).getValue().toString().equals("Trykken for brystet")){
                dagSTryk.add(dagSymptomListe.get(j));
            }
            else if  (dagSymptomListe.get(j).getValue().toString().equals("Åndenød")){
                dagSAande.add(dagSymptomListe.get(j));
            }
        }
        /*System.out.println("Hvaesen Size = " + dagSHvaesen.size());
        System.out.println("Hoste Size = " + dagSHoste.size());
        System.out.println("Aandenød Size = " + dagSAande.size());
        System.out.println("Slim Size = " + dagSSlim.size());
        System.out.println("Tryk Size = " + dagSTryk.size());*/

        /**
         * Afgør hvilket symptom er forekommet mest.
         * Simpelt størrelses check på de 5 lister.
         */
        String mostFrequent = dagSHvaesen.get(0).getValue().toString();
        int size = dagSHvaesen.size();
        if(size<dagSHoste.size()){
            size = dagSHoste.size();
            mostFrequent = dagSHoste.get(0).getValue().toString();
        }        if(size<dagSSlim.size()){
            size = dagSSlim.size();
            mostFrequent = dagSSlim.get(0).getValue().toString();
        }if(size<dagSTryk.size()){
            size = dagSTryk.size();
            mostFrequent = dagSTryk.get(0).getValue().toString();
        }if(size<dagSAande.size()){
            size = dagSAande.size();
            mostFrequent = dagSAande.get(0).getValue().toString();
        }

        Double avgPefMorgen = gnmsnit(pefMorgenListe);
        Double avgPefAften = gnmsnit(pefAftenListe);


        /**
         * Sætter alt i OverviewParameter modellen, her med constructor, har en idé om hvordan vi undgår constructor, hvis vi ikke gider det,
         * se weeklyparam og observér at der ikke er erklæret nogen constructor, så vi defaulter til en noArg constructor. Det kunne også
         * gøres her.
         */
        OVParam = new OverviewParameters(Math.round(dagSymptomListe.size()/avgWeekRounderInt), Math.round(natSymptomListe.size()/avgWeekRounderInt), Math.round(aktivitetsListe.size()/avgWeekRounderInt), Math.round(anfaldsMedListe.size()/avgWeekRounderInt), avgPefMorgen, avgPefAften, avgFev, mostFrequent);

        //////////////////// SLUT PÅ OVERVIEWPARAMETER BEREGNING ////////////////////////////////////////////////



        /**
         * Skaffer ugenumre for start og slut dato
         */
       TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = startDate.get(woy);
        int weekNumber2 = endDate.get(woy);
        int calendarWeeks = weekNumber2-weekNumber;
        DayOfWeek doW = startDate.getDayOfWeek();

        /**
         * DagSymptom pr uge liste, hver index i den yderste liste er et symptom
         * /////////////////////////////////////////////////////////////////////
         * Hver index i den inderste liste referer til symptomantallet for én uge
         * Inderste liste har den første uge = index[0]
         * Det samme er gældende for de efterfølgende lister
         */
        List<List<Integer>> ugeListeDagSymptomer = new ArrayList<>();
        ugeListeDagSymptomer.add(symptomListe(startDate, endDate, dagSAande));
        ugeListeDagSymptomer.add(symptomListe(startDate, endDate, dagSHoste));
        ugeListeDagSymptomer.add(symptomListe(startDate, endDate, dagSHvaesen));
        ugeListeDagSymptomer.add(symptomListe(startDate, endDate, dagSSlim));
        ugeListeDagSymptomer.add(symptomListe(startDate, endDate, dagSTryk));

       /* for (int i = 0; i<ugeListeDagSymptomer.size(); i++){
            System.out.println(ugeListeDagSymptomer.get(0).get(i));
        }*/
        List<Integer> ugeListeTotalDagSymptom = new ArrayList<>();
        Integer sum = 0;
        for (int j = 0; j <ugeListeDagSymptomer.get(0).size(); j++) {
            for (int i = 0; i < ugeListeDagSymptomer.size() ; i++) {
                sum += ugeListeDagSymptomer.get(i).get(j);
            }
            ugeListeTotalDagSymptom.add(sum);
            sum = 0;
        }


        //System.out.println(ugeListeTotalDagSymptom);
        // Nat Symptomer
        List<List<Integer>> ugeListeNatSymptomer = new ArrayList<>();
        ugeListeNatSymptomer.add(symptomListe(startDate, endDate, natSHoste));
        ugeListeNatSymptomer.add(symptomListe(startDate, endDate, natSTraethed));
        ugeListeNatSymptomer.add(symptomListe(startDate, endDate, natSOpvaagning));


        /**
         * Laver uge Listen med én værdi for
         */
        List<Integer> ugeListeTotalNatSymptom = new ArrayList<>();
        for (int j = 0; j <ugeListeNatSymptomer.get(0).size(); j++) {
            for (int i = 0; i < ugeListeNatSymptomer.size() ; i++) {
                sum += ugeListeNatSymptomer.get(i).get(j);
            }
            ugeListeTotalNatSymptom.add(sum);
            sum = 0;
        }


        // Aktivitet, kun én liste
        List<Integer> ugeListeAktivitet = symptomListe(startDate, endDate, aktivitetsListe);

        //Anfaldsmedicin, kun én liste
        List<Integer> ugeListeAnfaldsMed = symptomListe(startDate, endDate, anfaldsMedListe);

        /**
         * pctLister for alle uger
         */
        List<List<Double>> pctListeDagSymptomer = udregnPCT(ugeListeDagSymptomer);
        List<List<Double>> pctListeNatSymptomer = udregnPCT(ugeListeNatSymptomer);
        List<Observation> pctMorgenPEF = pctAfPEV1(pefMorgenListe);
        List<Observation> pctAftenPEF = pctAfPEV1(pefAftenListe);

        /**
         * Laver pctPerioden for dagSymptom
         */
        List<Double> pctPeriodeDagSymptom = new ArrayList<>();
        double dagSymptomSize = dagSymptomListe.size();
        pctPeriodeDagSymptom.add(dagSAande.size()/dagSymptomSize);
        pctPeriodeDagSymptom.add(dagSHoste.size()/dagSymptomSize);
        pctPeriodeDagSymptom.add(dagSHvaesen.size()/dagSymptomSize);
        pctPeriodeDagSymptom.add(dagSSlim.size()/dagSymptomSize);
        pctPeriodeDagSymptom.add(dagSTryk.size()/dagSymptomSize);

        /**
         * Laver pctPerioden for natSymptom
         */
        List<Double> pctPeriodeNatSymptom = new ArrayList<>();
        double natSymptomSize = natSymptomListe.size();
        pctPeriodeNatSymptom.add(natSHoste.size()/natSymptomSize);
        pctPeriodeNatSymptom.add(natSTraethed.size()/natSymptomSize);
        pctPeriodeNatSymptom.add(natSHoste.size()/natSymptomSize);



        /**
         * Sætter Weekly Parametre
         */
        WeekParam.setUgeListeDagSymptomer(ugeListeTotalDagSymptom);
        WeekParam.setUgeListeNatSymptomer(ugeListeTotalNatSymptom);
        WeekParam.setUgeListeAktivitet(ugeListeAktivitet);
        WeekParam.setUgeListeAnfaldsMed(ugeListeAnfaldsMed);
        WeekParam.setPctListeDagSymptomer(pctListeDagSymptomer);
        WeekParam.setPctListeNatSymptomer(pctListeNatSymptomer);
        WeekParam.setPctPeriodeDagSymptom(pctPeriodeDagSymptom);
        WeekParam.setPctPeriodeNatSymptom(pctPeriodeNatSymptom);
        WeekParam.setFoersteUge(weekNumber);
        WeekParam.setMorgenPEF(pctMorgenPEF);
        WeekParam.setAftenPEF(pctAftenPEF);
        WeekParam.setFev1(pctFev);
        encapsulatedParameters.setOverviewParameters(OVParam);
        encapsulatedParameters.setWeeklyParameters(WeekParam);

        System.out.println("CalculatedParameters ok");
        return encapsulatedParameters;
    }

    /*private List<Integer> symptomListe(Integer antalKalenderUger, Integer foersteUge, List<Observation> liste){
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        List<Integer> ugeListe = new ArrayList<>();
        int value = 0;
        for(int k = 0; k<antalKalenderUger+1; k++){
            ugeListe.add(0);
        }

        //for (int i = 0; i < antalKalenderUger; i++){
            for (int j = 0; j <liste.size(); j++){
                Integer weekNr = liste.get(j).getIssued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().get(woy);
                for (int i = 0; i <antalKalenderUger + 1; i++) {
                    if (weekNr.equals(foersteUge + i) && weekNr < foersteUge + antalKalenderUger + 1) {
                        ugeListe.set(i, ugeListe.get(i) + 1);// += ugeListe
                        break;
                    }
                }
            }
            return ugeListe;
    }*/
    private List<Integer> symptomListe(LocalDate startDate, LocalDate endDate, List<Observation> liste){
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        double numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        // % er resten efter division med tallet efter %
        long extraDays = (long) (numOfDaysBetween % 7);
        double numberOfWeeks = Math.floor(numOfDaysBetween/7);
        startDate = startDate.plusDays(extraDays);
        endDate = endDate.plusDays(1);


        //System.out.println(ChronoUnit.DAYS.between(startDate, endDate) + 1);
        //long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //System.out.println(numOfDaysBetween);
        List<Integer> ugeListe = new ArrayList<>();
        int value = 0;
        for(int k = 0; k< numberOfWeeks; k++){
            ugeListe.add(0);
        }
        //System.out.println(ugeListe.size());

        for (int j = 0; j <liste.size(); j++) {
            for (int i = 1; i < numberOfWeeks + 1; i++) {
                if (liste.get(j).getIssued().toInstant().atZone(
                        ZoneId.systemDefault()).toLocalDate().isBefore(startDate.plusDays(i*7)) &&
                        !liste.get(j).getIssued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(startDate)){
                    ugeListe.set(i-1, ugeListe.get(i-1) + 1);
                    break;
                }

            }
        }
        //for (int i = 0; i < antalKalenderUger; i++){
            /*for (int j = 0; j <liste.size(); j++){
                Integer weekNr = liste.get(j).getIssued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().get(woy);
                for (int i = 0; i <Math.ceil(numOfDaysBetween/7); i++) {
                    if (weekNr.equals(foersteUge + i) && weekNr < foersteUge + antalKalenderUger + 1) {
                        ugeListe.set(i, ugeListe.get(i) + 1);// += ugeListe
                        break;
                    }
                }
            }*/
            return ugeListe;
    }

    private List<List<Double>> udregnPCT(List<List<Integer>> sumPrUgeListe){
        List<List<Double>> pctListe = new ArrayList<>();
        double sum = 0;
        double zero = 0;
        for (int i = 0; i <sumPrUgeListe.size(); i++){
            pctListe.add(new ArrayList<>());
        }
        for (int j = 0; j <sumPrUgeListe.get(0).size(); j++){
            for (int i = 0; i <sumPrUgeListe.size(); i++){
                sum += sumPrUgeListe.get(i).get(j);
            }
            for (int k = 0; k <sumPrUgeListe.size();k++){
                if (sum != 0 && j < sumPrUgeListe.get(0).size())
                pctListe.get(k).add(sumPrUgeListe.get(k).get(j)/sum);
                else if (j < sumPrUgeListe.get(0).size())
                    pctListe.get(k).add(zero);
            }
            sum = 0;
        }
        return pctListe;
    }
    private Double gnmsnit(List<Observation> liste){
        Double sum = new Double(0);
        for (int i = 0; i < liste.size(); i++){
            try{
                sum += (liste.get(i).getValueQuantity().getValue().doubleValue());
            }catch(FHIRException e){
                System.out.println(e.getMessage());
            }
        }
        Double avg = sum/liste.size();

        return avg;

    }

    public List<Observation> pctAfPEV1(List<Observation> liste){
        Double max = new Double(0);
        Double test = new Double(0);
        for (int i = 0; i < liste.size(); i++) {
            try {
                test = liste.get(i).getValueQuantity().getValue().doubleValue();
            } catch (FHIRException e) {
                System.out.println(e.getMessage());
            }
            if (max < test) {
                    max = test;
            }
        }
        for (int j = 0; j<liste.size();j++){
            try{
                double temp = ((liste.get(j).getValueQuantity().getValue().doubleValue())/max)*100;
                liste.get(j).setValue(new Quantity(temp));
                //tempObs.get(1).setValue(randomPEF.get(1));
            }catch (FHIRException e){
                System.out.println(e.getMessage());
            }
        }
        try{
            System.out.println(liste.get(0).getValueQuantity().getValue());
        }catch (FHIRException e){
            e.getMessage();
        }
        return liste;
    }

}
