# Simulador de Sistema de Arquivos com Journaling
**AUTOR 1:** Eduardo Jorge Andrade Mourão Oliveira

**AUTOR 2:** Renan Elid Soares

---

## Metodologia

O simulador foi desenvolvido em linguagem de programação **Java**. Ele recebe comandos digitados pelo usuário via terminal (modo shell) e os interpreta como chamadas de métodos que simulam operações de um sistema de arquivos real.

Cada funcionalidade foi implementada como um método específico. Todas as ações que modificam o estado do sistema de arquivos são **registradas em log (journaling)**, permitindo maior segurança e controle de integridade.

---

## Parte 1: Introdução ao Sistema de Arquivos com Journaling

### O que é um Sistema de Arquivos?

Um sistema de arquivos é uma estrutura lógica que permite que o sistema operacional **armazene, organize, acesse e gerencie dados em dispositivos de armazenamento** (HD, SSD, etc.). Ele permite criar diretórios, arquivos, renomeá-los, apagá-los e muito mais.

### Journaling

Journaling é um mecanismo de **registro de operações antes que sejam efetivadas no sistema de arquivos**, garantindo integridade em caso de falhas. Tipos de journaling:

- **Write-Ahead Logging (WAL):** Registra a operação no log antes da execução.
- **Log-Structured:** Estrutura o próprio sistema como um log.
- **Metadata Journaling:** Apenas metadados são registrados.

Neste projeto, usamos o modelo **Write-Ahead Logging**, salvando cada operação de escrita antes da execução no arquivo `journal.log`.

---

## Parte 2: Arquitetura do Simulador

### Estrutura de Dados

Utilizamos classes Java para modelar o sistema:

- `Directory`: representa diretórios, contendo subdiretórios e arquivos.
- `MyFile`: representa arquivos simples com nome e conteúdo.
- `Journal`: classe para registrar operações em um arquivo de log.
- `FileSystemSimulator`: shell interativo principal, onde os comandos são interpretados.

### Implementação do Journaling

Todas as operações como criação, renomeação, remoção e cópia são **registradas no arquivo `journal.log`** antes de serem realizadas, como forma de garantir rastreabilidade e segurança.

---

## Parte 3: Implementação em Java

### Classe `FileSystemSimulator`

Responsável pela interface interativa (modo shell). Interpreta comandos como:

```bash
mkdir nome           # cria diretório
rmdir nome           # remove diretório
touch nome           # cria arquivo
rm nome              # remove arquivo
rename antigo novo   # renomeia arquivo ou diretório
cp origem destino    # copia arquivo
cd nome              # entra em subdiretório
cd ..                # volta ao diretório pai
ls                   # lista arquivos/diretórios
exit                 # sai do simulador
```

### Classe `Directory`

- Gerencia subdiretórios e arquivos
- Métodos: `addDirectory`, `removeDirectory`, `addFile`, `removeFile`, `renameEntry`, `copyFile`, `listEntries`

### Classe `MyFile`

- Representa arquivos com nome e conteúdo de texto
- Métodos: `getName`, `setName`, `getContent`, `setContent`

### Classe `Journal`

- Responsável por registrar em disco as operações executadas
- Cria um arquivo `journal.log` contendo as ações em formato de texto

---

## Parte 4: Instalação e Funcionamento

### Requisitos

- Java JDK 11 ou superior

### Estrutura de Arquivos

```
├── FileSystemSimulator.java    # Shell principal
├── Directory.java              # Classe de diretório
├── MyFile.java                 # Classe de arquivo
├── Journal.java                # Classe de journaling
├── journal.log                 # Log de operações (gerado em tempo de execução)
```

### Compilação

```bash
cd src/
javac FileSystemSimulator.java Directory.java MyFile.java Journal.java
```

### Execução

```bash
java FileSystemSimulator
```

### Exemplo de uso

```bash
> mkdir projetos
> cd projetos
> touch trabalho.txt
> cp trabalho.txt copia.txt
> ls
[FILE] trabalho.txt
[FILE] copia.txt
> cd ..
> ls
[DIR] projetos
```

---

## Resultados Esperados

Ao executar o simulador, espera-se:

- Funcionamento completo das operações de gerenciamento de arquivos
- Registro persistente das ações via journaling
- Interface intuitiva via terminal
- Simulação fiel de um sistema de arquivos real, com segurança e organização

---

## Link do Repositório no GitHub

> [https://github.com/pewpola/simulador-sistema-arquivos](https://github.com/pewpola/simulador-sistema-arquivos)

---
