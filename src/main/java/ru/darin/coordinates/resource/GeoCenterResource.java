package ru.darin.coordinates.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.darin.coordinates.dto.SearchDTOForDistrict;
import ru.darin.coordinates.dto.SearchDTOForRegion;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.exceptions.CoordinatesErrorResponse;

@Tag(name = "API сервиса определения географического центра")
public interface GeoCenterResource {
    @Operation(
            summary = "Расчет географического центра для области.",
            description = "Данный метод выполняет поиск введенной области и рассчитывает для нее географический центр." +
                    "\nНеобходимо ввести интересующую область."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Метод успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CoordinateResponse.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoordinatesErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найдено области с таким названием"
            )
    })
    ResponseEntity getGeoCenterForRegion(@RequestBody @Valid SearchDTOForRegion searchDTOForRegion, BindingResult bindingResult);

    @Operation(
            summary = "Расчет географического центра для федерального округа (ФО).",
            description = "Данный метод выполняет поиск введенного ФО и рассчитывает для него географический центр." +
                    "\nНеобходимо ввести интересующий ФО."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Метод успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CoordinateResponse.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoordinatesErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найдено федерального округа с таким названием"
            )
    })
    ResponseEntity getGeoCenterForDistrict(@RequestBody @Valid SearchDTOForDistrict searchDTO, BindingResult bindingResult);
}
