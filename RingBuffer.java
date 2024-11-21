public class RingBuffer {
    double[] buffer;
    private int capacity;
    private int first;
    private int last;
    private int size;

    // create an empty ring buffer, with given max capacity
    public RingBuffer(int capacity) {
        buffer = new double[capacity];
        // unnecessary since defaulted to value 0
        for (int i = 0; i < capacity; i++) {
            buffer[i] = 0;
        }
        this.capacity = capacity;
        first = 0;
        last = 0;
        size = 0;
    }

    // return number of items currently in the buffer
    public int size() {
        return size; // might update later
    }

    public int capacity() {
        return capacity;
    }

    // is the buffer empty (size equals zero)?
    public boolean isEmpty() {
        return size() == 0;
    }

    // is the buffer full  (size equals capacity)?
    public boolean isFull() {
        return size() == capacity;
    }

    // add item x to the end
    public void enqueue(double x) {
        if (size > capacity) {
            throw new IllegalArgumentException("Cannot add to full buffer");
        }
        if (last == capacity) {
            last = 0;
        }
        buffer[last] = x;
        last++;
        size++;
    }

    // delete and return item from the front
    public double dequeue() {
        if (size == 0) {
            throw new IllegalArgumentException("Cannot dequeue from empty array");
        }
        if (first == capacity) {
            first = 0;
        }
        double leastRecent = buffer[first];
        if (first != capacity) {
            first++;
        }
        size--;
        return leastRecent;
    }

    // return (but do not delete) item from the front
    public double peek() {
        if (first == capacity) {
            first = 0;
        }
        return buffer[first];
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(n);

        StdOut.printf("Test #1 - check capacity - should be %d\n", n);
        StdOut.printf("**** Capacity is %d\n", buffer.capacity());

        StdOut.printf("Test #2 - check size - should be %d\n", 0);
        StdOut.printf("**** Size is %d\n", buffer.size());

        for (int i = 1; i <= n; i++) {
            buffer.enqueue(i);
            StdOut.printf("Test #3.%d - check size after %d enqueues- should be %d\n",
                          i, i, i);
            StdOut.printf("**** Size is %d\n", buffer.size());
        }

        double val1 = buffer.peek();
        StdOut.printf("Test #4 - check peek value == %.1f\n", 1.0);
        StdOut.printf("**** Value is %.1f\n", val1);

        double val2 = buffer.dequeue();
        StdOut.printf("Test #5 - dequeue a value, then check value == %.1f and "
                              + "size == %d after a dequeue\n", 1.0, n - 1);
        StdOut.printf("**** Value is %.1f\n", val2);
        StdOut.printf("**** Size is %d\n", buffer.size());

        buffer.enqueue(val2);
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        StdOut.printf("Test #6 - enqueues + dequeues, then check size == %d and"
                              + " peek == %.1f\n",
                      1, (double) (n + 1) * n / 2);
        StdOut.printf("**** Size is %d\n", buffer.size());
        StdOut.printf("**** Peek value is %.1f\n", buffer.peek());

        // int N = Integer.parseInt(args[0]);
        // RingBuffer buffer = new RingBuffer(N);
        // for (int i = 1; i <= N; i++) {
        //     buffer.enqueue(i);
        // }
        // double t = buffer.dequeue();
        // buffer.enqueue(t);
        // System.out.println("Size after wrap-around is " + buffer.size());
        // while (buffer.size() >= 2) {
        //     double x = buffer.dequeue();
        //     double y = buffer.dequeue();
        //     buffer.enqueue(x + y);
        // }
        // System.out.println(buffer.peek());
    }

}
