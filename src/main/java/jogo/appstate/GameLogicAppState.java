package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import jogo.engine.GameRegistry;
import jogo.gameobject.GameObject;
import jogo.gameobject.Updatable;

/**
 * Ciclo de lógica do jogo: a cada frame, chama {@link Updatable#update(float)}
 * em todos os objetos registados que tenham lógica própria.
 * <p>
 * Ponte entre o motor (que conta os frames) e a camada de jogo
 * (que decide o que cada objeto faz).
 */
public class GameLogicAppState extends BaseAppState {

    private final GameRegistry registry;

    /**
     * @param registry registo com todos os objetos de jogo ativos
     */
    public GameLogicAppState(GameRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void update(float tpf) {
        for (GameObject obj : registry.getAll()) {
            if (obj instanceof Updatable u) {
                u.update(tpf);
            }
        }
    }

    // Métodos obrigatórios do BaseAppState (aqui não precisamos deles)
    @Override protected void initialize(Application app) { }
    @Override protected void cleanup(Application app) { }
    @Override protected void onEnable() { }
    @Override protected void onDisable() { }
}