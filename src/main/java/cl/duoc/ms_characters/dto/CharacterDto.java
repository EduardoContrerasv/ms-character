package cl.duoc.ms_characters.dto;

import cl.duoc.ms_characters.model.CharacterClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {

    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotBlank(message = "Debe ingresar un nombre para el personaje")
    private String name;

    @NotBlank(message = "Debe ingresar un genero")
    @Pattern(regexp = "^[MF]$", message = "El género debe ser 'M' o 'F'")
    private String gender;

    @NotNull(message = "Debe ingresar una clase (WARRIOR, MAGE, ARCHER, ASSASSIN, SUPPORT)")
    private CharacterClass characterClass;

    private Integer level;
    private Integer experience;
    private Integer health;
    private Integer attack;
    private Integer defense;
    private String status;
}
