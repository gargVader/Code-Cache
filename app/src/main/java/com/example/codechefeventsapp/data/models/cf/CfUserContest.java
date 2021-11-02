package com.example.codechefeventsapp.data.models.cf;

import java.util.List;

public class CfUserContest {

    String status;
    List<CfContest> result;

    public CfUserContest(String status, List<CfContest> result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CfContest> getResult() {
        return result;
    }

    public void setResult(List<CfContest> result) {
        this.result = result;
    }
}
