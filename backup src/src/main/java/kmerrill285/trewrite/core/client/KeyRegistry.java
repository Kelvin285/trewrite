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

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyRegistry {

    public static KeyBinding openInventory;
    public static KeyBinding swapHotbars;
    public static KeyBinding autoHeal;
    public static KeyBinding autoBuff;
    public static KeyBinding autoMana;
    public static KeyBinding autoMount;
    public static KeyBinding autoGrapple;
    public static KeyBinding hotbar0;
    public static KeyBinding trash;
    public static KeyBinding drop;

    public static KeyBinding hotbar1;
    
    public static KeyBinding hotbar2;

    public static KeyBinding hotbar3;

    public static KeyBinding hotbar4;

    public static KeyBinding hotbar5;

    public static KeyBinding hotbar6;

    public static KeyBinding hotbar7;

    public static KeyBinding hotbar8;

    public static KeyBinding hotbar9;

    public static void registerKeys() {
        openInventory = registerKeybinding(new KeyBinding("key.trewrite.open.desc", GLFW.GLFW_KEY_N,
                "key.trewrite.category"));
        swapHotbars = registerKeybinding(new KeyBinding("key.trewrite.swap.desc", GLFW.GLFW_KEY_M,
                "key.trewrite.category"));
        autoHeal = registerKeybinding(new KeyBinding("key.trewrite.heal.desc", GLFW.GLFW_KEY_H,
                "key.trewrite.category"));
        autoBuff = registerKeybinding(new KeyBinding("key.trewrite.buff.desc", GLFW.GLFW_KEY_B,
                "key.trewrite.category"));
        autoMana = registerKeybinding(new KeyBinding("key.trewrite.mana.desc", GLFW.GLFW_KEY_J,
                "key.trewrite.category"));
        autoMount = registerKeybinding(new KeyBinding("key.trewrite.mount.desc", GLFW.GLFW_KEY_R,
                "key.trewrite.category"));
        autoGrapple = registerKeybinding(new KeyBinding("key.trewrite.grapple.desc", GLFW.GLFW_KEY_G,
                "key.trewrite.category"));
        hotbar0 = registerKeybinding(new KeyBinding("key.trewrite.hotbarzero.desc", GLFW.GLFW_KEY_0,
                "key.trewrite.category"));
        hotbar1 = registerKeybinding(new KeyBinding("key.trewrite.hotbarone.desc", GLFW.GLFW_KEY_1,
                "key.trewrite.category"));
        hotbar2 = registerKeybinding(new KeyBinding("key.trewrite.hotbartwo.desc", GLFW.GLFW_KEY_2,
                "key.trewrite.category"));
        hotbar3 = registerKeybinding(new KeyBinding("key.trewrite.hotbarthree.desc", GLFW.GLFW_KEY_3,
                "key.trewrite.category"));
        hotbar4 = registerKeybinding(new KeyBinding("key.trewrite.hotbarfour.desc", GLFW.GLFW_KEY_4,
                "key.trewrite.category"));
        hotbar5 = registerKeybinding(new KeyBinding("key.trewrite.hotbarfive.desc", GLFW.GLFW_KEY_5,
                "key.trewrite.category"));
        hotbar6 = registerKeybinding(new KeyBinding("key.trewrite.hotbarsix.desc", GLFW.GLFW_KEY_6,
                "key.trewrite.category"));
        hotbar7 = registerKeybinding(new KeyBinding("key.trewrite.hotbarseven.desc", GLFW.GLFW_KEY_7,
                "key.trewrite.category"));
        hotbar8 = registerKeybinding(new KeyBinding("key.trewrite.hotbareight.desc", GLFW.GLFW_KEY_8,
                "key.trewrite.category"));
        hotbar9 = registerKeybinding(new KeyBinding("key.trewrite.hotbarnine.desc", GLFW.GLFW_KEY_9,
                "key.trewrite.category"));
        trash = registerKeybinding(new KeyBinding("key.trewrite.trash.desc", GLFW.GLFW_KEY_LEFT_SHIFT,
                "key.trewrite.category"));
        drop = registerKeybinding(new KeyBinding("key.trewrite.drop.desc", GLFW.GLFW_KEY_Q,
                "key.trewrite.category"));
    }

    private static KeyBinding registerKeybinding(KeyBinding key) {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
