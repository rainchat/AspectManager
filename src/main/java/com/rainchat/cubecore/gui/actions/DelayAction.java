package com.rainchat.cubecore.gui.actions;

import co.aikar.taskchain.TaskChain;
import com.rainchat.cubecore.api.action.BaseAction;
import com.rainchat.cubecore.utils.general.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;

public class DelayAction extends BaseAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public DelayAction(String string) {
    super(string);
  }

  @Override
  public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {

    String finalValue = getReplacedString(uuid);
    if (!MathUtil.isInteger(finalValue)) {
      Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> player.sendMessage(ChatColor.RED + "Invalid delay: " + finalValue));
      return;
    }

    taskChain.delay(Integer.parseInt(finalValue));
  }
}
