package jogo;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.system.AppSettings;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import jogo.appstate.InputAppState;
import jogo.appstate.PlayerAppState;
import jogo.appstate.WorldAppState;
import jogo.appstate.HudAppState;
import jogo.appstate.RenderAppState;
import jogo.appstate.InteractionAppState;
import jogo.appstate.GameLogicAppState;
import jogo.engine.GameRegistry;
import jogo.gameobject.character.NpcTeste;
import jogo.gameobject.item.HealthPotion;
import jogo.gameobject.item.StoneItem;
import jogo.gameobject.item.WoodItem;
import jogo.engine.RenderIndex;

/**
 * Main application entry.
 */
public class Jogo extends SimpleApplication {

    public static void main(String[] args) {
        // LWJGL's legacy memory backend uses deprecated sun.misc.Unsafe APIs.
        // FFM is available on Java 25+; preserve explicit user backend choices.
        if (Runtime.version().feature() >= 25
                && System.getProperty("org.lwjgl.system.memoryBackend") == null) {
            System.setProperty("org.lwjgl.system.memoryBackend", "ffm");
        }
        Jogo app = new Jogo();
        // AWT dialogs conflict with LWJGL first-thread startup on macOS.
        boolean isMac = System.getProperty("os.name").toLowerCase(java.util.Locale.ROOT).contains("mac");
        app.setShowSettings(!isMac);
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Test");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setGammaCorrection(true); // enable sRGB gamma-correct rendering
        app.setSettings(settings);
        app.start();
    }

    private BulletAppState bulletAppState;
    private WorldAppState world;
    private GameRegistry registry;
    private boolean npcCriado = false;

    @Override
    public void simpleInitApp() {
        // disable flyCam, we manage camera ourselves
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(false);
        viewPort.setBackgroundColor(new ColorRGBA(0.6f, 0.75f, 1f, 1f)); // sky-like

        // Physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(false); // toggle off later
        PhysicsSpace physicsSpace = bulletAppState.getPhysicsSpace();

        // AppStates (order matters a bit: input -> world -> render -> interaction -> player)
        InputAppState input = new InputAppState();
        stateManager.attach(input);

        world = new WorldAppState(rootNode, assetManager, physicsSpace, cam, input);
        stateManager.attach(world);

        // Engine registry and render layers
        registry = new GameRegistry();
        RenderIndex renderIndex = new RenderIndex();
        stateManager.attach(new RenderAppState(rootNode, assetManager, registry, renderIndex));
        stateManager.attach(new GameLogicAppState(registry));
        stateManager.attach(new InteractionAppState(rootNode, cam, input, renderIndex, world));

        // Demo objects
        // Chest chest = new Chest();
        // chest.setPosition(26.5f, world.getRecommendedSpawnPosition().y - 2f, 26.5f);
        // registry.add(chest);

        PlayerAppState player = new PlayerAppState(rootNode, assetManager, cam, input, physicsSpace, world);
        stateManager.attach(player);

        // Post-processing: SSAO for subtle contact shadows
        try {
            FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
            Class<?> ssaoCls = Class.forName("com.jme3.post.ssao.SSAOFilter");
            Object ssao = ssaoCls.getConstructor(float.class, float.class, float.class, float.class)
                    .newInstance(2.1f, 0.6f, 0.5f, 0.02f); // radius, intensity, scale, bias
            // Add filter via reflection to avoid compile-time dependency
            java.lang.reflect.Method addFilter = FilterPostProcessor.class.getMethod("addFilter", Class.forName("com.jme3.post.Filter"));
            addFilter.invoke(fpp, ssao);
            viewPort.addProcessor(fpp);
        } catch (Exception e) {
            System.out.println("SSAO not available (effects module missing?): " + e.getMessage());
        }

        // HUD (just a crosshair for now)
        stateManager.attach(new HudAppState(guiNode, assetManager));
    }

    @Override
    public void simpleUpdate(float tpf) {
        // Só no 1.º frame: o mundo já foi construído e o spawn já é o verdadeiro
        if (!npcCriado) {
            var spawn = world.getRecommendedSpawnPosition();

            NpcTeste npc = new NpcTeste();
            npc.setPosition(spawn.x + 3, spawn.y - 2, spawn.z);
            registry.add(npc);

            // Itens de teste (Miguel - Semana 1)
            HealthPotion potion = new HealthPotion(50);
            potion.setPosition(spawn.x + 1, spawn.y - 2, spawn.z + 1);
            registry.add(potion);

            WoodItem wood = new WoodItem(5);
            wood.setPosition(spawn.x - 1, spawn.y - 2, spawn.z + 1);
            registry.add(wood);

            StoneItem stone = new StoneItem(10);
            stone.setPosition(spawn.x, spawn.y - 2, spawn.z + 2);
            registry.add(stone);

            npcCriado = true;
        }
    }
}
