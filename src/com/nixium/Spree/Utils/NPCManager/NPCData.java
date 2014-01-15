package com.nixium.Spree.Utils.NPCManager;

public class NPCData {

	private NPCType npcType;
	private int npcID;
	
	public NPCData(NPCType npcType, int npcID) {
		this.npcType = npcType;
		this.npcID = npcID;
	}
	
	public NPCType getNPCType() {
		return this.npcType;
	}
	
	public int getNPCID() {
		return this.npcID;
	}
}
