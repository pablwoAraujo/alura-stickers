package stickers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerGenerator {

	public void generator(InputStream inputStream, String message, String newFileName) throws IOException {
		// leitura da imagem
		BufferedImage originalImage = ImageIO.read(inputStream);

		BufferedImage teste = resize(originalImage, 500);
		
		int originalWidth = teste.getWidth();
		int originalHeight = teste.getHeight();

		// cria nova imagem em memória com transparência
		int newHeight = originalHeight + 100;
		BufferedImage newImage = new BufferedImage(originalWidth, newHeight, BufferedImage.TRANSLUCENT);

		// copia a imagem original para nova imagem (em memória)
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.drawImage(teste, 0, 0, null);

		// configurar fonte
		var font = new Font("Comic Sans MS", Font.BOLD, 60);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(font);

		// escrever uma frase na nova imagem
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D rectangle = fontMetrics.getStringBounds(message, graphics);

		int textWidth = (int) rectangle.getWidth();

		int startingPosition = (originalWidth - textWidth) / 2;
		graphics.drawString(message, startingPosition, newHeight - 30);

		FontRenderContext context = graphics.getFontRenderContext();
		var textLayout = new TextLayout(message, font, context);

		Shape outline = textLayout.getOutline(null);
		AffineTransform transform = graphics.getTransform();
		transform.translate(startingPosition, newHeight - 30);
		graphics.setTransform(transform);

		var outlineStroke = new BasicStroke(originalWidth * 0.004f);
		graphics.setStroke(outlineStroke);

		graphics.setColor(Color.BLACK);
		graphics.draw(outline);
		graphics.setClip(outline);

		// escrever a nova imagem em um arquivo
		ImageIO.write(newImage, "png", new File(newFileName));
	}
	
	private BufferedImage resize(BufferedImage src, int targetSize) {
	    if (targetSize <= 0) {
	        return src; //this can't be resized
	    }
	    int targetWidth = targetSize;
	    int targetHeight = targetSize;
	    float ratio = ((float) src.getHeight() / (float) src.getWidth());
	    if (ratio <= 1) { //square or landscape-oriented image
	        targetHeight = (int) Math.ceil((float) targetWidth * ratio);
	    } else { //portrait image
	        targetWidth = Math.round((float) targetHeight / ratio);
	    }
	    BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = bi.createGraphics();
	    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
	    g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
	    g2d.dispose();
	    return bi;
	}
}
