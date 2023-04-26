import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;

public class StartFrame extends JFrame {
    public StartFrame(Image image){
        /**frame*/
        this.setTitle("Colt Express");
        /**content pane*/
        this.setContentPane(new ImagePanel(image));
        /**size*/
        this.setSize(new Dimension(640,480));
        /**icon*/
        ImageIcon logo = new ImageIcon("Project/Pictures/cowboyHat.jpeg");
        this.setIconImage(logo.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}


class ImagePanel extends JPanel {
    static StartFrame frame =  new StartFrame(new ImageIcon("Project/Pictures/homeScreen.jpg").getImage());
    private Image image;

    public ImagePanel(Image image){
        this.image = image;
        //////////////////////////////////////////////////////////////
        JButton classic = new JButton("Classic");
        add(classic);
        StartControleur ctrlClassic = new StartControleur(1);
        classic.addActionListener(ctrlClassic);
        //////////////////////////////////////////////////////////////
        JButton Parametres = new JButton("Paramètres");
        add(Parametres);
        StartControleur ctrlParametres = new StartControleur(2);
        Parametres.addActionListener(ctrlParametres);
        //////////////////////////////////////////////////////////////
        JButton MarshallFou = new JButton("Marshall Fou");
        add(MarshallFou);
        StartControleur ctrlMarshallFou = new StartControleur(3);
        MarshallFou.addActionListener(ctrlMarshallFou);
    }
    public void openStartFrame() {
        new StartFrame(this.image);
    }
    public void paintComponent(Graphics g) {
        g.drawImage(this.image, 0, 0, null);
    }
}

class StartControleur implements ActionListener {
    int mode;
    public StartControleur(int n){
        this.mode = n;
    }
    public void actionPerformed(ActionEvent e){
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        if(this.mode == 2){
            System.out.println("entrez le nombre d'action par joueur par tour.");
            Project.NB_ACTIONS = scanner.nextInt();
            System.out.println("entrez le nombre de tours.");
            Project.NB_TOURS = scanner.nextInt();
            System.out.println("entrez le nombre de butins max par wagons.");
            Project.NB_BUTINS = scanner.nextInt();
            System.out.println("entrez la nervosité du marshall, entre 0 et 1, sous le formet \"0,5\" par exemple.");
            Project.NERVOSITE_MARSHALL = scanner.nextDouble();
            System.out.println("entrez le nombre de déplacements du Marshall à la fin de chaque tour.");
            Project.DEPLACEMNT_MARSHALL = scanner.nextInt();

            }
        else if(this.mode == 3){
            Project.NB_BALLES = 100;
            Project.NERVOSITE_MARSHALL = 0.9;
            Project.DEPLACEMNT_MARSHALL = 2;
            Project.NB_ACTIONS = 6;
        }
        System.out.println("Super, combien de joueurs êtes-vous ? Vous ne pouvez pas dépasser 12.");
        flag = true;
        while(flag){
            int rep = scanner.nextInt();
            flag = false;
            if (rep <= 12 && rep > 0) {
                Project.NB_JOUEUR = rep;
            }
            else {
                System.out.println("vous n'avez pas entré de nombre, ou alors avez dépasser 12.");
                flag = true;
            }
        }
        ImagePanel.frame.setVisible(false);
        EventQueue.invokeLater(() -> {
            Train t = new Train(Project.NB_JOUEUR, Project.NB_WAGONS);
            CVue vue = new CVue(t);
        });
    }
}