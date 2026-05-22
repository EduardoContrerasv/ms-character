package cl.duoc.ms_characters.controller;

import cl.duoc.ms_characters.dto.BaseCharacterRequestDto;
import cl.duoc.ms_characters.dto.RosterResponseDto;
import cl.duoc.ms_characters.dto.UnlockCharacterDto;
import cl.duoc.ms_characters.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService service;

    @PostMapping("/admin/create")
    public ResponseEntity<String> createBaseCharacter(@Valid @RequestBody BaseCharacterRequestDto dto) {
        return ResponseEntity.ok(service.createBaseCharacter(dto));
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockCharacterForUser(@Valid @RequestBody UnlockCharacterDto dto) {
        return ResponseEntity.ok(service.unlockCharacterForUser(dto));
    }

    @GetMapping("/roster/{userId}")
    public ResponseEntity<List<RosterResponseDto>> getUserRoster(@PathVariable long userId) {
        return ResponseEntity.ok(service.getUserRoster(userId));
    }
}