   # Plano de Realização do Projeto

**Prazo final:** domingo, 20 de dezembro de 2026 · **Início:** segunda, 16 de novembro · **5 semanas**

## Equipa e capacidade

| Pessoa | Horas/semana | Papel principal | Pilar que "defende" na discussão |
|---|---|---|---|
| Mykhaylo | | Arquitetura, alterações ao motor, NPCs/IA, waves, integração, revisão de PRs | D (NPCs) + E (geração) |
| João Sarreira |  | Blocos e terreno | A (blocos) |
| Miguel Lima | | Itens, inventário, crafting e shop | B (objetos) + C (crafting) |
| Duarte |  | Save/Load, highscore, documentação final | F (persistência) |

**Total: ~30 h/semana, ~150 h no projeto.** Não há folga para extras antes de o núcleo estar jogável.

Cada pessoa tem **código seu** num pilar e tem de o conseguir explicar na discussão (o enunciado dá 0 a código que não se saiba explicar).

## Regras do grupo

- Uma reunião curta por semana (30 min): ver o que entrou, distribuir a semana seguinte.
- Tudo entra por Pull Request; o Mykhaylo revê. Os PRs pequenos são revistos mais depressa.
- O desenho das classes (UML) é decidido **em grupo**, não pedido à IA (política de IA do enunciado).
- Todo o uso de IA vai para `docs/REGISTO_IA.md`, na própria semana.
- Bloqueado mais de 1 h? Pergunta no grupo. Não fiques parado.

---

## Semana 1 (16–22 nov) — Arranque e fundamentos

**Objetivo:** toda a gente corre o jogo e sabe usar Git; primeiro desenho das classes.

| Quem | Tarefa |
|---|---|
| Todos | Instalar JDK 17 + IntelliJ, clonar o repo, correr `gradlew run`. Ler `docs/ENGINE_PT.md` e `docs/CONCEITO.md` |
| Todos | **Reunião de design (1 h):** fechar a lista "A decidir" do conceito e desenhar o UML inicial |
| Mykhaylo | Sessão de Git com o grupo (branch → commit → PR). Falar com o professor sobre as alterações ao motor (ver Riscos) |
| Mykhaylo | Ciclo de atualização dos objetos de jogo (AppState que chama a lógica por frame); objetos não-Item passam a ser desenhados (aparência descrita de forma neutra, sem jME) |
| João | Primeiros 2 tipos de bloco novos (seguir o padrão de `StoneBlockType`) |
| Miguel | 2–3 classes de itens simples com Javadoc |
| Duarte | Passar o UML a limpo (draw.io ou PlantUML) em `docs/uml/`; escrever a nota curta de responsabilidades |

**Entregável da semana:** classes com Javadoc, UML inicial, nota de responsabilidades.

## Semana 2 (23–29 nov) — Hierarquias, blocos e mundo

**Objetivo:** a floresta existe e os blocos têm efeitos.

| Quem | Tarefa |
|---|---|
| Mykhaylo | Geração v1 (relevo por heightmap com seed, ≥ 2 tipos de bloco). Ligação ao motor para os efeitos dos blocos no jogador (ex.: abrandar). Colisão dos NPCs com o terreno |
| Mykhaylo | Ciclo dia/noite e gestor de fases (dia ↔ noite) |
| João | Completar ≥ 4 blocos novos, cada um com visual e propriedade funcional; testar cada efeito no jogo |
| Miguel | Inventário (≥ 6 slots), com erros tratados por exceções (inventário cheio, item inexistente) |
| Duarte | Formato do ficheiro de save (o que se guarda e porquê); começar as classes de Save/Load |

**Entregável:** UML atualizado, screenshot/vídeo curto com os blocos no mundo.

## Semana 3 (30 nov–6 dez) — Sistemas: combate, waves, shop, crafting

**Objetivo: NÚCLEO JOGÁVEL até domingo, 6 dez.** Uma noite, uma wave, matar um inimigo, ganhar points, comprar uma coisa.

| Quem | Tarefa |
|---|---|
| Mykhaylo | Ataque corpo a corpo (input + acertar em NPCs). Sistema de waves (spawn, fim de wave, dificuldade crescente). 1.º inimigo com IA básica (perseguir/atacar) |
| Mykhaylo | Árvores na geração (estruturas), mantendo o determinismo |
| João | Mecânica de colocar blocos (tecla E sobre um bloco, que é o TODO do motor); blocos de defesa a ferir/abrandar inimigos |
| Miguel | Crafting com ≥ 2 receitas + exceções (receita inválida, recursos insuficientes). Vendedor aliado e shop com points |
| Duarte | Save/Load v1 funcional (posição, inventário, seed, wave atual), com exceções para ficheiro em falta ou corrompido |

**Entregável:** demo das receitas e do comportamento dos NPCs.

## Semana 4 (7–13 dez) — Polimorfismo, IA, bosses e erros

**Objetivo: funcionalidades completas até domingo, 13 dez.**

| Quem | Tarefa |
|---|---|
| Mykhaylo | Máquina de estados dos inimigos (ex.: idle → perseguir → atacar). 2.º inimigo com comportamento diferente. Bosses (waves 5/10/15/20) e boss final (25) |
| Mykhaylo | HUD: vida, wave, points, inventário (a biblioteca Lemur já está no projeto) |
| João | Geração v2 (parâmetros documentados, ex.: zonas mais densas); escrever os parâmetros e a seed no doc |
| Miguel | Chegar a ≥ 4 receitas; drops raros (tabela de loot por raridade); 2.º aliado |
| Duarte | Highscore com fila (top-N guardado em ficheiro); testes/harness para Save/Load e casos de erro |

**Entregável:** inimigos com ≥ 2 estados, aliados distintos, testes a mostrar polimorfismo e erros.

## Semana 5 (14–20 dez) — Integração, balanceamento e entrega

**Objetivo:** jogo de ponta a ponta, sem bugs graves, e documentação fechada. **Congelar funcionalidades na quarta, 16 dez.**

| Quem | Tarefa |
|---|---|
| Mykhaylo | Integração final, correção de bugs, revisão de visibilidade/duplicação/nomes |
| João + Miguel | Balancear as 25 waves (vida/dano/quantidade de inimigos, preços, receitas) numa tabela; jogar e reportar bugs |
| Todos | Completar o Javadoc das próprias classes; atualizar o registo de IA |
| Duarte | UML final, nota arquitetural (1–2 páginas), juntar o registo de IA |
| Todos | Gravar o walkthrough de 3–5 min (cada um apresenta o seu pilar) |
| Mykhaylo | Entrega até domingo, 20 dez (nunca no último minuto: alvo sábado, 19 dez) |

---

## Riscos e o que fazer

| Risco | Impacto | Mitigação |
|---|---|---|
| O enunciado diz "não alterar o motor", mas os mínimos exigem mexer (NPCs visíveis, efeitos de blocos, teclas novas, ataque) | Alto | Confirmar com o professor na semana 1, por escrito; preferir novos AppStates; justificar cada alteração na nota arquitetural |
| Colisão não implementada = trabalho excluído | Crítico | Colisão dos NPCs com o terreno na semana 2, antes das waves |
| Só uma pessoa sabe Java | Alto | Tarefas pequenas e guiadas; 1 h de pair programming por semana com cada colega; revisão de PRs a explicar o porquê |
| Âmbito grande para ~150 h | Alto | Núcleo jogável na semana 3; cortes já definidos (abaixo) |
| Discussão individual | Alto | Cada um defende o seu pilar; ensaio de perguntas na semana 5 |

## Cortes pré-definidos (se houver atraso, por esta ordem)

1. Bosses das waves 5–20 passam a ser inimigos normais reforçados (só o boss final é único)
2. 2.º aliado com comportamento simples (ex.: diálogo + cura)
3. HUD mínimo em texto em vez de menus Lemur
4. Geração v2 reduzida a um parâmetro extra

O que **nunca** se corta: os mínimos de cada pilar, a colisão, o Save/Load + highscore com fila, a documentação e o registo de IA.
