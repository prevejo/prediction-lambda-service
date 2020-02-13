# Introdução 
Este projeto consiste na função de cálculo de previsão de embarque para execução no serviço Lambda da Amazon AWS.


# Dependências
As dependências são gerenciados pela ferramenta maven.


# Veriáveis de embiente
Os dados de conexão com o banco de dados devem ser providos via variáveis de ambiente. São elas:

--> DB_URL: url no formato JDBC de conexão com o banco de dados.

--> DB_USER: username da conexão.

--> DB_PASS: password da conexão.

--> DYNAMO_DB_ACESS_KEY: DynamoDB acesskey.

--> DYNAMO_DB_SECRET_KEY: DynamoDB secrect key.

--> INST_OP_STORE_TYPE: identificação do recurso utilizado para consultar a base histórica - DATA_BASE (consulta por banco relacional) ou DYNAMO_DB(consulta pelo serviço AWS DynamoDB).


# Empacotamento
O projeto é empacotado, para posteriormente ser feito o upload na infraestrutura, com:

----> mvn package shade:shade

Será gerado um arquivo .jar em ./target/
