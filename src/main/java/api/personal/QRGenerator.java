package api.personal;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import constants.Branches;
import controllers.IUIServlet;
import entity.Worker;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet(value = Branches.QR_GENERATOR)
public class QRGenerator extends IUIServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        final String token = req.getParameter("t");
        try {
            BitMatrix bitMatrix = barcodeWriter.encode(token, BarcodeFormat.QR_CODE, 200, 200);
            final BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            final byte[] bytes = baos.toByteArray();
            final ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.write(bytes);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
