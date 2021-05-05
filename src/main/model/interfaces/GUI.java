package main.model.interfaces;

/*
    No idea if Java does this natively somewhere. We only need a couple of basic functions.
    All GUI panels will inherit this functionality, allowing easy switching between them with a GUIManager and
    can also ensure only 1 panel is active at any given time.
 */

public interface GUI {

    public void init(); // prepare the GUI for rendering
    public void show(); // make the GUI visible
    public void hide(); // make the GUI invisible
    public boolean isFailState(); // return true if something went wrong
}
