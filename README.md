# Introdução 
Este projeto consiste na função de cálculo de previsão de embarque para execução no serviço Lambda da Amazon AWS.


# Dependências
As dependências são gerenciados pela ferramenta maven.


# Veriáveis de embiente
Os dados de conexão com o banco de dados devem ser providos via variáveis de ambiente. São elas:

--> DB_URL: url no formato JDBC de conexão com o banco de dados.

--> DB_USER: username da conexão.

--> DB_PASS: password da conexão.


# Empacotamento
O projeto é empacotado, para posteriormente ser feito o upload na infraestrutura, com:

----> mvn package shade:shade

Será gerado um arquivo .jar em ./target/