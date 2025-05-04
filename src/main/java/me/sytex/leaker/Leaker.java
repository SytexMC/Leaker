package me.sytex.leaker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public final class Leaker extends JavaPlugin {

  private static final Random RANDOM = new Random();
  private static final long BROADCAST_INTERVAL_TICKS = 30 * 60 * 20;

  @Override
  public void onEnable() {
    new BukkitRunnable() {
      @Override
      public void run() {
        List<? extends Player> players = Bukkit.getOnlinePlayers().stream().toList();

        if (players.isEmpty()) return;

        Player randomPlayer = players.get(RANDOM.nextInt(players.size()));
        Location location = randomPlayer.getLocation();

        Component message = MiniMessage.miniMessage().deserialize(
            "<#dc143c><player> is currently at <x>, <y>, <z>.",
            Placeholder.unparsed("player", randomPlayer.getName()),
            Placeholder.unparsed("x", String.valueOf(location.getBlockX())),
            Placeholder.unparsed("y", String.valueOf(location.getBlockY())),
            Placeholder.unparsed("z", String.valueOf(location.getBlockZ()))
        );

        players.forEach(player -> player.sendMessage(message));
      }
    }.runTaskTimerAsynchronously(this, BROADCAST_INTERVAL_TICKS, BROADCAST_INTERVAL_TICKS);
  }
}
