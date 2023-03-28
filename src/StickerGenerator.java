import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
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
		var font = new Font(Font.SANS_SERIF, Font.BOLD, 64);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(font);

		// escrever uma frase na nova imagem
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D rectangle = fontMetrics.getStringBounds(message, graphics);

		int textWidth = (int) rectangle.getWidth();

		int startingPosition = (originalWidth - textWidth) / 2;
		graphics.drawString(message, startingPosition, newHeight - 100);

		// escrever a nova imagem em um arquivo
		ImageIO.write(newImage, "png", new File(newFileName));
	}
}
