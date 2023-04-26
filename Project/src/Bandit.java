import java.util.ArrayList;

public class Bandit extends Element{
    private String name;
    private boolean roof;
    private ArrayList<Butin> butins;
    private int nbBullet;

    public Bandit(String name, int id, boolean roof, int position, Train t){
        this.butins = new ArrayList<>();
        this.setPosition(position);
        this.setId(id);
        this.name = name;
        this.roof = roof;
        this.setTrain(t);
        this.nbBullet = Project.NB_BALLES;
    }
    public Bandit(String name, Train t){
        this.butins = new ArrayList<>();
        int tmp = (int)(Math.random()*t.getTrainSize());
        this.setPosition(tmp);
        tmp = (int)(Math.random()*2);
        this.roof = tmp == 0;
        this.name = name;
        this.setId(-1);
        this.setTrain(t);
        this.nbBullet = Project.NB_BALLES;
    }
    /**getters*/
    public boolean getRoof(){return this.roof;}
    public String getName(){return this.name;}
    public int getValueButin(){
        int res = 0;
        for(Butin b : butins){
            res += b.getValue();
        }
        return res;
    }
    /**adders removers de butin*/
    private void addButinToBandit(Butin b){this.butins.add(b);}
    private void removeButin(Butin b){this.butins.remove(b);}
    /**actions : */
    /**méthode de déplecement d'un bandit*/
    public String deplacement(Direction d){
        if(d == Direction.AVANT){
            if(this.getPosition() <= 0){
                this.setPosition(0);
                return this.name + " est déjà dans le premier wagon";
            }
            else{
                this.setPosition(this.getPosition() - 1);
                this.getTrain().addBanditWTparametres(this, this.getPosition());
                this.getTrain().removeBanditWTparametres(this,this.getPosition() + 1);
                return this.name + " entre dans le wagon " + this.getPosition();
            }
        }
        else if(d == Direction.ARRIERE){
            if(this.getPosition() >= this.getTrain().getTrainSize() - 1){
                this.setPosition(this.getTrain().getTrainSize() - 1);
                return this.name + " est déjà dans le dernier wagon";

            }
            else{
                this.setPosition(this.getPosition() + 1);
                this.getTrain().addBanditWTparametres(this, this.getPosition());
                this.getTrain().removeBanditWTparametres(this,this.getPosition() - 1);
                return this.name + " entre dans le wagon " + this.getPosition();

            }
        }
        else if(d == Direction.BAS){
            if(this.roof){
                this.roof = false;
                return this.name + " descend dans le wagon " + this.getPosition();
            }
            else{
                return this.name + " est déjà dans le wagon " + this.getPosition();
            }
        }
        else if(d == Direction.HAUT){
            if(!this.roof){
                this.roof = true;
                return this.name + " monte sur le wagon " + this.getPosition();
            }
            else{
                return this.name + " est déjà sur le toit du wagon " + this.getPosition();
            }
        }
        else{
            assert false;
            return "";
        }
    }
    /**braquage*/
    public String braquage(){
        if(!this.getRoof()) {
            if (this.getTrain().getWagon(this.getPosition()).getButins().equals(new ArrayList<Butin>())) {
                return "Le wagon " + this.getPosition() + " est vide.";
            } else {
                int tmp = (int) (Math.random() * this.getTrain().getWagon(this.getPosition()).getButins().size());
                Butin b = this.getTrain().getWagon(this.getPosition()).getButins().get(tmp);
                this.getTrain().getWagon(this.getPosition()).getButins().remove(b);
                this.addButinToBandit(b);
                return "Le bandit " + this.name + " a récupéré un butin : " + b.getName() + "!";
            }
        }
        else{
            return "il n'y a pas de butins sur les toits";
        }
    }
    /**escape*/
    public String escape(){
        if(!this.butins.equals(new ArrayList<Butin>())){
            int tmp = (int)(Math.random()*(this.butins.size()));
            Butin b = this.butins.get(tmp);
            this.removeButin(b);
            this.getTrain().getWagon(this.getPosition()).addButin(b);
        }
        return this.deplacement(Direction.HAUT);
    }
    /**shoot*/
    public String shoot(Direction d){
        if(this.nbBullet == 0){
            return this.getName() + " n'a plus de munitions";
        }
        else{
            this.nbBullet--;
            if(d == Direction.HAUT){
                return this.getTrain().getWagon(this.getPosition()).shooted(true, this.getName());
            }
            else if(d == Direction.BAS){
                return this.getTrain().getWagon(this.getPosition()).shooted(false, this.getName());
            }
            else if(d == Direction.AVANT){
                if(this.getPosition() != 0){
                    return this.getTrain().getWagon(this.getPosition() - 1).shooted(this.getRoof(), this.getName());
                }
                else{
                    return this.getName() + " tire dans le vide";
                }
            }
            else if(d == Direction.ARRIERE){
                if(this.getPosition() != this.getTrain().getTrainSize() - 1){
                    return this.getTrain().getWagon(this.getPosition() + 1).shooted(this.getRoof(), this.getName());
                }
                else{
                    return this.getName() + " tire dans le vide";
                }
            }
            else{
                return "";
            }
        }
    }
    /**get shooted*/
    public void getShooted(){
        if(!this.butins.equals(new ArrayList<Butin>())){
            int tmp = (int)(Math.random()*(this.butins.size()));
            Butin b = this.butins.get(tmp);
            this.removeButin(b);
            this.getTrain().getWagon(this.getPosition()).addButin(b);
        }
    }
    /**printer*/
    /**printElement (printBandit en particulier)*/
    public void printElement(){
        System.out.print("(Bandit " + this.name + " roof : " + this.getRoof() +  ")");
    }
    /**toString*/
    public String toString(){
        return this.name + " ";
    }
    /**showStuff*/
    public String showStuff(){ return this.name + " : " + this.getValueButin() + "$";}
    /**tests*/
    public static void main(String[] args) {
    }
}

enum Direction { AVANT,
    ARRIERE,
    HAUT,
    BAS,
    NULL}