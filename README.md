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

