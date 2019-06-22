package umte.fim.uhk.cz.ctapplication.model;

public class Light {
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Light (){
        state = false;
    }
}
