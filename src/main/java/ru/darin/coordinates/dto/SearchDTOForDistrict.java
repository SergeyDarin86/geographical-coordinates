package ru.darin.coordinates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.darin.coordinates.util.ValidSearchDTO;

@Data
@ValidSearchDTO
@Schema(description = "DTO для поиска географического центра федерального округа.")
public class SearchDTOForDistrict {
    @Schema(description = "Название федерального округа", example = "пфо")
    private String location;
}
