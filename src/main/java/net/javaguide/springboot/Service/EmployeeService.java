package net.javaguide.springboot.Service;

import net.javaguide.springboot.dtos.EmployeeDto;
import net.javaguide.springboot.dtos.LoginDto;
import net.javaguide.springboot.model.Employee;
import net.javaguide.springboot.model.LoginMessage;


import java.util.List;

public interface EmployeeService {

        Employee createEmployee(EmployeeDto employee);

        Employee getEmployeeById(Long id);

        Employee updateEmployee(Long employeeId, EmployeeDto employeeDto);

        void deleteEmployee(Long id);

        List<Employee> getAllEmployees();

        LoginMessage loginMessage(LoginDto loginDto);


}






