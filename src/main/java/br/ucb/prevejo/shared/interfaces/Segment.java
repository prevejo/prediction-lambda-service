package br.ucb.prevejo.shared.interfaces;

import org.locationtech.jts.geom.Point;

public interface Segment<T> {

    public T getSegmentStart();
    public T getSegmentEnd();
    public double distance(Point point);
    public T middlePoint(Point point);

}
