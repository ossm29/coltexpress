import java.util.ArrayList;

public class Butin extends Element{
    /**attributs*/
    private int value;
    private String name;

    /**constructeur*/
    public Butin(int value, String name, int id, int position){
        this.value = value;
        this.name = name;
        this.setId(id);
        this.setPosition(position);
    }
    public Butin(int value, String name){
        this.value = value;
        this.name = name;
    }
    public Butin(int value, String name, Train train, int id, int position){
        this.value = value;
        this.name = name;
        this.setTrain(train);
        this.setId(id);
        this.setPosition(position);
    }
    /**getters et setters*/
    public int getValue(){
        return this.value;
    }
    public String getName(){return this.name;}
    /**toString*/
    public String toString(){
        return this.name + " ";
    }
    /**printer*/
    public void printElement(){
        System.out.print("("+this.name+")");
    }
}