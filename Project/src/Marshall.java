public class Marshall extends Element{
    public Marshall(int id, int position, Train t){
        this.setPosition(position);
        this.setId(id);
        this.setTrain(t);
    }
    /**actions :*/
    /**déplacement*/
    public String deplacement(Direction d){
        if(d == Direction.AVANT){
            if(this.getPosition() <= 0){
                this.setPosition(0);
                for(Bandit b : this.getTrain().getWagon(this.getPosition()).getBandits()){
                    if(!b.getRoof()) {
                        this.shooting(b);
                    }
                }
                return "Le Marshall est déjà dans le premier wagon";
            }
            else{
                this.setPosition(this.getPosition() - 1);
                this.getTrain().getWagons().get(this.getPosition()).addMarshall(this);
                this.getTrain().removeMarshall(this,this.getPosition() + 1);
                for(Bandit b : this.getTrain().getWagon(this.getPosition()).getBandits()){
                    if(!b.getRoof()) {
                        this.shooting(b);
                    }
                }
                return "Le Marshall entre dans le wagon " + this.getPosition();
            }
        }
        else if(d == Direction.ARRIERE){
            if(this.getPosition() >= this.getTrain().getTrainSize() - 1){
                this.setPosition(this.getTrain().getTrainSize() - 1);
                for(Bandit b : this.getTrain().getWagon(this.getPosition()).getBandits()){
                    if(!b.getRoof()) {
                        this.shooting(b);
                    }
                }
                return "Le Marshall est déjà dans le dernier wagon";
            }
            else{
                this.setPosition(this.getPosition() + 1);
                this.getTrain().getWagons().get(this.getPosition()).addMarshall(this);
                this.getTrain().removeMarshall(this,this.getPosition() - 1);
                for(Bandit b : this.getTrain().getWagon(this.getPosition()).getBandits()){
                    if(!b.getRoof()) {
                        this.shooting(b);
                    }
                }
                return "Le Marshall entre dans le wagon " + this.getPosition();
            }
        }
        else{
            for(Bandit b : this.getTrain().getWagon(this.getPosition()).getBandits()){
                if(!b.getRoof()) {
                    this.shooting(b);
                }
            }
            return "Le Marshall temporise...";
        }
    }
    /**déplacement aléatoire*/
    public String randomDeplacement(){
        double tirage =  Math.random();
        if(tirage <= Project.NERVOSITE_MARSHALL){
            tirage = Math.random();
            if(this.getPosition() == 0){
                return this.deplacement(Direction.ARRIERE);
            }
            else if(this.getPosition() == this.getTrain().getTrainSize()-1){
            return this.deplacement(Direction.AVANT);
            }
            else if(tirage < 0.5){
                return this.deplacement(Direction.AVANT);
            }
            else{
                return this.deplacement(Direction.ARRIERE);
            }
        }
        else{
            return this.deplacement(Direction.NULL);
        }
    }
    /**Marshall shooting*/
    public String shooting(Bandit b){
        b.escape();
        this.getTrain().notifyObservers();
        return "le Bandit " + b.getName() + " s'est échappé.";

    }
    /**toString*/
    public String toString(){
        return "Marshall ";
    }
    /**printer*/
    public void printElement(){
        System.out.print("(Marshall)");
    }

}
