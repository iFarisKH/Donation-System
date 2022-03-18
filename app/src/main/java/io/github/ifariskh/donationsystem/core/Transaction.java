package io.github.ifariskh.donationsystem.core;

public class Transaction {

    private String id;
    private Float amount;
    private String donatorId;
    private String neederId;
    private String date;

    public Transaction(String id, Float amount, String donatorId, String neederId, String date) {
        this.id = id;
        this.amount = amount;
        this.donatorId = donatorId;
        this.neederId = neederId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(String donatorId) {
        this.donatorId = donatorId;
    }

    public String getNeederId() {
        return neederId;
    }

    public void setNeederId(String neederId) {
        this.neederId = neederId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
