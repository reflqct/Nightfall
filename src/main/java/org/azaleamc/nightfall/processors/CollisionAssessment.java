package org.azaleamc.nightfall.processors;

import cc.funkemunky.api.utils.BlockUtils;
import cc.funkemunky.api.utils.BoundingBox;
import lombok.Getter;
import lombok.Setter;
import org.azaleamc.nightfall.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by George on 27/04/2019 at 18:16.
 */
@Getter
@Setter
public class CollisionAssessment {

    private PlayerData data;
    private boolean onGround, inLiquid, inWeb;
    private Set<Material> materialsCollided;
    private BoundingBox playerBox;

    public CollisionAssessment(BoundingBox playerBox, PlayerData data) {
        onGround = inLiquid = false;
        this.data = data;
        this.playerBox = playerBox;
        materialsCollided = new HashSet<>();
    }

    public void assessBox(BoundingBox bb, World world, boolean isEntity) {
        Location location = bb.getMinimum().toLocation(world);
        Block block = BlockUtils.getBlock(location);

        if (BlockUtils.isSolid(block) || isEntity) {
            if (bb.getMinimum().getY() < (playerBox.getMinimum().getY() + 0.1) && bb.collidesVertically(playerBox.subtract(0, 0.001f, 0, 0, 0, 0))) {
                onGround = true;
            }
        } else {
            if (BlockUtils.isLiquid(block) && playerBox.collidesVertically(bb)) {
                inLiquid = true;
            }

            if (block.getType().toString().contains("WEB") && playerBox.collidesVertically(bb)) {
                inWeb = true;
            }
        }
        addMaterial(block);
    }

    public void addMaterial(Block block) {
        materialsCollided.add(block.getType());
    }
}
