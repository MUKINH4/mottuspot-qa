package mottu_spot.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mottu_spot.mvc.model.Dispositivo;
import mottu_spot.mvc.model.Moto;
import mottu_spot.mvc.service.DispositivoService;
import mottu_spot.mvc.service.MotoService;
import mottu_spot.mvc.service.MqttService;

@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    private final MotoService motoService;
    private final MqttService mqttService;
    private final DispositivoService dispositivoService;

    MqttController(MotoService motoService, MqttService mqttService, DispositivoService dispositivoService) {
        this.motoService = motoService;
        this.mqttService = mqttService;
        this.dispositivoService = dispositivoService;
    }

    @PostMapping("/localizar/{motoId}")
    public ResponseEntity<?> localizarMoto(@PathVariable Long motoId) {
        try {
            Moto moto = motoService.encontrarMoto(motoId);
            
            if (moto == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Moto não encontrada"));
            }
            
            if (moto.getDispositivo() == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Moto sem dispositivo vinculado"));
            }
            
            // Envia comando MQTT
            mqttService.ativarLocalizador(moto.getPlaca(), moto.getDescricao());
            
            // Atualiza status do dispositivo no banco
            Dispositivo dispositivo = moto.getDispositivo();
            dispositivo.setAtivo(true);
            dispositivoService.save(dispositivo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", true);
            response.put("mensagem", "Comando enviado ao dispositivo");
            response.put("placa", moto.getPlaca());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao enviar comando: " + e.getMessage()));
        }
    }

    @PostMapping("/desativar/{motoId}")
    public ResponseEntity<?> desativarMoto(@PathVariable Long motoId) {
        try {
            Moto moto = motoService.encontrarMoto(motoId);
            
            if (moto == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Moto não encontrada"));
            }
            
            if (moto.getDispositivo() == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Moto sem dispositivo vinculado"));
            }
            
            // Envia comando MQTT
            mqttService.desativarLocalizador(moto.getPlaca(), moto.getDescricao());
            
            // Atualiza status do dispositivo no banco
            Dispositivo dispositivo = moto.getDispositivo();
            dispositivo.setAtivo(false);
            dispositivoService.save(dispositivo);
            
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "mensagem", "Localizador desativado",
                "placa", moto.getPlaca()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}