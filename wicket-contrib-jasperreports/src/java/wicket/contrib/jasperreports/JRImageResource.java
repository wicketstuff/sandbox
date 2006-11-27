/*
 * $Id: JRImageResource.java 635 2006-03-28 11:49:11 +0000 (Tue, 28 Mar 2006)
 * joco01 $ $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.jasperreports;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wicket.WicketRuntimeException;

/**
 * Resource class for jasper reports PDF resources.
 *
 * @author Eelco Hillenius
 * @author Justin Lee
 */
public class JRImageResource extends JRResource {
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(JRImageResource.class);
    /**
     * Type of image (one of BufferedImage.TYPE_*).
     */
    private int type = BufferedImage.TYPE_INT_RGB;
    /**
     * The zoom ratio used for the export. The default value is 1.
     */
    private float zoomRatio = 1;
    /**
     * the image type. The default value is 'png'.
     */
    private String format = "png";

    /**
     * Construct without a report. You must provide a report before you can use this resource.
     */
    public JRImageResource() {
        super();
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRImageResource(InputStream report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRImageResource(JasperReport report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRImageResource(URL report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRImageResource(File report) {
        super(report);
    }

    @Override
    public final JRAbstractExporter newExporter() {
        throw new UnsupportedOperationException(
            "this method is not used in this implementation");
    }

    @Override
    protected ResourceState getResourceState() {
        try {
            long t1 = System.currentTimeMillis();
            // get a print instance for exporting
            JasperPrint print = newJasperPrint();

            // get a fresh instance of an exporter for this report
            JRGraphics2DExporter exporter = new JRGraphics2DExporter();

            // create an image object
            int width = (int)(print.getPageWidth() * getZoomRatio());
            int height = (int)(print.getPageHeight() * getZoomRatio());
            BufferedImage image = new BufferedImage(width, height, type);
            exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, image.getGraphics());
            exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, new Float(zoomRatio));
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

            // execute the export and return the trapped result
            exporter.exportReport();
            final byte[] data = toImageData(image);
            return new ResourceState() {
                @Override
                public int getLength() {
                    return data.length;
                }

                @Override
                public byte[] getData() {
                    return data;
                }

                @Override
                public String getContentType() {
                    return "image/" + format;
                }
            };
        }
        catch(JRException e) {
            throw new WicketRuntimeException(e);
        }
    }

    /**
     * @param image The image to turn into data
     *
     * @return The image data for this dynamic image
     */
    protected byte[] toImageData(final BufferedImage image) {
        try {
            // Create output stream
            final ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Get image writer for format
            final ImageWriter writer = ImageIO.getImageWritersByFormatName(format).next();

            // Write out image
            writer.setOutput(ImageIO.createImageOutputStream(out));
            writer.write(image);

            // Return the image data
            return out.toByteArray();
        } catch(IOException e) {
            throw new WicketRuntimeException("Unable to convert dynamic image to stream", e);
        }
    }

    @Override
    public String getContentType() {
        return "image/" + format;
    }

    /**
     * Gets the zoom ratio.
     *
     * @return the zoom ratio used for the export. The default value is 1
     */
    public float getZoomRatio() {
        return zoomRatio;
    }

    /**
     * Sets the zoom ratio.
     *
     * @param ratio the zoom ratio used for the export. The default value is 1
     */
    public void setZoomRatio(float ratio) {
        this.zoomRatio = ratio;
    }

    /**
     * Gets the image type.
     *
     * @return the image type. The default value is 'png'
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the image type.
     *
     * @param imageType the image type. The default value is 'png'
     */
    public void setFormat(String imageType) {
        this.format = imageType;
    }

    /**
     * Gets type of image (one of BufferedImage.TYPE_*).
     *
     * @return type of image
     */
    public int getType() {
        return type;
    }

    /**
     * Sets type of image (one of BufferedImage.TYPE_*).
     *
     * @param imageType type of image
     */
    public void setType(int imageType) {
        this.type = imageType;
    }

    @Override
    public String getExtension() {
        return getFormat();
    }
}