package skypro.course4.homework.telegrambot.controller;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.CatShelter;
import skypro.course4.homework.telegrambot.repository.CatShelterRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catShelter")
@Tag(name = "Приют с кошками", description = "Редактирование информация о приюте для кошек")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный запрос"),
        @ApiResponse(responseCode = "400", description = "Невалидные параметры запроса"),
        @ApiResponse(responseCode = "404", description = "Результат запроса не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка программы")
})

public class CatShelterController {

    private final CatShelterRepository catShelterRepository;

    @PostConstruct
    public void findShelter() {
        catShelterRepository.findById(2).orElse(null);// приют кошек под индексом 2
    }

    @Operation(summary = "Добавление приюта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приют добавлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CatShelter.class)))),})
    @PostMapping("/")
    public ResponseEntity<CatShelter> create(@RequestBody CatShelter catShelter) {
        try {
            return ResponseEntity.ok(catShelterRepository.save(catShelter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CatShelter>> getAll() {
        return ResponseEntity.ok(catShelterRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение всей информации о приюте"
    )
    public ResponseEntity<CatShelter> findById(@PathVariable Integer id) {
        return ResponseEntity.of(catShelterRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @NotNull Integer id) {
        catShelterRepository.deleteById(id);
    }

}