package adapter;

import DifferentPen.PilotPen;

public class PenAdapter implements Pen{
    PilotPen pPen =  new PilotPen();
    @Override
    public void write(String str) {
        pPen.writeSomething(str);
    }
}
