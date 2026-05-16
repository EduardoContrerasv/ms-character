package cl.duoc.ms_characters.service.impl;

import cl.duoc.ms_characters.model.Characters;
import cl.duoc.ms_characters.dto.CharacterDto;
import cl.duoc.ms_characters.service.CharacterService;
import cl.duoc.ms_characters.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository repository;

    private CharacterDto toDto(Characters characters) {
        return new CharacterDto(
                characters.getCharacterId(),
                characters.getName(),
                characters.getGender(),
                characters.getRole(),
                characters.getElement()
        );
    }

    private Characters toEntity(CharacterDto dto) {
        Characters character = new Characters();
        character.setName(dto.getName());
        character.setGender(dto.getGender());
        character.setRole(dto.getRole());
        character.setElement(dto.getElement());

        return character;
    }

    @Override
    public List<CharacterDto> findAllCharacters() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<CharacterDto> findCharactersByName(String name) {
        return repository.findByName(name).stream().map(this::toDto).toList();
    }

    @Override
    public CharacterDto findCharactersById(long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Character with ID " + id + " not found"));
    }

    @Override
    public CharacterDto createCharacter(CharacterDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Character name cannot be empty");
        }
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("A character with the name '" + dto.getName() + "' already exists");
        }
        Characters savedCharacter = repository.save(this.toEntity(dto));
        return this.toDto(savedCharacter);
    }

    @Override
    public Boolean deleteCharacter(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        throw new RuntimeException("Cannot delete: Character with ID " + id + " not found");
    }

    @Override
    public CharacterDto updateCharacter(long id, CharacterDto dto) {
        Characters existingCharacter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update: Character not found"));

        existingCharacter.setName(dto.getName());
        existingCharacter.setGender(dto.getGender());
        existingCharacter.setRole(dto.getRole());
        existingCharacter.setElement(dto.getElement());

        Characters updatedCharacter = repository.save(existingCharacter);
        return toDto(updatedCharacter);
    }
}