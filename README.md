
# 🐾 Pet Location 

A **Pet Location** é uma aplicação para registrar e consultar a localização de pets com base em coordenadas fornecidas. 

---

## 🚀 Executando a aplicação

### Pré-requisitos

-   Java 17
-   Docker Compose


### Passos para Execução

1.  **Clone o repositório**:

```bash
 git clone https://github.com/seu-usuario/pet-location.git
 cd pet-location
```

2.  **Suba os containers com Docker**:

```bash
    docker-compose up 
```

3.  **Execute a aplicação**:

```bash
    ./gradlew bootRun
``` 

5.  **Acesse a API via swagger em**:

```
http://localhost:8080/swagger-ui.html/index.html?
```


### 📝 Exemplos de Requisições

Registrar Localização do Pet


```bash
curl -X POST "http://localhost:8080/api/pet-location" \
-H "Content-Type: application/json" \
-d '{
  "sensorId": "sensor123",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "dateTime": "2024-01-01T12:00:00"
}'
```
Obter histórico de localizações do Pet

```bash
curl -X GET "http://localhost:8080/api/pet-location/sensor123/locations"
```
Obter Última Localização do Pet


```bash
curl -X GET "http://localhost:8080/api/pet-location/sensor123/last-location"
```

---

### 🛠️ Tecnologias Utilizadas

- Spring Boot
- JUnit 5
- Mockito
- Lombok
- Feign (OpenFeign)
- Micrometer
- Swagger (SpringDoc OpenAPI)
- JaCoCo
- Gradle 
- Git
- Docker
- Grafana
- Prometheus
- Java 17


### 🏗️ Arquitetura e Princípios de Design

A aplicação segue os princípios alguns princípios de Design Patterns, SOLID e Clean Architecture.
Documentação detalhada [aqui](docs/architecture-and-principles.md).


### 🧪 Testes

A aplicação conta com gestão de relatórios de testes e cobertura, inclui ainda os seguintes tipos de teste:

- Testes de Unidade
- Testes de Integração
- Testes de Contrato

Mais informações sobre os testes da aplicação [aqui](docs/test.md)

### 🔍 Observabilidade

O projeto conta com uma métrica para auxiliar na observabilidade da aplicação, 
utilizando dos container de prometheus e grafana é possível usar do template de dashboard disponibilizado 
[aqui](templates/dashboard_template.json) para visualizar as métricas.

### 🎲 Banco de dados
A aplicação conta com um Banco de dados relacional. Para entender a respeito do relacionamento de tabelas e visualizar 
diagramas clique [aqui](docs/database.md).

---

Esse projeto se trata do desenvolvimento de um case para o processo seletivo do Banco Itaú.

---

**Em caso de dúvidas verifique o [video demonstrativo]() e [collection base](src/main/resources/templates/PetLocation.postman_collection.json)**
<br>
[Links disponiveis na aplicação](docs/sumary-links.md) Att Dan

