package GUI;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public GUISingle panelSingleBeam = null;
    public GUIDouble panelDoubleBeam = null;
    public GUIT panelT = null;

    public void change(String panelName) { //패널을 바꿔주기
        if (panelName.equals("panelSingleBeam")) {  //패널 이름이 "panelSingleBeam"과 같으면
            getContentPane().removeAll();           //프레임 초기화
            getContentPane().add(panelSingleBeam);  //singleBeam 호출
            revalidate(); //프레임 변화 확인
            repaint(); //프레임 변화 재확인
        } else if (panelName.equals("panelDoubleBeam")){
            getContentPane().removeAll();
            getContentPane().add(panelDoubleBeam);
            revalidate();
            repaint();
        } else {
            getContentPane().removeAll();
            getContentPane().add(panelT);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        Main win = new Main();

        win.setTitle("Design Beam Section");
        win.panelSingleBeam = new GUISingle(win);
        win.panelDoubleBeam = new GUIDouble(win);
        win.panelT = new GUIT(win);

        win.add(win.panelSingleBeam); //프로그램 실행 시 panelSigleBeam 이 첫 화면
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //종료를 누르면 Frame만 꺼지는것이 아닌 프로그램 전체가 종료됨
        win.setSize(1050,580);

        //프로그램 실행시 윈도우 화면에 나타나는 위치를 조정
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = win.getSize();
        win.setLocation((scr.width-frameSize.width)/2,(scr.height-frameSize.height)/2);

        win.setResizable(false); //프로그램의 크기 조절 불가
        win.setVisible(true);
    }
}