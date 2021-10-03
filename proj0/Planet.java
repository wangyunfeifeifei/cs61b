class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    // calculate the distance of two planet
    public double calcDistance(Planet p) {
        return Math.sqrt((p.xxPos-xxPos)*(p.xxPos-xxPos)+(p.yyPos-yyPos)*(p.yyPos-yyPos));
    }

    // calculate the force between the two palnet
    public double calcForceExertedBy(Planet p) {
        double G = 6.67e-11;
        double dis = calcDistance(p);
        return G*mass*p.mass/(dis*dis);
    }

    // calculate the x direct force
    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - xxPos;
        if(dx==0) return 0;
        return calcForceExertedBy(p) / calcDistance(p) * dx;
    }

    // calculate the y direct force
    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - yyPos;
        if(dy==0) return 0;
        return calcForceExertedBy(p) / calcDistance(p) * dy;
    }

    // calculate net force by x
    public double calcNetForceExertedByX(Planet[] ps) {
        double sum = 0.0;
        for(Planet p:ps) {
            if(equals(p)) continue;
            sum += calcForceExertedByX(p);
        }
        return sum;
    }

    // calculate net force by y
    public double calcNetForceExertedByY(Planet[] ps) {
        double sum = 0.0;
        for(Planet p:ps) {
            if(equals(p)) continue;
            sum += calcForceExertedByY(p);
        }
        return sum;
    }

    // update the position of the planet per dt.
    public void update(double dt, double fX, double fY) {
        double ax = fX/mass; // acceleration of x direction
        double ay = fY/mass;
        xxVel += dt*ax; // new velocity of x direction
        yyVel += dt*ay;
        xxPos += xxVel*dt; // new x position
        yyPos += yyVel*dt;
    }

    // draw the planet picture
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/"+imgFileName);
    }


}