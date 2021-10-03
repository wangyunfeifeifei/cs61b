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

    // read the radius property from file
    public static double readRadius(String path) {
        In in = new In(path);
        in.readInt();
        return in.readDouble();
    }

    // read the data of planets from file
    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int n = in.readInt();
        in.readDouble();

        Planet[] ret = new Planet[n];

        for(int i=0; i<n; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            ret[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass, img);
        }
        return ret;
    }

    // draw the planet picture
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/"+imgFileName);
    }

    public static void main(String[] args) {
        if(args.length<3) return;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        // read data from file
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.setScale(-radius, radius);

        // animation loop
        StdDraw.enableDoubleBuffering();
        for(int time=0; time<T; time+=dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for(int i=0; i< planets.length; i++)  {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i=0; i<planets.length; i++) planets[i].update(dt, xForces[i], yForces[i]);
            StdDraw.picture(0, 0, "./images/starfield.jpg");
            for (Planet p:planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        // print the final state
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }
}