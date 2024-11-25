# FlatMC Java Edition v0.4.0

## Update Details
### Multiplayer Update
- Play with your friends anytime!

### Command Update
- Empower players with command-based game customization!

## How to Start
- **Double-click** `FlatMC_v0.4.0.exe` to launch the game.
- A **popup** will let you select your language:
  - Chinese players can choose **Chinese**.
- Enter your **player name** and **resolution**, then click **Start Game**.

## Language Switching
- The **main menu** supports language switching with a simple click.

## Game Modes
### Singleplayer Mode
- Click **Singleplayer** to start.

### Multiplayer Mode
- Includes a **built-in server available 24/7**. Simply click to join.

## Multiplayer Chat and Commands
- Press **T** or **/** to open the chat box in multiplayer. Use it to send messages or input commands.
- **Registration is required** to join the game:
  - Type `/register <password> <repeat_password>` to create an account.
  - Registered players can log in with `/login <password>` using the same player name.
- Press **Enter** to execute commands.
- Players are **protected near the spawn point**, where building, breaking, and PvP are disabled.

## Common Server Commands
- `/help <page_number>`: View the command manual.
- `/spawn`: Return to the main city.
- `/tpa <player_name>`: Request to teleport to another player.
- `/tpaccept`: Accept a teleport request.
- `/tpdeny`: Decline a teleport request.
- `/register <password> <repeat_password>`: Register an account.
- `/login <password>`: Log in to the game.
- `/sethome`: Set your current location as home.
- `/home`: Teleport home.
- `/msg <player_name> <message>`: Send a private message to a player.

## Common Singleplayer Commands
- `/time (set <time>)`: View/adjust the current time in the world.
- `/keepinventory <player_name> true/false/1/0`: Toggle inventory preservation on death.
- `/gamemode <player_name> creative/survival/1/0`: Switch a player’s mode.
- `/difficulty easy/normal/hard/peaceful`: Change the world difficulty.
- `/kill <player_name>`: Instantly kill a player.
- `/setblock <block_id/block_name> <x> <y>`: Place a block at specified coordinates.
- `/give <player_name> <item_id/item_name> <quantity>`: Give items to a player.
- `/tp <player_name> <x> <y>`: Teleport a player to specific coordinates.
- `/clear <player_name> (<item> <quantity>)`: Clear a player’s inventory or specific items.
- `/summon <entity_name> <x> <y>`: Spawn an entity at specific coordinates.
- `/gama (set <brightness>)`: View/adjust world brightness.
- `/gravity (set <value>)`: View/adjust world gravity.
- `/spawnpoint <player_name> <x> <y>`: Set a player’s spawn point.
- `/spawnworld <x> <y>`: Set the world spawn point.
- `/resistance air (set <value>)`: View/adjust air resistance.

## World Management
- **Click a button** with the world name to enter it.
- If no local save is detected, click **New World** to create one.
  - Default names for new worlds are `<player_name>’s World`.
- Existing worlds cannot be recreated; delete them first to create a new one.

## Key Controls
- **W**: Jump/Fly up.
- **A**: Move left.
- **D**: Move right.
- **S**: Fly down.
- **X**: Toggle attack mode.
- **E**: Open/close inventory.
- **Q**: Drop the item in hand.
- **Hold Q**: Drop all items in hand.
- **Double-tap W**: Toggle flight in Creative mode.
- **Double-tap A/D**: Sprint left/right.
- **Mouse Wheel**: Switch selected items in the inventory.
- **Left Mouse Button**: Break blocks/attack.
- **Right Mouse Button**: Place blocks/use crafting table.

## Function Keys
- **Esc**: Open/close game settings.
- **1–9**: Switch to specific inventory slots.
- **F1**: Toggle GUI.
- **F2**: Toggle block selection box.
- **F3**: Toggle game information.
- **F4**: Switch between Survival and Creative modes.
- **F5**: Toggle item visibility in hand.
- **F6**: Toggle time display.
- **F7**: Toggle mob health bar display.
- **F8**: Reset GUI size.
- **F9**: Toggle player name display.

## Inventory Operations
- **Left Mouse Button**: Pick up/place items or craft.
- **Right Mouse Button**: Place one item or craft.
- **Shift + Left Mouse Button**: Quickly move items between inventory and slots or craft.
- **Ctrl + Left Mouse Button**: Stack items quickly.
- **Q**: Drop one item at cursor.
- **Hold Q**: Drop all items at cursor.
- Drag items outside the inventory to drop them.

## Gameplay Notes
- Blocks cannot be broken/placed in attack mode, and block selection boxes disappear.
- Without attack mode and with a non-sword item:
  - Left-clicking air attacks.
  - Left-clicking blocks breaks them.
- Holding a sword disables block breaking/placing and removes block selection boxes.
- Health regenerates if no damage is taken for a while.
- Placing blocks in Creative mode does not reduce item quantity.
- **Creative Mode**:
  - Allows instant block breaking without drops.
  - Grants access to all items through the inventory.
  - Removes interaction range limits.
- **Survival Mode**:
  - Requires time to break blocks.
  - Reduces health from falling.
  - Zombies automatically attack players.
  - Zombies spawn naturally at night (21:00–05:00) in low light (excluding Peaceful difficulty).
- **Crafting Table**:
  - Right-click to open the crafting menu.
  - Gold and diamond ores drop resources only when mined with iron or diamond pickaxes.
  - Closing the crafting menu auto-drops uncollected crafted items.
