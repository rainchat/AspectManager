package com.rainchat.cubecore.gui.actions;

import co.aikar.taskchain.TaskChain;
import com.rainchat.cubecore.api.action.BaseAction;
import org.bukkit.Bukkit;

import java.util.UUID;

public class ConsoleAction extends BaseAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public ConsoleAction(String string) {
    super(string);
  }

  @Override
  public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {
    taskChain.sync(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getReplacedString(uuid)));
  }
}
