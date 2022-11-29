package com.example.android.qrcodescanner.Models;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

    public Record(){}

    String recordId,data,state,publishedBy;
    Date recordedAt;

    public Record(String data, String state, String publishedBy, Date recordedAt) {
        this.data = data;
        this.state = state;
        this.publishedBy = publishedBy;
        this.recordedAt = recordedAt;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Date recordedAt) {
        this.recordedAt = recordedAt;
    }
}
