package ru.darin.coordinates.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import ru.darin.coordinates.model.Region;
import ru.darin.coordinates.repository.RegionRepository;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.GeoJson;
import ru.darin.coordinates.util.Location;
import ru.darin.coordinates.util.LocationForDistrict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CoordinatesServiceTest {

    private RegionRepository repository = Mockito.mock(RegionRepository.class);

    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    private Location[] responseForRegion;

    private LocationForDistrict[] responseForDistrict;

    @InjectMocks
    private CoordinatesService service;

    private String urlForRegion = "http://example-region.com";

    private String urlForDistrict = "http://example-district.com";

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
        GeoJson geoJson = new GeoJson("gson");
        Location location = new Location(111, "licence", "region", geoJson);

        List<List<Float>> coordinates = new ArrayList<>();
        coordinates.add(Arrays.asList(1.0f, 2.0f));
        coordinates.add(Arrays.asList(3.0f, 4.0f));

        geoJson.setCoordinates(Collections.singletonList(coordinates));
        location.setGeoJson(geoJson);
        location.setName("Тульская область");

        when(service.getResponseForRegion(urlForRegion)).thenReturn(new Location[]{location});

        CoordinateResponse result = service.getCoordinatesForRegion(urlForRegion);

        assertEquals("Тульская область", result.getName());
        assertEquals(2.0, result.getCoordinatesForGeoCenter().getLongitude(), 0.001);
        assertEquals(3.0, result.getCoordinatesForGeoCenter().getLatitude(), 0.001);
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