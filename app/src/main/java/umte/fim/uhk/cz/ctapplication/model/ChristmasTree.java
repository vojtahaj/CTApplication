package umte.fim.uhk.cz.ctapplication.model;

public class ChristmasTree {
    public Light nL;
    public Light nR;
    public Light fL;
    public Light fR;
    public Light y1;
    public Light y2;
    public Light y3;
    public Light g;
    public Light rL;
    public Light rR;

    public int green;
    public int yellow;
    public String sequence;

    public ChristmasTree() {
        nL = new Light();
        nR = new Light();
        fL = new Light();
        fR = new Light();
        y1 = new Light();
        y2 = new Light();
        y3 = new Light();
        g = new Light();
        rL = new Light();
        rR = new Light();


        //nastaveni defaultnich hodnot semaforu
        //pro svit zelene, a oranzovych
        //se startovaci sekvenci pro rucni starty
        green = 40;
        yellow = 25;
        sequence = "SE";

    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public double temperature;

    public Light getnR() {
        return nR;
    }

    public void setnR(Light nR) {
        this.nR = nR;
    }

    public Light getfL() {
        return fL;
    }

    public void setfL(Light fL) {
        this.fL = fL;
    }

    public Light getfR() {
        return fR;
    }

    public void setfR(Light fR) {
        this.fR = fR;
    }

    public Light getY1() {
        return y1;
    }

    public void setY1(Light y1) {
        this.y1 = y1;
    }

    public Light getY2() {
        return y2;
    }

    public void setY2(Light y2) {
        this.y2 = y2;
    }

    public Light getY3() {
        return y3;
    }

    public void setY3(Light y3) {
        this.y3 = y3;
    }

    public Light getG() {
        return g;
    }

    public void setG(Light g) {
        this.g = g;
    }

    public Light getrL() {
        return rL;
    }

    public void setrL(Light rL) {
        this.rL = rL;
    }

    public Light getrR() {
        return rR;
    }

    public void setrR(Light rR) {
        this.rR = rR;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Light getnL() {
        return nL;
    }

    public void setnL(Light nL) {
        this.nL = nL;
    }
}
