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
---

## **🟦 11. Diagrama de Caso de Uso**

```mermaid

flowchart TD

    %% Atores
    A[Administrador]:::actor
    T[Técnico]:::actor

    %% Sistema
    S((SIGET)):::system

    %% Casos de Uso - Administrador
    CU1[Gerenciar Usuários]
    CU2[Gerenciar Técnicos]
    CU3[Gerenciar Catálogo]

    %% Casos de Uso - Técnico
    CU4[Realizar Vistoria]
    CU5[Instalar Equipamento]
    CU6[Substituir/Remover Equipamento]
    CU7[Atualizar Inventário]

    %% Relações Administrador
    A --> CU1
    A --> CU2
    A --> CU3

    %% Relações Técnico
    T --> CU4
    T --> CU5
    T --> CU6
    T --> CU7

    %% Casos de uso conectados ao sistema
    CU1 --> S
    CU2 --> S
    CU3 --> S
    CU4 --> S
    CU5 --> S
    CU6 --> S
    CU7 --> S

    %% Estilos
    classDef actor fill:#f4f4f4,stroke:#333,stroke-width:1px;
    classDef system fill:#d1e8ff,stroke:#036,stroke-width:2px;

```

---

## **🟦 12. Diagrama de Classes**

```mermaid

classDiagram

    class Usuario {
        +Long id
        +String nome
        +String email
        +String senha
        +String perfil
        +Boolean ativo
    }

    class Tecnico {
        +Long id
        +String matricula
        +String regional
    }

    class Site {
        +Long id
        +String endid
        +String nome
        +String endereco
        +String status
    }

    class Catalogo {
        +Long id
        +String modelo
        +String fabricante
        +String tipo
        +Boolean adaptada
    }

    class Equipamento {
        +Long id
        +String serial
        +String status
    }

    class Inventario {
        +Long id
        +String status
    }

    class Vistoria {
        +Long id
        +String status
    }

    class HistoricoVistoria {
        +Long id
        +String acao
        +String descricao
    }

    class LogSistema {
        +Long id
        +String acao
        +String detalhes
    }

    Usuario --> Tecnico
    Usuario --> LogSistema

    Site --> Equipamento
    Equipamento --> Inventario

    Site --> Vistoria
    Vistoria --> HistoricoVistoria

    Catalogo --> Equipamento


```

---


### **🟦 13. Introdução aos Requisitos**

Os requisitos do SIGET definem o comportamento esperado do sistema e servem como base para orientar o desenvolvimento, garantir alinhamento entre as áreas envolvidas e permitir validação futura.

Eles foram organizados em:

   - Requisitos Funcionais (RF): o que o sistema deve fazer.

   - Requisitos Não Funcionais (RNF): como o sistema deve se comportar.
---

### **🟦 14. Requisitos Funcionais (RF)**

- RF01 – Autenticação via JWT
- RF02 – Gerenciamento de usuários
- RF03 – Gerenciamento de técnicos
- RF04 – Catálogo de equipamentos
- RF05 – Consulta e cadastro de site
- RF06 – Instalação de equipamentos
- RF07 – Remoção de equipamentos
- RF08 – Substituição de equipamentos
- RF09 – Atualização de equipamentos
- RF10 – Regra de adaptação da fonte
- RF11 – Inventário automático
- RF12 – Vistorias
- RF13 – Histórico de vistoria
- RF14 – Logs de auditoria
- RF15 – Serial automático
- RF16 – Validação de catálogo
- RF17 – Site não ativo

### **🟦 15. Requisitos Não Funcionais (RNF)**

(lista completa com Guided Links)

- RNF01 – Segurança
- RNF02 – Integridade dos dados
 - RNF03 – Escalabilidade
- RNF04 – Disponibilidade
 - RNF05 – Padrão REST
- RNF06 – Auditoria completa
- RNF07 – Independência do frontend
- RNF08 – Performance adequada
- RNF09 – Tolerância a falhas

---

### **🟦 16. Fluxos Operacionais**
#### Fluxo do Técnico

- Login
- Buscar site
- Criar vistoria
 - Instalar/remover/substituir equipamentos
 - Finalizar vistoria
 - Inventário atualizado automaticamente

#### Fluxo do Administrador

- Criar usuários
- Criar técnicos
- Criar catálogo
- Atualizar catálogo
- Consultar logs

### **🟦 16. Licença**

O projeto utiliza a Licença MIT.