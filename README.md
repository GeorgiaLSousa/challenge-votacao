# Sistema de Votação para Cooperativas

Este projeto implementa uma API REST para gerenciar sessões de votação em cooperativas. A API permite criar pautas, abrir sessões de votação, registrar votos e contabilizar resultados. O sistema valida os CPFs dos associados para garantir que apenas membros elegíveis possam votar.  A comunicação com o aplicativo mobile é um foco central, utilizando mensagens JSON para a interação do usuário.


## Funcionalidades

* **Gestão de Pautas:** Permite criar novas pautas de votação com título e descrição.
* **Gestão de Sessões:** Permite abrir e fechar sessões de votação para pautas específicas, com definição de tempo de duração.
* **Registro de Votos:** Permite aos associados registrar seus votos ("Sim" ou "Não") em pautas abertas. Cada associado pode votar apenas uma vez por pauta.
* **Contabilização de Votos:**  Fornece a contagem dos votos e o resultado da votação para cada pauta.
* **Validação de CPF:**  Integração com um sistema externo (atualmente mockado) para validar o CPF dos votantes e garantir a elegibilidade.

## Tecnologias

* Java 17
* Maven
* Spring Boot 3.4.4
* Spring Data JPA
* H2 Database (em memória)
* Swagger/OpenAPI
* JUnit 5
* Mockito
* Spring Boot Starter Web

## Arquitetura

A aplicação segue uma arquitetura RESTful, com endpoints bem definidos para cada operação. A comunicação com o aplicativo mobile é baseada em JSON. A validação de CPF é realizada através de um serviço externo simulado.

## Instalação e Execução

1. **Clone o Repositório:** `git clone <URL_DO_REPOSITORIO>`
2. **Configure o Ambiente:** Certifique-se de ter o Java 17 instalado. O projeto utiliza o H2 Database em memória, portanto, não é necessária configuração adicional de banco de dados.
3. **Execute a Aplicação:** Navegue até o diretório do projeto e execute o comando `mvn spring-boot:run`. A aplicação estará disponível em `http://localhost:8080`.
4. **Acesse a Documentação da API (Swagger UI):** `http://localhost:8080/swagger-ui.html`
5. **Acesse o Console do H2 (opcional):** `http://localhost:8080/h2-console` (URL: `jdbc:h2:mem:votacaodb`, Usuário: `sa`, Senha: `sa`)


Estrutura do Projeto
com.example.votacao: Pacote raiz.
controller: Controladores REST.
entity: Entidades JPA.
repository: Interfaces de repositório.
service: Camada de serviço.
exception: Tratamento de exceções.
client: Cliente para validação de CPF.
config: Configurações.

### Endpoints

1. Criar Pauta

POST /api/v1/pautas

{
"titulo": "Pauta 1",
"descricao": "Descrição da pauta"
}

2. Abrir Sessão de Votação
   
POST /api/v1/sessoes/abrir

{
  "id": 1,
  "inicio": "2023-10-05T10:00:00",
  "fim": "2023-10-05T10:05:00",
  "pauta": {
    "id": 1,
    "titulo": "Pauta 1",
    "descricao": "Descrição da Pauta"
  }
}

3. Votação
   
POST /api/v1/votos
{
    "cpf": "2234058804",
     "pauta": {
    "id": 2,
    "titulo": "Título da Pauta",
    "descricao": "Descrição da Pauta"
    },
    "voto": "true"
}

4. Contabilizar Votos
   

GET /api/v1/voto/resultado/{pautaId}
{
    "pautaId" : "1"
}

## Testes

Os testes estão localizados no diretório src/test/java.


## Desafios e Tarefas Bônus
•	Integração com sistemas externos: A validação de CPF é realizada atráves de modo aleatorio que retorna se o CPF pode ou não votar.

•	Performance: A aplicação foi projetada para lidar com um grande volume de votos.

•	Versionamento da API: Para futuras versões, o versionamento da API pode ser feito por URL (/api/v1/votos).
