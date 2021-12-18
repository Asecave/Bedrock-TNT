package com.asecave;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TNT;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class BedrockTNT extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {

		ShapelessRecipe denseGunpowderRecipe = new ShapelessRecipe(NamespacedKey.minecraft("dense_gunpowder"), getDenseGunpowder());
		denseGunpowderRecipe.addIngredient(9, Material.GUNPOWDER);

		getServer().addRecipe(denseGunpowderRecipe);

		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	private void craft(PrepareItemCraftEvent event) {

		Recipe r = event.getRecipe();
		if (r != null) {
			if (r.getResult().getType().equals(Material.TNT)) {

				boolean fullyDense = true;

				for (ItemStack s : event.getInventory().getMatrix()) {
					if (s.getType().equals(Material.GUNPOWDER)) {
						if (!s.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {
							fullyDense = false;
						}
					}
				}
				if (fullyDense) {
					ItemStack denseTNT = getDenseTNT();
					event.getInventory().getResult().setItemMeta(denseTNT.getItemMeta());
				}
			}
		}
	}

	@EventHandler
	private void place(BlockPlaceEvent event) {
		ItemStack item = event.getItemInHand();
		if (item.getType().equals(Material.TNT)) {
			if (item.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {

				event.getBlockPlaced().getWorld().playEffect(event.getBlockPlaced().getLocation().add(0, 0, 0), Effect.MOBSPAWNER_FLAMES, 1);

				TNT blockData = (TNT) event.getBlockPlaced().getBlockData();
				blockData.setUnstable(true);
				event.getBlockPlaced().setBlockData(blockData);
				event.getBlockPlaced().getWorld().playEffect(event.getBlockPlaced().getLocation(), Effect.ANVIL_LAND, 0);
			}
		}
	}

	@EventHandler
	private void ignite(BlockBreakEvent event) {
		Block b = event.getBlock();
		if (b.getType().equals(Material.TNT)) {
			if (((TNT) b.getBlockData()).isUnstable()) {
				BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

					int tick = 0;

					@Override
					public void run() {
						b.getWorld().spawnParticle(Particle.LAVA, b.getLocation().add(0.5d, 1d, 0.5d),
								Math.max(0, tick - 20));
						b.getWorld().spawnParticle(Particle.DRIP_LAVA,
								b.getLocation().add(0.5d, 0d, 0.5d).add(0.6d, 0.3d, tick / 40d * 1.2d - 0.6d), 1);
						b.getWorld().spawnParticle(Particle.DRIP_LAVA,
								b.getLocation().add(0.5d, 0d, 0.5d).add(tick / 40d * 1.2d - 0.6d, 0.3d, -0.6d), 1);
						b.getWorld().spawnParticle(Particle.DRIP_LAVA,
								b.getLocation().add(0.5d, 0d, 0.5d).add(-0.6d, 0.3d, tick / -40d * 1.2d + 0.6d), 1);
						b.getWorld().spawnParticle(Particle.DRIP_LAVA,
								b.getLocation().add(0.5d, 0d, 0.5d).add(tick / -40d * 1.2d + 0.6d, 0.3d, 0.6d), 1);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin(tick / 4d) * tick / 20d, 0d, Math.cos(tick / 4d) * tick / 20d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 4) / 4d) * tick / 20d, 0d, Math.cos((tick + 4) / 4d) * tick / 20d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 8) / 4d) * tick / 20d, 0d, Math.cos((tick + 8) / 4d) * tick / 20d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 12) / 4d) * tick / 20d, 0d, Math.cos((tick + 12) / 4d) * tick / 20d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 16) / 4d) * tick / 20d, 0d, Math.cos((tick + 16) / 4d) * tick / 20d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 20) / 4d) * tick / 20d, 0d, Math.cos((tick + 20) / 4d) * tick / 20d), 0);
						
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin(tick / 4d) * tick / 10d, 0d, Math.cos(tick / 4d) * tick / 10d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 4) / 4d) * tick / 10d, 0d, Math.cos((tick + 4) / 4d) * tick / 10d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 8) / 4d) * tick / 10d, 0d, Math.cos((tick + 8) / 4d) * tick / 10d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 12) / 4d) * tick / 10d, 0d, Math.cos((tick + 12) / 4d) * tick / 10d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 16) / 4d) * tick / 10d, 0d, Math.cos((tick + 16) / 4d) * tick / 10d), 0);
						b.getWorld().spawnParticle(Particle.FLAME, b.getLocation().add(0.5d, 0.1d, 0.5d)
								.add(Math.sin((tick + 20) / 4d) * tick / 10d, 0d, Math.cos((tick + 20) / 4d) * tick / 10d), 0);
						if (tick % 10 == 0) {
							b.getWorld().playEffect(b.getLocation(), Effect.ENDER_SIGNAL, 0);
						}
						if (tick % 5 == 0) {
							b.getWorld().playSound(b.getLocation(), Sound.ENTITY_PHANTOM_FLAP, tick / 20f, 0.5f);
						}
						tick++;
					}
				}, 0, 2);
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						task.cancel();
						event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().add(0d, -1d, 0d)).breakNaturally();

						event.getBlock().getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, event.getBlock().getLocation().add(0.5d, -0.5d, 0.5d),
								100, 0, 0.1, 0, 0.01);
						b.getWorld().playEffect(b.getLocation(), Effect.WITHER_BREAK_BLOCK, 0);
					}
				}, 80L);
			}
		}
	}

	private ItemStack getDenseGunpowder() {
		ItemStack denseGunpowder = new ItemStack(Material.GUNPOWDER, 1);
		ItemMeta denseGunpowderMeta = denseGunpowder.getItemMeta();
		List<String> denseGunpowderLore = new ArrayList<>();
		denseGunpowderLore.add("§5Pure.. Will make special TNT");
		denseGunpowderMeta.setLore(denseGunpowderLore);
		denseGunpowderMeta.addEnchant(Enchantment.DIG_SPEED, 1, false);
		denseGunpowderMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		denseGunpowderMeta.setDisplayName("Dense Gunpowder");
		denseGunpowder.setItemMeta(denseGunpowderMeta);

		return denseGunpowder;
	}

	private ItemStack getDenseTNT() {
		ItemStack denseTNT = new ItemStack(Material.TNT, 1);
		ItemMeta denseTNTMeta = denseTNT.getItemMeta();
		List<String> denseTNTLore = new ArrayList<>();
		denseTNTLore.add("§5Heavy.. Has the power to break bedrock");
		denseTNTLore.add("§5Unstable! Once placed you can't remove it!");
		denseTNTMeta.setLore(denseTNTLore);
		denseTNTMeta.addEnchant(Enchantment.DIG_SPEED, 1, false);
		denseTNTMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		denseTNTMeta.setDisplayName("Dense TNT");
		denseTNT.setItemMeta(denseTNTMeta);

		return denseTNT;
	}
}
