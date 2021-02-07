package com.mindex.challenge.data;


public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure(com.mindex.challenge.data.Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public com.mindex.challenge.data.Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
