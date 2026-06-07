### **📘 DOCUMENTAÇÃO COMPLETA – SIGET**

**Sistema Integrado de Gestão de Equipamentos e Telecom**

---

### **🟦 1. Nome do Projeto**

**SIGET – Sistema Integrado de Gestão de Equipamentos e Telecom**

---

### **🟦 2. Descrição Geral**

O SIGET é um backend corporativo desenvolvido em Java 25 + Spring Boot, utilizando PostgreSQL como banco de dados relacional.

O sistema foi projetado para:

   - gerenciar inventário de equipamentos
   - registrar vistorias técnicas
   - controlar instalação, remoção e substituição
   - manter catálogo padronizado
   - garantir auditoria completa
   - centralizar regras de negócio

---

### **🟦 3. Objetivos do Projeto**

   - Criar um backend robusto, seguro e escalável
   - Garantir integridade e rastreabilidade dos dados
   - Padronizar modelos e fabricantes
   - Automatizar inventário
   - Registrar histórico detalhado de ações
   - Fornecer APIs REST padronizadas
   - Facilitar integração com sistemas externos

---

### **🟦 4. Escopo**

Inclui:

   - Backend completo
   - APIs REST
   - Banco PostgreSQL
   - Regras de negócio
   - Auditoria
   - Inventário
   - Vistorias
   - Catálogo
   - Usuários e técnicos

Não inclui:

   - Frontend
   - Aplicativo mobile
   - Dashboards

---

### **🟦 5. Arquitetura do Sistema**

        FRONTEND (qualquer)
        │
        ▼
        API GATEWAY
        │
        ▼
        SPRING BOOT BACKEND
        ├── Usuários
        ├── Técnicos
        ├── Catálogo
        ├── Sites
        ├── Equipamentos
        ├── Inventário
        ├── Vistorias
        └── Logs
        │
        ▼
        POSTGRESQL

```mermaid

    flowchart TD
    A[Frontend / Mobile] --> B[API Gateway]
    B --> C[Backend Spring Boot]
    C -->|JPA| D[(PostgreSQL)]
    
        subgraph Backend Spring Boot
            C1[Usuários]
            C2[Técnicos]
            C3[Catálogo]
            C4[Sites]
            C5[Equipamentos]
            C6[Inventário]
            C7[Vistorias]
            C8[Logs]
        end

```

---

### **🟦 6. Tecnologias Utilizadas**

   - Java 25
   - Spring Boot 3.x
   - Spring Web
   - Spring Data JPA
   - Spring Security (JWT)
   - PostgreSQL
   - Flyway
   - Lombok
   - Validation (Jakarta)
   - Swagger / SpringDoc OpenAPI

---

### **🟦 7. Dependências Essenciais (com justificativas)**

**Spring Web**

Permite criar APIs REST, controllers e rotas. Sem ele, o backend não expõe endpoints.

**Spring Data JPA**

Simplifica o acesso ao banco e reduz código. Essencial para o modelo relacional do SIGET.

**PostgreSQL Driver**

Driver JDBC para conectar ao banco. Sem ele, o backend não acessa o PostgreSQL.

**Spring Security**

Responsável pela autenticação e autorização via JWT.

**Validation (Jakarta)**

Valida DTOs automaticamente.

Evita dados inválidos no banco.

**Lombok**

Reduz código repetitivo (getters, setters, builders).

**DevTools**

Acelera o desenvolvimento com reload automático.

**Flyway**

Versiona o banco de dados e garante consistência entre ambientes.

**SpringDoc OpenAPI**

Gera documentação automática das APIs (Swagger).

---

### **🟦 8. Modelagem de Dados – DER**

    ┌──────────────┐        1 ── 1        ┌──────────────┐
    │   USUARIO    │──────────────────────│   TECNICO    │
    └──────────────┘                      └──────────────┘
    │ 1                                     
    │ N                                    
    ▼                                      
    ┌──────────────┐        1 ── N        ┌──────────────────┐
    │ LOG_SISTEMA  │──────────────────────│     USUARIO      │
    └──────────────┘                      └──────────────────┘
    
    ┌──────────────┐        1 ── N        ┌──────────────────┐
    │    SITE      │──────────────────────│   EQUIPAMENTO    │
    └──────────────┘                      └──────────────────┘
    │ 1
    │ N
    ▼
    ┌──────────────┐
    │  INVENTARIO  │
    └──────────────┘
    
    ┌──────────────┐        1 ── N        ┌──────────────────┐
    │    SITE      │──────────────────────│    VISTORIA      │
    └──────────────┘                      └──────────────────┘
    │ 1
    │ N
    ▼
    ┌────────────────────┐
    │ HISTORICO_VISTORIA │
    └────────────────────┘
    
    ┌──────────────┐        1 ── N        ┌──────────────────┐
    │   CATALOGO   │──────────────────────│   EQUIPAMENTO    │
    └──────────────┘                      └──────────────────┘

```mermaid


erDiagram

    USUARIO ||--|| TECNICO : "possui"
    USUARIO ||--o{ LOG_SISTEMA : "gera"

    SITE ||--o{ EQUIPAMENTO : "possui"
    EQUIPAMENTO ||--o{ INVENTARIO : "registra"

    SITE ||--o{ VISTORIA : "tem"
    VISTORIA ||--o{ HISTORICO_VISTORIA : "registra"

    CATALOGO ||--o{ EQUIPAMENTO : "modelo"

    USUARIO {
        bigint id PK
        varchar nome
        varchar email
        varchar senha
        varchar perfil
        boolean ativo
    }

    TECNICO {
        bigint id PK
        bigint usuario_id FK
        varchar matricula
        varchar regional
    }

    SITE {
        bigint id PK
        varchar endid
        varchar nome
        varchar endereco
        varchar status
    }

    CATALOGO {
        bigint id PK
        varchar modelo
        varchar fabricante
        varchar tipo
        boolean adaptada
    }

    EQUIPAMENTO {
        bigint id PK
        bigint site_id FK
        bigint catalogo_id FK
        varchar serial
        varchar status
    }

    INVENTARIO {
        bigint id PK
        bigint equipamento_id FK
        bigint site_id FK
        varchar status
    }

    VISTORIA {
        bigint id PK
        bigint site_id FK
        bigint tecnico_id FK
        varchar status
    }

    HISTORICO_VISTORIA {
        bigint id PK
        bigint vistoria_id FK
        bigint equipamento_id FK
        varchar acao
        text descricao
    }

    LOG_SISTEMA {
        bigint id PK
        bigint usuario_id FK
        varchar acao
        jsonb detalhes
    }

```

---

### **🟦 9. Tabelas do Banco de Dados**

---

### **🟦 10. Diagrama de Sequência – Fluxo de Instalação** 

```mermaid

sequenceDiagram
    actor Tecnico
    participant App as App Mobile
    participant API as Backend SIGET
    participant DB as Banco

    Tecnico->>App: Login
    App->>API: POST /auth/login
    API->>DB: Consulta usuário
    DB-->>API: Retorna usuário
    API-->>App: Token JWT

    Tecnico->>App: Buscar Site
    App->>API: GET /sites/{endid}
    API->>DB: SELECT site
    DB-->>API: Dados do site
    API-->>App: Retorno site

    Tecnico->>App: Criar Vistoria
    App->>API: POST /vistorias
    API->>DB: INSERT vistoria
    DB-->>API: OK
    API-->>App: Vistoria criada

    Tecnico->>App: Instalar Equipamento
    App->>API: POST /equipamentos/instalar
    API->>DB: Valida catálogo
    API->>DB: INSERT equipamento
    API->>DB: INSERT inventário
    API->>DB: INSERT histórico
    DB-->>API: OK
    API-->>App: Instalação concluída

    Tecnico->>App: Finalizar Vistoria
    App->>API: PUT /vistorias/{id}/finalizar
    API->>DB: UPDATE vistoria
    DB-->>API: OK
    API-->>App: Vistoria finalizada

```