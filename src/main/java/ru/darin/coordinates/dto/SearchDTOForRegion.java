package ru.darin.coordinates.dto;

import lombok.Data;
import ru.darin.coordinates.util.ValidSearchDTOForRegion;

@Data
@ValidSearchDTOForRegion
public class SearchDTOForRegion {
    private String location;
}
