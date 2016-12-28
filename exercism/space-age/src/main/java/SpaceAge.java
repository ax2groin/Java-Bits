public final class SpaceAge {

    private final long seconds;

    public SpaceAge(long seconds) { this.seconds = seconds; }

    public long getSeconds() { return seconds; }

    public double onEarth() { return Planet.EARTH.yearsOld(seconds); }
    public double onMercury() { return Planet.MERCURY.yearsOld(seconds); }
    public double onVenus() { return Planet.VENUS.yearsOld(seconds); }
    public double onMars() { return Planet.MARS.yearsOld(seconds); }
    public double onJupiter() { return Planet.JUPITER.yearsOld(seconds); }
    public double onSaturn() { return Planet.SATURN.yearsOld(seconds); }
    public double onUranus() { return Planet.URANUS.yearsOld(seconds); }
    public double onNeptune() { return Planet.NEPTUNE.yearsOld(seconds); }

    enum Planet {
        EARTH(1.0),
        MERCURY(0.2408467),
        VENUS(0.61519726),
        MARS(1.8808158),
        JUPITER(11.862615),
        SATURN(29.447498),
        URANUS(84.016846),
        NEPTUNE(164.79132);

        static final long SECONDS_IN_EARTH_YEAR = 31557600;

        private final double period;

        Planet(double period) { this.period = period; }

        double yearsOld(long seconds) {
            return seconds / period / SECONDS_IN_EARTH_YEAR;
        }
    }
}