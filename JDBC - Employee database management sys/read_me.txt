JDBC - Employee Management Sys
------------------------------------
I have created a employee management system with JDBC connection.I have created several packages to identify the server,dao,controller and the client.
Execution of the project is outlined below.

 main(EmployeeUtil) --> EmployeeDataMgmt --> EmployeeService -->EmployeeDao --> myConnection(&verify it)---> Database
        |                                                             |
        |                                                             |
        Menu --> Switch cases 1-6 (About Database Management sys)     queries are Given with statement,PreparedStatement(Main Logic)

        Cases 6 , 8 and 7 are SQL procedures with the same logic(Callable Statements instead of Prepared Statements)- IN and OUT parameters(Procedures)