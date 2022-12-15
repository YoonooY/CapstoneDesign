package Section;

public class SingleRec {

    private int fck,fy,b,d,h;//,eta,beta,epsilon;
    private double As;


    protected SingleRec() {
    }

    public SingleRec(int fck, int fy, double As, int b, int d, int h) {
        this.fck = fck;
        this.fy = fy;
        this.As = As;
        this.b = b;
        this.d = d;
        this.h = h;
    }

    public double eta() {      // η
        if (fck <= 40) return 1;
        else if (fck <= 50) return 0.97;
        else if (fck <= 60) return 0.95;
        else if (fck <= 70) return 0.91;
        else if (fck <= 80) return 0.87;
        else return 0.84;
    }

    public double beta() {
        if (fck <= 40) return 0.8;
        else if (fck <= 50) return 0.8;
        else if (fck <= 60) return 0.76;
        else if (fck <= 70) return 0.74;
        else if (fck <= 80) return 0.72;
        else return 0.70;
    }

    public double epsilonCU() {
        if (fck <= 40) return 0.0033;
        else if (fck <=90 ) return 0.0033 - (fck-40)/100000;
        else return 0.0031;
    }

    public double epsilonS() { return (d-a()/beta()) * 0.0033 / (a()/beta()); }
    public double epsilonSPrime() { return (d-c()) * 0.0033 / c(); }

    public double epsilonY() { return (double)fy/200000; }
    public double a() { return As * fy / (eta()*(0.85*fck*b)); } //등가 응력사각형 깊이 a
    public double cb()  { return 660/(660 + (double)fy)*(double)d; }// 균형보 중립축 위치 cb
    public double c() {return a()/beta(); }

    public double rho() { return As/(b*d); }     // 철근비 ρ
    public double rhob() { return eta()*0.85*beta()*660*fck/(fy*(660+fy)); }     // 균형철근비 ρb

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

    public double Mn() { return As*fy*(d-a()/2); }  //공칭휨강도
    public double Md() { return pi()*Mn(); }        //설계휨강도
}
