package main.java.open.dpoo.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Availability;
import main.java.open.dpoo.model.galeria.piece.Impression;
import main.java.open.dpoo.model.galeria.piece.Painting;
import main.java.open.dpoo.model.galeria.piece.Photography;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.galeria.piece.Sculpture;
import main.java.open.dpoo.model.galeria.piece.TemporalLocation;
import main.java.open.dpoo.model.galeria.piece.Valoration;
import main.java.open.dpoo.model.galeria.piece.Video;
import main.java.open.dpoo.model.usuarios.Author;

public class DefaultInterface extends JFrame implements ActionListener {

    public final static String FIRST = "First";
    public final static String PREVIOUS = "Previous";
    public final static String NEXT = "Next";
    public final static String LAST = "Last";

    public  Integer posCurrentPieceShow;



    public DefaultInterface() {
        setTitle("Uniandes Gallery");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    public JPanel createOptions(String[] options, HashMap<String, JFrame> actions) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout( options.length, 1, 0, 10)); // Vertical space between buttons

        for (String option : options) {
            JButton button = new JButton(option);
            InterfazUtilities.buttonModel(button, getBackground(), null);
            button.setText(option);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame actionMethod = actions.get(option);
                    if (actionMethod != null) {
                        actionMethod.setVisible(true);
                    } else {
                        System.out.println("No action found for " + option);
                    }
                }
            });
            panel.add(button);
        }
        panel.setPreferredSize(new Dimension(200, options.length * 10));

        return panel;
    }

    public JPanel createNavigationPanel(Piece piece) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1, 0, 10)); // Vertical space between buttons

        JButton btnFirst;
        JButton btnPrevious;
        JButton btnNext;
        JButton btnLast;

        btnFirst = new JButton("<<");
        btnFirst.setActionCommand(FIRST);
        btnFirst.addActionListener(this);
        panel.add(btnFirst);

        btnPrevious = new JButton("<");
        btnPrevious.setActionCommand(PREVIOUS);
        btnPrevious.addActionListener(this);
        panel.add(btnPrevious);

        btnNext = new JButton(">");
        btnNext.setActionCommand(NEXT);
        btnNext.addActionListener(this);
        panel.add(btnNext);

        btnLast = new JButton(">>");
        btnLast.setActionCommand(LAST);
        btnLast.addActionListener(this);
        panel.add(btnLast);

        panel.setPreferredSize(new Dimension(200, 40));

        return panel;
    }
    
    public JPanel showPieceInfo(Piece piece){
        JPanel panel = new JPanel();
        setLayout(new GridLayout(0, 2)); // GridLayout con 2 columnas y filas automáticas

        
        // Añadir las etiquetas y valores de los atributos de la pieza
        addTextField("ID:", String.valueOf(piece.getId()), panel);
        addTextField("Title:", piece.getTitle(), panel);
        addTextField("Year:", String.valueOf(piece.getYear()), panel);
        addTextField("Origin:", piece.getOrigin().getDisplayCountry(), panel);
        addTextField("Authors:", piece.getAuthors().toString(), panel);
        addTextField("Description:", piece.getDescription(), panel);
        addTextField("Availability:", piece.getAvailability().toString(), panel);
        addTextField("Valoration:", piece.getValoration().toString(), panel);
        addTextField("Storage:", piece.getStorage().toString(), panel);

        return panel;

    }

    
    public Piece collectInput(String type, Long id, String title, int year, Locale origin, List<Author> authorsList, String description,
                            Availability availability, Valoration valoration, TemporalLocation storage) {
        try {
            Piece piece;
            if (type.toUpperCase() =="video".toUpperCase()) {
                piece = new Video(id, title, year, origin, authorsList, description, availability, valoration, storage, description, year, year);
                //TODO: Add video specific fields
            } else if (type.toUpperCase() =="painting".toUpperCase()) {
                piece = new Painting(id, title, year, origin, authorsList, description, availability, valoration, storage, description, year, year);
            } else if (type.toUpperCase() =="sculpture".toUpperCase()) {
                piece = new Sculpture( id,  title,  year,  origin, authorsList,  description, availability,  valoration,  storage, 545454,
                45454, 77,777, false);
            } else if (type.toUpperCase() =="photography".toUpperCase()) {
                piece = new Photography(id, title, year, origin, authorsList, description, availability, valoration, storage, year, year, description); 
            } else{
                piece = new Impression(id, title, year, origin, authorsList, description, availability, valoration, storage, "",
                "", "String size", "String quality", "String technique");
            }
            return piece;
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating piece: " + e.getMessage());
        }
    }
    private void addTextField(String label, String value, JPanel panel) {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setText(value);
        InterfazUtilities.textFieldModel(textField, getBackground(), label);
        panel.add(textField);
    }
    
    public void actionPerformed(ActionEvent pEvent) {
        String command = pEvent.getActionCommand();
        if( command.equals(FIRST)){
            showPieceInfo(Inventory.getInstance().getPieces().get(0));
        }else if (command.equals(NEXT)){
            showPieceInfo(Inventory.getInstance().getPieces().get(posCurrentPieceShow +1));
        }else if (command.equals(PREVIOUS)){
            showPieceInfo(Inventory.getInstance().getPieces().get(posCurrentPieceShow -1));
        }else if (command.equals(LAST)){
            showPieceInfo(Inventory.getInstance().getPieces().getLast());
        }
    }
    
}
