package com.rainchat.cubecore.api.builder;

import com.rainchat.cubecore.api.action.Action;
import com.rainchat.cubecore.gui.actions.*;
import com.rainchat.cubecore.gui.menu.Menu;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import com.rainchat.cubecore.utils.objects.Builder;
import com.rainchat.cubecore.utils.objects.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ActionBuilder extends Builder<String, Action> {

    public static final ActionBuilder INSTANCE = new ActionBuilder();

    private ActionBuilder() {
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        register(ConsoleAction::new, "console");
        register(OpAction::new, "op");
        register(PlayerAction::new, "player");
        register(DelayAction::new, "delay");
        register(TellAction::new, "tell");
        register(BroadcastAction::new, "broadcast");
        register(s -> new CloseMenuAction(), "close-menu", "closemenu");
        register(PermissionAction::new, "permission");
        register(MusicAction::new, "music");
    }

    /**
     * Build a list of actions
     *
     * @param menu   the menu involved in
     * @param object the object
     *
     * @return the list of actions
     */
    public List<Action> getActions(SimpleInventory menu, Object object) {
        return CollectionUtils.createStringListFromObject(object, true)
                .stream()
                .map(string -> {
                    String[] split = string.split(":", 2);
                    String name = split[0];
                    String value = split.length > 1 ? split[1] : "";

                    Action action = build(name.trim(), value.trim()).orElseGet(() -> new PlayerAction(string.trim()));
                    action.setMenu(menu);
                    return action;
                })
                .collect(Collectors.toList());
    }
}