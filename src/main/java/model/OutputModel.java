package model;

import java.util.Date;

public class OutputModel {

    private String message;
    private Date processedDate;

    private String enrichment;

    public OutputModel(){

    }

    public OutputModel(String message, Date processedDate, String enrichment) {
        this.message = message;
        this.processedDate = processedDate;
        this.enrichment = enrichment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public String getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(String enrichment) {
        this.enrichment = enrichment;
    }
}
