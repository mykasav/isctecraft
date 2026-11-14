# IscteCraft — Projeto de POO 2026/27

Projeto de grupo da UC de Programação Orientada a Objetos (LDSA, turma DSA, Iscte Sintra).
Mini-jogo 3D em primeira pessoa, em Java, sobre o motor fornecido (jMonkeyEngine).

**Entrega final:** 20 de dezembro de 2026

## Correr o projeto

Requisitos: JDK 17 ou superior.

```bash
# Windows
gradlew.bat run
# macOS / Linux
./gradlew run
```

No IntelliJ: *File → Open* na pasta do projeto, escolher o JDK 17+ e correr a tarefa Gradle `run`.

Controlos: `Tab` captura o rato · `WASD` move · `Espaço` salta · `Shift` sprint · `E` interage · clique esquerdo parte bloco · `R` respawn · `L` modo de render · `Esc` sai.

## Estrutura

| Pasta | O que é | Quem mexe |
|---|---|---|
| `src/main/java/jogo/gameobject/` | Camada de jogo, **sem imports `com.jme3`** | Grupo |
| `src/main/java/jogo/appstate/`, `engine/`, `voxel/` | Motor fornecido | Só quando necessário, e sempre com nota no PR |
| `docs/` | Documentação do motor, UML, nota arquitetural, registo de IA | Grupo |

## Regras de trabalho (Git)

1. Ninguém faz push direto para `main`.
2. Cada tarefa tem uma branch: `feature/<nome-curto>`, `docs/<nome>` ou `fix/<nome>`.
3. Abre-se um Pull Request e outra pessoa do grupo revê antes do merge.
4. Antes de abrir o PR, o projeto tem de compilar (`./gradlew compileJava`).
5. Commits pequenos, com mensagens claras em português (ex.: `Adiciona bloco de areia com queda`).

## Registo de uso de IA

Todo o uso de IA fica registado em `docs/REGISTO_IA.md` (obrigatório pelo enunciado).
Código feito com ajuda de IA tem de ser percebido e defendido por quem o submete.

## Grupo

| Nome | Número |
|---|---|
| Mykhaylo Savchyshkin | 139718 | 
| | |
| | |
| | |
