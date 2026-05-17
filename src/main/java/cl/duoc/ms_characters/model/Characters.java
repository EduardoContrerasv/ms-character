package cl.duoc.ms_characters.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "characters")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long characterId;

    private long userId;
    private String name;
    private String gender;

    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;

    private Integer level;
    private Integer experience;
    private Integer health;
    private Integer attack;
    private Integer defense;
    private String status;


}
