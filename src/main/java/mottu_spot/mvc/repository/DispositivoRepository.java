package mottu_spot.mvc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mottu_spot.mvc.model.Dispositivo;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {
    
    List<Dispositivo> findByAtivoTrue();
    
    List<Dispositivo> findByAtivoFalse();
    
    long countByAtivoTrue();
    
    long countByAtivoFalse();
}