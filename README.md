# Mottu Spot - Sistema de Gerenciamento de Pátios

## 📋 Sobre o Projeto

O **Mottu Spot** é um sistema de gerenciamento de pátios da empresa Mottu que fornece rastreamento em tempo real das motos estacionadas. O projeto propõe a instalação de dispositivos de localização em cada moto para organizar e monitorar eficientemente os pátios da empresa.

### 🎯 Funcionalidades

- **Gestão de Pátios**: Cadastro, edição e remoção de pátios com endereços completos
- **Gestão de Motos**: Registro de motos com placas, descrições e status
- **Dispositivos de Rastreamento**: Vinculação de dispositivos de localização às motos
- **Interface Web Responsiva**: Sistema web com Bootstrap e Tailwind CSS
- **Autenticação**: Sistema de login e controle de acesso
- **Banco de Dados Robusto**: PostgreSQL com migrações automáticas via Flyway

## 🏗️ Arquitetura

### Tecnologias Utilizadas

- **Backend**: Spring Boot 3.5.4 + Java 17
- **Frontend**: Thymeleaf + Bootstrap 5 + Tailwind CSS
- **Banco de Dados**: PostgreSQL
- **Migrações**: Flyway
- **Validação**: Spring Validation
- **Segurança**: Spring Security
- **Containerização**: Docker + Docker Compose
- **Build**: Gradle

### Estrutura do Projeto

```
src/
├── main/
│   ├── java/mottu_spot/mvc/
│   │   ├── config/          # Configurações (Security, etc.)
│   │   ├── controller/      # Controladores MVC
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios JPA
│   │   └── service/        # Lógica de negócio
│   └── resources/
│       ├── db/migration/   # Scripts Flyway
│       ├── templates/      # Templates Thymeleaf
│       └── static/         # Recursos estáticos
└── test/                   # Testes automatizados
```

## 🚀 Instalação e Execução

### Pré-requisitos

- **Docker** e **Docker Compose** instalados
- **Java 17** (apenas para desenvolvimento local)
- **PostgreSQL** (apenas para desenvolvimento local)

### 📦 Execução com Docker (Recomendado)

1. **Clone o repositório:**
```bash
git clone https://github.com/MUKINH4/mottu-spot-mvc.git
cd mottu-spot-mvc
```

2. **Execute com Docker Compose:**
```bash
docker-compose up --build
```

3. **Aguarde a inicialização completa:**
   - O PostgreSQL será iniciado primeiro
   - A aplicação aguardará o banco estar saudável
   - As migrações Flyway serão executadas automaticamente
   - A aplicação estará disponível em: http://localhost:8080

### 🔧 Execução Local (Desenvolvimento)

#### 1. Configuração do Banco de Dados

```bash
# Inicie apenas o PostgreSQL com Docker
docker run --name mottu-postgres \
  -e POSTGRES_DB=mottuspot_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:latest
```

#### 2. Execute a Aplicação

```bash
# Com Gradle Wrapper
./gradlew bootRun

# Ou compile e execute o JAR
./gradlew build
java -jar build/libs/mvc-0.0.1-SNAPSHOT.jar
```

#### 3. Migrações do Banco (se necessário)

```bash
./gradlew flywayMigrate
```

## 🌐 Acesso à Aplicação

### URLs Principais

- **Página Inicial**: http://localhost:8080
- **Login**: http://localhost:8080/login
- **Formulários**: http://localhost:8080/formularios
- **Pátios**: http://localhost:8080/patios/{id}

### 👤 Credenciais Padrão

As credenciais são criadas automaticamente pelas migrações:

```
Usuário: admin
Senha: admin123
```

### 📱 Funcionalidades da Interface

1. **Dashboard Principal**:
   - Visualização de todos os pátios cadastrados
   - Cards informativos com dados de cada pátio
   - Botão para acessar formulários de cadastro

2. **Página de Formulários** (`/formularios`):
   - Formulário de cadastro de pátios (nome, endereço completo, lotação)
   - Formulário de cadastro de motos (placa, descrição, status, pátio)
   - Validações em tempo real

3. **Detalhes do Pátio**:
   - Listagem de motos por pátio
   - Informações detalhadas de cada moto
   - Status dos dispositivos de rastreamento

## 🔧 Configuração

### Variáveis de Ambiente

As seguintes variáveis podem ser configuradas:

```bash
# Banco de Dados
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mottuspot_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# JPA/Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=none

# Flyway
SPRING_FLYWAY_ENABLED=true
```

### Profiles de Execução

- **Desenvolvimento**: Usa configurações padrão do `application.properties`
- **Docker**: Usa variáveis de ambiente do `compose.yaml`

## 🗃️ Estrutura do Banco de Dados

### Principais Entidades

- **Patio**: Informações do pátio e endereço
- **Moto**: Dados das motos (placa, descrição, status)
- **Dispositivo**: Dispositivos de rastreamento
- **Endereco**: Endereços completos dos pátios
- **User**: Usuários do sistema

### Relacionamentos

- Pátio ↔ Endereço (One-to-One)
- Pátio → Motos (One-to-Many)
- Moto ↔ Dispositivo (One-to-One)

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
./gradlew test

# Testes com relatório
./gradlew test --info

# Gerar relatório de cobertura
./gradlew jacocoTestReport
```

### Estrutura de Testes

- **Testes Unitários**: Services e Controllers
- **Testes de Integração**: Repositórios e APIs
- **Testes Web**: Interface Thymeleaf

## 🔄 Comandos Úteis

### Docker

```bash
# Parar containers
docker-compose down

# Rebuild completo
docker-compose up --build --force-recreate

# Ver logs da aplicação
docker-compose logs -f app

# Ver logs do banco
docker-compose logs -f db

# Limpar volumes (CUIDADO: apaga dados)
docker-compose down -v
```

### Gradle

```bash
# Limpar build
./gradlew clean

# Verificar dependências
./gradlew dependencies

# Atualizar wrapper
./gradlew wrapper --gradle-version=8.5
```

### Flyway

```bash
# Status das migrações
./gradlew flywayInfo

# Limpar banco (CUIDADO)
./gradlew flywayClean

# Reparar migrações com erro
./gradlew flywayRepair
```

## 🐛 Solução de Problemas

### Problemas Comuns

1. **Erro de conexão com banco**:
   - Verifique se o PostgreSQL está rodando
   - Confirme as credenciais no `application.properties`
   - Para Docker: `docker-compose down && docker-compose up --build`

2. **Erro de migrações**:
   ```bash
   ./gradlew flywayRepair
   ./gradlew flywayMigrate
   ```

3. **Porta 8080 em uso**:
   - Altere a porta no `compose.yaml` ou `application.properties`
   - Ou mate o processo: `lsof -ti:8080 | xargs kill -9`

4. **Problemas de permissão no Docker**:
   ```bash
   chmod +x gradlew
   docker-compose down && docker-compose up --build
   ```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Crie um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📞 Suporte

Para dúvidas e suporte:
- Abra uma issue no GitHub
- Entre em contato via email: [seu-email@mottu.com]

---

**Desenvolvido com ❤️ para Mottu Challenge**