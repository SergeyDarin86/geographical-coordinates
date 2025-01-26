package ru.darin.coordinates.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import ru.darin.coordinates.model.Region;
import ru.darin.coordinates.repository.RegionRepository;
import ru.darin.coordinates.util.Location;
import ru.darin.coordinates.util.LocationForDistrict;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CoordinatesServiceTest {

    private RegionRepository repository = Mockito.mock(RegionRepository.class);

    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    private Location[] responseForRegion;

    private LocationForDistrict[] responseForDistrict;

    @InjectMocks
    private CoordinatesService service;

    private String urlForRegion = "url/region";

    private String urlForDistrict = "url/district";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responseForRegion = restTemplate.getForObject(urlForRegion, Location[].class);
        responseForDistrict = restTemplate.getForObject(urlForDistrict, LocationForDistrict[].class);
    }

    @Test
    void getResponseForRegion() {
        service.getResponseForRegion(urlForRegion);
        when(restTemplate.getForObject(anyString(), Mockito.eq(Location[].class))).thenReturn(responseForRegion);
    }

    @Test
    void getResponseForDistrict() {
        service.getResponseForDistrict(urlForDistrict);
        when(restTemplate.getForObject(anyString(), Mockito.eq(LocationForDistrict[].class))).thenReturn(responseForDistrict);
    }

    @Test
    void getCoordinatesForRegion() {

    }

    @Test
    void getCoordinatesForDistrict() {
    }

    @Test
    void getMaxPartOfDistrict() {
    }

    @Test
    void saveCoordinates() {
        service.saveCoordinates("пфо", 53.65, 43.45);
        verify(repository, times(1)).save(any(Region.class));
    }
}