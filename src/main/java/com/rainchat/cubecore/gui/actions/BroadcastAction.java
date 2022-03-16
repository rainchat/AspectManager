package com.rainchat.cubecore.gui.actions;

import co.aikar.taskchain.TaskChain;
import com.rainchat.cubecore.api.action.BaseAction;
import com.rainchat.cubecore.utils.general.Chat;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BroadcastAction extends BaseAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public BroadcastAction(String string) {
    super(Chat.translateRaw(string));
  }

  @Override
  public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {
    taskChain.sync(() -> Bukkit.broadcastMessage((getReplacedString(uuid))));
  }
}
