## 🔍 Fluxos e cenários

A aplicação conta com uma integração com a API PositionStack para a interpretação/tradução de coordenadas. 
Diante disso, alguns cenários precisam ser levados em consideração. Abaixo seguem alguns cenários encontrado pela equipe
(vulgo Dan 😁) de desenvolvimento.

---

### 1. Cenário típico

Ao realizar a requisição: **/api/pet-location**
<br>
Enviando um body com as seguintes coordenadas:
```
{
  "sensorId": "sensor123",
  "latitude": -23.5568,
  "longitude": -46.6538,
  "dateTime": "2025-01-01T12:00:00"
}
```
Você deve receber a seguinte resposta:
```
{
    "country": "Brazil",
    "state": "Sao Paulo",
    "city": "São Paulo",
    "neighborhood": "Consolação",
    "address": "Avenida Barata Ribeiro 339, São Paulo, Brazil"
}
```
Todos os campos são preenchidos corretamente.

### 2. Cenário com campos parcialmente não preenchidos

Ao realizar a requisição: **/api/pet-location**
<br>
Enviando um body com as seguintes coordenadas:
```
{
  "sensorId": "sensor123",
  "latitude": -22.6275,
  "longitude": -46.6323,
  "dateTime": "2023-10-01T12:00:00"
}
```
Você deve receber a seguinte resposta:
```
{
    "country": "Brazil",
    "state": "Sao Paulo",
    "city": null,
    "neighborhood": null,
    "address": "Sitio Santa Cruz 3, SP, Brazil"
}
```
Onde os campos cidade e bairro são apresentados com null.

### 3. Cenário com campos maioritariamente não preenchidos.

Ao realizar a requisição: **/api/pet-location**
<br>
Enviando um body com as seguintes coordenadas:
```
{
  "sensorId": "sensor123",
  "latitude": -22.6275,
  "longitude": -88.6323,
  "dateTime": "2023-10-01T12:00:00"
}
```
Você deve receber a seguinte resposta:
```
{
    "country": null,
    "state": null,
    "city": null,
    "neighborhood": null,
    "address": "South Pacific Ocean"
}
```
Onde os pais, estado, cidade e bairro são apresentados com null.

---
👀**Obs**: Os cenários 2 e 3 podem ocorrer tanto por má mapeamento, quanto por inexistência do campo para as coordenadas
fornecidas. Devido aos cenários atípicos, alguns campos precisam ser sempre tratados como nullable na aplicação.