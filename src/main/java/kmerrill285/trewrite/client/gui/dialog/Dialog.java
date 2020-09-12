package kmerrill285.trewrite.client.gui.dialog;

public abstract  class Dialog {
	
	protected DialogGui gui;
	
	public Dialog(DialogGui gui) {
		this.gui = gui;
	}
	
	public abstract void Update();
	
	public abstract void Render();

	protected abstract void mouseClicked(double x, double y, int code);
}
