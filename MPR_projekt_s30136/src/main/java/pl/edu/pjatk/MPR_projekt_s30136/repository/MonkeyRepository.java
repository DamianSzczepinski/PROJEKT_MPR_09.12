package pl.edu.pjatk.MPR_projekt_s30136.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.MPR_projekt_s30136.model.Monkey;

import java.util.List;

@Repository
public interface MonkeyRepository extends CrudRepository<Monkey, Long> {
    List<Monkey> findByName(String name);
}
