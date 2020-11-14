package ex1;

import java.io.Serializable;
import java.util.Objects;

public class EdgeInfo implements edge_info, Serializable {


    private double _weight;
    private String _info;


    public EdgeInfo(double weight)
    {
        this._weight = weight;
    }


    @Override
    public double getWeight() {
        return this._weight;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {
        _info = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeInfo edgeInfo = (EdgeInfo) o;
        return Double.compare(edgeInfo._weight, _weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_weight);
    }
}
