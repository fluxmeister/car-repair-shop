package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = findById(id);
        vehicleRepository.delete(vehicle);
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Vehicle.class.getSimpleName()));
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }
}
