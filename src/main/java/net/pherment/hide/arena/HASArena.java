package net.pherment.hide.arena;

import net.pherment.hide.HideAndSeek;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HASArena {
    public static List<HASArena> arenasList = new ArrayList<>();

    public static HASArena getArenaByName(String name) {
        for (HASArena hs: arenasList) {
            if (hs.arenaName.equals(name)) {
                return hs;
            }
        }
        return null;
    }

    public static HASArena getArenaOfPlayer(Player player) {
        for (HASArena hs: arenasList) {
            if (hs.playerOnArena.contains(player)) {
                return hs;
            }
        }
        return null;
    }

    private String arenaName;

    private List<Player> playerOnArena = new ArrayList<>();
    private Map<Player, Location> onJoinLocation = new HashMap<>();

    private Location seekersLocation;
    private Location hidersLocation;

    private int minPlayer;
    private int maxPlayer;
    private int startTime;

    private Game game;

    private Location lobbyLocation;

    public HASArena(int minPlayer, int maxPlayer, int startTime, String name) {
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.startTime = startTime;
        this.arenaName = name;
    }

    public void joinPlayer(Player player) {
        playerOnArena.add(player);
        onJoinLocation.put(player, player.getLocation());

        player.teleport(lobbyLocation);
        sendArenaMessage(player.getDisplayName() + " зашёл в игру (" + playerOnArena.size() + "/" + maxPlayer + ")");
        playArenaSound(Sound.BLOCK_NOTE_BLOCK_PLING);

        if (playerOnArena.size() == minPlayer) {
            startTimer();
        }
    }

    public void leavePlayer(Player player) {
        player.teleport(onJoinLocation.get(player));
        player.getInventory().clear();
        onJoinLocation.remove(player);
        playerOnArena.remove(player);
        sendArenaMessage(player.getDisplayName() + " вышел из игры (" + playerOnArena.size() + "/" + maxPlayer + ")");

    }

    private void startTimer() {
        Thread thread = new Thread() {
          @Override
          public void run() {
              int ctr = startTime;
              while (ctr > 0 && playerOnArena.size() >= minPlayer) {
                  int finalCtr = ctr;
                  new BukkitRunnable() {
                      @Override
                      public void run() {
                          sendArenaTitle("Осталось " + finalCtr + " секунд!", "");
                      }
                  }.runTask(HideAndSeek.getPlugin(HideAndSeek.class));
                  ctr -= 1;
                  if (playerOnArena.size() < minPlayer) {
                      return;
                  }
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
              }
              if (playerOnArena.size() > minPlayer) {
                  new BukkitRunnable() {
                      @Override
                      public void run() {
                          startGame();
                      }
                  }.runTask(HideAndSeek.getPlugin(HideAndSeek.class));
              }
          }
        };
        thread.start();
    }

    private void startGame() {
        game = new Game();
        for (Player p: playerOnArena) {
            game.addPlayerToTeam(p);
        }
        for (HASArena hs: arenasList) {
            game.startGame(hs);
        }
    }

    public void sendArenaMessage(String msg) {
        for (Player p: playerOnArena) {
            p.sendMessage(msg);
        }
    }

    public void sendArenaTitle(String msg, String subMsg) {
        for (Player p: playerOnArena) {
            p.sendTitle(msg, subMsg, 0, 20, 0);
        }
    }

    public void playArenaSound(Sound sound) {
        for (Player p: playerOnArena) {
            p.playSound(p.getLocation(), sound, 1, 1);
        }
    }

    /* ===== GETTERS & SETTERS ===== */

    public List<Player> getPlayerOnArena() {
        return playerOnArena;
    }

    public Game getGame() {
        return game;
    }

    public Location getSeekersLocation() {
        return seekersLocation;
    }

    public void setSeekersLocation(Location seekersLocation) {
        this.seekersLocation = seekersLocation;
    }

    public Location getHidersLocation() {
        return hidersLocation;
    }

    public void setHidersLocation(Location hidersLocation) {
        this.hidersLocation = hidersLocation;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }
}
