package com.rainchat.cubecore.gui.actions;

import co.aikar.taskchain.TaskChain;
import com.rainchat.cubecore.api.action.BaseAction;
import com.rainchat.cubecore.utils.general.Chat;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class TellAction extends BaseAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public TellAction(String string) {
    super(string);
  }

  @Override
  public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {
    String replacedString = Chat.translateRaw(getReplacedString(uuid));
    Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> taskChain.sync(() -> player.sendMessage(replacedString)));
  }
}
