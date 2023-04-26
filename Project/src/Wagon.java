import java.util.ArrayList;

public class Wagon {
    /**attributs*/
    private int rang;
    private String type;
    private ArrayList<Bandit> bandits;
    private ArrayList<Butin> butins;
    private ArrayList<Marshall> marshall = new ArrayList<>();
    private ArrayList<String> butinsType = new ArrayList();
    private Train train;
    /**constructeur*/
    public Wagon(int rang, String type, ArrayList<Bandit> bandits, ArrayList<Butin> butins, Train train){
        this.train = train;
        this.rang = rang;
        this.type = type;
        this.bandits = bandits;
        this.butins = butins;
        this.butinsType.add("bourse");
        this.butinsType.add("bijoux");
        if(this.rang != 0) {this.setRandomButins();}
        else{
            Butin magot = new Butin(1000, "magot", 0, this.rang);
            this.addButin(magot);
        }
    }
    /**print un wagon dans la console*/
    public void printWagon(){
        System.out.print("[" + type);
        for(Bandit e : this.bandits){
            e.printElement();
        }
        for(Butin e : this.butins){
            e.printElement();
        }
        for(Marshall e : this.marshall){
            e.printElement();
        }
        System.out.print("]");
    }
    /**renvoie le rang du wagon dans le train*/
    public int getRang(){return this.rang;}
    /**set le rang du wagon*/
    public void setRang(int n){this.rang = n;}
    /**renvoie le nom du wagon (locomotive, voiture, etc.)*/
    public String getType(){return this.type;}
    /**ajoute un objet Element au wagon*/
    public void addBandit(Bandit e){
        this.bandits.add(e);
        e.setPosition(this.rang);
        this.train.incrCompteElement();
    }
    public void addButin(Butin e){
        this.butins.add(e);
        e.setPosition(this.rang);
        this.train.incrCompteElement();
    }
    public void addMarshall(Marshall e){
        this.marshall.add(e);
        e.setPosition(this.rang);
        this.train.incrCompteElement();
    }
    /**getter des elements*/
    public ArrayList<Bandit> getBandits(){return this.bandits;}
    public ArrayList<Butin> getButins(){return this.butins;}
    public ArrayList<Marshall> getMarshall(){return this.marshall;}
    /**remove un objet Element du wagon*/
    public void removeBandit(Bandit e){
        this.bandits.remove(e);
        this.train.decrCompteElement();
    }
    public void removeButin(Butin e){
        this.bandits.remove(e);
        this.train.decrCompteElement();
        System.out.println(this.getButins());
    }
    public void removeMarshall(Marshall e){
        this.marshall.remove(e);
        this.train.decrCompteElement();
    }
    /**génération des butins*/
    public void setRandomButins(){
        int m =  1 + (int) (Math.random()*Project.NB_BUTINS);
        for(int i = 0; i < m; i++){
            /**génération aléatoire de butin*/
            int price;
            String type;
            int n = (int) (Math.random()*2);
            type = this.butinsType.get(n);
            if(type == "bourse"){
                price = (int) (Math.random()*500);
            }
            else{
                price = 500;
            }
            Butin tmp = new Butin(price, type, 0, this.rang);
            this.addButin(tmp);
        }
    }
    /**après qu'un bandit a tiré*/
    public String shooted(boolean roof, String shooter){
        if(this.getBandits().size() != 0){
            if(this.getBandits().size() == 1){
                if(!this.getBandits().get(0).getName().equals(shooter)){
                    if(this.getBandits().get(0).getRoof() == roof){
                        this.getBandits().get(0).getShooted();
                        return shooter + " a tiré sur " + this.getBandits().get(0).getName();
                    }
                    else{
                        return shooter + "a tiré dans un wagon vide";
                    }
                }
                else{
                    return shooter + "a tiré dans un wagon vide";
                }
            }
            else{
                boolean flag = false;
                for(Bandit e : this.getBandits()){
                    if(e.getName() != shooter){
                        flag = flag || (e.getRoof() == roof);
                    }
                }
                if(flag){
                    int tmp = (int) (Math.random() * this.getBandits().size());
                    while (this.getBandits().get(tmp).getName() == shooter || this.getBandits().get(tmp).getRoof() != roof) {
                        tmp = (int) (Math.random() * this.getBandits().size());
                    }
                    this.getBandits().get(tmp).getShooted();
                    return shooter + " a tiré sur " + this.getBandits().get(tmp).getName();
                }
                else{
                    return shooter + " a tiré dans un wagon vide";
                }
            }
        }
        else{
            return shooter + " a tiré dans un wagon vide";
        }
    }
    /**tests*/
    public static void main(String[] args) {
    }
}
