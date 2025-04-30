# Votação


Este projeto implementa uma API REST para gerenciar sessões de votação em cooperativas. A API permite criar pautas, abrir sessões de votação, registrar votos e contabilizar resultados. O sistema também valida os CPFs dos associados para garantir que eles podem votar.


- Cadastrar uma nova pauta
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por
  um tempo determinado na chamada de abertura ou 1 minuto por default)
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado
  é identificado por um id único e pode votar apenas uma vez por pauta)
- Contabilizar os votos e dar o resultado da votação na pauta

Tecnologias

•	Java 17

•	Maven

•	Spring Boot 3.4.4

•	Spring Data JPA

•	H2 Database (em memória)

•	Swagger/OpenAPI

•	JUnit 5 (Testes)

•	Mockito (Mocking)

•	Spring Boot Starter Web

O foco dessa avaliação é a comunicação entre o backend e o aplicativo mobile. Essa comunicação é feita através de mensagens no formato JSON, onde essas mensagens serão interpretadas pelo cliente para montar as telas onde o usuário vai interagir com o sistema. 

## Como executar o Projeto

1. Clone o Respositorio para o seu ambiente local
2. Configurar o Ambiente
   O projeto usa o H2 como banco de dados em memória. Não há necessidade de configurar um banco externo. Apenas certifique-se de ter o Java 17 instalado em sua máquina.
3.	Rodar a Aplicação
      Execute o comando abaixo para rodar a aplicação: A aplicação estará disponível no http://localhost:8080.
4.	Acessar a Swagger UI
      Após rodar a aplicação, você pode acessar a documentação da API em:
      •	Swagger UI: http://localhost:8080/swagger-ui.html
5.	Acessar o Console do H2
      Você pode acessar o banco de dados em memória H2 através da interface web no seguinte URL:
      •	H2 Console: http://localhost:8080/h2-console
      Use as seguintes credenciais para acessar o banco:
      •	URL: jdbc:h2:mem:votacaodb
      •	Usuário: sa
      •	Senha: sa

### Estrutura de Pacotes

•	com.example.votacao: Pacote principal com as classes de serviço, controller, e repositórios.

•	controller: Camada de controle da aplicação (APIs REST).

•	entity: Entidades JPA que representam os objetos de negócio (Pauta, Sessao, Voto).

•	repository: Repositórios JPA para persistência.

•	service: Lógica de negócios para as operações.

•	exception: Tratamento de exceções global.

•	client: Cliente para validação de CPF.

•	config: Configurações da aplicação, incluindo Swagger.


### Endpoints

1. Criar Pauta

POST /api/v1/pautas

{
"titulo": "Pauta 1",
"descricao": "Descrição da pauta"
}

Abrir Sessão de Votação
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

Votação
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

Contabilizar Votos

GET /api/v1/voto/resultado/{pautaId}
{
    "pautaId" : "1"
}

## Testes

Os testes estão localizados no diretório src/test/java.


Os testes cobrem as principais funcionalidades da aplicação, incluindo:

•	Cadastro de pautas

•	Abertura de sessões de votação

•	Registro de votos

•	Validação de CPF

## Desafios e Tarefas Bônus
•	Integração com sistemas externos: A validação de CPF é realizada atráves de modo aleatorio que retorna se o CPF pode ou não votar.

•	Performance: A aplicação foi projetada para lidar com um grande volume de votos.

•	Versionamento da API: Para futuras versões, o versionamento da API pode ser feito por URL (/api/v1/votos).
