# 📅 Sistema de Gerenciamento de Eventos E Participantes

Bem-vindo ao sistema definitivo de gestão de eventos, desenvolvido com **Java 21 + Spring Boot**!

Aqui você pode criar eventos épicos, cadastrar participantes e gerenciar inscrições com controle de vagas. O projeto foi desenvolvido por uma equipe colaborativa, com foco em arquitetura limpa, validações e divisão de responsabilidades.

---

### 📌 Eventos
- Criar evento com: nome, descrição, data, local e número de vagas
- Listar eventos disponíveis
- Atualizar dados de um evento
- Excluir evento

### 🙋 Participantes
- Cadastro de participante com nome, e-mail e telefone
- Inscrição em evento (com verificação de vagas disponíveis)
- Cancelamento de inscrição
- Listar participantes de um evento

### 🔗 Relacionamentos
- Um evento pode conter vários participantes
- Um participante pode se inscrever em vários eventos

---

## 🧱 Stack Utilizada
- **Java 21**
- **Spring Boot**
- **Maven**
- **JPA**
- **MySQL**
- **Postman** (para testes manuais)
- **Git + GitHub**
- **IDE:** Eclipse

---

## 📁 Estrutura de Pastas

```
src/
└── main/
    ├── java/
    │   └── com/example/evento/
    │       ├── dtos/
    │       │   ├── EventoDTO.java
    │       │   └── ParticipantesDTO.java
    │       ├── entities/
    │       │   ├── Evento.java
    │       │   └── Participantes.java
    │       ├── repositories/
    │       │   ├── EventoRepository.java
    │       │   └── ParticipantesRepository.java
    │       ├── services/
    │       │   ├── EventoService.java
    │       │   └── ParticipantesService.java
    │       └── EventoApplication.java
    └── resources/
        └── application.properties

```

---

## 🚀 Como Rodar o Projeto

```bash
# Clone o repositório
git clone https://github.com/VitorAguiiar/api-gestao-de-eventos.git

# Acesse a pasta
cd evento

# Execute no Eclipse como aplicação Spring Boot
```

O backend estará disponível em:  
📍 `http://localhost:8080`

---

## 🌐 Endpoints RESTful

### 🎯 Eventos

| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | /eventos | Criar novo evento |
| GET    | /eventos | Listar todos os eventos |
| PUT    | /eventos/{id} | Atualizar evento |
| DELETE | /eventos/{id} | Remover evento |
| POST   | /eventos/{eventoId}/inscrever/{participanteId} | Inscrever participante |
| POST   | /eventos/{eventoId}/cancelar/{participanteId} | Cancelar inscrição |
| GET    | /eventos/{eventoId}/participantes | Ver todos os participantes do evento |

### 🙋 Participantes

| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | /participantes | Cadastrar novo participante |
| GET    | /participantes | Listar todos os participantes |

---

## 🔐 Validações

- Não é possível ultrapassar o número de vagas disponíveis
- Cancelamento devolve automaticamente a vaga
- Um participante não pode se inscrever duas vezes no mesmo evento

---

## 👥 Equipe de Desenvolvimento

| Nome | Responsabilidade |
|------|-------------------|
| Millena | Configuração do projeto, entidades e relacionamentos |
| Isaac | Repository, DTO e Service, incluindo lógica de inscrição e controle de vagas |
| Paulo | Controllers e testes com Postman |
| Vitor | Organização do GitHub, documentação e acompanhamento de commits |

---

## 📈 Git & Colaboração

- Commits organizados e descritivos
- Branches por funcionalidade
- Acompanhamento contínuo via GitHub
- README completo com instruções claras

---
