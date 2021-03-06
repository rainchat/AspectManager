package com.rainchat.cubecore.gui.actions;

import co.aikar.taskchain.TaskChain;

import com.rainchat.cubecore.api.action.CommandAction;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class PlayerAction extends CommandAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public PlayerAction(String string) {
    super(string);
  }

  @Override
  public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {
    String command = getFinalCommand(getReplacedString(uuid));
    Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> taskChain.sync(() -> player.chat(command)));
  }
}
