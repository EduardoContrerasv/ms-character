package cl.duoc.ms_characters.controller;

import cl.duoc.ms_characters.dto.CharacterDto;
import cl.duoc.ms_characters.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/characters/v1")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacterById(@PathVariable long id) {
        return ResponseEntity.ok(characterService.findCharactersById(id));
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterDto>> getAllCharacters() {
        return ResponseEntity.ok(characterService.findAllCharacters());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDto> updateCharacter(@PathVariable long id, @RequestBody CharacterDto characterDto) {
        return ResponseEntity.ok(characterService.updateCharacter(id, characterDto));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<CharacterDto>> getCharacterByName(@PathVariable String name) {
        return ResponseEntity.ok(characterService.findCharactersByName(name));
    }

    @PostMapping("/create")
    public ResponseEntity<CharacterDto> createCharacter(@RequestBody CharacterDto characterDto) {
        return ResponseEntity.ok(characterService.createCharacter(characterDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable long id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }


}
