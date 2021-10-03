public class NBody {
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
