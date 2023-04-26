abstract class Element{
    private int position;
    private int id;
    private Train train;
    private boolean roof = false;
    public int getPosition(){
        return this.position;
    }

    public void setPosition(int n){
        this.position = n;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int n){
        this.id = n;
    }

    public void setTrain(Train t){this.train = t;}

    public boolean getRoof(){return this.roof;}

    public Train getTrain(){return this.train;}


    public String deplacement(Direction d){ return "";}
    /**printer*/
    public void printElement(){
        System.out.print("(id : " + this.id + " pos : " + this.position + ")");
    }
    /**toString*/
    public String toString(){
        return "id :" + this.id;
    }
    /**tests*/
    public static void main(String[] args) {
    }

}


