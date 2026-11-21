# Registo de Uso de IA

Segue o formato pedido em `docs/PROJECT_PT.md` (Política de Uso de IA) e o
"Guia para uma Utilização Responsável da Inteligência Artificial em Trabalhos Académicos" do ISCTE.

| # | Data | Autor | Ferramenta | Contexto | Prompt (exato) | Output relevante | Decisão (usar / alterar / rejeitar) | Reflexão | Commit |
|---|---|---|---|---|---|---|---|---|---|
| 1 | 2026-11-14 | Mykhaylo | Claude | Análise do enunciado e do motor; preparação do repositório e planeamento de tarefas | (copiar da conversa) | Resumo do enunciado, análise do motor, README e estrutura do repo | | | |
| 2 | 2026-11-21 | Mykhaylo | Claude | Ciclo de update dos objetos de jogo e rendering de NPCs (semana 1) | "eu agora queria fazer o meu codigo , podes explicar oque consiste essa tarefa?" / "vamos fazer um npc de teste" / "o npc spawnou longe de mim , como o faco spawnar ao meu lado" | Interface `Updatable`, `GameLogicAppState`, ramo `Character` no `RenderAppState`, `NpcTeste`, criação do NPC no 1.º frame (`Jogo.simpleUpdate`) | Usar (colado e testado passo a passo) | | PR #3 |
| 3 | 2026-11-21 | Mykhaylo | Claude | Revisão do PR #2 (blocos relva/terra do João) para cumprir as regras do enunciado | "poderias corrigir o pr do joao para que seja \"legal\" , que va de encontro com as instrucoes da professora" | Repor Javadoc/getters do motor, `hasRandomizedUV()` em vez de `DIRT_ID` no `Chunk`, `getMaterial(am, face, blockPos)`, Javadoc nos blocos | Usar | | f00b0e0 |
| 4 | 2026-11-21 | Mykhaylo | Claude | Resolver o conflito entre o PR #3 e a `main` no `VoxelWorld.generateLayers` | "podes rever o repo e resolver os conflitos" | Plano 50×50 de teste mantido, agora com blocos de relva (`GRASS_ID`) | Usar | | PR #3 |
