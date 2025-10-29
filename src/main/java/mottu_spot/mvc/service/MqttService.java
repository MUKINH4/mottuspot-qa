package mottu_spot.mvc.service;

import com.google.gson.Gson;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MqttService {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.topic.localizacao}")
    private String topicComando;

    private MqttClient mqttClient;
    private final Gson gson = new Gson();

    @PostConstruct
    public void connect() {
        try {
            // Adiciona timestamp único ao clientId para evitar conflitos
            String uniqueClientId = clientId + "_" + UUID.randomUUID().toString().substring(0, 8);
            
            // ✅ USA MEMÓRIA AO INVÉS DE ARQUIVO - FIX DO ERRO
            MemoryPersistence persistence = new MemoryPersistence();
            mqttClient = new MqttClient(brokerUrl, uniqueClientId, persistence);
            
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(30);
            options.setKeepAliveInterval(60);
            options.setMaxInflight(10);
            
            // Callback para reconexão
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("⚠️ Conexão MQTT perdida: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("📩 Mensagem recebida em " + topic + ": " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("✅ Mensagem entregue");
                }
            });
            
            System.out.println("🔄 Conectando ao broker MQTT: " + brokerUrl);
            System.out.println("🆔 Client ID: " + uniqueClientId);
            
            mqttClient.connect(options);
            System.out.println("✅ Conectado ao broker MQTT com sucesso!");
            
            // Subscreve ao tópico de status
            mqttClient.subscribe("iot/mottuspot/status", 1);
            System.out.println("📡 Inscrito no tópico: iot/mottuspot/status");
            
        } catch (MqttException e) {
            System.err.println("❌ Erro ao conectar ao MQTT:");
            System.err.println("   Código: " + e.getReasonCode());
            System.err.println("   Mensagem: " + e.getMessage());
            System.err.println("   Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A"));
            e.printStackTrace();
        }
    }

    public void ativarLocalizador(String placa, String descricao) throws MqttException {
        verificarConexao();
        
        Map<String, String> payload = new HashMap<>();
        payload.put("placa", placa);
        payload.put("descricao", descricao);
        payload.put("status", "ativar");
        
        String json = gson.toJson(payload);
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setQos(1);
        message.setRetained(false);
        
        System.out.println("📤 Publicando no tópico: " + topicComando);
        System.out.println("📦 Payload: " + json);
        
        mqttClient.publish(topicComando, message);
        System.out.println("🚨 Comando ATIVAR enviado para: " + placa);
    }

    public void desativarLocalizador(String placa, String descricao) throws MqttException {
        verificarConexao();
        
        Map<String, String> payload = new HashMap<>();
        payload.put("placa", placa);
        payload.put("descricao", descricao);
        payload.put("status", "desativar");
        
        String json = gson.toJson(payload);
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setQos(1);
        
        mqttClient.publish(topicComando, message);
        System.out.println("✅ Comando DESATIVAR enviado para: " + placa);
    }

    private void verificarConexao() throws MqttException {
        if (mqttClient == null || !mqttClient.isConnected()) {
            System.err.println("⚠️ Cliente MQTT desconectado. Tentando reconectar...");
            connect();
            
            if (!mqttClient.isConnected()) {
                throw new MqttException(MqttException.REASON_CODE_CLIENT_NOT_CONNECTED);
            }
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.unsubscribe("iot/mottuspot/status");
                mqttClient.disconnect();
                mqttClient.close();
                System.out.println("🔌 Desconectado do MQTT");
            }
        } catch (MqttException e) {
            System.err.println("⚠️ Erro ao desconectar: " + e.getMessage());
        }
    }
    
    // Método para verificar status
    public boolean isConnected() {
        return mqttClient != null && mqttClient.isConnected();
    }
}