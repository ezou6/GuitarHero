public class TestStdAudio {
    public static void main(String[] args) {
        double freq = 440.0;
        for (int i = 0; i < StdAudio.SAMPLE_RATE; i++) {
            double sample = 0.5 * Math.sin(2 * Math.PI * freq * i / StdAudio.SAMPLE_RATE);
            StdAudio.play(sample);
        }
        StdAudio.drain();
    }
}

