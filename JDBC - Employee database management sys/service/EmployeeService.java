package com.ford.service;

import com.ford.dao.EmployeeDao;
import com.ford.model.Employee;

import java.util.List;

public class EmployeeService {

    EmployeeDao edao;

    public EmployeeService() {
        edao = new EmployeeDao();
    }

    public List<Employee> getAllEmployeesSVC(){
        return edao.getAllEmployees();
    }

    public Employee getEmployeeByIdSVC(String employeeId){
        return edao.getEmployeeById(employeeId);
    }
    public boolean insertEmployeeSVC(Employee employee)
    {
        return edao.insertEmployee(employee);
    }
    public boolean deleteEmployeeByIdSVC(Employee employee,String employeeId){
        return edao.deleteEmployeeById(employee,employeeId);
    }
    public boolean updateEmployeeSVC(Employee employee,String employeeId){
       return edao.updateEmployee(employee,employeeId);
    }
    public List<String> getEmployeeIdsSVC(){
        return edao.getEmployeeIds();
    }
    public String getMaxEmpIdSVC(){
        return edao.getMaxEmployeeId();
    }

}
