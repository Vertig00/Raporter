package reports;

import operations.FileOperations;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Raport Rekomendacja analityka rcak cakc negatywna a decyzja pozytywna
 * */

public class Report1 {

    public static final List<String> RESULT_CELLS = Arrays.asList("NUMER_WNIOSKU", "ODDZIAL", "NAZWA_KLIENTA", "REKOMENDACJA RCAK", "REKOMENDACJA_CAKC", "DECYZJA", "ORGAN_WYDAJACY_DECYZJE", "DATA_KONCA");
    List<Report1> list = new ArrayList<Report1>();

    String appNo;
    String analytic;
    String customer;
    String recommendation;
    String recommendationDate;
    String type;
    String role;
    String place;
    String endDate;

    public Report1(String appNo, String analytic, String customer, String recommendation, String recommendationDate, String type, String role, String place, String endDate) {
        this.appNo = appNo;
        this.analytic = analytic;
        this.customer = customer;
        this.recommendation = recommendation;
        this.recommendationDate = recommendationDate;
        this.type = type;
        this.role = role;
        this.place = place;
        this.endDate = endDate;
    }

    public Report1(List<String> list) {
        this.appNo = list.get(0);
        this.analytic = list.get(1);
        this.customer = list.get(2);
        this.recommendation = list.get(3);
        this.recommendationDate = list.get(4);
        this.type = list.get(5);
        this.role = list.get(6);
        this.place = list.get(7);
        this.endDate = list.get(8);
    }

    public Report1(){}

    public void createReportList(Iterator<Row> iterator) {
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            List<String> row = new ArrayList();
            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                row.add(currentCell.getStringCellValue());
            }
            list.add(new Report1(row));
        }
        list.remove(0);
    }

    public List<List<String>> process() {
        List<List<String>> result = new ArrayList<>();
        Map<Object, List<Report1>> subList = this.list.stream().collect(Collectors.groupingBy(c -> c.appNo));
        for (List<Report1> rep : subList.values()) {
            boolean recomendation = false;
            Report1 recom_rcak = new Report1();
            Report1 recom_cakc = new Report1();
            Report1 dec = new Report1();
            boolean decision = false;
            if (rep.size() > 1) {
                for (Report1 r:rep) {
                    if (r.recommendation.contains("Negatywna") && r.type.equals("RECOMMENDATION")){
                        recomendation = true;
                        if (r.role.equals("RCAK")){
                            recom_rcak = r;
                        } else {
                            recom_cakc = r;
                        }
                    }
                    if (r.recommendation.contains("Pozytywna") && r.type.equals("DECISION")){
                        decision = true;
                        dec = r;
                    }
                }
            }
            if (recomendation && decision) {
                String rcak = recom_rcak.appNo != null ? recom_rcak.analytic + "; " + recom_rcak.recommendation : "brak";
                String cakc = recom_cakc.appNo != null ? recom_cakc.analytic + "; " + recom_cakc.recommendation : "brak";
                List<String> row = Arrays.asList(dec.appNo, dec.place, dec.customer, rcak, cakc, dec.recommendation, dec.analytic, dec.endDate);
                result.add(row);
            }
        }
        return result;
    }








    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAnalytic() {
        return analytic;
    }

    public void setAnalytic(String analytic) {
        this.analytic = analytic;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(String recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
