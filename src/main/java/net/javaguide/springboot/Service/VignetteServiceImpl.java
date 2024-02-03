package net.javaguide.springboot.Service;

import net.javaguide.springboot.exception.ResourceNotFoundException;
import net.javaguide.springboot.model.Employee;
import net.javaguide.springboot.model.Vignette;
import net.javaguide.springboot.repository.EmployeeRepository;
import net.javaguide.springboot.repository.VignetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VignetteServiceImpl implements VignetteService {

    @Autowired
    private  VignetteRepository vignetteRepository;
    @Autowired
    private EmployeeRepository employeeRepository;



    @Override
    public Vignette createVignette(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        // Create and save a vignette for the employee
        Vignette vignette = new Vignette();
        vignette.setFirstname(employee.getFirstname());
        vignette.setLastname(employee.getLastname());
        vignette.setStart(LocalDate.now());
        vignette.setEnd(LocalDate.now().plusYears(1)); // Set end date as an example

        return vignetteRepository.save(vignette);
    }

    @Override
    public Vignette updateVignette(Long vignetteId, LocalDate start, LocalDate end, Vignette updatedVignette) {
        Vignette existingVignette = vignetteRepository.findById(vignetteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vignette not found with ID: " + vignetteId));

        // Update the start and end fields
        existingVignette.setStart(start);
        existingVignette.setEnd(end);

        // You can also update other fields if needed
        existingVignette.setFirstname(updatedVignette.getFirstname());
        existingVignette.setLastname(updatedVignette.getLastname());
        existingVignette.setEmail(updatedVignette.getEmail());

        // Save the updated vignette
        return vignetteRepository.save(existingVignette);
    }



    @Override
    public Vignette deleteVignette(Long vignetteId) {
        Vignette vignette = vignetteRepository.findById(vignetteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vignette not found with ID: " + vignetteId));

        vignetteRepository.delete(vignette);
        return vignette;
    }

    @Override
    public Vignette getVignette(Long vignetteId) {
        return vignetteRepository.findById(vignetteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vignette not found with ID: " + vignetteId));
    }

    @Override
    public List<Vignette> getAllVignettes() {
        return vignetteRepository.findAll();
    }

}

