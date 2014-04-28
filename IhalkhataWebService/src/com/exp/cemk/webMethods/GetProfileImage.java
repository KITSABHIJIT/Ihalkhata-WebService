package com.exp.cemk.webMethods;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.exp.cemk.controller.UserDataProcessor;

public class GetProfileImage {
	public static Response getImage(MultivaluedMap<String, String> formParams) {
		String userId = formParams.getFirst("param1");
		BufferedImage originalImage;
		try {
			File image = UserDataProcessor.getInstance().getUserImage(userId);
			originalImage = ImageIO.read(image);

			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
					: originalImage.getType();

			BufferedImage resizeImageJpg = resizeImageWithHint(originalImage,
					type, 90, 90);

			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			ImageIO.write((BufferedImage) resizeImageJpg, "png", out);

			final byte[] imgData = out.toByteArray();

			final InputStream bigInputStream = new ByteArrayInputStream(imgData);

			return Response.ok(bigInputStream).build();
		} catch (final IOException e) {
			return Response.noContent().build();
		}

	}

	public static BufferedImage resizeImageWithHint(
			BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}
