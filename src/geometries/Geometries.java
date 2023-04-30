package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

public class Geometries implements Intersectable{
    List <Intersectable> geometries;
    public Geometries(){
        geometries = new LinkedList();
    }
    public Geometries(Intersectable... geometries){
        this.geometries= List.of(geometries);
    }
    public void add(Intersectable... geometries){
        this.geometries.addAll(List.of(geometries));
    }
    public List<Point> findIntersections(Ray ray){
        boolean isIntersect=false;
        for(Intersectable i:geometries){
            if(i.findIntersections(ray)!=null)isIntersect=true;
        }
        if(!isIntersect)return null;
        List <Point> intersects = new LinkedList();
        for(Intersectable i:geometries){
            if(i.findIntersections(ray)!=null)intersects.addAll(i.findIntersections(ray));
        }
        return intersects;
    }
}
