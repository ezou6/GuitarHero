public class GuitarHero {

    //  Plays two guitar strings (middle C and concert A) when the user types
    //  the corresponding keys ('p' and 'v') in an interactive window.
    public static void main(String[] args) {
        String keyboardString = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] strings = new GuitarString[37];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = new GuitarString(440 * Math.pow(2, (((double) i - 24) / 12)));
        }


        // the main input loop
        Keyboard keyboard = new Keyboard();
        while (true) {


            // check if the user has played a key; if so, process it
            if (keyboard.hasNextKeyPlayed()) {

                // the key the user played
                char key = keyboard.nextKeyPlayed();

                // index in string where key lies
                int keyIndex = keyboardString.indexOf(key);

                if (keyIndex != -1) {
                    // pluck the corresponding string
                    strings[keyIndex].pluck();
                }


            }

            double sample = 0;
            for (int i = 0; i < strings.length; i++) {
                sample += strings[i].sample();
            }
            // double sample = middleC.sample() + concertA.sample();
            // play the sample on standard audio
            StdAudio.play(sample);
            for (int i = 0; i < strings.length; i++) {
                strings[i].tic();
            }

        }
    }


}
