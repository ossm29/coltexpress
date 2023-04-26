

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

interface Observer {
    /**méthode update*/
    public void update();
}

abstract class Observable {
    /**attributs*/
    private ArrayList<Observer> observers;
    /**constructeur*/
    public Observable() {
        this.observers = new ArrayList<Observer>();
    }

    /**ajoute  un observer à l'objet observable*/
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**méthode de notification de changement pour les observer*/
    public void notifyObservers() {
        for(Observer o : observers) {
            o.update();
        }
    }
}

class CVue {
    private JFrame frame;
    private int width;
    private int height;
    private vueTrain vuetrain;
    private vueButtons vuebuttons;
    private vueNarrateur vuenarrateur;
    public CVue(Train train){
        this.width = train.getWidth();
        this.height = train.getHeight();
        /**frame*/
        this.frame = new JFrame();
        this.frame.setTitle("Colt Express");
        /**icon*/
        ImageIcon logo = new ImageIcon("Project/Pictures/cowboyHat.jpeg");
        this.frame.setIconImage(logo.getImage());
        /**layout*/
        this.frame.setLayout(new GridLayout(3,1));
        /**ajouts des JPanels*/
        this.vuetrain = new vueTrain(train);
        this.frame.add(this.vuetrain);
        this.vuebuttons = new vueButtons(train);
        this.frame.add(this.vuebuttons);
        this.vuenarrateur = new vueNarrateur(train);
        this.frame.add(this.vuenarrateur);
        /**final lines*/
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void update(){
        this.vuetrain.update();
    }
    /**getters*/
    public JFrame getFrame(){
        return this.frame;
    }
}

class vueTrain extends JPanel implements Observer{
    /**attributs*/
    private Train train;
    private ArrayList<JLabel> labelsTOP = new ArrayList<>();
    private ArrayList<JLabel> labelsBOT = new ArrayList<>();
    /**constructeur*/
    public vueTrain(Train train){
        super();
        this.train = train;
        train.addObserver(this);
        this.setLayout(new GridLayout(2, 2*this.train.getTrainSize() + 1));
        int cpt = 0;
        JLabel label = new JLabel("   [");
        this.add(label);
        for(Wagon w : this.train.getWagons()){
            String string = new String();
            for(Bandit e : w.getBandits()){
                if(e.getRoof()) {
                    string = string +"\n" + e.toString();
                }
            }
            for(Butin e : w.getButins()){
                if(e.getRoof()) {
                    string = string +"\n" + e.toString();
                }
            }
            label = new JLabel(string);
            this.labelsTOP.add(label);
            if(cpt != 0 || cpt != this.train.getTrainSize()){
                this.add(new JLabel(" ][ "));
            }
            cpt++;
            this.add(label);
        }
        this.add(new JLabel(" ]"));

        cpt = 0;
        label = new JLabel("<[");
        this.add(label);
        for(Wagon w : this.train.getWagons()){
            String string = new String();
            for(Bandit e : w.getBandits()){
                if(!e.getRoof()) {
                    string = string +"\n"+ e.toString();
                }
            }
            for(Marshall e : w.getMarshall()){
                if(!e.getRoof()) {
                    string = string +"\n"+ e.toString();
                }
            }
            for(Butin e : w.getButins()){
                if(!e.getRoof()) {
                    string = string +"\n"+ e.toString();
                }
            }
            label = new JLabel(string);
            this.labelsBOT.add(label);
            if(cpt != 0 || cpt != this.train.getTrainSize()){
                this.add(new JLabel(" ][ "));
            }
            cpt++;
            this.add(label);
        }
        this.add(new JLabel(" ]"));
    }
    /**implements observer : update()*/
    public void update(){
        int i;
        i = 0;
        for(Wagon w : this.train.getWagons()){
            String string = "";
            for(Bandit e : w.getBandits()){
                if(e.getRoof()) {
                    string = string + e.toString();
                }
            }
            for(Butin e : w.getButins()){
                if(e.getRoof()) {
                    string = string + e.toString();
                }
            }
            this.labelsTOP.get(i).setText(string);
            i++;
        }
        i = 0;
        for(Wagon w : this.train.getWagons()){
            String string = "";
            for(Bandit e : w.getBandits()){
                if(!e.getRoof()) {
                    string = string + e.toString();
                }
            }
            for(Marshall e : w.getMarshall()){
                string = string +"\n"+ e.toString();
            }
            for(Butin e : w.getButins()){
                string = string + e.toString();

            }
            this.labelsBOT.get(i).setText(string);
            i++;
        }
    }
}

class vueButtons extends JPanel{
    /**attributs*/
    private Train train;
    /**constructeur*/
    public vueButtons(Train train){
        this.train = train;
        JButton boutonAction = new JButton("Planification [" + String.valueOf(Project.NB_ACTIONS) + "]");// --|
        //////////////////////////////////////////////                                                    |
        JButton showPlayer = new JButton(this.train.getBandit(0).getName());//                         |
        this.add(showPlayer);//                                                                           |
        Controleur ctrlPlayer = new Controleur(this.train, -1, boutonAction, showPlayer);//         |
        showPlayer.addActionListener(ctrlPlayer);//                                                       |
        //////////////////////////////////////////////                                                    |
        this.add(boutonAction);//                                                                      <--|
        Controleur ctrlAction = new Controleur(this.train, 0, boutonAction, showPlayer);
        boutonAction.addActionListener(ctrlAction);
        //////////////////////////////////////////////
        JButton boutonAvant = new JButton("AVANT");
        this.add(boutonAvant);
        Controleur ctrlAVANT = new Controleur(this.train, 1, boutonAction, showPlayer);
        boutonAvant.addActionListener(ctrlAVANT);
        //////////////////////////////////////////////
        JButton boutonARRIERE = new JButton("ARRIERE");
        this.add(boutonARRIERE);
        Controleur ctrlARRIERE = new Controleur(this.train, 2, boutonAction, showPlayer);
        boutonARRIERE.addActionListener(ctrlARRIERE);
        //////////////////////////////////////////////
        JButton boutonHAUT = new JButton("HAUT");
        this.add(boutonHAUT);
        Controleur ctrlRHAUT = new Controleur(this.train, 3, boutonAction, showPlayer);
        boutonHAUT.addActionListener(ctrlRHAUT);
        //////////////////////////////////////////////
        JButton buttonBAS = new JButton("BAS");
        this.add(buttonBAS);
        Controleur ctrlBAS = new Controleur(this.train, 4, boutonAction, showPlayer);
        buttonBAS.addActionListener(ctrlBAS);
        //////////////////////////////////////////////
        JButton boutonShoot = new JButton("SHOOT");
        this.add(boutonShoot);
        Controleur ctrlShoot = new Controleur(this.train, 6, boutonAction, showPlayer);
        boutonShoot.addActionListener(ctrlShoot);
        //////////////////////////////////////////////
        JButton boutonBraquage = new JButton("BRAQUAGE");
        this.add(boutonBraquage);
        Controleur ctrlBraquage = new Controleur(this.train, 5, boutonAction, showPlayer);
        boutonBraquage.addActionListener(ctrlBraquage);
    }
}

class Controleur implements ActionListener{
    /**attributs*/
    static private int state = 0;
    private int mode; //valeur pour désigner l'action : 1-AVANT, 2-ARRIERE, 3-HAUT
    private Train train;//                              4-BAS, 5-BRAQUAGE
    private JButton action;//
    private JButton showPlayer;
    static private ArrayList<ArrayList<Integer>> buffer = new ArrayList<>();
    static private int actualPlayer = 0;
    static private int actionToDo = 0;
    static private boolean shoot = false;
    static private int nbRounds = Project.NB_TOURS;
    static private boolean marshallPlay = false;
    /**constructor*/
    public Controleur(Train train, int mode, JButton action, JButton showPlayer){
        if(buffer.size() == 0) {
            for(int i = 0; i < Project.NB_JOUEUR; i++){
                buffer.add(new ArrayList<>());
            }
        }
        this.train = train;
        this.mode = mode;
        this.action = action;
        this.showPlayer = showPlayer;
        this.action.setText("Planification [" + String.valueOf(Project.NB_ACTIONS - actionToDo) + "]");
    }
    /**methode actionPerformed*/
    public void actionPerformed(ActionEvent e){
        if(nbRounds == 0){
            this.train.compteRendu();
            vueNarrateur.event = "fin du jeu !";
            exit(0);
        }
        else if(marshallPlay && this.mode == 0){
            vueNarrateur.event = this.train.getMarshall().randomDeplacement();
            actionToDo  = (actionToDo + 1)%(Project.DEPLACEMNT_MARSHALL + 1);
            if(actionToDo == Project.DEPLACEMNT_MARSHALL) {
                actionToDo = 0;
                marshallPlay = false;
                this.showPlayer.setText(this.train.getBandit(0).getName());
                this.action.setText("Planification [" + String.valueOf(Project.NB_ACTIONS) + "]");
                nbRounds--;
                for (int k = 0; k < this.train.getNumberBandits(); k++) {
                    System.out.println("le bandit " + this.train.getBandit(k).getName() + " a ramassé " + this.train.getBandit(k).getValueButin() + "$");
                }
                System.out.println("il reste " + nbRounds + " tours");
            }
        }
        else if(state == 0){
            if(actualPlayer < Project.NB_JOUEUR){
                if(actionToDo < Project.NB_ACTIONS) {
                    if (this.mode == 1 || this.mode == 2 || this.mode == 3 || this.mode == 4 || this.mode == 5) {
                        this.action.setText("Planification [" + String.valueOf(Project.NB_ACTIONS - actionToDo - 1) + "]");
                        if(shoot && this.mode != 5){
                            buffer.get(actualPlayer).add(this.mode + 6);
                        }
                        else{
                            buffer.get(actualPlayer).add(this.mode);
                        }
                        actionToDo = (actionToDo + 1) % (Project.NB_ACTIONS + 1);
                        if (actionToDo == Project.NB_ACTIONS) {
                            actualPlayer = (actualPlayer + 1) % (Project.NB_JOUEUR + 1);
                            actionToDo = 0;
                            this.action.setText("Planification [" + String.valueOf(Project.NB_ACTIONS) + "]");
                        }
                        if(actualPlayer == Project.NB_JOUEUR){
                            state = 1;
                            actualPlayer = 0;
                            this.action.setText("       Action!       ");
                            this.showPlayer.setText(this.train.getBandit(0).getName());
                        }
                        else{
                            this.showPlayer.setText(this.train.getBandit(actualPlayer).getName());
                        }
                        shoot = false;
                    }
                    else if(this.mode == 6){
                        shoot = true;
                    }
                }
            }
        }
        else{
            if(actualPlayer < Project.NB_JOUEUR){
                if (actionToDo < Project.NB_ACTIONS) {
                    if (this.mode == 0) {
                        int i = buffer.get(actualPlayer).get(actionToDo);
                        if (i == 1) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).deplacement(Direction.AVANT);
                        } else if (i == 2) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).deplacement(Direction.ARRIERE);
                        } else if (i == 3) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).deplacement(Direction.HAUT);
                        } else if (i == 4) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).deplacement(Direction.BAS);
                        } else if (i == 5) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).braquage();
                        } else if (i == 7) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).shoot(Direction.AVANT);
                        } else if (i == 8) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).shoot(Direction.ARRIERE);
                        } else if (i == 9) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).shoot(Direction.HAUT);
                        } else if (i == 10) {
                            vueNarrateur.event =  this.train.getBandit(actualPlayer).shoot(Direction.BAS);
                        }
                        actualPlayer = (actualPlayer + 1)%(Project.NB_JOUEUR + 1);

                        if(actualPlayer == Project.NB_JOUEUR){
                            actualPlayer = 0;
                            actionToDo = (actionToDo + 1) % (Project.NB_ACTIONS + 1);
                            if (actionToDo == Project.NB_ACTIONS) {
                                buffer = new ArrayList<>();
                                for (int k = 0; k < Project.NB_JOUEUR; k++) {
                                    buffer.add(new ArrayList<>());
                                }
                                actionToDo = 0;
                                state = 0;
                                marshallPlay = true;
                                this.showPlayer.setText("Marshall");
                            }
                            else{
                                this.showPlayer.setText(this.train.getBandit(actualPlayer).getName());
                            }
                        }
                        else{
                            this.showPlayer.setText(this.train.getBandit(actualPlayer).getName());
                        }
                    }
                }
            }
        }
        this.train.notifyObservers();
    }
}

class vueNarrateur extends JPanel implements Observer{
        private JLabel nar;
        static String event;
        Train train;
        public vueNarrateur(Train train){
            this.train = train;
            train.addObserver(this);
            this.nar = new JLabel("bonjour");
            this.event = "";
            this.add(nar);
        }
    public void update(){
        this.nar.setText(event);
    }
}