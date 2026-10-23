package ma.ensaf.springbootjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.ensaf.springbootjpa.model.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}