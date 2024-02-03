package net.javaguide.springboot.controller;

import net.javaguide.springboot.Service.VignetteService;
import net.javaguide.springboot.model.Vignette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/vignettes")
public class VignetteController {

    private final VignetteService vignetteService;

    @Autowired
    public VignetteController(VignetteService vignetteService) {
        this.vignetteService = vignetteService;
    }

    @GetMapping
    public ResponseEntity<List<Vignette>> getAllVignette() {
        List<Vignette> vignettes = vignetteService.getAllVignettes();
        return new ResponseEntity<>(vignettes, HttpStatus.OK);
    }
    @PostMapping("/{employeeId}")
    public ResponseEntity<Vignette> createVignette(@PathVariable Long employeeId) {
        Vignette createdVignette = vignetteService.createVignette(employeeId);
        return new ResponseEntity<>(createdVignette, HttpStatus.CREATED);
    }

    @PutMapping("/{vignetteId}")
    public ResponseEntity<Vignette> updateVignette(
            @PathVariable Long vignetteId,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            @RequestBody Vignette updatedVignette
    ) {
        Vignette updated = vignetteService.updateVignette(vignetteId, start, end, updatedVignette);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{vignetteId}")
    public ResponseEntity<Vignette> deleteVignette(@PathVariable Long vignetteId) {
        Vignette deletedVignette = vignetteService.deleteVignette(vignetteId);
        return new ResponseEntity<>(deletedVignette, HttpStatus.OK);
    }

    @GetMapping("/{vignetteId}")
    public ResponseEntity<Vignette> getVignette(@PathVariable Long vignetteId) {
        Vignette vignette = vignetteService.getVignette(vignetteId);
        return new ResponseEntity<>(vignette, HttpStatus.OK);
    }

}
