package org.example;

public class File {

    private FileTypeEnum type;
    private int size;


    public File(FileTypeEnum type, int size) {
        this.type = type;
        if (size < 10 || size > 100) {
            throw new IllegalArgumentException("File size must be from 10 to 100");
        } else {
            this.size = size;
        }
    }


    public FileTypeEnum getType() {
        return type;
    }

    public int getSize() {
        return size;
    }


    @Override
    public String toString() {
        return "File{" +
                "type=" + type +
                ", size=" + size +
                '}';
    }
}
