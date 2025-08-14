# Agenda API - Backend de Gerenciamento de Contatos

![GitHub](https://img.shields.io/badge/.NET-7.0-blue)
![GitHub](https://img.shields.io/badge/JWT-Authentication-yellow)
![GitHub](https://img.shields.io/badge/SQLite-Database-green)

API completa para gerenciamento de contatos pessoais com autenticação segura via JWT.

## 📋 Funcionalidades

- ✅ Autenticação segura com JWT
- ✅ CRUD completo de contatos
- ✅ Validação de dados
- ✅ Banco de dados SQLite integrado
- ✅ Seed automático de dados iniciais
- ✅ Documentação Swagger (em desenvolvimento)


## Estrutura do Código Android

mobile/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/agenda/
│   │   │   ├── di/           # Injeção de dependência
│   │   │   ├── model/        # Modelos de dados
│   │   │   ├── network/      # Configuração da API
│   │   │   ├── repository/   # Lógica de dados
│   │   │   ├── ui/           # Componentes de UI
│   │   │   │   ├── auth/     # Telas de autenticação
│   │   │   │   ├── contacts/ # Telas de contatos
│   │   │   │   └── components # Componentes compartilhados
│   │   │   └── utils/        # Utilitários
│   │   └── res/              # Recursos do app


## 🛠️ Tecnologias Utilizadas

- .NET 7
- Entity Framework Core
- JWT Authentication
- SQLite
- Swagger/OpenAPI
- Android
## 🔧 Configuração do Ambiente

1. **Pré-requisitos**:
   - [.NET 7 SDK](https://dotnet.microsoft.com/download)
   - Visual Studio Code ou Visual Studio (opcional)

2. **Instalação**:
   ```bash
   git clone https://github.com/seu-usuario/AgendaAPI.git
   cd AgendaAPI
   dotnet restore
