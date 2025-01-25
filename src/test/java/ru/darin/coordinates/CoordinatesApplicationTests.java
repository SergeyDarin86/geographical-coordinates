package ru.darin.coordinates;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.darin.coordinates.controller.CoordinatesController;
import ru.darin.coordinates.dto.SearchDTOForDistrict;
import ru.darin.coordinates.dto.SearchDTOForRegion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/create-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-from-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("/application-test.properties")
class CoordinatesApplicationTests {

    @Autowired
    private CoordinatesController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    SearchDTOForRegion searchDTOForRegion;

    SearchDTOForDistrict searchDTOForDistrict;

    SearchDTOForRegion searchDTOForRegionWithEmptyLocation;

    SearchDTOForDistrict searchDTOForDistrictWithNoExistLocation;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @BeforeEach
    void setUp() {
        searchDTOForRegion = new SearchDTOForRegion();
        searchDTOForRegion.setLocation("Тульская обл");
        searchDTOForDistrict = new SearchDTOForDistrict();
        searchDTOForDistrict.setLocation("пфо");
    }

    @BeforeEach
    void setUpForThrowExceptions() {
        searchDTOForRegionWithEmptyLocation = new SearchDTOForRegion();
        searchDTOForRegionWithEmptyLocation.setLocation("");
        searchDTOForDistrictWithNoExistLocation = new SearchDTOForDistrict();
        searchDTOForDistrictWithNoExistLocation.setLocation("несуществующий");
    }

    @Test
    void getGeoCenterForRegion() throws Exception {
        this.mockMvc
                .perform(get("/getGeoCenter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(searchDTOForRegion)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getGeoCenterForRegionWithThrowExceptionBadRequest() throws Exception {
        this.mockMvc
                .perform(get("/getGeoCenter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(searchDTOForRegionWithEmptyLocation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getGeoCenterForDistrict() throws Exception {
        this.mockMvc
                .perform(get("/getGeoCenterForDistrict")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(searchDTOForDistrict)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getGeoCenterForDistrictWithThrowExceptionNotFound() throws Exception {
        this.mockMvc
                .perform(get("/getGeoCenterForDistrict")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(searchDTOForDistrictWithNoExistLocation)))
                .andExpect(status().isNotFound());
    }
}
