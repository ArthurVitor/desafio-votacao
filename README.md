# Documentação da Aplicação de Votação Cooperativista

## Objetivo

A aplicação tem como objetivo simular e gerenciar sessões de votação em um sistema de cooperativas. Cada associado pode votar em uma pauta específica, sendo que cada voto é registrado como "Sim" ou "Não". As funcionalidades incluem o cadastro de novas pautas, a abertura de sessões de votação, o recebimento de votos e a contabilização do resultado.

### Funcionalidades da API REST:
- **Cadastrar uma nova pauta**
- **Abrir uma sessão de votação em uma pauta**
- **Receber votos dos associados** (os votos são "Sim" ou "Não", e cada associado pode votar apenas uma vez por pauta)
- **Contabilizar os votos e apresentar o resultado da votação**

A aplicação deve ser executada na nuvem, com persistência dos dados para que os votos e pautas não se percam durante reinicializações.

## Tecnologias Utilizadas
- **Spring Boot** (Java 21)
- **Maven 3.8.6**
- **PostgreSQL 17**
- **Docker (opcional)**

## Como Rodar a Aplicação

### Opção 1: Usando Maven

1. **Limpeza e compilação do projeto**:
   Execute o seguinte comando para limpar e compilar o projeto:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Opção 2: Usando Docker

1. **Subir os containers com Docker Compose**:
  Execute o seguinte comando para construir e subir os containers:

   ```bash
   docker-compose up --build
   ```

## Informações Adicionais

A aplicação utiliza **Spring Boot** como framework para o backend, com **Maven 3.8.6** como ferramenta de build e **Java 21** como linguagem de programação. O banco de dados utilizado é o **PostgreSQL 18**, que pode ser configurado e acessado diretamente através do Docker, caso deseje usar o `docker-compose` para facilitar o setup.
