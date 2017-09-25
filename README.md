# Banco Distribuído

Projeto para a disciplina de Sistemas Distribuídos.


## Arquitetura

Para este projeto, foi escolhido uma arquitetura **Serverless**, baseado no **AWS Lambda** e implementado em **Java**. Para o banco de dados, foi utilizado o **DynamoDB**.


### Serverless

Basicamente, uma arquitetura Serverless refere-se à aplicações onde o servidor é gerenciado por um provedor de serviços cloud. Em outras palavras, um modelo onde você não precisa se preocupar com o servidor. Isto torna a aplicação altamente escalável e por isto foi selecionado esta arquitetura.


### AWS Lambda

Com isso em mente, a escolha mais popular para este tipo de aplição é o AWS Lambda, onde você consegue definir funções e criar microserviços e endpoints ao redor delas.

O *Lambda* está disponível em diversas lingaguens, e a escolha foi Java por restrições do projeto e familiaridade. 

No Lambda, as instâncias das máquinas são inicializadas quando há um request para o endpoint e o webservice pode rodar em diversas máquinas de modo paralelo e distribuído e isso faz com que a idéia de distribuição do projeto fique mais clara.

O processo todo leva milissegundos independente da linguagem, mas a idéia era fazer um sistema que fosse o mais rápido e escalável possível.


### DynamoDB

DynamoDB é um banco de dados NoSQL rápido e flexível de alta escalabilidade. Ele foi escolhido por suas opções de *lock*, que ajudariam a demonstrar de modo simples as necessidades do projeto.


## Projeto

### Estrutura

A estrutura interna do projeto segue o modelo de um projeto AWS Lambda no Eclipse de acordo com o AWS Toolkit.

O projeto é dividido em dois pacotes principais, **Function** e **Model**.

- **Function**: Contém as classes com as respectivas funções que são executadas pelo Lambda.

- **Model**: Subdividida em **Input**, **Output** e **Lock**. Onde **Input** e **Output** são apenas os modelos para serem passados e retornados pelas funções, e **Lock** cuida de toda a lógica de *lock* e *unlock* da aplicação.


### Como rodar

1. Instale o Eclipse;
2. Instale o [AWS Toolkit for Eclipse](https://aws.amazon.com/pt/eclipse/);
3. Importe o projeto para o Eclipse;
4. Na AWS, crie uma tabela no DynamoDB com os nomes de acordo com os valores da classe **Commons** (ou atualize os valores nela).
5. Clique com o botão direito no projeto, vá em **Amazon Web Services**, e clique em **Upload function to AWS Lambda...**;
6. Depois, no mesmo caminho, clique em **Run function on AWS Lambda...**, e irá abrir um pop up para inserir o JSON de entrada. Basta escrever o input, no formato JSON, de acordo com os modelos do pacote *model.input*.

E.g.: Para **GetSaldo**
```JSON
{
  "id" : 1
}
```


### Log

Todo o monitoramento e histórico de logs é feito e mantido usando o Cloudwatch.


## Leia mais

- [AWS Lambda](https://aws.amazon.com/pt/lambda/)
- [Amazon DynamoDB](https://aws.amazon.com/pt/dynamodb/)
- [Serverless Architectures](https://martinfowler.com/articles/serverless.html)
- [Amazon DynamoDB Lock Client](https://github.com/awslabs/dynamodb-lock-client)
- [AWS Toolkit for Eclipse](https://aws.amazon.com/pt/eclipse/)


## LICENSE
```
The MIT License (MIT)

Copyright (c) 2017 Victor Santiago

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```