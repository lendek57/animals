package gui;

import simulation.Animal;
import simulation.IWorldMap;
import simulation.Vector2D;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private final IWorldMap map;

    public MapPanel(IWorldMap map) {
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setupLayout(graphics);
        drawPlants(graphics);
        drawAnimals(graphics);
        drawBorder(graphics);
    }

    private void setupLayout(Graphics graphics) {
        graphics.setColor(CustomColors.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(CustomColors.MAP_BACKGROUND_COLOR);
        graphics.fillRect(
                0, 0,
                map.getWidth() * LayoutConstants.MAP_SCALE,
                map.getHeight() * LayoutConstants.MAP_SCALE
        );
    }

    private Polygon createPlant() {
        int[] leafX = {
                0,
                LayoutConstants.MAP_SCALE / 4,
                LayoutConstants.MAP_SCALE,
                3 * LayoutConstants.MAP_SCALE / 4
        };
        int[] leafY = {
                LayoutConstants.MAP_SCALE,
                LayoutConstants.MAP_SCALE / 4,
                0,
                3 * LayoutConstants.MAP_SCALE / 4
        };
        return new Polygon(leafX, leafY, leafX.length);
    }

    private void drawPlants(Graphics graphics) {
        Polygon leaf = createPlant();
        graphics.setColor(CustomColors.PLANT_COLOR);
        Vector2D previousPosition = new Vector2D(0, 0);
        for (Vector2D position : map.getPlantsPositions().keySet()) {
            Vector2D move = position.subtract(previousPosition);
            leaf.translate(LayoutConstants.MAP_SCALE * move.x(), LayoutConstants.MAP_SCALE * move.y());
            graphics.fillPolygon(leaf);
            previousPosition = position;
        }
    }

    private void drawAnimals(Graphics graphics) {
        map.getAnimalsPositions().forEach((position, animals) -> {
            int energy = animals.stream().mapToInt(Animal::getEnergy).max().orElse(0);
            Color color = CustomColors.ENERGY_COLOR[Math.min(energy / 10, CustomColors.ENERGY_COLOR.length - 1)];
            graphics.setColor(color);
            graphics.fillOval(
                    LayoutConstants.MAP_SCALE * position.x(),
                    LayoutConstants.MAP_SCALE * position.y(),
                    LayoutConstants.MAP_SCALE,
                    LayoutConstants.MAP_SCALE
            );
            displayNoOfAnimals(graphics, color, animals.size(), position);
        });
    }

    private void displayNoOfAnimals(Graphics graphics, Color energyColor, int noOfAnimals, Vector2D position) {
        Color textColor = new Color(
                255 - energyColor.getRed(),
                255 - energyColor.getGreen(),
                255 - energyColor.getBlue()
        );
        String text = Integer.toString(noOfAnimals);
        int textWidth = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        int textHeight = graphics.getFontMetrics().getMaxAscent();
        int x = position.x() * LayoutConstants.MAP_SCALE + LayoutConstants.MAP_SCALE / 2;
        int y = position.y() * LayoutConstants.MAP_SCALE + LayoutConstants.MAP_SCALE / 2;
        graphics.setColor(textColor);
        graphics.drawString(text, x - textWidth / 2, y + textHeight / 2);
    }

    private void drawBorder(Graphics graphics) {
        graphics.setColor(CustomColors.BORDER_COLOR);
        graphics.drawRect(
                0,
                0,
                LayoutConstants.MAP_SCALE * map.getWidth(),
                LayoutConstants.MAP_SCALE * map.getHeight()
        );
    }
}
