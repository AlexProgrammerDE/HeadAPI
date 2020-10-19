package me.alexprogrammerde.headapi;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

public final class HeadAPIPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);

        if (!PaperLib.isPaper()) {
            getServer().getPluginManager().disablePlugin(this);
        } else {
            getServer().getPluginManager().registerEvents(this, this);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(HeadAPI.getHeadImage(event.getPlayer()), "png", os);
            getLogger().info(Base64.getEncoder().encodeToString(os.toByteArray()));
        }
        catch (final IOException ioe)
        {
            throw new UncheckedIOException(ioe);
        }
    }
}

