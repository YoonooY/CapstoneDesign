package Section;

public class DoubleRec {

    private int fck,fy,b,d,dPrime,h;
    private double As,AsPrime;
    private double Es = 200000; //철근 탄성계수

    public DoubleRec(int fck, int fy, double As, double AsPrime, int b, int d, int dPrime, int h) {
        this.fck = fck;
        this.fy = fy;
        this.As = As;
        this.AsPrime = AsPrime;
        this.b = b;
        this.d = d;
        this.dPrime = dPrime;
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

    public double epsilonT() { return (d-cb()) * 0.0033 / cb(); }  //순인장 변형률 εt
    public double epsilonY() { return (double)fy/200000; }
    public double epsilonS() { return (d-c()) * 0.0033 / c(); }
    public double a() { return fy*(As-AsPrime)/(eta()*0.85*fck*b); } //등가 응력사각형 깊이 a
    public double cb()  { return 660/(660 + (double)fy)*(double)d; }// 균형보 중립축 위치 cb
    public double c() { return a()/beta();}
    public double rho() { return As/(b*d); }     // 철근비 ρ
    public double rhoPrime() { return AsPrime/(b*d); }
    public double rhob() { return eta()*0.85*beta()*660/(fy*(660+fy)); }     // 균형철근비 ρb
    public double rhoMin() { return eta()*0.85*fck*beta()/fy*dPrime/d*660/(660-fy)+rhoPrime(); }
    public double rhoMax() { return (0.0033 + fy/Es)/0.007*rhob() + AsPrime/(b*d);}

    public double C() { return eta()*0.85*fck*a()*b+fy*AsPrime;} //총압축력
    public double T() { return fy*As;}                           //총인장력

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
    //공칭휨강도
    public double Mn() { return (As-AsPrime)*(double)fy*(d-a()/2) + AsPrime*(double)fy*((double)d-(double)dPrime); }//공칭휨강도
    public double Md() { return pi()*Mn(); }//설계휨강도
}