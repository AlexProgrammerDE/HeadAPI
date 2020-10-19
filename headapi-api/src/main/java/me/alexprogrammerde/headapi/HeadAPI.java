package me.alexprogrammerde.headapi;

import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;

public final class HeadAPI {
    public static @Nonnull BufferedImage getHeadImage(Player player) {
        if (!player.getPlayerProfile().isComplete()) {
            player.getPlayerProfile().complete();
        }

        for (ProfileProperty property : player.getPlayerProfile().getProperties()) {
            if (property.getName().equals("textures")) {
                TexturesObject texture = new Gson().fromJson(new String(Base64.getDecoder().decode(property.getValue())), TexturesObject.class);

                try {
                    BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);

                    Graphics g = image.getGraphics();
                    g.drawImage(ImageIO.read(new URL(texture.textures.SKIN.url).openStream()).getSubimage(8, 8, 8, 8), 0, 0, null);
                    g.drawImage(ImageIO.read(new URL(texture.textures.SKIN.url).openStream()).getSubimage(37, 8, 14, 8), -2, 0, null);

                    return image;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            image = ImageIO.read(HeadAPI.class.getClassLoader().getResourceAsStream("steve.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

class TexturesObject {
    public AvatarParts textures = new AvatarParts();
}

class AvatarParts {
    public MojangURL SKIN;
    public MojangURL CAPE;
}

class MojangURL {
    public String url;
}