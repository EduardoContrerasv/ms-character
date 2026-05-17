package cl.duoc.ms_characters.service;

import cl.duoc.ms_characters.dto.CharacterDto;


import java.util.List;


public interface CharacterService {

    List<CharacterDto> findAllCharacters();

    List<CharacterDto> findCharactersByName(String name);

    CharacterDto findCharactersById(long id);

    List<CharacterDto> findCharactersByUserId(long id);

    CharacterDto createCharacter(CharacterDto characterDto);

    Boolean deleteCharacter(long id);

    CharacterDto updateCharacter (long id, CharacterDto dto);
}
