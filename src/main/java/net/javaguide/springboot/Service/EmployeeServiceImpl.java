package net.javaguide.springboot.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.javaguide.springboot.dtos.EmployeeDto;
import net.javaguide.springboot.dtos.LoginDto;
import net.javaguide.springboot.model.Employee;
import net.javaguide.springboot.model.LoginMessage;
import net.javaguide.springboot.repository.EmployeeRepository;
import net.javaguide.springboot.security.model.Role;
import net.javaguide.springboot.security.model.User;
import net.javaguide.springboot.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Employee createEmployee(EmployeeDto employeeDto) {
        User user = User.builder()
                .role(employeeDto.getRole())
                .admin(true)
                .enabled(true)
                .email(employeeDto.getEmail())
                .password(employeeDto.getPassword())
                .build();
        User user1 = userService.create(user);
        Employee employee = new Employee();
        employee.setUser(user1);
         employee.setFirstname(employeeDto.getFirstname());
         employee.setLastname(employeeDto.getLastname());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

        // Update user details if necessary
        User user = existingEmployee.getUser();
        if (user != null) {
            user.setRole(employeeDto.getRole());
            user.setEmail(employeeDto.getEmail());
            user.setPassword(employeeDto.getPassword());
            userService.update(user); // Assuming you have an update method in your userService
        }

        // Update employee details
        existingEmployee.setFirstname(employeeDto.getFirstname());
        existingEmployee.setLastname(employeeDto.getLastname());
        existingEmployee.setInsuranceExpiryDate(employeeDto.getInsuranceExpiryDate());

        // Save and return the updated employee
        return employeeRepository.save(existingEmployee);
    }


    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override

    public LoginMessage loginMessage(LoginDto loginDto) {
    /*    String msg = "";
        Employee employee1 = employeeRepository.findByEmailId(loginDto.getEmailId());
        if (employee1 != null) {
            String password = loginDto.getPassword();
            String encodedPassword = employee1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Employee> employee = employeeRepository.findOneByEmailIdAndPassword(loginDto.getEmailId(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginMessage("Login Success", true);
                } else {
                    return new LoginMessage("Login Failed", false);
                }
            } else {
                return new LoginMessage("password Not Match", false);
            }
        }else {
            return new LoginMessage("Email not exits", false);
        }

     */
        return null;
    }


}



