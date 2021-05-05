package main;

import main.model.interfaces.GUI;

import java.util.Vector;


public class GUIManager
{
    Vector<GUI> vGUI;

    void switchTo(int index) {
        for (int i = 0; i < vGUI.size(); ++i)
        {
            vGUI.get(i).hide();
        }
    }

    void add(GUI gui)
    {
        vGUI.add(gui);
    }
}
