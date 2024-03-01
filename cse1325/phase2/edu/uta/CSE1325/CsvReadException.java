package edu.uta.CSE1325;

public class CsvReadException extends Exception {
    private String data;

    public CsvReadException(String message) {
        this.data = message;
    }

    @Override
    public String toString() {
        return "CsvReadException{" + "data = " + data + '}';
    }
}
