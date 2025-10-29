package mottu_spot.mvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mottu_spot.mvc.model.Dispositivo;
import mottu_spot.mvc.repository.DispositivoRepository;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    @Transactional
    public Dispositivo save(Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo findById(UUID id) {
        return dispositivoRepository.findById(id).orElse(null);
    }

    public Optional<Dispositivo> findByIdOptional(UUID id) {
        return dispositivoRepository.findById(id);
    }

    public List<Dispositivo> findAll() {
        return dispositivoRepository.findAll();
    }

    public List<Dispositivo> findAllAtivos() {
        return dispositivoRepository.findByAtivoTrue();
    }

    public List<Dispositivo> findAllInativos() {
        return dispositivoRepository.findByAtivoFalse();
    }

    @Transactional
    public void delete(UUID id) {
        dispositivoRepository.deleteById(id);
    }

    @Transactional
    public void delete(Dispositivo dispositivo) {
        dispositivoRepository.delete(dispositivo);
    }

    @Transactional
    public Dispositivo ativar(UUID id) {
        Dispositivo dispositivo = findById(id);
        if (dispositivo != null) {
            dispositivo.setAtivo(true);
            return save(dispositivo);
        }
        return null;
    }

    @Transactional
    public Dispositivo desativar(UUID id) {
        Dispositivo dispositivo = findById(id);
        if (dispositivo != null) {
            dispositivo.setAtivo(false);
            return save(dispositivo);
        }
        return null;
    }

    public boolean exists(UUID id) {
        return dispositivoRepository.existsById(id);
    }

    public long count() {
        return dispositivoRepository.count();
    }

    public long countAtivos() {
        return dispositivoRepository.countByAtivoTrue();
    }

    public long countInativos() {
        return dispositivoRepository.countByAtivoFalse();
    }
}