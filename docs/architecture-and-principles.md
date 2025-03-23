## 🏛️ **Arquitetura**

A aplicação segue a **Clean Architecture**, com as seguintes camadas:

1. **Camada de Apresentação**:
    - Expõe os endpoints da API.
    - Responsável por receber requisições HTTP e retornar respostas.

2. **Camada de Aplicação**:
    - Orquestra os casos de uso.

3. **Camada de Domínio**:
    - Contém as entidades e regras de negócio.

4. **Camada de Infraestrutura**:
    - Lida com detalhes externos, como banco de dados e APIs externas.

---

## 🧠 **Abstração, Acoplamento, Extensibilidade e Coesão**

### **Abstração**
- Uso de interfaces para esconder detalhes de implementação.

### **Baixo Acoplamento**
- Injeção de dependência e uso de interfaces.

### **Extensibilidade**
- Facilidade de adicionar novas funcionalidades.

### **Alta Coesão**
- Cada classe e método tem uma responsabilidade clara.

---

## 🛠️ **Design Patterns**

A aplicação utiliza os seguintes padrões de projeto:

1. **Repository Pattern**:
    - `PetRepository` e `LocationRepository` para acesso ao banco de dados.

2. **Adapter Pattern**:
    - `PositionStackClient` adapta a API externa para o domínio da aplicação.

3. **Singleton Pattern**:
    - Beans gerenciados pelo Spring (`@Service`, `@Repository`).

4. **Strategy Pattern**:
    - Tratamento de erros com `PositionStackError` e `GlobalExceptionHandler`.

5. **Observer Pattern**:
    - Atualização de métricas (`PetLocationMetrics`).

---

## 🧹 **Clean Architecture**

A aplicação adere aos princípios da **Clean Architecture**:
- **Testabilidade**: Facilita a criação de testes unitários e de integração.
- **Manutenibilidade**: Mudanças em uma camada não afetam as outras.

---

## ✨ **Clean Code**

O código da aplicação segue boas práticas de **Clean Code**:
- Nomes Significativos
- Funções Pequenas e Específicas**: 
- Código autoexplicativo.
---

## 🔧 **SOLID**

A aplicação segue os princípios **SOLID**:

1. **Single Responsibility Principle (SRP)**:
    - Cada classe tem uma única responsabilidade.
    - `PetLocationService` gerencia localizações, `PetRepository` lida com persistência.

2. **Open/Closed Principle (OCP)**:
    - A aplicação é aberta para extensão e fechada para modificação.
    - Adicionar novos provedores de geolocalização sem modificar o núcleo.

3. **Liskov Substitution Principle (LSP)**:
    - Abstrações podem ser substituídas por implementações concretas.
    - `PetRepository` pode ser substituído por qualquer implementação.

4. **Interface Segregation Principle (ISP)**:
    - Interfaces são específicas e não "gordas".
    - `PositionStackClient` contém apenas métodos relacionados à API externa.

5. **Dependency Inversion Principle (DIP)**:
    - Módulos de alto nível dependem de abstrações, não de implementações concretas.
    - `PetLocationService` depende de `PetRepository` e `PositionStackClient`.

---

## 🔍 **Observabilidade**

A aplicação foi projetada com foco em **observabilidade**:

1. **Métricas**:
    - `PetLocationMetrics` com Micrometer para monitorar registros de localização.

2. **Logs**:
    - Logs detalhados em pontos-chave (início e fim do registro de localização, erros na API externa).

3. **Traces**:
    - Estrutura pronta para integração com ferramentas como Jaeger ou Zipkin.


