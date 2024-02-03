package net.javaguide.springboot.repository;

import lombok.NonNull;
import net.javaguide.springboot.model.Vignette;
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguide.springboot.model.Employee;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee , Long> {

    //Optional<Employee> findOneByEmailIdAndPassword(String emailId, String password);
   // Employee findByEmailId(String emailId);



}
