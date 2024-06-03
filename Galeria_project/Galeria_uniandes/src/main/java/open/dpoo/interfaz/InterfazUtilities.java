package main.java.open.dpoo.interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import main.java.open.dpoo.model.galeria.Auction;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.usuarios.Author;
import main.java.open.dpoo.model.usuarios.Purchaser;


public class InterfazUtilities {

    private static void addSectionTitle(JPanel panel, GridBagConstraints gbc, String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
    }

    private static void addAuctionInfo(JPanel panel, GridBagConstraints gbc, Auction auction) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        addLabelAndData(panel, gbc, "Start Date:", sdf.format(auction.getStartAuctionDate()));
        addLabelAndData(panel, gbc, "End Date:", sdf.format(auction.getEndAuctionDate()));
        addLabelAndData(panel, gbc, "Pieces in Auction:", piecesToString(auction.getPiecesAuction()));
        addLabelAndData(panel, gbc, "Purchasers:", purchasersToString(auction.getPurchasers()));
    }

    private static String authorsToString(List<Author> authors) {
        StringBuilder authorsStr = new StringBuilder();
        for (Author author : authors) {
            authorsStr.append(author.getName()).append(" (").append(author.getDocument()).append("), ");
        }
        if (authorsStr.length() > 0) {
            authorsStr.setLength(authorsStr.length() - 2);
        }
        return authorsStr.toString();
    }

    private static String piecesToString(List<Piece> pieces) {
        StringBuilder piecesStr = new StringBuilder();
        for (Piece piece : pieces) {
            piecesStr.append(piece.getTitle()).append(", ");
        }
        if (piecesStr.length() > 0) {
            piecesStr.setLength(piecesStr.length() - 2);
        }
        return piecesStr.toString();
    }

    private static String purchasersToString(List<Purchaser> purchasers) {
        StringBuilder purchasersStr = new StringBuilder();
        for (Purchaser purchaser : purchasers) {
            purchasersStr.append(purchaser.getName()).append(", ");
        }
        if (purchasersStr.length() > 0) {
            purchasersStr.setLength(purchasersStr.length() - 2);
        }
        return purchasersStr.toString();
    }

    private static void addLabelAndData(JPanel panel, GridBagConstraints gbc, String label, String data) {
        JLabel labelComponent = new JLabel(label);
        JLabel dataComponent = new JLabel(data);
        labelComponent.setForeground(Color.DARK_GRAY);
        dataComponent.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(labelComponent, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(dataComponent, gbc);
        gbc.gridy++;
    }
    
    public static void buttonModel(JButton button, Color backgroundColor, Color textColor){
        if (textColor == null) {
            textColor = Color.WHITE;
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(textColor); // Color azul
        button.setForeground(backgroundColor); // Color del texto blanco
        button.setUI(new GradientButtonUI());
    }

    public static JTextField textFieldModel(JTextField textField, Color backgroundColor, String text){
        
        if(text != null){
            textField.setBorder(new TitledBorder(text));

        }
        textField.setBackground(backgroundColor);
        textField.setEditable(true);
        textField.setPreferredSize(new Dimension(400, 50));  // Ajusta el ancho y la altura
        return textField;
    }

    static class GradientButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g.create();
            int width = c.getWidth();
            int height = c.getHeight();
            Color color1 = new Color(0, 150, 255);
            Color color2 = new Color(0, 100, 200);
            g2d.setPaint(new GradientPaint(0, 0, color1, 0, height, color2));
            g2d.fillRoundRect(0, 0, width, height, 20, 20); // Bordes redondeados
            g2d.dispose();
            super.paint(g, c);
        }
    }


}
