import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Project{
    public static int NB_WAGONS = 4;
    public static int NB_JOUEUR = 2;
    public static int NB_ACTIONS = 2;
    public static int NB_BALLES = 6;
    public static int NB_BUTINS = 4;
    public static int NB_TOURS = 4;
    public static double NERVOSITE_MARSHALL = 0.3;
    public static int DEPLACEMNT_MARSHALL = 1;
    public static void main(String[] args) throws InterruptedException {
        Image homeScreen = new ImageIcon("Project/Pictures/homeScreen.jpg").getImage();
        ImagePanel startFrame = new ImagePanel(homeScreen);
    }
}

