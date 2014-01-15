package com.nixium.Spree.Utils.UserManager;

import java.util.ArrayList;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Utils.DonatorManager.DonationType;
import com.nixium.Spree.Utils.KitManager.Item;
import com.nixium.Spree.Utils.KitManager.Kit;
import com.nixium.Spree.Utils.KitManager.KitManager;
import com.nixium.Spree.Utils.SettingsManager.PlayerSettings;
import com.nixium.Spree.Utils.StaffManager.StaffType;
import com.nixium.Spree.Utils.StatsManager.PlayerStats;
import com.nixium.Spree.Utils.StreakManager.Streak;
import com.nixium.Spree.Utils.VoteManager.Vote;

public class User {

	private String name;
	
	public static Spree instance;
	
	public User(String name) {
		this.name = name;
	}
	
	public User(Player player) {
		this.name = player.getName();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.name);
	}
	
	public boolean isOnline() {
		return this.getPlayer() != null;
	}
	
	//****************** Staff ************************
	
	public boolean isStaff() {
		return instance.getStaffManager().hasPlayer(this.name);
	}
	
	public StaffType getStaffType() {
		return instance.getStaffManager().getStaffType(this.name);
	}
	
	//****************** Donator ************************
	
	public boolean isDonator() {
		return instance.getDonatorManager().hasPlayer(this.name);
	}
	
	public DonationType getDonatorType() {
		return instance.getDonatorManager().getDonationType(this.name);
	}
	
	//******************* Left **********************
	
	public boolean isLeft() {
		return instance.getGame().containsPlayerLeft(this.name);
	}
	
	public void addLeft() {
		instance.getGame().addPlayerLeft(this.name);
	}
	
	public void removeLeft() {
		instance.getGame().removePlayerLeft(this.name);
	}
	
	//****************** Vote ***************************
	
	public boolean hasVoted() {
		return instance.getVoteManager().hasVote(this.name);
	}
	
	public Vote getVote() {
		return instance.getVoteManager().getVote(this.name);
	}
	
	public Vote addVote(Vote vote) {
		return instance.getVoteManager().addVote(vote);
	}
	
	//********************* Streak *********************
	
	public boolean hasStreak() {
		return instance.getStreakManager().hasStreak(this.name);
	}
	
	public Streak getStreak() {
		return instance.getStreakManager().getStreak(this.name);
	}
	
	public Streak createStreak() {
		return instance.getStreakManager().createStreak(this.name);
	}
	
	//******************* Points ************************
	
	public int getMaxPoints() {
		return instance.getPointManager().getMaxPoints(this.name);
	}
	
	public int getCalculatedPoints() {
		return instance.getPointManager().calculatePoints(this.getPlayer().getInventory());
	}
	
	//****************** Player Kits ************************
	
	public void loadKits() {
		instance.getKitManager().loadKits(this.name);
	}
	
	public void unloadKits() {
		instance.getKitManager().unloadKits(this.name);
	}
	
	public boolean hasKit(String kitName) {
		return instance.getKitManager().hasKit(this.name, kitName);
	}
	
	public boolean hasKits() {
		return instance.getKitManager().hasKits(this.name);
	}
	
	public ArrayList<Kit> getKits() {
		ArrayList<Kit> kits = new ArrayList<Kit>();
		for(Kit kit : instance.getKitManager().getKits(this.name)) {
			if(!kit.isHidden()) {
				kits.add(kit);
			}
		}
		return kits;
	}
	
	public Kit getKit(String kitName) {
		return instance.getKitManager().getKit(this.name, kitName);
	}
	
	public void removeKit(String kitName) {
		instance.getKitManager().removeKit(this.name, kitName);
	}
	
	public void deleteKit(String kitName) {
		instance.getKitManager().deleteKit(this.name, kitName);
	}
	
	public void addKit(Kit kit) {
		if(kit != null) {
			instance.getKitManager().addKit(kit);
			int totalPoints = 0;
			ArrayList<ItemStack> invContents = new ArrayList<ItemStack>();
			for(ItemStack i : this.getPlayer().getInventory().getContents()) {
				if(i != null) {
					if(totalPoints + instance.getPointManager().getPointValue(i) <= instance.getPointManager().getMaxPoints(this.name)) {
						invContents.add(i);
						totalPoints += instance.getPointManager().getPointValue(i);
					}else{
						break;
					}
				}else{
					invContents.add(new ItemStack(Material.AIR));
				}
			}
			kit.setContents(invContents);
			ArrayList<ItemStack> stack = new ArrayList<ItemStack>();
			for(ItemStack i : this.getPlayer().getInventory().getArmorContents()) {
				if(i != null) {
					if(totalPoints + instance.getPointManager().getPointValue(i) <= instance.getPointManager().getMaxPoints(this.name)) {
						stack.add(i);
						totalPoints += instance.getPointManager().getPointValue(i);
					}else{
						break;
					}
				}else{
					stack.add(new ItemStack(Material.AIR));
				}
			}
			kit.setArmorContents(stack);
			this.saveKits();
		}else{
			instance.logError("User", "User", "setInventory(Kit kit)", "Kit is null");
		}
	}
	
	public void setInventory(Kit kit) {
		if(kit != null) {
			this.getPlayer().getInventory().clear();
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			for(Item i : kit.getItems()) {
				if(i != null) {
					items.add(i.getItemStack());
				}else{
					items.add(new ItemStack(Material.AIR));
				}
			}
			ItemStack[] item = new ItemStack[items.size()];
			Object[] items1 = items.toArray();
			for(int i = 0; i < items.size(); i++) {
				item[i] = (ItemStack) items1[i];
			}
			this.getPlayer().getInventory().setContents(item);
			items.clear();
			for(Item i : kit.getArmor()) {
				if(i != null) {
					items.add(i.getItemStack());
				}else{
					items.add(new ItemStack(Material.AIR));
				}
			}
			item = new ItemStack[items.size()];
			items1 = items.toArray();
			for(int i = 0; i < items.size(); i++) {
				if(items1[i] != null) {
					item[i] = (ItemStack) items1[i];
				}
			}
			this.getPlayer().getInventory().setArmorContents(item);
		}else{
			instance.logError("User", "User", "setInventory(Kit kit)", "Kit is null");
		}
	}
	
	public void saveKits() {
		instance.getKitDatabase().saveKits(this.name);
	}
	
	//****************** Player Settings ************************
	
	public PlayerSettings getPlayerSettings() {
		return instance.getSettingsManager().getPlayerSettings(this.name);
	}
	
	//****************** Player Statistics ************************
	
	public PlayerStats getPlayerStats() {
		return instance.getStatManager().getPlayerStats(this.name);
	}
	
	//********************* General ************************
	
	public void reset() {
		if(this.isOnline()) {
			this.getPlayer().getInventory().clear();
			this.getPlayer().getInventory().setArmorContents(null);
			this.getPlayer().setHealth(this.getPlayer().getMaxHealth());
			this.getPlayer().setFoodLevel(20);
		}
		if(this.hasKit(KitManager.INGAMEKIT)) {
			this.deleteKit(KitManager.INGAMEKIT);
		}
	}
}
