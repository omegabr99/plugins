package net.runelite.client.plugins.nmzhelper.Tasks;

import java.awt.event.KeyEvent;

import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.plugins.nmzhelper.MiscUtils;
import net.runelite.client.plugins.nmzhelper.NMZHelperConfig;
import net.runelite.client.plugins.nmzhelper.NMZHelperPlugin;
import net.runelite.client.plugins.nmzhelper.Task;

public class DominicDialogue2Task extends Task {
    public DominicDialogue2Task(NMZHelperPlugin plugin, Client client, ClientThread clientThread, NMZHelperConfig config) {
        super(plugin, client, clientThread, config);
    }

    @Override
    public boolean validate() {
        //check if dream is created
        if (MiscUtils.isDreamCreated(client)) {
            return false;
        }

        //DIALOG_OPTION_OPTION1[0] == Which dream would you like to experience?
        Widget chatTitleParent = client.getWidget(WidgetInfo.DIALOG_OPTION_OPTION1);

        if (chatTitleParent != null && !chatTitleParent.isHidden()) {
            Widget chatTitleChild = chatTitleParent.getChild(0);

            return chatTitleChild.getText().contains("Agree to pay");
        }

        return false;
    }

    @Override
    public String getTaskDescription() {
        return "Dominic Dialogue 2";
    }

    @Override
    public void onGameTick(GameTick event) {
        pressKey('1');
    }

    public void pressKey(char key) {
        keyEvent(401, key);
        keyEvent(402, key);
        keyEvent(400, key);
    }

    private void keyEvent(int id, char key) {
        KeyEvent e = new KeyEvent(
                client.getCanvas(), id, System.currentTimeMillis(),
                0, KeyEvent.VK_UNDEFINED, key
        );

        client.getCanvas().dispatchEvent(e);
    }
}
