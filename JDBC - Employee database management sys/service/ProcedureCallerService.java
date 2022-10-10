package com.ford.service;

import com.ford.dao.ProcedureCaller;
import com.ford.model.Employee;

import java.util.List;

public class ProcedureCallerService {

    ProcedureCaller procedureCaller;

    public ProcedureCallerService() {
        procedureCaller = new ProcedureCaller();
    }

    public List<Employee> fetchEmployeesProcSVC(){
        return procedureCaller.fetchEmployeeProc();
    }
    public Employee fetchEmployeesByIdSVC(String empId){
        return procedureCaller.fetchEmployeeByIdProc(empId);
    }

    public Employee fetchEmployeeDetailsByIdThruProcOutParamSVC(String empId){
        return procedureCaller.fetchEmployeeDetailsByIdThruProcOutParam(empId);
    }


}
