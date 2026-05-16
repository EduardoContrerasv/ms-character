package cl.duoc.ms_characters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.ms_characters.model.Characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Characters, Long> {

    Optional<Characters> findByName(String name);

    List<Characters> characterList = new ArrayList<>();


}
