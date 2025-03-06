# PEDIDOS API

# pedidos-api:


Aplicação para criação e controle de pedidos.

## Tecnologias:
- Spring Boot
- Java 17
- Docker
- Lombok
- H2 

---

## Como Rodar:
- Rodar a classe Main
- Na pasta "postman" importar a collection no Postman 
- Rodar primeiramente o método post, para que os outros 2 gets tragam resultados
- Caso não queira utilizar o post, rodar o método post passando id 1 para cliente e produto, pois fiz esse pré-cadastro no arquivo 
"data.sql" e há validação de cliente e produto existentes.

## Observação:
- Eu criei apenas 1 teste unitário e 1 integrado para cada método do Controller, apenas para mostrar meus 
conhecimentos em testes. Não quis me prolongar com testes detalhados, pois já gastei muitas horas construindo essa API e fazendo
funcionar os testes, e acredito que o intuito seja ver se a pessoa sabe programar bem.
- Não quis me prolongar também em muitas validações de entrada.
- A forma como modelei a base não condiz exatamente com o Json de entrada, na minha opinião não está correto o Json, pois informações
de valor devem estar na tabela "produtos", e não na tabela "item", sendo assim, seria necessário passar somente o id do produto na entrada, 
pois na tabela produto já constaria essa informação de preço.
Além disso, não ficou claro qual seria a validação para pedido duplicado, então verifiquei apenas por id.


