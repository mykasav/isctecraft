
# Registo de Uso de IA

Este documento regista a utilização de ferramentas de Inteligência Artificial durante o desenvolvimento do projeto ISCTECraft, de acordo com a Política de Uso de IA descrita em `docs/PROJECT_PT.md` e com o "Guia para uma Utilização Responsável da Inteligência Artificial em Trabalhos Académicos" do ISCTE.

| # | Data | Autor | Ferramenta | Contexto | Prompt (exato) | Output relevante | Decisão (usar / alterar / rejeitar) | Reflexão | Commit |
|---|---|---|---|---|---|---|---|---|---|
| 1 | 2026-11-14 | Mykhaylo | Claude | Análise do enunciado e do motor; preparação do repositório e planeamento de tarefas | (Copiar da conversa original) | Resumo do enunciado, análise do motor, README e estrutura do repo | A preencher pelo autor | A preencher pelo autor | A preencher |
| 2 | 2026-11-18 | João | ChatGPT | Análise e desenvolvimento do sistema de blocos voxel, incluindo a criação dos blocos Dirt e Grass e adaptação do motor para suportar materiais específicos por face. | Pedidos de orientação para criar o `DirtBlockType` e o `GrassBlockType`, analisar a arquitetura existente, implementar texturas procedurais, adaptar o `Chunk.buildMesh()` e resolver erros de compilação. (Resumo dos pedidos, não transcrição literal integral.) | Orientação para implementação das classes `DirtBlockType` e `GrassBlockType`; criação de texturas procedurais para terra e relva; alteração de `VoxelBlockType` para suportar materiais por posição e por face; adaptação de `Chunk` para utilizar um `MeshBuilder` por tipo de bloco e por face; atualização de `VoxelPalette`; apoio na resolução de erros de compilação e utilização de comandos Gradle e Git. | Usar e adaptar | As sugestões foram analisadas e integradas no projeto. A utilização da IA permitiu compreender melhor a arquitetura do sistema voxel, a relação entre os blocos, os materiais e as geometrias, bem como a resolução de problemas de compatibilidade entre métodos. O código foi compilado através do Gradle, tendo sido obtido `BUILD SUCCESSFUL`. | A preencher após o commit |
| 3 | 2026-11-27 | João | ChatGPT (GPT-6) | Geração procedural de árvores e implementação dos blocos funcionais Areia, Gelo e Água; separação entre renderização e colisões; integração da física e propagação da água | Pedidos de orientação para criação de árvores, implementação da gravidade da areia, deslizamento no gelo e propagação limitada da água | Orientação e código para geração de árvores, `WaterBlockType`, `isVisible()`, separação das geometrias visuais e de colisão em `Chunk`, `updateWaterPhysics()` e integração dos comportamentos nos AppStates | Alterar e usar após revisão e testes | A IA ajudou a estruturar a geração procedural e os comportamentos dos blocos. O código foi integrado no projeto e deverá ser revisto e testado para garantir a compreensão das decisões de implementação. | A preencher |

## Observações

- A IA foi utilizada como ferramenta de apoio à aprendizagem, análise e resolução de problemas de programação.
- As sugestões de código foram analisadas e adaptadas à arquitetura existente do ISCTECraft.
- A compilação foi validada através de `.\gradlew.bat clean build`.
- Os prompts apresentados de forma resumida devem ser substituídos pelas transcrições literais caso a política do projeto exija a reprodução integral das conversas.
- O identificador do commit deve ser preenchido depois de as alterações serem submetidas ao Git.

