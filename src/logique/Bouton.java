package logique;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bouton extends JButton{
		
	private String name;
	public Bouton (String str) {
		super(str);
		this.name = str;
	}
	
	public void paintComponents(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
	}
}
