package cl.duoc.ms_characters.service.impl;

import cl.duoc.ms_characters.client.UserFeignClient;
import cl.duoc.ms_characters.dto.BaseCharacterRequestDto;
import cl.duoc.ms_characters.dto.RosterResponseDto;
import cl.duoc.ms_characters.dto.UserFeignDto;
import cl.duoc.ms_characters.model.BaseCharacter;
import cl.duoc.ms_characters.model.UserCharacter;
import cl.duoc.ms_characters.repository.BaseCharacterRepository;
import cl.duoc.ms_characters.repository.UserCharacterRepository;
import cl.duoc.ms_characters.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cl.duoc.ms_characters.dto.UnlockCharacterDto;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final BaseCharacterRepository baseCharacterRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final UserFeignClient userFeignClient;



    @Override
    public List<RosterResponseDto> getUserRoster(long userId) {
        List<UserCharacter> roster = userCharacterRepository.findByUserId(userId);

        return roster.stream().map(uc -> {
            RosterResponseDto dto = new RosterResponseDto();
            dto.setCharacterName(uc.getBaseCharacter().getName());
            dto.setCharacterClass(uc.getBaseCharacter().getCharacterArchetype().name());
            dto.setLevel(uc.getCurrentLevel());
            return dto;
        }).toList();
    }

    @Override
    public String createBaseCharacter(BaseCharacterRequestDto dto) {

        if (baseCharacterRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("El héroe " + dto.getName() + " ya existe.");
        }

        BaseCharacter hero = new BaseCharacter();
        hero.setName(dto.getName());
        hero.setCharacterArchetype(dto.getCharacterArchetype());

        int rawHealth = dto.getBaseHealth();
        int rawAttack = dto.getBaseAttack();
        int rawDefense = dto.getBaseDefense();

        switch (dto.getCharacterArchetype()) {
            case ATTACK:
                hero.setBaseAttack((int) (rawAttack * 1.2));
                hero.setBaseHealth((int) (rawHealth * 0.9));
                hero.setBaseDefense((int) (rawDefense * 0.7));
                break;

            case VANGUARD:
                hero.setBaseAttack((int) (rawAttack * 0.6));
                hero.setBaseHealth((int) (rawHealth * 1.5));
                hero.setBaseDefense((int) (rawDefense * 1.3));
                break;

            case STRATEGIST:
                hero.setBaseAttack((int) (rawAttack * 1.05));
                hero.setBaseHealth((int) (rawHealth * 1.05));
                hero.setBaseDefense((int) (rawDefense * 0.9));
                break;

            case SUPPORT:
                hero.setBaseAttack((int) (rawAttack * 0.7));
                hero.setBaseHealth((int) (rawHealth * 1.1));
                hero.setBaseDefense((int) (rawDefense * 1.1));
                break;
        }

        baseCharacterRepository.save(hero);
        return "Héroe " + hero.getName() + " creado";
    }

    @Override
    public String unlockCharacterForUser(UnlockCharacterDto dto) {

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

        userCharacterRepository.save(playerCopy);
        return "El jugador ha desbloqueado a " + blueprint.getName() + "!";
    }

}