# Kanban API

Este é um projeto de API para um sistema Kanban, desenvolvido com Spring Boot. A API permite gerenciar tarefas, colunas e usuários em um quadro Kanban.

## Pré-requisitos

Antes de começar, certifique-se de que você tem os seguintes softwares instalados:

- **Java 21**: Baixe e instale o JDK 21 do [site oficial da Oracle](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) ou use uma distribuição como OpenJDK.
- **Maven**: Instale o Apache Maven. Você pode baixar em [maven.apache.org](https://maven.apache.org/download.cgi). Alternativamente, use o wrapper Maven incluído no projeto (`mvnw` ou `mvnw.cmd`).
- **Docker e Docker Compose**: Necessários para executar o banco de dados PostgreSQL. Baixe em [docker.com](https://www.docker.com/get-started).

## Instalação e Configuração

### 1. Clonar o Repositório

Clone este repositório para sua máquina local:

```bash
git clone <URL_DO_REPOSITORIO>
cd kanban-api
```

Substitua `<URL_DO_REPOSITORIO>` pela URL real do repositório (por exemplo, no GitHub ou GitLab).

### 2. Configurar o Banco de Dados

O projeto usa PostgreSQL como banco de dados. Use o Docker Compose para iniciar o banco de dados e o PgAdmin (ferramenta de administração do banco):

```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL na porta 5432
- PgAdmin na porta 5050 (acesse via navegador em `http://localhost:5050` com email: `admin@kanban.com` e senha: `admin`)

### 3. Executar a Aplicação

Para executar a aplicação Spring Boot:

- Usando Maven instalado:
  ```bash
  mvn spring-boot:run
  ```

- Ou usando o wrapper Maven (recomendado):
  ```bash
  ./mvnw spring-boot:run
  ```
  (No Windows: `mvnw.cmd spring-boot:run`)

A aplicação será iniciada na porta 8080. Você pode acessar a API em `http://localhost:8080`.

## Estrutura do Projeto

- `src/main/java/com/devdogstudio/kanban_api/`: Código fonte da aplicação
  - `controller/`: Controladores REST
  - `entity/`: Entidades JPA
  - `repository/`: Repositórios de dados
  - `service/`: Lógica de negócio
  - `security/`: Configurações de segurança
  - `config/`: Outras configurações
- `src/main/resources/`: Recursos estáticos e configurações
- `src/test/`: Testes unitários e de integração

## Configurações

As configurações principais estão em `src/main/resources/application.properties`:

- Banco de dados: PostgreSQL local
- Porta do servidor: 8080
- JPA: Hibernate com DDL auto-update

## Testes

Para executar os testes:

```bash
mvn test
```

Ou com wrapper:

```bash
./mvnw test
```

## Documentação Adicional

Para mais informações sobre Spring Boot e as tecnologias usadas, consulte:

- [Documentação do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)

Se você tiver dúvidas ou problemas, entre em contato com o desenvolvedor principal.
