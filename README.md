# Sistema de Gerenciamento de Usuários e Cálculo de Impostos NFE

Este projeto é um sistema de gerenciamento de usuários e um serviço que calcula impostos para Notas Fiscais Eletrônicas (NFEs). Foi desenvolvido usando Java, Spring Boot e Gradle.

## Sobre o Projeto

O projeto consiste em duas partes principais:

1. **Sistema de Gerenciamento de Usuários**: Este módulo permite a criação, leitura, atualização e exclusão de usuários. Ele também verifica se um CPF já está registrado antes de salvar ou atualizar um usuário.

2. **Serviço de Cálculo de Impostos NFE**: Este módulo calcula impostos para NFEs. Ele integra com uma API externa para obter a taxa Selic e calcula o valor dos impostos com base no valor da NFE e na taxa de imposto.

## Tecnologias Utilizadas

- Java (Gradle)
- Spring Boot
- PostgresSQL
- Docker
- IntelliJ IDEA

## Como Executar o Projeto

1. Clone o repositório para a sua máquina local.
2. Abra o projeto no IntelliJ IDEA.
3. Execute o comando `gradle bootRun` no terminal para iniciar a aplicação.

## Contribuições

Contribuições são bem-vindas. Para contribuir, por favor, abra um pull request.

## Contato

Você pode me encontrar no [LinkedIn](https://www.linkedin.com/in/vitortinelli/) ou me enviar um e-mail para (vitor.mtinelli@gmail.com).
