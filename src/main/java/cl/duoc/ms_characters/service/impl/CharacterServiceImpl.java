package cl.duoc.ms_characters.service.impl;

import cl.duoc.ms_characters.client.InventoryClient;
import cl.duoc.ms_characters.client.UserFeignClient;
import cl.duoc.ms_characters.dto.*;
import cl.duoc.ms_characters.model.BaseCharacter;
import cl.duoc.ms_characters.model.UserCharacter;
import cl.duoc.ms_characters.repository.BaseCharacterRepository;
import cl.duoc.ms_characters.repository.UserCharacterRepository;
import cl.duoc.ms_characters.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final BaseCharacterRepository baseCharacterRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final UserFeignClient userFeignClient;
    private final InventoryClient inventoryClient;
    private final ItemClient itemClient;

    private CharacterDto toDto(Characters character) {
        log.info("toDto()");
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
        log.info("findAllCharacters()");
        return repository.findAll().stream().map(this::toDto).toList();
    }


    @Override
    public List<CharacterDto> findCharactersByName(String name) {
        log.info("findCharactersByName()");
        return repository.findByName(name).stream().map(this::toDto).toList();
    }

    @Override
    public CharacterDto findCharactersById(long id) {
        log.info("findCharactersById()");
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Character with ID " + id + " not found"));
    }

    @Override
    public List<CharacterDto> findCharactersByUserId(long userId) {
        log.info("findCharactersByUserId()");
        return repository.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public CharacterDto createCharacter(CharacterDto dto) {
        log.info("createCharacter()");

        try {
            UserFeignDto user = userFeignClient.getUserById(dto.getUserId());
            if (user == null) throw new RuntimeException("Usuario no encontrado.");
        } catch (Exception e) {
            throw new RuntimeException("Error validando usuario con ms-user.");

        }
        BaseCharacter blueprint = baseCharacterRepository.findById(dto.getBaseCharacterId())
                .orElseThrow(() -> new RuntimeException("Ese héroe no existe en el juego."));

        boolean alreadyOwns = userCharacterRepository.existsByUserIdAndBaseCharacterId(dto.getUserId(), dto.getBaseCharacterId());
        if (alreadyOwns) {
            throw new RuntimeException("El usuario ya posee a este héroe.");
        }

        UserCharacter playerCopy = new UserCharacter();
        playerCopy.setUserId(dto.getUserId());
        playerCopy.setBaseCharacter(blueprint);
        playerCopy.setEquippedCosmeticId(blueprint.getDefaultCosmeticItemId());

        userCharacterRepository.save(playerCopy);
        return "El jugador ha desbloqueado a " + blueprint.getName() + "!";
    }

    @Override
    public Boolean deleteCharacter(long id) {
        log.info("deleteCharacter()");
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        userCharacterRepository.save(playerHero);
        return "Item equipado: " + dto.getSlot();
    }

    @Override
    public CharacterDto updateCharacter(long id, CharacterDto dto) {
        log.info("updateCharacter()");
        Characters existingCharacter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update: Character not found"));

            dto.setHealth(hero.getBaseHealth());
            dto.setAttack(hero.getBaseAttack());
            dto.setDefense(hero.getBaseDefense());

            return dto;
        }).toList();
    }
}