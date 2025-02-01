package ru.darin.coordinates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.darin.coordinates.util.ValidSearchDTO;

@Data
@ValidSearchDTO
@Schema(description = "DTO для поиска географического центра области.")
public class SearchDTOForRegion {
    @Schema(description = "Название области", example = "Тульская область")
    private String location;
}
