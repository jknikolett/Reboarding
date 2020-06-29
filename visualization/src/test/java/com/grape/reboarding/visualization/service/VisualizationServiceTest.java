package com.grape.reboarding.visualization.service;

import com.grape.reboarding.visualization.dto.Chair;
import com.grape.reboarding.visualization.dto.ChairStatus;
import com.grape.reboarding.visualization.dto.Desk;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class VisualizationServiceTest {

    @Mock
    MessageSource messageSource;

    @InjectMocks
    VisualizationService visualizationService;

    @Test
    public void layout_image_created() throws IOException {
        List<Desk> desks = new ArrayList<>();
        Desk d = new Desk();
        d.setAvailable(true);
        d.setId(1);
        d.setRectangle(new Rectangle(10,10,30,30));

        Chair occupied = Chair.builder().position(new Point(10,10)).status(ChairStatus.OCCUPIED).build();
        Chair bookable = Chair.builder().position(new Point(30,10)).status(ChairStatus.BOOKABLE).build();
        Chair unBookable = Chair.builder().position(new Point(50,10)).status(ChairStatus.UN_BOOKABLE).build();
        Chair reserved = Chair.builder().position(new Point(70,10)).status(ChairStatus.RESERVED).build();
        List<Chair> chairs = new ArrayList<>();
        chairs.add(occupied);
        chairs.add(bookable);
        chairs.add(unBookable);
        chairs.add(reserved);
        d.setChairs(chairs);
        desks.add(d);

        Mockito.when(messageSource.getMessage(occupied.getStatus().getLegend(), null, Locale.ENGLISH))
                .thenReturn(occupied.getStatus().getLegend());

        Mockito.when(messageSource.getMessage(bookable.getStatus().getLegend(), null, Locale.ENGLISH))
                .thenReturn(bookable.getStatus().getLegend());

        Mockito.when(messageSource.getMessage(unBookable.getStatus().getLegend(), null, Locale.ENGLISH))
                .thenReturn(unBookable.getStatus().getLegend());

        Mockito.when(messageSource.getMessage(reserved.getStatus().getLegend(), null, Locale.ENGLISH))
                .thenReturn(reserved.getStatus().getLegend());

        Resource resource = visualizationService.createLayoutImage(desks, Locale.ENGLISH);
        BufferedImage image = ImageIO.read(resource.getInputStream());
        Assert.notNull(image, "Layout Image created");
    }

    @Test
    public void workplace_image_created() throws IOException{
        Point p = new Point(10,10);
        boolean hasPosition = true;
        Mockito.when(messageSource.getMessage(hasPosition ? "workspace.available" : "workspace.not.available", null, Locale.ENGLISH))
                .thenReturn(hasPosition ? "workspace.available" : "workspace.not.available");
        Resource resource = visualizationService.createWorkplaceImage(p, Locale.ENGLISH);
        BufferedImage image = ImageIO.read(resource.getInputStream());
        Assert.notNull(image, "Workplace image created");
    }

}
