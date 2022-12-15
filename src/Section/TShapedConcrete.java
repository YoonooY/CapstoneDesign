package Section;
public class TShapedConcrete {

    private int fck, fy, b, bw,d,tf;
    private double  As;

    protected TShapedConcrete() {}

    public TShapedConcrete(int fck, int fy, double As, int b, int d, int bw, int tf) {
        this.fck = fck;
        this.fy = fy;
        this.As = As;
        this.b = b;
        this.d = d;
        this.bw = bw;
        this.tf = tf;
    }

    public double getFck() {return this.fck;}
    public double getFy() {return this.fy;}
    public double getAs() {return this.As;}
    public double getB() {return this.b;}
    public double getD() {return this.d;}
    public double getBw() {return this.bw;}
    public double getTf() {return this.tf;}

    public double a() { //등가 응력사각형 깊이 a, 티형보 해석 여부 결정
        double tmp = As * fy / (eta()*(0.85*fck*b));

        if (tmp <= tf) {
            return tmp;
        }
        else {
            tmp = fy*(As-Ast())/(eta()*(0.85*fck)*bw);
            return tmp;
        }
    }
    public double epsilonS() { return (d-a()/beta()) * 0.0033 / (a()/beta()); } //철근 순인장 변형률
    public double epsilonY() { return (double)fy/200000; } //철근 항복 변형률

    public double pi() {                                                     // 강도감수계수 Φ
        if (fy < 400) {
            if (epsilonS() <= 0.002) return 0.65;
            else if (epsilonS() <= 2.5 * 0.002) return 0.65+(epsilonS()-0.002)*(0.2/(1.5*0.002));
            else return 0.85;
        }
        else {
            if (epsilonS() <= epsilonY()) return 0.65;
            else if (epsilonS() <= 2.5 * epsilonY()) return 0.65+(epsilonS()-epsilonY())*(0.2/(1.5*epsilonY()));
            else return 0.85;
        }
    }
    public double Ast() {
        return eta()*0.85*fck*(b-bw)*tf/fy;
    }

    public double Mn() { //공칭휨강도
        if (a() <= tf) {
            return (As*fy*(d-2/a()))/1000000;
        }
        else {
         return (Ast()*fy*(d-tf/2)+(As-Ast())*fy*(d-a()/2))/1000000;
        }
    }


    public double c() {
        return a()/beta();
    }

    public double eta() {      // η
        if (fck <= 40) return 1;
        else if (fck <= 50) return 0.97;
        else if (fck <= 60) return 0.95;
        else if (fck <= 70) return 0.91;
        else if (fck <= 80) return 0.87;
        else return 0.84;
    }
    public double epsilonC() {
        if (fck <= 40) return 0.0033;
        else if (fck <=90 ) return 0.0033 - (fck-40)/100000;
        else return 0.0031;
    }

    public double beta() {
        if (fck <= 40) return 0.8;
        else if (fck <= 50) return 0.8;
        else if (fck <= 60) return 0.76;
        else if (fck <= 70) return 0.74;
        else if (fck <= 80) return 0.72;
        else return 0.70;
    }

    public double Md() {
        return Mn()*pi();
    } //설계휨강도
}