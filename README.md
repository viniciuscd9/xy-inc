# xy-inc
Serviço RESTful para busca por CEP ou Endereço no site dos correios.

## Requisitos mínimos.

* *Java 1.8* ou superior.
* *Apache Maven 3.0* ou superior.

## Instruções para compilação e execução.

Para compilar o código execute os comandos dentro do diretório xy-inc da aplicação:

    MAVEN_HOME/bin/mvn install

Para executar o código:

    MAVEN_HOME/bin/mvn jetty:run

Onde MAVEN_HOME é o caminho no qual o maven foi instalado no sistema.

Para parar a execução pressione CTRL+C.

### Execução dos serviços.

Os serviços podem ser chamados por qualquer cliente http através dos seguintes endereços:

* GET `http://hostname:8080/buscar/cep/{logradouro}` para buscar CEPs por logradouro.
* GET `http://hostname:8080/buscar/logradouro/{cep}` para buscar um endereço por CEP.

Onde:

* ***hostname*** é o endereço da máquina no qual o serviço está sendo hospedado (*localhost* caso seja hospedado no mesmo computador no qual está sendo chamado).
* ***{logradouro}*** é o logradouro passado como parâmetro de busca.
* ***{cep}*** é o CEP passado como parâmetro de busca.

### Status de erro e formatos de resposta.
#### Erros:
* Status: 400 - Caso o parâmetro passado para busca seja inválido.
* Status: 404 - Caso a busca não retorne nenhum valor.
* Status: 500 - Caso ocorra erro de conexão ou algum erro imprevisto.

#### Formato de resposta:

* `http://hostname:8080/buscar/cep/{logradouro}`:

Status: 200

Formato:

    [
        {
            "logradouro": "Nome rua, avenida, etc.",
            "bairro": "Nome do bairro",
            "localidade": "Nome da Cidade/Estado",
            "cep": "CEP"
        },
        ...
    ]

* `http://hostname:8080/buscar/logradouro/{cep}`

Status: 200

Formato:

    {
        "logradouro": "Nome rua, avenida, etc.",
        "bairro": "Nome do bairro",
        "localidade": "Nome da Cidade/Estado",
        "cep": "CEP"
    }
