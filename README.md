# PiNotifier - Minecraft Plugin
![PiNotifier Logo](docs/logo.png)
## Descrição
**PiNotifier** é um plugin para Minecraft projetado para transformar servidores em uma rede social de notificações.
O objetivo do **PiNotifier** é notificar os jogadores quando seus amigos entrarem no servidor.
As notificações podem ser enviadas por várias plataformas configuráveis, incluindo WhatsApp, SMS, Discord e notificações push.

## Funcionalidades
- **Lista de Amigos**: Permite aos jogadores adicionar outros jogadores à sua lista de amigos.
- **Notificações em Tempo Real**: Notifica os jogadores quando um amigo entra no servidor.
- **Plataformas de Notificação Configuráveis**: Os jogadores podem configurar a plataforma de notificação de sua preferência, como WhatsApp, SMS, Discord e notificações push.

## Requisitos
- Servidor Minecraft versão 1.16 ou superior.
- Java 8 ou superior.
- Conexão com a internet para integração com plataformas de notificação externas.

## Dependências
- [Spigot API](https://www.spigotmc.org/) - API para desenvolvimento de plugins Minecraft.
- [Twilio API](https://www.twilio.com/docs/usage/api) - API para envio de mensagens SMS e WhatsApp.

## Instalação
- Baixe o plugin PiNotifier.jar da página de releases.
- Coloque o arquivo PiNotifier.jar na pasta plugins do seu servidor Minecraft.
- Reinicie o servidor Minecraft para carregar o plugin.
- Configure o arquivo config.yml na pasta plugins/PiNotifier conforme suas necessidades.

# Princípios de Design
### SOLID
- Single Responsibility Principle (SRP): Cada classe no PiNotifier tem uma única responsabilidade, como gerenciar amigos, enviar notificações ou integrar-se com uma plataforma de notificação específica.
- Open/Closed Principle (OCP): O sistema é aberto para extensão, mas fechado para modificação. Novas plataformas de notificação podem ser adicionadas implementando interfaces específicas sem alterar o código existente.
- Liskov Substitution Principle (LSP): As classes derivadas podem ser substituídas por suas classes base sem alterar a funcionalidade do sistema.
- Interface Segregation Principle (ISP): Interfaces específicas são usadas para cada plataforma de notificação, evitando a implementação de métodos desnecessários.
- Dependency Inversion Principle (DIP): O **PiNotifier** depende de abstrações (interfaces) em vez de classes concretas, facilitando a manutenção e a expansão do código.
## Injeção de Dependência e Inversão de Controle
O **PiNotifier** usa injeção de dependência para fornecer as dependências necessárias às classes. Isso é feito por meio de construtores ou injeção de métodos. Por exemplo:

```java
package org.piegottin.pinotifier.services;

public interface NotificationService {
    void sendNotification(String destination, String message);
}

```
Aqui, a NotificationService depende de uma interface NotificationPlatform, que pode ser qualquer implementação concreta, como WhatsAppPlatform, SMSPlatform, etc. Essa abordagem facilita a substituição de implementações e melhora a testabilidade do código.