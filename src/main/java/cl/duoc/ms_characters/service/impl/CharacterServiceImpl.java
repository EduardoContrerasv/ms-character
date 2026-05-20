package cl.duoc.ms_characters.service.impl;

import cl.duoc.ms_characters.client.UserFeignClient;
import cl.duoc.ms_characters.dto.UserDto;
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
    private final UserFeignClient userFeignClient;

    private CharacterDto toDto(Characters character) {
        CharacterDto dto = new CharacterDto();
        dto.setId(character.getCharacterId());
        dto.setUserId(character.getUserId());
        dto.setName(character.getName());
        dto.setGender(character.getGender());
        dto.setCharacterClass(character.getCharacterClass());
        dto.setLevel(character.getLevel());
        dto.setExperience(character.getExperience());
        dto.setHealth(character.getHealth());
        dto.setAttack(character.getAttack());
        dto.setDefense(character.getDefense());
        dto.setStatus(character.getStatus());
        return dto;
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
    public List<CharacterDto> findCharactersByUserId(long userId) {
        return repository.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public CharacterDto createCharacter(CharacterDto dto) {

        try {
            UserDto user = userFeignClient.getUserById(dto.getUserId());

            if (user == null || user.getId() == null) {
                throw new RuntimeException("El usuario con la ID: " + dto.getUserId() + " no existe");
            }
        } catch (Exception e) {
            System.err.println("Error de conexión con ms-user: " + e.getMessage());
            throw new RuntimeException("No se pudo validar el usuario. Asegúrate de que el usuario existe");
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Debe ingresar un nombre");
        }

        if (!repository.findByName(dto.getName()).isEmpty()) {
            throw new RuntimeException("Ya existe un personaje con el nombre: " + dto.getName());
        }

        Characters character = new Characters();
        character.setUserId(dto.getUserId());
        character.setName(dto.getName());
        character.setGender(dto.getGender());
        character.setCharacterClass(dto.getCharacterClass());
        character.setLevel(1);
        character.setExperience(0);
        character.setStatus("ACTIVE");

        if (dto.getCharacterClass() != null) {
            switch (dto.getCharacterClass()) {
                case WARRIOR:
                    character.setHealth(150); character.setAttack(20); character.setDefense(15);
                    break;
                case MAGE:
                    character.setHealth(80); character.setAttack(30); character.setDefense(5);
                    break;
                case ARCHER:
                    character.setHealth(100); character.setAttack(25); character.setDefense(10);
                    break;
                case ASSASSIN:
                    character.setHealth(90); character.setAttack(35); character.setDefense(5);
                    break;
                case SUPPORT:
                    character.setHealth(120); character.setAttack(10); character.setDefense(20);
                    break;
            }
        }

        Characters savedCharacter = repository.save(character);
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

        Characters updatedCharacter = repository.save(existingCharacter);
        return toDto(updatedCharacter);
    }
}