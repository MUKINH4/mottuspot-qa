package mottu_spot.mvc.repository;

import mottu_spot.mvc.model.Moto;
import mottu_spot.mvc.model.StatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {

    List<Moto> findByPatioId(Long id);
    boolean existsByPlaca(String placa);
    Optional<Moto> findByPlaca(String placa);

    List<Moto> findByStatus(StatusEnum status);

}
