package com.example.codechefeventsapp.data.models.cf;

import java.util.List;

public class CfUserSubmissions {

    String status;
    List<CfSubmission> result;

    public CfUserSubmissions(String status, List<CfSubmission> result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CfSubmission> getResult() {
        return result;
    }

    public void setResult(List<CfSubmission> result) {
        this.result = result;
    }
}
