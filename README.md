# Projeto_Phone_List

API de Agenda de Contatos com Autenticação JWT
Visão Geral
Este projeto implementa uma API RESTful para gerenciamento de contatos pessoais com autenticação segura usando JWT (JSON Web Tokens). A API permite:

Cadastro e autenticação de usuários

CRUD completo de contatos

Proteção de endpoints com JWT

Validação de dados e segurança básica

Configuração
Banco de Dados
Utiliza SQLite como banco de dados embutido

Configuração automática do banco na primeira execução

Popula dados iniciais para testes:

Um usuário admin padrão (admin@example.com / Admin123)

Um contato de exemplo

Autenticação JWT
Configuração do middleware de autenticação

Geração de tokens com validade configurável

Validação automática de tokens em requisições

Estrutura do Projeto
Principais Componentes
Models:

Usuario: Modelo para usuários do sistema

Contato: Modelo para contatos da agenda

LoginRequest: DTO para requisições de login

Controllers:

AuthController: Endpoints para registro e login

ContatosController: CRUD de contatos (protegido por autenticação)

Services:

AuthService: Lógica de geração de tokens JWT

Data:

AgendaContext: Configuração do Entity Framework Core

Como Executar
Instale as dependências:

bash
dotnet restore
Execute a aplicação:

bash
dotnet run
Acesse a documentação da API (em desenvolvimento):

text
http://localhost:<port>/swagger
Endpoints Principais
Autenticação
POST /api/auth/register: Registrar novo usuário

POST /api/auth/login: Autenticar e obter token JWT

Contatos (requer autenticação)
GET /api/contatos: Listar todos os contatos do usuário

POST /api/contatos: Criar novo contato

GET /api/contatos/{id}: Obter contato específico

PUT /api/contatos/{id}: Atualizar contato

DELETE /api/contatos/{id}: Remover contato

Segurança
Todas as senhas são armazenadas como hash usando BCrypt

Endpoints protegidos por autenticação JWT

Validação de dados de entrada

Configuração básica de CORS (em desenvolvimento)

Testes
A API inclui um endpoint de exemplo para testes:

text
GET /weatherforecast
New chat
