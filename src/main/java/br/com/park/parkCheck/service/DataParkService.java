package br.com.park.parkCheck.service;

import br.com.park.park.model.ParkingSession;
import br.com.park.parkCheck.repository.ApiKeyRepository;
import br.com.park.parkCheck.repository.DataParkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataParkService {

    private final ApiKeyRepository apiKeyRepository;
    private final DataParkRepository dataParkRepository;
    private final AuthService authService;

    public DataParkService(ApiKeyRepository apiKeyRepository, DataParkRepository dataParkRepository, AuthService authService) {
        this.apiKeyRepository = apiKeyRepository;
        this.dataParkRepository = dataParkRepository;
        this.authService = authService;
    }

    public ParkingSession getDataPark(String apiKey, String licensePlate) throws IllegalAccessException, EntityNotFoundException  {

        boolean apiKeyValid = authService.validateApiKey(apiKey);

        if(!apiKeyValid){
            throw new IllegalAccessException("API Key inválida ou expirada.");
        }

        Optional<ParkingSession> parkingSession = dataParkRepository.findFirstByLicensePlate(licensePlate);

        if(parkingSession.isPresent()){
            return parkingSession.get();
        }else{
            throw new EntityNotFoundException("Sessão de estacionamento não encontrada para a placa: " + licensePlate);
        }
    }
}
