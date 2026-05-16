package cl.duoc.ms_characters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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


    Long id;
    @NotBlank(message = "Character must have a name")
    String name;
    @NotBlank
    @Pattern(regexp = "^[MF]$", message = "Gender must be M or F")
    String gender;
    @NotBlank(message = "Character must have a role")
    String role;
    @NotBlank(message = "Character must have an element")
    String element;

}
