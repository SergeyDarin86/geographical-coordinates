package ru.darin.coordinates.dto;

import lombok.Data;
import ru.darin.coordinates.util.ValidSearchDTO;

@Data
@ValidSearchDTO
public class SearchDTOForDistrict {
    private String location;
}
