public class GuitarString {
    RingBuffer buffer;
    static final int SAMPLING_RATE = 44100;
    static final double ENERGY_DECAY_FACTOR = 0.994;
    int capacity;
    int tics;

    // create a guitar string of the given frequency, using a sampling rate of 44,100
    public GuitarString(double frequency) {
        // capacity n = Math.ceil(44100/frequency)
        // enqueue n 0s
        capacity = (int) Math.ceil(SAMPLING_RATE / frequency);
        buffer = new RingBuffer(capacity);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0);
        }
    }

    // create a guitar string whose size and initial values are given by the array
    public GuitarString(double[] init) {
        buffer = new RingBuffer(init.length);
        for (int i = 0; i < init.length; i++) {
            buffer.enqueue(init[i]);
        }

    }

    // set the buffer to white noise
    public void pluck() {
        double randomVal;
        for (int i = 0; i < capacity; i++) {
            buffer.dequeue();
            randomVal = StdRandom.uniformDouble(-0.5, 0.5);
            buffer.enqueue(randomVal);
        }

    }

    // advance the simulation one time step
    public void tic() {
        double firstDisplacement = buffer.peek();
        buffer.dequeue();
        double secondDisplacement = buffer.peek();
        buffer.enqueue((firstDisplacement + secondDisplacement) / 2 * ENERGY_DECAY_FACTOR);
        tics++;
    }

    // return the current sample
    public double sample() {
        return buffer.peek();
    }

    // Returns the number of samples in the ring buffer.
    public int length() {
        return buffer.size();
    }

    public int time() {
        return tics;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        GuitarString gs1 = new GuitarString(n);
        for (int i = 0; i < gs1.capacity; i++) {
            System.out.println(gs1.sample());
        }
        StdOut.printf("Test #1 - check length based on given n == %d\n", n);
        StdOut.printf("**** Length is %d\n", gs1.length());

        StdOut.printf("Test #2 - check sample is %.1f\n", 0.0);
        StdOut.printf("**** Sample is %.1f\n", gs1.sample());

        double[] samples = { -0.7, +0.8, -0.9, +0.6 };
        GuitarString gs2 = new GuitarString(samples);
        int len = samples.length;
        StdOut.printf("Test #3 - check length based on given samples == %d\n",
                      len);
        StdOut.printf("**** Length is %d\n", gs2.length());

        StdOut.printf("Test #4 - check sample is %.2f\n", samples[0]);
        StdOut.printf("**** Sample is %.2f\n", gs2.sample());

        gs2.pluck();
        StdOut.printf("Test #5 - check length after pluck is still == %d\n",
                      len);
        StdOut.printf("**** Length is %d\n", gs2.length());

        StdOut.printf("Test #6 - check sample is range [-0.5, +0.5)\n");
        StdOut.printf("**** Sample is %.2f\n", gs2.sample());

        GuitarString gs3 = new GuitarString(samples);
        StdOut.printf("Test #7 - check sample is %.2f\n", samples[0]);
        StdOut.printf("**** Sample is %.1f\n", gs3.sample());
        gs3.tic();
        StdOut.printf("Test #8 - check sample is %.2f\n", samples[1]);
        StdOut.printf("**** Sample is %.2f\n", gs3.sample());

        int m = 25; // number of tics
        double[] moreSamples = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
        StdOut.printf("Test #9 - test %d tics\n", m);
        GuitarString gs4 = new GuitarString(moreSamples);
        for (int i = 0; i < m; i++) {
            double sample = gs4.sample();
            StdOut.printf("%6d %8.4f\n", i, sample);
            gs4.tic();
        }

        GuitarString test1 = new GuitarString(20);
        GuitarString test2 = new GuitarString(50);
        System.out.println("expect 0: " + test1.length());
        System.out.println("expect 0.0: " + test1.sample());
        test1.pluck();
        System.out.println("expect n: " + test1.length());
        System.out.println("expect random val: " + test1.sample());
        // int N = Integer.parseInt(args[0]);
        // double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };
        // GuitarString testString = new GuitarString(samples);
        // for (int i = 0; i < N; i++) {
        //     int t = testString.time();
        //     double sample = testString.sample();
        //     System.out.printf("%6d %8.4f\n", t, sample);
        //     testString.tic();
        // }
    }
}
