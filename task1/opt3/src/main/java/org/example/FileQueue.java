package org.example;

import java.util.concurrent.LinkedBlockingQueue;

public class FileQueue extends LinkedBlockingQueue<File> {

    private final int MAX_CAPACITY = 5;

    public FileQueue() {
        super(5);
    }

    @Override
    public boolean add(File file) {
        if (this.size() == MAX_CAPACITY) {
            throw new FileQueueHasMaxSizeException();
        } else {
            return super.add(file);
        }
    }
}
