package com.hospeda.img.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hospeda.img.objectDTO.ImagemDTO;

import java.util.Base64;

@RestController
@RequestMapping("host-of-image")
public class HospedarController {

	@PostMapping
	public void saveImg(@RequestBody ImagemDTO img) {
		try (FileOutputStream imageOutFile = new FileOutputStream(
				"C:\\Users\\andre\\Desktop\\teste\\" + img.getNomeImg())) {
			byte[] imageByteArray = Base64.getDecoder().decode(img.getImgBase64());

			imageOutFile.write(imageByteArray);
		} catch (FileNotFoundException e) {
			System.out.println("Imagem não encontrada: " + e);
		} catch (IOException ioe) {
			System.out.println("Ocorrer algum erro ao ler a imagem: " + ioe);
		}
	}

	@GetMapping(value = "recover", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImg(@RequestParam("img") String img) throws IOException {
		try {

			File f = new File("C:\\Users\\andre\\Desktop\\\\teste\\" + img + ".jpg");
			System.out.println("C:\\Users\\andre\\Desktop\\\\teste\\" + img + ".jpg");
			BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(f);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", os);
			ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

			return IOUtils.toByteArray(is);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
