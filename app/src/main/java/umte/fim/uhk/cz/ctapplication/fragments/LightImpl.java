package umte.fim.uhk.cz.ctapplication.fragments;

import android.support.v4.app.Fragment;

import umte.fim.uhk.cz.ctapplication.model.ChristmasTree;

public class LightImpl implements LightListener {

    private ChristmasTree christmasTree;
    private CTFragment ctFragment;

    public LightImpl(Fragment ctFragment) {
        christmasTree = new ChristmasTree();
        System.out.println("CT created");
        this.ctFragment = (CTFragment) ctFragment;
    }

    @Override
    public void parse(String message, ChristmasTree christmasTree) {

        switch (message) {
            case "A$":
                System.out.println("init CT communication");
                break;
            case "NL0":
                christmasTree.getnL().setState(false);
                break;
            case "NL1":
                christmasTree.getnL().setState(true);
                break;
            case "FL0":
                christmasTree.getfL().setState(false);
                break;
            case "FL1":
                christmasTree.getfL().setState(true);
                break;
            case "FR0":
                christmasTree.getfR().setState(false);
                break;
            case "FR1":
                christmasTree.getfR().setState(true);
                break;
            case "NR0":
                christmasTree.getnR().setState(false);
                break;
            case "NR1":
                christmasTree.getnR().setState(true);
                break;
            case "Y10":
                christmasTree.getY1().setState(false);
                break;
            case "Y11":
                christmasTree.getY1().setState(true);
                break;
            case "Y20":
                christmasTree.getY2().setState(false);
                break;
            case "Y21":
                christmasTree.getY2().setState(true);
                break;
            case "Y30":
                christmasTree.getY3().setState(false);
                break;
            case "Y31":
                christmasTree.getY3().setState(true);
                break;
            case "G0":
                christmasTree.getG().setState(false);
                break;
            case "G1":
                christmasTree.getG().setState(true);
                break;
            case "RL0":
                christmasTree.getrL().setState(false);
                break;
            case "RL1":
                christmasTree.getrL().setState(true);
                break;
            case "RR0":
                christmasTree.getrR().setState(false);
                break;
            case "RR1":
                christmasTree.getrR().setState(true);
                break;
            default:
                parseTemp(message);
        }

        ctFragment.updateTextView(christmasTree);
    }

    private void parseTemp(String message) {
        if (message.length() > 0) {
            String temp = message.substring(1);

            try {
                christmasTree.setTemperature(Double.parseDouble(temp));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("short message!!!");
        }
    }

    public ChristmasTree getCT() {
        return christmasTree;
    }
}
