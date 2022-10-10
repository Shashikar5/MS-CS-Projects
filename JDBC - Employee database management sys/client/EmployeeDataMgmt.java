package com.ford.client;

import com.ford.model.Employee;
import com.ford.service.EmployeeService;
import com.ford.service.ProcedureCallerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class EmployeeDataMgmt {

    Scanner scan1;
    String reply;
    String choice;

    String empId,empName,empAddress,empPhone;
    String empDOJ;
    float empSalary;

    EmployeeService empService;
    ProcedureCallerService procedureCallerService;
    public EmployeeDataMgmt()
    {
        scan1 = new Scanner(System.in);
        reply = "Yes";
        empService = new EmployeeService();
        procedureCallerService = new ProcedureCallerService();
    }

    public void getEmployeeData(){  // Used in cases 3 and 5
        System.out.println("Enter the empName");
        empName = scan1.next();
        System.out.println("Enter the Employee Address");
        empAddress = scan1.next();
        System.out.println("Enter the emp Phone no");
        empPhone  =scan1.next();
        System.out.println("Enter the Date of Joining in YYYY-MM-DD format");
        empDOJ = scan1.next();
        System.out.println("Enter the emp Salary");
        empSalary = scan1.nextFloat();
    }
    public static java.util.Date stringToDateConverter(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException pe) {
            return null;
        }
    }

    private List<String> getEmployeeIds(){  //Calling From the Service Function
           return empService.getEmployeeIdsSVC();
    }

    private void displayEmployeeIds(List<String> empIds){
        Iterator<String > empIdItr = empIds.iterator();
        while(empIdItr.hasNext()){
            System.out.println(empIdItr.next());
        }
    }

    private boolean checkIdExist(String empId){
        boolean flag = false;
        List<String> empIdList = getEmployeeIds();
        Iterator<String> empIdItr = empIdList.iterator();
        while(empIdItr.hasNext()){
            if(empIdItr.next().equals(empId)){
                flag = true;
            }
        }
        return flag;
    }

    public String generateEmployeeId(){
        String generatedId="";
        String maxEmpId = "";
       maxEmpId =  empService.getMaxEmpIdSVC();
        //E009
        String prePartId = maxEmpId.substring(0,1);//E
        String postPartId = maxEmpId.substring(1,maxEmpId.length());//009
        int postPartIdInt = Integer.parseInt(postPartId);//9
        int incrementedPostPartId = postPartIdInt + 1;//10

        if((incrementedPostPartId>=0) && (incrementedPostPartId <10)){ //For joining after incrementing the Id
            generatedId = "E00" + incrementedPostPartId;
        }
        else if((incrementedPostPartId>=10) && (incrementedPostPartId <100)){
            generatedId = "E0" + incrementedPostPartId;
        }
        else if((incrementedPostPartId>=100) && (incrementedPostPartId <1000)){
            generatedId = "E" + incrementedPostPartId;
        }
        else{
            generatedId="";
        }
        return generatedId;
    }
    /*
    * hamburger str.substring(4,8) = urge
     */

    public void showMenu()
    {
        while(reply.equals("Yes")||reply.equals("YES"))
        {
            System.out.println("----------------------Employee Data Mgmt Menu--------------------");
            System.out.println("1.View all employee records");
            System.out.println("2.View by emp Id...");
            System.out.println("3.Insert Employee...");
            System.out.println("4.Delete employee....");
            System.out.println("5.Update Employee....");
            System.out.println("6.Call Procedure to fetch all employee records");
            System.out.println("7.Call Procedure with i/p parameter(empId) to fetch employee records");
            System.out.println("8.Call Procedure with i/p and o/p parameter");
            System.out.println("9.Exit menu//System.exit()");

            System.out.println("Enter your choice..");
            choice = scan1.next();

            switch(choice){
                case "1":
                {
                    List<Employee> employees;
                    employees = empService.getAllEmployeesSVC();
                    Iterator empIter = employees.iterator();
                    System.out.println("The Employee Records are...");
                    while(empIter.hasNext())
                    {
                        System.out.println(empIter.next());
                    }
                    break;
                }
                case "2":
                {
                    String empId;
                    List<String> empIds = getEmployeeIds();
                    displayEmployeeIds(empIds); // Displaying all the available emp Ids

                    System.out.println("Enter the emp Id ,whose record you wish to see...");
                    empId = scan1.next();
                    /*  Performing view operation if only empId exists */
                    if(checkIdExist(empId)) {
                        Employee e = empService.getEmployeeByIdSVC(empId);
                        System.out.println(e);
                    }
                    else{
                        System.out.println("Emp Id you entered does not exist");
                    }
                    break;
                }
                case "3":
                {
                    Employee employee = new Employee();
                    String empId;
                  /*  System.out.println("Enter the EmpId");
                    empId = scan1.next(); */

                    empId = generateEmployeeId();//Using the Generate empId func
                    System.out.println("The New empId is " + empId);

                    getEmployeeData();//Accepting Employee details for insertion

                    employee.setEmployeeId(empId);
                    employee.setEmpName(empName);
                    employee.setEmployeeAddress(empAddress);
                    employee.setEmpPhone(empPhone);
                    employee.setDateOfJoining(stringToDateConverter(empDOJ));
                    employee.setEmployeeSalary(empSalary);

                  if(empService.insertEmployeeSVC(employee))
                  {
                      System.out.println("Insertion Successfull");
                  }else{
                      System.out.println("Failed");
                  }
                  break;
                }
                case "4":
                {
                    Employee employee = new Employee();
                    String empId;
                    List<String> empIds = getEmployeeIds();
                    displayEmployeeIds(empIds); // Displaying all the available emp Ids
                    System.out.println("Enter the EmpId whose record you want to delete?");
                    empId = scan1.next();
                    /*  Performing updation if only empId exists */
                    if(checkIdExist(empId)) {
                        if (empService.deleteEmployeeByIdSVC(employee, empId)) {
                            System.out.println("Emp Id: " + empId + " record deleted");
                        } else {
                            System.out.println("Failed");
                        }
                    }else{
                        System.out.println("empIds does not exist");
                    }
                    break;
                }
                case "5":
                {
                    Employee employee = new Employee();

                    List<String> empIds = getEmployeeIds();
                    displayEmployeeIds(empIds); // Displaying all the available emp Ids

                    System.out.println("Enter the EmpId whose record you want to update?");
                    empId = scan1.next();

                 /*  Performing updation if only empId exists */
                    if(checkIdExist(empId)) {
                        employee = empService.getEmployeeByIdSVC(empId);
                        System.out.println("The Current record for the Id " + empId + "is ");
                        System.out.println(employee);

                        getEmployeeData(); // Accepting Emp details for Updation


                        employee.setEmployeeId(employee.getEmployeeId());// Cannot Change(Primary Key)
                        employee.setEmpName(empName);
                        employee.setEmployeeAddress(empAddress);
                        employee.setEmpPhone(empPhone);
                        employee.setDateOfJoining(stringToDateConverter(empDOJ));
                        employee.setEmployeeSalary(empSalary);

                        if (empService.updateEmployeeSVC(employee, employee.getEmployeeId())) {
                            System.out.println("Update Successful for empId " + empId);
                        } else {
                            System.out.println("Failed");
                        }
                    }
                    else{
                        System.out.println("empId does not exist");
                    }
                    break;
                }
                case "6":
                {
                    List<Employee> employees = procedureCallerService.fetchEmployeesProcSVC();
                    Iterator<Employee> employeeIterator = employees.iterator();
                    System.out.println("Employee Details fetched through procedure");
                    while(employeeIterator.hasNext()){
                        Employee e = employeeIterator.next();
                        System.out.println(e);
                    }
                    break;
                }
                case "7":
                {
                    String empId;
                    Employee emp = new Employee();
                    System.out.println("Enter the emp Id you wish to see?");
                    empId = scan1.next();
                    emp = procedureCallerService.fetchEmployeesByIdSVC(empId);
                    System.out.println("Employee Record fetched are....");
                    System.out.println(emp);
                    break;
                }
                case "8":
                {
                    String empId;
                    System.out.println("Enter the empId whose records you want to see?(Procedure-SQL OUT Parameter)");
                    empId = scan1.next();
                    Employee e = procedureCallerService.fetchEmployeeDetailsByIdThruProcOutParamSVC(empId);
                    System.out.println(e);
                    break;
                }
                default:
                {
                    break;
                }
            }
            System.out.println("Do you wish to continue(Yes/No)?");
            reply = scan1.next();
        }
    }
}
