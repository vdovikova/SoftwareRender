package com.cgvsu.objreader;

public class ObjReaderException extends RuntimeException {
    protected ObjReaderException(String message) {
        super(message);
    }

    public static class ObjFormatException extends ObjReaderException {
        public ObjFormatException(String errorMessage, int lineInd) {
            super("Error parsing OBJ file on line: " + lineInd + ". " + errorMessage);
        }
    }

    public static class ObjContentException extends ObjReaderException {
        public ObjContentException(String errorMessage) {
            super("Error! Impossible format! " + errorMessage);
        }
    }

    public static class ObjRuntimeExeption extends ObjReaderException {
        public ObjRuntimeExeption (String errorMessage) { super(("Error! It's a couch! " + errorMessage));}
    }
}