package inputs;

import main.GameConstant;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class handles mouseinput logic
 * @author Alan Liu
 */
public class  MouseInputs implements MouseListener, MouseMotionListener {
    public int cursorX;
    public int cursorY;
    private boolean mouseDown = false;
    public MouseInputs(){
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = true;
        }
        long time = System.currentTimeMillis();
        while (true){
            if (System.currentTimeMillis() - GameConstant.WAIT >= time){
                mouseDown = false;
                break;
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = true;
        }
    }
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        cursorX = e.getLocationOnScreen().x; cursorY = e.getLocationOnScreen().y;
        if (e.getButton() == MouseEvent.BUTTON1){
            mouseDown = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursorX = e.getLocationOnScreen().x; cursorY = e.getLocationOnScreen().y;
    }
    public boolean returnMouseDown(){
        return mouseDown;
    }
}
