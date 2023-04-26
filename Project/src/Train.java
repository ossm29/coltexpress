import javax.sound.midi.Soundbank;
import javax.swing.plaf.basic.BasicTableUI;
import java.util.ArrayList;

public class Train extends Observable{
    /**attributs*/
    private ArrayList<Wagon> wagons;
    private int trainSize;
    private int Height = 400;
    private int Width = 600;
    private ArrayList<Bandit> bandits;
    private ArrayList<Butin> butins;
    private Marshall marshall;
    private int compteElements; //sert pour les id
    /**constructeur*/
    public Train(){
        this.wagons = new ArrayList<>();
        this.bandits = new ArrayList<>();
        this.butins = new ArrayList<>();
        this.trainSize = 0;
        compteElements = 0;
    }
    public Train(ArrayList<Wagon> w){
        this.wagons = w;
        this.trainSize = w.size();
        this.wagons = new ArrayList<>();
        this.bandits = new ArrayList<>();
        for(Wagon v : w) {
            for (Bandit e : v.getBandits()) {
                this.bandits.add(e);
            }
            for (Butin e : v.getButins()) {
                this.butins.add(e);
            }
        }
    }
    public Train(int n){
        this.wagons = new ArrayList<>();
        this.butins = new ArrayList<>();
        this.bandits = new ArrayList<>();
        this.trainSize = 0;
        for (int i = 0; i < n; i++) {
            this.addWagonByName("Wagon");
        }
    }
    public Train(int nbJoueur, int nbWagon){
        ArrayList<String> names = new ArrayList<>();
        names.add("Bob");
        names.add("Baxter");
        names.add("Jane");
        names.add("Walter");
        names.add("Jesse");
        names.add("Will");
        names.add("Charlie");
        names.add("Annie");
        names.add("Tom");
        names.add("Doc");
        names.add("Billy");
        names.add("Jane");
        this.wagons = new ArrayList<>();
        this.butins = new ArrayList<>();
        this.bandits = new ArrayList<>();
        this.trainSize = 0;
        for(int i = 0; i < nbWagon; i++){
            ArrayList ba = new ArrayList();
            ArrayList bu = new ArrayList();
            Wagon w = new Wagon(i, "Wagon",ba,bu,this);
            this.addWagon(w);
        }
        for(int i = 0; i < nbJoueur; i++){
            String name = names.get(i);
            Bandit b = new Bandit(name, this);
            this.addBandit(b, b.getPosition());
        }
        this.addMarshall();
    }
    /**getters de éléments*/
    public Bandit getBandit(int n) {
        return this.bandits.get(n);
    }
    public int getNumberBandits(){return this.bandits.size();}
    public Butin getButin(int n){
        return this.butins.get(n);
    }
    /**adder getter remover marshall*/
    public void addMarshall(){
        Marshall marshall = new Marshall(-1, 0, this);
        this.marshall = marshall;
        this.wagons.get(0).addMarshall(marshall);

    }
    public Marshall getMarshall(){return this.marshall;}
    public void removeMarshall(Marshall e, int position){
        this.wagons.get(position).removeMarshall(e);
    }
    /**getter des dimensions*/
    public int getHeight(){return this.Height;}
    public int getWidth(){return this.Width;}
    /**adder de wagon*/
    public void addWagon(Wagon w){
        this.wagons.add(w);
        w.setRang(this.trainSize);
        this.trainSize++;
        for (Bandit e : w.getBandits()) {
            this.bandits.add(e);
        }
        for (Butin e : w.getButins()) {
            this.butins.add(e);
        }
        notifyObservers();
    }
    public void addWagonByName(String type){
        ArrayList<Bandit> banditss = new ArrayList<>();
        ArrayList<Butin> butinss = new ArrayList<>();
        Wagon tmp = new Wagon(this.trainSize, type, banditss, butinss, this);
        this.addWagon(tmp);
        notifyObservers();
    }
    /**remover de wagon*/
    public void removeWagon(Wagon w){
        this.wagons.remove(w);
        this.trainSize--;
        notifyObservers();
        for (Bandit e : w.getBandits()) {
            this.bandits.remove(e);
        }
        for (Butin e : w.getButins()) {
            this.butins.remove(e);
        }
    }
    /**getter de wagons*/
    public ArrayList<Wagon> getWagons(){
        return this.wagons;
    }
    /**getter de size*/
    public int getTrainSize(){return this.trainSize;}
    public Wagon getWagon(int n){
        return this.wagons.get(n);
    }
    /**printer de train*/
    public void printTrain() {
        System.out.print("<");
        int tmp = 0;
        for (Wagon e : this.wagons) {
            if (tmp > 0) {
                System.out.print("-");
            } else {
                tmp++;
            }
            e.printWagon();
        }
        System.out.println("");
    }
    /**compte rendu*/
    public void compteRendu(){
        ArrayList<Bandit> banditsprime = this.bandits;
        ArrayList<Bandit> res = new ArrayList();
        while(!banditsprime.isEmpty()) {
            Bandit max = banditsprime.get(0);
            for (Bandit e : banditsprime) {
                if(e.getValueButin() > max.getValueButin()){
                    max = e;
                }
            }
            res.add(max);
            banditsprime.remove(max);
        }
        for(int i = 0; i < res.size(); i++){
            Bandit local = res.get(i);
            if(i == 0){
                System.out.println("Le bandit " + local.getName() + " est arrivé premier avec un butin de " + local.getValueButin() + "$!");
            }
            else if(i == 1){
                System.out.println("Le bandit " + local.getName() + " est arrivé second avec un butin de " + local.getValueButin() + "$!");
            }
            else if(i == 2){
                System.out.println("Le bandit " + local.getName() + " est arrivé troisième avec un butin de " + local.getValueButin() + "$!");

            }
            else{
                System.out.println("Le bandit " + local.getName() + " est arrivé en " + i + " position avec un butin de " + local.getValueButin() + "$.");
            }
        }
    }
    /**adder d'élément à une certaine position*/
    public void addBandit(Bandit e, int n){
        this.wagons.get(n).addBandit(e);
        this.bandits.add(e);
        notifyObservers();
    }
    public void addBanditWTparametres(Bandit e, int n){
        this.wagons.get(n).addBandit(e);
        notifyObservers();
    }
    public void addButin(Butin e, int n){
        this.wagons.get(n).addButin(e);
        this.butins.add(e);
        notifyObservers();
    }
    public void addButinWTparametres(Butin e, int n){
        this.wagons.get(n).addButin(e);
        notifyObservers();
    }
    public void removeBandit(Bandit e, int n){
        this.wagons.get(n).removeBandit(e);
        this.bandits.remove(e);
        notifyObservers();
    }
    public void removeBanditWTparametres(Bandit e, int n){
        this.wagons.get(n).removeBandit(e);
        notifyObservers();
    }
    public void removeButin(Butin e, int n){
        this.wagons.get(n).removeButin(e);
        this.butins.remove(e);
        notifyObservers();
    }
    public void removeButinWTparametres(Butin e, int n){
        this.wagons.get(n).removeButin(e);
        notifyObservers();
    }
    public int getCompteElements(int n){
        return this.compteElements;
    }
    public void incrCompteElement(){this.compteElements++;}
    public void decrCompteElement(){this.compteElements--;}
    /**tests*/
    public static void main(String[] args) {
    }
}
