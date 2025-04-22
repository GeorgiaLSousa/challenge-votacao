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

•	Spring Boot 2.6.5

•	Spring Data JPA

•	H2 Database (em memória)

•	Swagger/OpenAPI

•	JUnit 5 (Testes)

•	Mockito (Mocking)

•	Spring Boot Starter Web

O foco dessa avaliação é a comunicação entre o backend e o aplicativo mobile. Essa comunicação é feita através de mensagens no formato JSON, onde essas mensagens serão interpretadas pelo cliente para montar as telas onde o usuário vai interagir com o sistema. A aplicação cliente não faz parte da avaliação, apenas os componentes do servidor. O formato padrão dessas mensagens será detalhado no anexo 1.

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
•	client: Cliente para validação de CPF (integração fake).
•	config: Configurações da aplicação, incluindo Swagger.

### Tarefa Bônus 2 - Performance

- Imagine que sua aplicação possa ser usada em cenários que existam centenas de
  milhares de votos. Ela deve se comportar de maneira performática nesses
  cenários
- Testes de performance são uma boa maneira de garantir e observar como sua
  aplicação se comporta

### Endpoints

1. Criar Pauta

POST /pautas
{
"titulo": "Pauta 1",
"descricao": "Descrição da pauta"
}

Abrir Sessão de Votação
POST /sessoes/{pautaId}

Votação
POST /votos/{pautaId}
{
"cpf": "12345678900",
"voto": "SIM"
}

Contabilizar Votos

GET /voto/resultado

○ Como você versionaria a API da sua aplicação? Que estratégia usar?

## Testes

Os testes estão localizados no diretório src/test/java.
./mvnw test

Os testes cobrem as principais funcionalidades da aplicação, incluindo:
•	Cadastro de pautas
•	Abertura de sessões de votação
•	Registro de votos
•	Validação de CPF

## Desafios e Tarefas Bônus
•	Integração com sistemas externos: A validação de CPF é realizada atráves de modo aleatorio que retorna se o CPF pode ou não votar.
•	Performance: A aplicação foi projetada para lidar com um grande volume de votos.
•	Versionamento da API: Para futuras versões, o versionamento da API pode ser feito por URL (/api/v1/votos).
