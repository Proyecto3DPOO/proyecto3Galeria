package main.java.open.dpoo.interfaz;

import javax.sound.midi.SysexMessage;
import javax.swing.*;

import main.java.open.dpoo.controller.AccountManager;
import main.java.open.dpoo.model.galeria.Inventory;
import main.java.open.dpoo.model.galeria.piece.Piece;
import main.java.open.dpoo.model.usuarios.BaseUser;

import java.awt.*;
import java.util.HashMap;

 

public class MainInterface extends JFrame {

    public  BaseUser user;

    public  AccountManager accountManager = new AccountManager();


    public DefaultInterface defaultInterface = new DefaultInterface();


    public MainInterface(){
        setTitle("Uniandes Gallery");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        if(RegisterDialog.rol == "Admin"){
            adminViewUtilities admin = new adminViewUtilities(this);
            admin.frameActual = this;
            if(Inventory.getInstance().getPieces().size() > 0){
                add(defaultInterface.showPieceInfo(Inventory.getInstance().getPieces().get(0)), BorderLayout.CENTER);
            }
            JPanel options = defaultInterface.createOptions(admin.options, admin.actions);
            add(options, BorderLayout.EAST);
            JPanel navegacion = admin.createPieceInfoFrame();
            add(navegacion, BorderLayout. NORTH);
            setVisible(true);

            
        }else if(RegisterDialog.rol == "Cajero"){

            String[] options = {};
            HashMap<String, JFrame> actions = new HashMap<>();
            
            JPanel panel = defaultInterface.createOptions(options, actions);
            add(panel, BorderLayout.EAST);

        }else if(RegisterDialog.rol == "Comprador"){

            String[] options = {DefaultInterface.FIRST, DefaultInterface.PREVIOUS, DefaultInterface.NEXT, DefaultInterface.LAST};
            HashMap<String, JFrame> actions = new HashMap<>();


            JPanel panel = defaultInterface.createOptions(options, actions);
            add(panel, BorderLayout.EAST);

        }else if(RegisterDialog.rol == "Propietario"){

            String[] options = {DefaultInterface.FIRST, DefaultInterface.PREVIOUS, DefaultInterface.NEXT, DefaultInterface.LAST};
            HashMap<String, JFrame> actions = new HashMap<>();


            JPanel panel = defaultInterface.createOptions(options, actions);
            add(panel, BorderLayout.EAST);

        }else if(RegisterDialog.rol == "Author"){
            
        }

    }

    public BaseUser giveUser(String name, String password){
        BaseUser userq = AccountManager.login(name, password);
            user = userq;
        return userq;

    }

    public void registerUser(BaseUser user){
        AccountManager.register(user);
    }
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginDialog = new LoginForm();
            loginDialog.setVisible(true);
        });
    }
}