package net.mcpolitics.dev.Spree.Utils.KitManager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Item {
	
	private ItemStack itemStack;
	
	public Item(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	public ItemStack getItemStack() {
		return this.itemStack;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		String enchantments = "";
		for(Enchantment enchant : this.itemStack.getEnchantments().keySet()) {
			enchantments += enchant.getName() + ";" + this.itemStack.getEnchantments().get(enchant).toString();
		}
		String i = this.itemStack.getType().name() + ":" + this.itemStack.getData().getData() + ":" + this.itemStack.getAmount();
		if(!enchantments.isEmpty()) {
			i += ":" + enchantments;
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack parseStringToItemStack(String items) {
		String[] parse = items.split(":");
		if(parse.length > 2) {
			ItemStack item = new ItemStack(Material.matchMaterial(parse[0]), Integer.parseInt(parse[2])
				, Material.matchMaterial(parse[0]).getMaxDurability(), Byte.parseByte(parse[1]));
			if(parse.length > 3) {
				for(String enchants : parse[3].split(",")) {
					String[] split = enchants.split(";");
					item.addEnchantment(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
				}
			}
			return item;
		}
		return null;
	}
}