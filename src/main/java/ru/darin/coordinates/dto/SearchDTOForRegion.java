package ru.darin.coordinates.dto;

import lombok.Data;
import ru.darin.coordinates.util.ValidSearchDTO;

@Data
@ValidSearchDTO
public class SearchDTOForRegion {
    private String location;
}
