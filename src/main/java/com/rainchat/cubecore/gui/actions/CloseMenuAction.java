package com.rainchat.cubecore.gui.actions;

import co.aikar.taskchain.TaskChain;
import com.rainchat.cubecore.api.action.Action;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CloseMenuAction implements Action {
  private SimpleInventory menu;

  @Override
  public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {
    Player player = Bukkit.getPlayer(uuid);
    if (menu == null && player == null) {
      return;
    }
    taskChain.sync(() -> menu.closeInventory());
  }

  @Override
  public SimpleInventory getMenu() {
    return menu;
  }

  @Override
  public void setMenu(SimpleInventory menu) {
    this.menu = menu;
  }
}
