package cl.duoc.ms_characters.controller;

import cl.duoc.ms_characters.dto.CharacterDto;
import cl.duoc.ms_characters.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<List<CharacterDto>> getAllCharacters() {
        return ResponseEntity.ok(characterService.findAllCharacters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacterById(@PathVariable long id) {
        return ResponseEntity.ok(characterService.findCharactersById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CharacterDto>> getCharactersByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(characterService.findCharactersByUserId(userId));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CharacterDto>> getCharacterByName(@PathVariable String name) {
        return ResponseEntity.ok(characterService.findCharactersByName(name));
    }

    @PostMapping
    public ResponseEntity<CharacterDto> createCharacter(@Valid @RequestBody CharacterDto characterDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(characterService.createCharacter(characterDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDto> updateCharacter(@PathVariable long id, @Valid @RequestBody CharacterDto characterDto) {
        return ResponseEntity.ok(characterService.updateCharacter(id, characterDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable long id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

}
