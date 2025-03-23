# 🔭Testes
---

## 🛠️ **Tecnologias Utilizadas**

### 1. **JUnit 5**
### 2. **Mockito**
### 3. **Spring Boot Test**
### 4. **MockMvc**
### 5. **JaCoCo**
---

### 🧪 **Tipos de Testes Implementados**
Para evitar a redundância entre testes foram implementados diferentes tipos de testes 
para a garantia de qualidade do código

## 1. **Testes de Contrato**:
- Para a validação de entradas inválidas nas coordenadas.
- Tecnologias: **JUnit 5**, **MockMvc**.

## 2. Testes de Integração:
- Para verificar a integração entre diferentes componentes da aplicação, como serviços, repositórios e APIs externas.
Cobre boa parte dos cenários de sucesso na aplicação.
- Tecnologias: JUnit 5, Spring Boot Test.

## 3. Testes Unitários:
- Para verificar o comportamento de trechos específicos do código, nesse caso especialmente cenários de exceção.
- Tecnologias: JUnit 5, Mockito.

Utilize o seguinte comando para executar os testes:
```bash      
    ./gradlew test
```
___
### 📊 JaCoCo
Utilizado no projeto para medir a cobertura de código dos testes e gerar relatórios.

Utilize o seguinte comando para gerar relatórios:
```bash
./gradlew test jacocoTestReport
```