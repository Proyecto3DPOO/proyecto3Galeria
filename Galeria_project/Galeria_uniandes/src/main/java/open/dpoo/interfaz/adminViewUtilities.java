package main.java.open.dpoo.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.*;
import main.java.open.dpoo.model.usuarios.Author;
import main.java.open.dpoo.model.usuarios.Owner;


public class adminViewUtilities extends JPanel implements ActionListener{

    String[] options ={"Ver Inventario", "Ver subastas", "Empezar Subasta", "Agregar obra", "Ver Obra", "Ver obras", "Consignar Obra" };

    HashMap<String, JFrame> actions = new HashMap<>();

    JFrame frameActual ;

    private DefaultInterface defaultInterface = new DefaultInterface();
    private Color backgroundColor = getBackground();

    public  Piece piece; 

    private static final String SUBMIT = "submit";
    private static final String CLOSE = "close";

    private JTextField titleField;
    private JTextField idField;
    private JTextField typeField;
    private JTextField yearField;
    private JTextField originField;
    private JTextField descriptionField;
    private JTextField authorsField;
    private JTextField ownerDocumentField;
    private JCheckBox blockedCheckBox;
    private JCheckBox soldCheckBox;
    private JCheckBox auctionedCheckBox;
    private JTextField fixedValueField;
    private JTextField minValueField;
    private JTextField initialValueField;
    private JTextField entryDateField;
    private JTextField limitDateField;
    private JTextField startAuctionDateField;
    private JTextField endAuctionDateField;
    private JTextField locationField;
    private JTextField ownerName;
    private JTextField ownerPhone;
    private JTextField ownerLogin;
    private JTextField ownerPassword;
    private JButton submitButton;
    private JButton closeButton;


    public adminViewUtilities(JFrame frame){

        setSize(600, 600);

        actions.put("Ver Inventario", null);
        actions.put("Ver subastas", null);
        actions.put("Agregar obra", agregarObra());
        actions.put("Ver Obra", null);
        actions.put("Ver obras", null);

        }
    
    public JFrame agregarObra() {
        frameActual = new JFrame();
        frameActual.setLayout(new BorderLayout());
        
        frameActual.setTitle("Agregar Obra");
        frameActual.setSize(600, 600);
        frameActual.add(createFormPanel(), BorderLayout.CENTER);
    
        return frameActual;
    }

    public JPanel createFormPanel() {
    
        JPanel panel = new JPanel(new GridLayout(15, 2));

        titleField = addFormField(panel, "Title:");
        typeField = addFormField(panel, "Type:");
        idField = addFormField(panel, "ID:");
        yearField = addFormField(panel, "Year:");
        originField = addFormField(panel, "Origin:");
        descriptionField = addFormField(panel, "Description:");
        authorsField = addFormField(panel, "Authors (comma separated):");
        ownerDocumentField = addFormField(panel, "Owner Document:");
        blockedCheckBox = addFormCheckBox(panel, "Blocked?");
        soldCheckBox = addFormCheckBox(panel, "Sold?");
        auctionedCheckBox = addFormCheckBox(panel, "Auctioned?");
        fixedValueField = addFormField(panel, "Fixed Value:");
        minValueField = addFormField(panel, "Minimum Value:");
        initialValueField = addFormField(panel, "Initial Value:");
        entryDateField = addFormField(panel, "Entry Date (yyyy-MM-dd):");
        limitDateField = addFormField(panel, "Limit Date (yyyy-MM-dd):");
        startAuctionDateField = addFormField(panel, "Start Auction Date (yyyy-MM-dd):");
        endAuctionDateField = addFormField(panel, "End Auction Date (yyyy-MM-dd):");
        locationField = addFormField(panel, "Location (Exhibited/Bodega):");
        ownerName = addFormField(panel, "Owner Name:");
        ownerPhone = addFormField(panel, "Owner Phone:");
        ownerLogin = addFormField(panel, "Owner Login:");
        ownerPassword = addFormField(panel, "Owner Password:");

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setActionCommand(SUBMIT);

        
        closeButton = new JButton("Show Inventory");
        closeButton.addActionListener(this);
        closeButton.setActionCommand(CLOSE);

        panel.add(submitButton);
        panel.add(closeButton);

        return panel;
    }

    private JTextField addFormField(JPanel panel, String label) {
        JTextField textField = new JTextField();
        InterfazUtilities.textFieldModel(textField, backgroundColor, label);
        panel.add(textField);
        return textField;
    }

    private JCheckBox addFormCheckBox(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        JCheckBox checkBox = new JCheckBox();
        panel.add(jLabel);
        panel.add(checkBox);
        return checkBox;
    }

    private Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    }

    //TODO: completar los metodos de las otras opciones
    public JPanel createPieceInfoFrame(){
        setSize(400, 400);
        setLayout(new BorderLayout());

        JPanel panelNavegacion = new JPanel();
        panelNavegacion = defaultInterface.createNavigationPanel(piece);

        return panelNavegacion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String command = e.getActionCommand();
            if (SUBMIT.equals(command)) 
            {               
                String title = titleField.getText();
                long id = Long.parseLong(idField.getText());
                String type = typeField.getText();
                int year = Integer.parseInt(yearField.getText());
                @SuppressWarnings("deprecation")
                Locale origin = new Locale("es", originField.getText());
                String description = descriptionField.getText();
                List<Author> authorsList = Arrays.stream(authorsField.getText().split(","))
                        .map(AccountManager::searchByDoc)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(Author.class::isInstance)
                        .map(user -> (Author) user)
                        .collect(Collectors.toList());
                // ...

                Logger logger = Logger.getLogger(adminViewUtilities.class.getName());
                logger.warning("[AdministratorC::consignArtwork] Owner not found");
                boolean sold = soldCheckBox.isSelected();
                boolean auctioned = auctionedCheckBox.isSelected();
                boolean blocked = blockedCheckBox.isSelected();
                Owner owner = new Owner(ownerName.getText(), ownerDocumentField.getText(), ownerPhone.getText(), ownerLogin.getText(), ownerPassword.getText());
                Availability availability = new Availability(owner, blocked, sold, auctioned);

                double fixedValue = Double.parseDouble(fixedValueField.getText());
                double minValue = Double.parseDouble(minValueField.getText());
                double initialValue = Double.parseDouble(initialValueField.getText());
                Valoration valoration = new Valoration(fixedValue, minValue, initialValue);

                Date entryDate = parseDate(entryDateField.getText());
                Date limitDate = parseDate(limitDateField.getText());
                TemporalLocation storage = new TemporalLocation(entryDate, limitDate);

                piece = defaultInterface.collectInput(type, id, title, year, origin, authorsList, description, availability, valoration, storage);

                if (sold) {
                    Inventory.getInstance().getSold().add(piece);
                }
                if (auctioned) {
                    Date startAuctionDate = parseDate(startAuctionDateField.getText());
                    Date endAuctionDate = parseDate(endAuctionDateField.getText());
                    Inventory.getInstance().getAuction(startAuctionDate, endAuctionDate).addPiece(piece);
                }

                String location = locationField.getText().toLowerCase();
                if (location.contains("exhibited")) {
                    Inventory.getInstance().addPieceEXH(piece);
                } else {
                    Inventory.getInstance().addPieceBOD(piece);
                }
                Inventory.getInstance().getPieces().add(piece);
                frameActual.dispose();
                JPanel pieceInfo = defaultInterface.showPieceInfo(piece);
                MainInterface main = new MainInterface();
                frameActual= main;
                frameActual.add(pieceInfo, BorderLayout.CENTER);
                
            }
            if(command.equals(CLOSE)){
                frameActual.dispose();
                
                frameActual.setVisible(true);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    
}
