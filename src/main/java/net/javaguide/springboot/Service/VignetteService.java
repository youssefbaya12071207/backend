package net.javaguide.springboot.Service;

import net.javaguide.springboot.model.Vignette;

import java.time.LocalDate;
import java.util.List;

public interface VignetteService {
    Vignette createVignette(Long employeeId);
    Vignette updateVignette(Long vignetteId, LocalDate start, LocalDate end , Vignette updatedVignette);
    Vignette deleteVignette(Long vignetteId);
    Vignette getVignette(Long vignetteId);

    List<Vignette> getAllVignettes();
}