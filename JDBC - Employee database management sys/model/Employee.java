package com.ford.model;

import java.util.Date;

public class Employee {

    String employeeId;
    String empName;
    String employeeAddress;
    String empPhone;
    Date dateOfJoining;
    float employeeSalary;

    public Employee() {
    }

    public Employee(String employeeId, String empName, String employeeAddress, String empPhone, Date dateOfJoining, float employeeSalary) {
        this.employeeId = employeeId;
        this.empName = empName;
        this.employeeAddress = employeeAddress;
        this.empPhone = empPhone;
        this.dateOfJoining = dateOfJoining;
        this.employeeSalary = employeeSalary;
    }

    public Employee(String employeeId,String empName,String employeeAddress,float employeeSalary){
        this.employeeId = employeeId;
        this.empName = empName;
        this.employeeAddress = employeeAddress;
        this.employeeSalary = employeeSalary;
    }

    public Employee(String empName, String employeeAddress, String empPhone, Date dateOfJoining, float employeeSalary) {
        this.empName = empName;
        this.employeeAddress = employeeAddress;
        this.empPhone = empPhone;
        this.dateOfJoining = dateOfJoining;
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public float getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(float employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee emp = (Employee) obj;
        return employeeId.equals(emp.employeeId)
                && empName.equals(emp.empName)
                && employeeAddress.equals(emp.employeeAddress)
                && empPhone.equals(emp.empPhone)
                && dateOfJoining.equals(emp.dateOfJoining)
                && Float.compare(employeeSalary, emp.employeeSalary) == 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", empName='" + empName + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", employeeSalary=" + employeeSalary +
                '}';
    }
}
