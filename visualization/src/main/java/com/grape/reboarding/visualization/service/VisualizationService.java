package com.grape.reboarding.visualization.service;

import com.grape.reboarding.visualization.dto.Chair;
import com.grape.reboarding.visualization.dto.ChairStatus;
import com.grape.reboarding.visualization.dto.Desk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@Service
public class VisualizationService {

    private static final Resource resource = new ClassPathResource("VGB_Layout_graphic_recolored.jpg");

    private static final Integer RADIUS = 4;
    private static final Integer DIAMETER = 2 * RADIUS;

    @Autowired
    private MessageSource messageSource;

    public Resource createWorkplaceImage(Point position, Locale locale) throws IOException {
        BufferedImage image = ImageIO.read(resource.getInputStream());
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        if(position.x > 0 && position.y > 0){
            drawCircle(graphics, Color.BLUE, position);
        }
        drawWorkplaceLegend(graphics, position, locale);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return new InputStreamResource(is);
    }

    public Resource createLayoutImage(List<Desk> desks, Locale locale) throws IOException {
        BufferedImage image = ImageIO.read(resource.getInputStream());
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        desks.stream().flatMap(d->d.getChairs().stream()).forEach(c->this.drawChair(c, graphics));
        drawDeskNumbers(desks, graphics);
        drawLegend(graphics, locale);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return new InputStreamResource(is);
    }

    private void drawChair(Chair chair, Graphics2D graphics){
        drawCircle(graphics, chair.getStatus().getColor(), chair.getPosition());
    }

    private void drawDeskNumbers(List<Desk> desks, Graphics2D graphics){
        graphics.setColor(Color.BLACK);
        desks.forEach(d->{
            String text = String.format("%d", d.getId());
            Dimension textDimension = getTextDimension(graphics, text);
            graphics.drawString(text, (int) (d.getRectangle().getCenterX() - textDimension.width / 2), (int) (d.getRectangle().getCenterY() + textDimension.height / 2 - 2));
        });
    }

    private Dimension getTextDimension(Graphics2D graphics, String text){
        Font font = new Font("Default", Font.BOLD, 10);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int hgt = metrics.getHeight();
        int adv = metrics.stringWidth(text);
        return new Dimension(adv, hgt);
    }

    private void drawWorkplaceLegend(Graphics2D graphics, Point position, Locale locale){
        boolean hasPosition = position.x > 0 && position.y > 0;
        graphics.setColor(Color.BLACK);
        String legend = messageSource.getMessage(hasPosition ? "workspace.available" : "workspace.not.available", null, locale);
        graphics.drawString(legend, 930, 50);
        graphics.setColor(Color.BLUE);
        Dimension textDimension = getTextDimension(graphics, legend);
        graphics.drawLine(920,55, 920 + textDimension.width + 30, 55);
        if(hasPosition){
            graphics.drawLine(920,55, position.x, position.y);
        }
    }

    private void drawLegend(Graphics2D graphics, Locale locale){
        Point legendPosition = new Point(930,100);
        drawLegend(graphics, legendPosition, ChairStatus.BOOKABLE, locale);
        legendPosition.setLocation(legendPosition.x, legendPosition.y + 15);
        drawLegend(graphics, legendPosition, ChairStatus.RESERVED, locale);
        legendPosition.setLocation(legendPosition.x, legendPosition.y + 15);
        drawLegend(graphics, legendPosition, ChairStatus.OCCUPIED, locale);
        legendPosition.setLocation(legendPosition.x, legendPosition.y + 15);
        drawLegend(graphics, legendPosition, ChairStatus.UN_BOOKABLE, locale);
    }

    private void drawLegend(Graphics2D graphics, Point position, ChairStatus chairStatus, Locale locale){
        drawCircle(graphics, chairStatus.getColor(), position);
        graphics.setColor(Color.BLACK);
        graphics.drawString(messageSource.getMessage(chairStatus.getLegend(), null, locale), position.x + DIAMETER, position.y + RADIUS);
    }

    private void drawCircle(Graphics2D graphics, Color color, Point position){
        graphics.setColor(color);
        graphics.fillOval(position.x-RADIUS,position.y-RADIUS,DIAMETER,DIAMETER);
    }

}
