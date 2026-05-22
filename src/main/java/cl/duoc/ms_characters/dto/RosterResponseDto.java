package cl.duoc.ms_characters.dto;

import lombok.Data;

@Data
public class RosterResponseDto {
    private String characterName;
    private String characterClass;
    private int level;
}
