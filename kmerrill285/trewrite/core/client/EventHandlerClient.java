/*
 * Copyright (C) 2018-2019  C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package kmerrill285.trewrite.core.client;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketOpenInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketSyncInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketThrowItemTerraria;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandlerClient {
	
    @SubscribeEvent
    public void onKeyInput(TickEvent.ClientTickEvent evt) {

        if (evt.phase != TickEvent.Phase.END) return;
        
        Minecraft mc = Minecraft.getInstance();

        if (KeyRegistry.openInventory.isPressed() && mc.isGameFocused()) {

        	if (mc.player != null) {
        		NetworkHandler.INSTANCE.sendToServer(new CPacketOpenInventoryTerraria(mc.player.getScoreboardName()));
        	}
        }
        if (Minecraft.getInstance() != null) {
	       
	        if (KeyRegistry.hotbar0.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 9;
	        }
	        if (KeyRegistry.hotbar1.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 0;
	        }
	        if (KeyRegistry.hotbar2.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 1;
	        }
	        if (KeyRegistry.hotbar3.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 2;
	        }
	        if (KeyRegistry.hotbar4.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 3;
	        }
	        if (KeyRegistry.hotbar5.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 4;
	        }
	        if (KeyRegistry.hotbar6.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 5;
	        }
	        if (KeyRegistry.hotbar7.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 6;
	        }
	        if (KeyRegistry.hotbar8.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 7;
	        }
	        if (KeyRegistry.hotbar9.isPressed()) {
	        	ContainerTerrariaInventory.inventory.hotbarSelected = 8;
	        }
        }
        if (KeyRegistry.drop.isPressed() && Util.terrariaInventory == true) {
        	InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
        	EntityItemT item = (EntityItemT) Trewrite.ITEM_ENTITY_TYPE.spawnEntity(mc.player.world, null, null, mc.player.getPosition().up(), false, false);
			if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
	        	item.item = inventory.hotbar[inventory.hotbarSelected].stack.item.itemName;
				item.stack = inventory.hotbar[inventory.hotbarSelected].stack.size;
				item.pickupDelay = 20 * 4;
				InventorySlot selectedSlot = inventory.hotbar[inventory.hotbarSelected];
				inventory.hotbar[inventory.hotbarSelected].stack = null;
	    		NetworkHandler.INSTANCE.sendToServer(new CPacketThrowItemTerraria(0, selectedSlot.area, selectedSlot.id, selectedSlot.stack));
				NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, inventory.hotbar[inventory.hotbarSelected].area, inventory.hotbar[inventory.hotbarSelected].id, inventory.hotbar[inventory.hotbarSelected].stack));
				
			}
        }
        if (KeyRegistry.swapHotbars.isPressed() && mc.isGameFocused()) {
        	Util.terrariaInventory = !Util.terrariaInventory;
        	if (Util.terrariaInventory == false) {
        		Util.justClosedInventory = true;
        	}
        }
    }
}
