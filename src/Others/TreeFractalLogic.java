package Others;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class TreeFractalLogic {
    private ArrayList<TreeLine> treeLines;

    public TreeFractalLogic(){
        this.treeLines = new ArrayList<>();
    }

    public ArrayList<TreeLine> getTreeLines() {
        return this.treeLines;
    }

    public void displayTree(int order, Point2D origin, double lengthDiff, double degree, double rightDiff, double leftDiff){
        Point2D nextOrigin = new Point2D(origin.getX() + 100 * Math.cos(degree) * lengthDiff, origin.getY() + 100 * Math.sin(degree) * lengthDiff);
        TreeLine add = new TreeLine(origin, nextOrigin, (float)(order + 9)  / 9.0f);
        this.treeLines.add(add);
        if (order != 0){
            double degree1 = degree + rightDiff;
            double degree2 = degree - leftDiff;
            displayTree(order - 1, nextOrigin, lengthDiff, degree1, rightDiff, leftDiff);
            displayTree(order - 1, nextOrigin, lengthDiff, degree2, rightDiff, leftDiff);
        }
    }
}

