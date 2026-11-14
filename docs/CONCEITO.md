# Conceito do Jogo — IscteCraft: Noites na Floresta

> Documento vivo. Decisões de design tomadas pelo grupo; alterações via Pull Request.

## Em uma frase

Uma floresta gerada por seed onde, **de dia**, recolhes materiais, fazes crafting e alteras o terreno para te defenderes, e **à noite** sobrevives a **25 waves** de inimigos cada vez mais fortes, com um **boss a cada 5 waves** e um **boss final na wave 25**.

## Loop de jogo

```
DIA  → explorar · recolher (madeira, folhas, pedra…) · crafting · construir defesas · comprar na shop
NOITE → wave de inimigos · combate corpo a corpo · ganhar points e drops
         ↓
      wave seguinte (mais difícil) … wave 5, 10, 15, 20 = boss · wave 25 = boss final
```

- **Vitória:** derrotar o boss final da wave 25.
- **Derrota:** a vida do jogador chega a 0. O highscore regista até onde chegou.

## Decisões fechadas

| Tema | Decisão |
|---|---|
| Estrutura | Fases de dia (preparar) e noite (wave) |
| Nº de waves | 25, dificuldade crescente; boss nas waves 5, 10, 15, 20; boss final na 25 |
| Combate | Só corpo a corpo |
| Economia | Só **points** (sem minérios). Ganham-se a matar inimigos e a sobreviver a waves |
| Ambiente | Floresta; o jogador pode alterar o terreno (partir/colocar blocos) |

## Como cada sistema contribui (e não se sobrepõe)

| Sistema | Função no jogo | Pilar da rubrica |
|---|---|---|
| Crafting | Com materiais do mapa e drops: gear base, blocos de defesa, consumíveis | C |
| Shop (vendedor aliado) | Com points: gear superior e itens que não se fazem | D (aliado "trocar"), B |
| Drops raros | Ingredientes especiais para as receitas mais fortes | B, C |
| Blocos funcionais | Defesa: blocos que abrandam ou ferem inimigos, paredes | A |
| Geração | Floresta com relevo e árvores, determinística por seed | E |
| Pontuação vs. saldo | **Pontuação total** (só sobe, vai para o highscore) separada do **saldo** (o que se gasta na shop) | F |

## A decidir em grupo

- [ ] Lista final dos ≥ 4 blocos novos e a propriedade funcional de cada um
- [ ] Tipos de inimigos (≥ 2) e como se distinguem (velocidade, vida, dano, comportamento)
- [ ] Os 2 aliados (vendedor + ?)
- [ ] As ≥ 4 receitas de crafting
- [ ] Catálogo e preços da shop
- [ ] O que torna cada boss diferente
- [ ] Critério exato do highscore (ex.: wave alcançada, depois pontuação, depois tempo)
- [ ] Nome final do jogo
