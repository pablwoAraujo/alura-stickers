package stickers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
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

		int originalWidth = originalImage.getWidth();
		int originalHeight = originalImage.getHeight();

		// cria nova imagem em memória com transparência
		int newHeight = originalHeight + 200;
		BufferedImage newImage = new BufferedImage(originalWidth, newHeight, BufferedImage.TRANSLUCENT);

		// copia a imagem original para nova imagem (em memória)
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.drawImage(originalImage, 0, 0, null);

		// configurar fonte
		var font = new Font("Comic Sans MS", Font.BOLD, 128);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(font);

		// escrever uma frase na nova imagem
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D rectangle = fontMetrics.getStringBounds(message, graphics);

		int textWidth = (int) rectangle.getWidth();

		int startingPosition = (originalWidth - textWidth) / 2;
		graphics.drawString(message, startingPosition, newHeight - 50);

		FontRenderContext context = graphics.getFontRenderContext();
		var textLayout = new TextLayout(message, font, context);

		Shape outline = textLayout.getOutline(null);
		AffineTransform transform = graphics.getTransform();
		transform.translate(startingPosition, newHeight - 50);
		graphics.setTransform(transform);

		var outlineStroke = new BasicStroke(originalWidth * 0.004f);
		graphics.setStroke(outlineStroke);

		graphics.setColor(Color.BLACK);
		graphics.draw(outline);
		graphics.setClip(outline);

		// escrever a nova imagem em um arquivo
		ImageIO.write(newImage, "png", new File(newFileName));
	}

}
