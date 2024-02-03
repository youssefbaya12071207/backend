package net.javaguide.springboot.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InsurenceServ {
    public boolean isInsuranceExpired() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // In this example, let's assume a hardcoded insurance expiry date for demonstration purposes
        LocalDate insuranceExpiryDate = LocalDate.of(2023, 12, 31);

        // Compare the current date to the insurance expiry date
        return currentDate.isAfter(insuranceExpiryDate);
    }
}
