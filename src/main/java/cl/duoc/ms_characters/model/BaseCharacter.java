package cl.duoc.ms_characters.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "base_characters")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BaseCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CharacterArchetype characterArchetype;

    private int baseHealth;
    private int baseAttack;
    private int baseDefense;
}