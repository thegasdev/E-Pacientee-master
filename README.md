ePacient
 
ePacient é uma aplicação desktop desenvolvida como um protótipo para o gerenciamento simplificado de informações de pacientes. O sistema permite que usuários se cadastrem, façam login e acessem funcionalidades como agendamento de consultas, visualização de exames e receitas.

Este projeto foi desenvolvido como parte das atividades acadêmicas para as disciplinas de Programação Orientada a Objetos e Análise e Projeto de Sistemas.

- Funcionalidades Principais
Autenticação de Usuário: Sistema de login e cadastro seguro (protótipo).

Agendamento de Consultas: Interface para marcar e cancelar consultas.

Visualização de Informações: Acesso rápido a resultados de exames, receitas prescritas e informações pessoais do usuário.

Parceiros e Convênios: Lista de farmácias parceiras.

Interface Gráfica Moderna: Desenvolvida com JavaFX e estilizada com CSS.

- Tecnologias Utilizadas
Linguagem: Java

Framework de UI: JavaFX

IDE: IntelliJ IDEA

- Como Executar o Projeto
Para obter uma cópia local e executar o projeto, siga estes passos.

Pré-requisitos
Você precisará ter os seguintes softwares instalados na sua máquina:

Java Development Kit (JDK) - Versão 17 ou superior.

JavaFX SDK - Versão compatível com seu JDK (o projeto foi desenvolvido com a versão 24).

Link para download do JavaFX SDK

IntelliJ IDEA - Community Edition ou Ultimate.

Guia de Instalação e Configuração
Clone o Repositório

Bash

git clone https://github.com/seu-usuario/E-Pacientee-master.git
(Substitua seu-usuario pelo seu nome de usuário no GitHub)

Descompacte o JavaFX SDK

Após o download, descompacte o arquivo .zip do JavaFX SDK em um local de fácil acesso (ex: C:\Java\javafx-sdk-24.0.1).

Abra o Projeto no IntelliJ IDEA

No IntelliJ, vá em "File" -> "Open..." e selecione a pasta do projeto que você clonou.

Configure a Pasta de Recursos

No painel de projeto, clique com o botão direito na pasta resources.

Vá em Mark Directory as -> Resources Root.

Adicione a Biblioteca JavaFX ao Projeto

Vá em File -> Project Structure....

No menu esquerdo, selecione Libraries.

Clique no ícone + e selecione Java.

Navegue até a pasta onde você descompactou o JavaFX SDK e selecione a subpasta lib. Clique em OK.

Adicione a biblioteca ao seu módulo quando solicitado.

Configure a Execução (Run Configuration)

Vá em Run -> Edit Configurations....

Selecione a configuração da sua classe MainApp.

Encontre o campo VM options e adicione a seguinte linha, substituindo C:\CAMINHO\PARA\SEU\JAVAFX\SDK pelo caminho real no seu computador:

--module-path "C:\CAMINHO\PARA\SEU\JAVAFX\SDK\lib" --add-modules javafx.controls,javafx.graphics
Clique em Apply e OK.

Execute a Aplicação

Agora você pode executar a classe MainApp clicando no botão de "Play" (Run).

🏛️ Arquitetura do Projeto
O projeto segue uma arquitetura inspirada no padrão MVC (Model-View-Controller) para promover a separação de responsabilidades e a organização do código.

model/ (Modelo): Contém as classes de dados (User) e a lógica de negócios (UserService). É responsável por manipular os dados da aplicação.

view/ (Visão): Contém as classes responsáveis pela interface gráfica do usuário (GUI), construídas com JavaFX.

controller/ (Controlador): Faz a mediação entre a View e o Model, tratando os eventos gerados pelo usuário na interface e atualizando os dados conforme necessário.

🗺️ Roadmap e Melhorias Futuras
Este projeto é um protótipo com grande potencial de evolução. Algumas melhorias planejadas incluem:

[ ] Persistência de Dados: Substituir o armazenamento em memória (HashMap) por um banco de dados (ex: SQLite, PostgreSQL) para salvar os dados permanentemente.

[ ] Segurança: Implementar hashing de senhas (ex: bcrypt) em vez de armazená-las em texto plano.

[ ] Validação de Entradas: Adicionar validações para os dados de entrada nos formulários (formato de CPF, e-mail, etc.).

[ ] Novas Funcionalidades: Expandir o sistema com módulos de prontuário eletrônico, histórico de consultas, etc.

[ ] Refatoração: Melhorar a navegação entre telas para evitar a criação de múltiplas instâncias e gerenciar o estado da sessão de forma mais robusta.

✒️ Autores
Thiago Rizzo Padilha e Marcos Eduardo
