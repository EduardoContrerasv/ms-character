package cl.duoc.ms_characters.service;

import cl.duoc.ms_characters.dto.CharacterDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CharacterService {

    List<CharacterDto> findAllCharacters();
    List<CharacterDto> findCharactersByName(String name);
    CharacterDto findCharactersById(long id);
    CharacterDto createCharacter(CharacterDto characterDto);
    Boolean deleteCharacter(long id);
    CharacterDto updateCharacter (long id, CharacterDto dto);
}
