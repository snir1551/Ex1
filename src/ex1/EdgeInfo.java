package ex1;

import java.io.Serializable;

public class EdgeInfo implements edge_info, Serializable {


    private double weight;


    public EdgeInfo(double weight)
    {
        this.weight = weight;
    }


    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }



}
