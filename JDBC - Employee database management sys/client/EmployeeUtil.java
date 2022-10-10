package com.ford.client;

import com.ford.dao.EmployeeDao;

public class EmployeeUtil {
    public static void main(String[] args) {
        EmployeeDataMgmt edmt = new EmployeeDataMgmt();
        edmt.showMenu();
       // EmployeeDao edao = new EmployeeDao();
      //  String s = edao.getMaxEmployeeId();
    }
    /*
    main(EmployeeUtil) --> EmployeeDataMgmt --> EmployeeService -->EmployeeDao --> myConnection(&verify it)---> Database
        |                                                             |
        |                                                             |
        Menu --> Switch cases 1-6 (About Database Management sys)     queries are Given with statement,PreparedStatement(Main Logic)

        Cases 6 , 8 and 7 are SQL procedures with the same logic(Callable Statements instead of Prepared Statements)- IN and OUT parameters(Procedures)

     */
}
