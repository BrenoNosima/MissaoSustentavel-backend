# Sistema de Ações Sustentáveis - AEP 2025

## 👥 Informações da Equipe
- **Disciplinas**: Engenharia de Software, Banco de Dados II, Programação Web
- **Professor**: [Nome do Professor]
- **Turma**: 4º Semestre - Engenharia de Software
- **Integrantes**:
  - Breno Bertaglia Nosima (RA: 24113673-2)
  - [Nome do Aluno 2] (RA: XXXXX)
  - [Nome do Aluno 3] (RA: XXXXX)

## 📝 Sobre o Projeto
Desenvolvemos uma API em Spring Boot para registrar e gamificar ações sustentáveis. Os usuários podem:
- Criar uma conta no sistema
- Registrar ações sustentáveis que fizeram
- Ganhar pontos por suas ações
- Ver o ranking dos usuários mais ativos
- Validar ações de outros usuários

### Por que Spring Boot?
Escolhemos o Spring Boot porque:
- É a tecnologia que estamos aprendendo em Programação Web
- Facilita a criação de APIs REST
- Tem integração fácil com banco de dados
- É muito usado no mercado de trabalho

## 🛠️ Tecnologias Usadas
- Java 17 (linguagem principal)
- Spring Boot 3.1.5 (framework web)
- H2 Database (banco de dados)
- Maven (gerenciador de dependências)
- Postman (para testar a API)

## 💻 Como Executar o Projeto

### Requisitos no Computador
- Java 17 ou superior
- Maven instalado
- Postman (para testar)

### Passo a Passo
1. Copie o projeto:
```bash
git clone https://github.com/[seu-usuario]/missao-sustentavel-api
```

2. Entre na pasta:
```bash
cd missao-sustentavel-api
```

3. Rode o projeto:
```bash
mvn spring-boot:run
```

4. Pronto! A API está rodando em: http://localhost:8080

### Como Acessar o Banco de Dados
1. Abra o console H2: http://localhost:8080/h2-console
2. Configure:
   - URL: jdbc:h2:file:./data/missaosustentavel
   - Usuário: sa
   - Senha: password

## 🌐 Endpoints da API

### Usuários
Endpoints para gerenciar os usuários:

- **GET** `/api/usuarios` - Lista todos
- **GET** `/api/usuarios/{id}` - Busca um
- **POST** `/api/usuarios` - Cadastra novo
  ```json
  {
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "123456"
  }
  ```
- **POST** `/api/usuarios/login` - Faz login
  ```json
  {
    "email": "joao@email.com",
    "senha": "123456"
  }
  ```
- **GET** `/api/usuarios/ranking` - Mostra ranking

### Ações Sustentáveis
Endpoints para registrar e validar ações:

- **GET** `/api/acoes` - Lista todas
- **POST** `/api/acoes` - Registra nova
  ```json
  {
    "usuarioId": 1,
    "titulo": "Reciclagem",
    "descricao": "Reciclei 5kg de plástico",
    "pontos": 10
  }
  ```
- **PUT** `/api/acoes/{id}/validar` - Valida uma ação

## 📂 Como o Projeto Está Organizado
```
src/main/java/com/missaosustentavel/api/
├── config/          # Configurações básicas
├── controller/      # Endpoints da API
├── model/          # Classes do banco
├── repository/     # Acesso ao banco
└── service/        # Regras de negócio
```

## 📚 O Que Aprendemos
- Como criar uma API REST com Spring
- Usar banco de dados com JPA
- Fazer CRUD completo
- Relacionamentos entre tabelas
- Padrão MVC na prática
- Trabalhar em equipe com Git

## 📱 Prints do Sistema
[Colocar aqui 3-4 prints do Postman mostrando as requisições funcionando]


## 📋 Melhorias Futuras
- [ ] Fazer uma interface web bonita
- [ ] Permitir upload de fotos das ações
- [ ] Adicionar um sistema de níveis
- [ ] Mandar email quando ação for validada
- [ ] Adicionar mais tipos de ações sustentáveis

