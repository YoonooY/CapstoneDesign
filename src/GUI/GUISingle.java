package GUI;

import Section.RebarCrawling;
import Section.SingleRec;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class GUISingle extends JPanel implements ActionListener, KeyListener {

    private JButton btnSingle, btnDouble, btnT,btnPut,btnDelete;
    private JPanel panelTop, panelBottom;
    private JPanel panelLeft, panelRight;
    private JPanel pnL, pnWd, pnWl, pnFck, pnFy, pnAsNum, pnAsKind, pnB, pnD, pnH,
            pnBlank1,pnBlank2,pnBlank;
    private JPanel panelBtn,panelVariables,panelProcess;
    private JLabel lbL, lbWd, lbWl, lbFck, lbFy, lbAsKind, lbAsNum, lbB, lbD, lbH;
    private JTextField tfL, tfFck, tfFy, tfAsNum, tfB, tfD, tfH;
    private JComboBox<String> cbAs;
    private JFormattedTextField tfWd, tfWl;
    private String[] strAs = {"D4 (14.05)", "D5 (21.98)", "D6 (31.67)", "D8 (49.51)", "D10 (71.33)",
            "D13 (126.7)", "D16 (198.6)", "D19 (286.5)", "D22 (387.1)", "D25 (506.7)", "D29 (642.4)", "D32 (794.2)",
            "D35 (956.6)", "D38 (1140)", "D41 (1340)", "D43 (1452)", "D51 (2027)", "D57 (2579)"};
    private Main win;
    private ImageIcon imgSingle;
    private JLabel lbImg;
    private DefaultTableModel model;
    private String[] columns = {"L (m)", "fck (Mpa)", "fy (Mpa)", "b (mm)", "d (mm)",
            "h (mm)", "As (mm^2)", "Mu (kN·m)", "Mn (kN·m)", "Md (kN·m)", "Result","총 가격 (원/m)"};
    private String[][] data;
    private JTable resultTable;

    private int L, fck, fy, AsNum, b, d, h;
    private double As, Ac, AsType, wd, wl, U, Mu;

    public GUISingle(Main win) {
        this.win = win;
        setLayout(new GridLayout(2,1));

        makePanelTop();
        makePanelBottom();
    }

    public void makePanelTop() { //윗쪽 패널 만들기
        panelTop = new JPanel(new GridLayout(1,2));

        makePanelLeft();
        makePanelRight();

        add(panelTop);
    }

    public void makePanelBottom() { //아랫쪽 패널 만들기
        panelBottom = new JPanel(new GridLayout(1,1));

        //데이터 출력을 위한 테이블의 모델 생성
        model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        resultTable = new JTable(model);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(40); //L
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(60); //fck
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(60); //fy
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(50); //b
        resultTable.getColumnModel().getColumn(4).setPreferredWidth(50); //d
        resultTable.getColumnModel().getColumn(5).setPreferredWidth(50); //h
        resultTable.getColumnModel().getColumn(6).setPreferredWidth(72); //As
        resultTable.getColumnModel().getColumn(7).setPreferredWidth(72); //Mu
        resultTable.getColumnModel().getColumn(8).setPreferredWidth(72); //Mn
        resultTable.getColumnModel().getColumn(9).setPreferredWidth(72); //Md
        resultTable.getColumnModel().getColumn(10).setPreferredWidth(65); //Result
        resultTable.getColumnModel().getColumn(11).setPreferredWidth(85); //총 가격

        //항목의 오름차순 혹은 내림차순 정렬
        resultTable.setAutoCreateRowSorter(true);
        TableRowSorter tableSorter = new TableRowSorter(resultTable.getModel());
        resultTable.setRowSorter(tableSorter);

        //테이블에 스크롤 만들기
        JScrollPane sp = new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelBottom.add(sp);
        add(panelBottom);
    }

    public void makePanelLeft() { //왼쪽 패널 만들기

        panelLeft = new JPanel(new BorderLayout());

        makePanelBtn();
        makePanelVariables();
        makePanelProcess();

        panelTop.add(panelLeft);
    }

    public void makePanelRight() { //오른쪽 패널
        panelRight = new JPanel();

        URL url = this.getClass().getClassLoader().getResource("images/단철근.PNG");
        imgSingle = new ImageIcon(url);
        Image img = imgSingle.getImage();
        Image changeImg = img.getScaledInstance(170, 250, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);

        lbImg = new JLabel(changeIcon);
        lbImg.setSize(400, 500);
        panelRight.add(lbImg);
        panelTop.add(panelRight);
    }
    public void makePanelBtn() { //단면 선택 버튼 패널
        panelBtn = new JPanel();
        btnSingle = new JButton("단철근 보");
        btnDouble = new JButton("복철근 보");
        btnDouble.addActionListener(this);
        btnT = new JButton("T형 보");
        btnT.addActionListener(this);

        panelBtn.add(btnSingle);
        panelBtn.add(btnDouble);
        panelBtn.add(btnT);
        panelLeft.add(panelBtn,BorderLayout.NORTH);
    }
    public void makePanelVariables() { //변수 입력 패널
        panelVariables = new JPanel(new GridLayout(8,2,2,3));


        pnL = new JPanel(new GridLayout(1,2));
        pnWd = new JPanel(new GridLayout(1,2));
        pnWl = new JPanel(new GridLayout(1,2));
        pnFck = new JPanel(new GridLayout(1,2));
        pnFy = new JPanel(new GridLayout(1,2));
        pnBlank = new JPanel();
        pnAsNum = new JPanel(new GridLayout(1,2));
        pnAsKind = new JPanel(new GridLayout(1,2));
        pnB = new JPanel(new GridLayout(1,2));
        pnD = new JPanel(new GridLayout(1,2));
        pnH = new JPanel(new GridLayout(1,2));

        lbL = new JLabel("                        L");
        lbWd =  new JLabel("                         Wd");
        lbWl = new JLabel("                       Wl");
        lbFck = new JLabel("                       fck");
        lbFy =  new JLabel("                          fy");
        lbAsNum = new JLabel("                     철근 수");
        lbAsKind = new JLabel("                철근 타입");
        lbB = new JLabel("                        b");
        lbD = new JLabel("                          d");
        lbH = new JLabel("                        h");

        cbAs = new JComboBox<String>(strAs);
        cbAs.addActionListener(this);

        tfWd = new JFormattedTextField(new NumberFormatter());
        tfWd.setColumns(3);
        tfWl = new JFormattedTextField(new NumberFormatter());
        tfWl.setColumns(3);

        tfL = new JTextField(2);
        tfL.addKeyListener(this);
        tfFck = new JTextField(2);
        tfFck.addKeyListener(this);
        tfFy = new JTextField(2);
        tfFy.addKeyListener(this);
        tfAsNum = new JTextField(2);
        tfAsNum.addKeyListener(this);
        tfB = new JTextField(2);
        tfB.addKeyListener(this);
        tfD = new JTextField(2);
        tfD.addKeyListener(this);
        tfH = new JTextField(2);
        tfH.addKeyListener(this);

        pnL.add(lbL); pnL.add(tfL);
        pnWd.add(lbWd); pnWd.add(tfWd);
        pnWl.add(lbWl); pnWl.add(tfWl);
        pnFck.add(lbFck); pnFck.add(tfFck);
        pnFy.add(lbFy); pnFy.add(tfFy);
        pnAsKind.add(lbAsKind); pnAsKind.add(cbAs);
        pnAsNum.add(lbAsNum); pnAsNum.add(tfAsNum);
        pnB.add(lbB); pnB.add(tfB);
        pnD.add(lbD); pnD.add(tfD);
        pnH.add(lbH); pnH.add(tfH);

        pnBlank1 = new JPanel();
        pnBlank2 = new JPanel();

        panelVariables.add(pnBlank1);
        panelVariables.add(pnBlank2);
        panelVariables.add(pnL);
        panelVariables.add(pnWd);
        panelVariables.add(pnWl);
        panelVariables.add(pnBlank);
        panelVariables.add(pnFck);
        panelVariables.add(pnFy);
        panelVariables.add(pnAsKind);
        panelVariables.add(pnAsNum);
        panelVariables.add(pnB);
        panelVariables.add(pnD);
        panelVariables.add(pnH);

        panelLeft.add(panelVariables, BorderLayout.CENTER);
    }
    public void makePanelProcess() { //Put 버튼, Delete 버튼 패널 만들기
        panelProcess = new JPanel();

        btnPut = new JButton("Put");
        btnDelete = new JButton("Delete");
        btnPut.addActionListener(this);
        btnDelete.addActionListener(this);

        panelProcess.add(btnPut);
        panelProcess.add(btnDelete);

        panelLeft.add(panelProcess,BorderLayout.SOUTH);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        //단면 변경 버튼
        if(obj == btnDouble) {
            win.change("panelDoubleBeam");
        } else if (obj == btnT) {
            win.change("panelT");
        }

        else if (obj == btnPut) { //변수 계산 버튼
            calculate();
        } else if (obj == btnDelete) { //생성한 데이터 삭제 버튼
            int selected = resultTable.getSelectedRow();
            removeItem(selected);
        } else if (obj == cbAs) { //철근 종류
            int index = cbAs.getSelectedIndex();
            switch (index) {
                case 0:
                    AsType = 14.05;
                    break;
                case 1:
                    AsType = 21.98;
                    break;
                case 2:
                    AsType = 31.67;
                    break;
                case 3:
                    AsType = 49.51;
                    break;
                case 4:
                    AsType = 71.33;
                    break;
                case 5:
                    AsType = 126.7;
                    break;
                case 6:
                    AsType = 198.6;
                    break;
                case 7:
                    AsType = 286.5;
                    break;
                case 8:
                    AsType = 387.1;
                    break;
                case 9:
                    AsType = 506.7;
                    break;
                case 10:
                    AsType = 642.4;
                    break;
                case 11:
                    AsType = 794.2;
                    break;
                case 12:
                    AsType = 956.6;
                    break;
                case 13:
                    AsType = 1140;
                    break;
                case 14:
                    AsType = 1340;
                    break;
                case 15:
                    AsType = 1452;
                    break;
                case 16:
                    AsType = 2027;
                    break;
                case 17:
                    AsType = 2579;
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void calculate(){ //입력값 계산
        try {
            String strFck = tfFck.getText();
            String strFy = tfFy.getText();
            String strAsNum = tfAsNum.getText();
            String strB = tfB.getText();
            String strD = tfD.getText();
            String strH = tfH.getText();
            String strL = tfL.getText();
            String strWd = tfWd.getText();
            String strWl = tfWl.getText();

            if (isStringEmpty(strL) || isStringEmpty(strWl) || isStringEmpty(strWd) ||
                    isStringEmpty(strFck) || isStringEmpty(strFy) || isStringEmpty(strB) || isStringEmpty(strD) ||
                    isStringEmpty(strH) || isStringEmpty(strAsNum)) {
                JOptionPane aa = new JOptionPane();
                aa.showMessageDialog(null, "올바른 값을 입력해 주세요.", "입력값 오류", JOptionPane.WARNING_MESSAGE);
            } //숫자를 입력하지 않았을 경우 입력값 오류

            else {
                L = Integer.parseInt(strL);
                wd = Double.parseDouble(strWd);
                wl = Double.parseDouble(strWl);
                U = Math.max(1.2 * wd + 1.6 * wl, 1.4 * wd);
                Mu = U * L * L / 8;

                fck = Integer.parseInt(strFck);
                fy = Integer.parseInt(strFy);
                AsNum = Integer.parseInt(strAsNum);
                b = Integer.parseInt(strB);
                d = Integer.parseInt(strD);
                h = Integer.parseInt(strH);
                As = AsType * AsNum;
                Ac = b * h - As;

                RebarCrawling.crawling();

                double C = (87.82*fck*fck - 2846.7*fck + 115753)/1000000; //fck에 따른 콘크리트의 가격
                double CCost = Ac*C; //단위 길이당 콘크리트 가격
                double S; //철근 가격
                if (AsType < 300) { //고장력 절근
                    S = RebarCrawling.getPrice();
                } else { //초고장력 철근
                    S = RebarCrawling.getUHSSPrice();
                }
                double SCost = As*S; //단위 길이당 철근 가격
                double totCost = CCost + SCost; //단위길이당 총 가격


                SingleRec p = new SingleRec(fck, fy, As, b, d, h); //단철근 보 객체 생성

                //테이블에 데이터 생성
                model = (DefaultTableModel) resultTable.getModel();
                String[] row = {strL, strFck, strFy, strB, strD, strH, As + "", String.format("%.3f", Mu),
                        String.format("%.3f", p.Mn() / 1000000), String.format("%.3f", p.Mn() * 0.85 / 1000000),
                        checkMu(Mu, p.Md() / 1000000),String.format("%.1f",totCost)};
                model.addRow(row);
                resultTable.updateUI();

                //데이터 생성이 끝나면 입력된 변수값들 빈칸으로 초기화
                tfFck.setText("");
                tfFy.setText("");
                tfD.setText("");
                tfB.setText("");
                tfH.setText("");
                tfAsNum.setText("");

            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
    public void removeItem(int index) { //변수입력란에 숫자가 아닌 문자가 들어갔을 경우 입력 되지 않게 하기
        if (index < 0) {
            if (model.getRowCount() == 0) return;
            index = 0;
        }
        model.removeRow(index);
    }
    public static String checkMu(double mu, double md) {  //소요강도에 대해 설계강도가 만족하는지 판단
        if (mu <= md)
            return "OK";
        else return "Error";
    }
    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
