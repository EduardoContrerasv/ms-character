package cl.duoc.ms_characters.service;


import cl.duoc.ms_characters.dto.BaseCharacterRequestDto;
import cl.duoc.ms_characters.dto.RosterResponseDto;
import cl.duoc.ms_characters.dto.UnlockCharacterDto;

import java.util.List;


public interface CharacterService {
    List<RosterResponseDto> getUserRoster(long userId);
    String createBaseCharacter(BaseCharacterRequestDto dto);
    String unlockCharacterForUser(UnlockCharacterDto dto);
}
