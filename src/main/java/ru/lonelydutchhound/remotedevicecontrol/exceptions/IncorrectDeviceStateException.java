package ru.lonelydutchhound.remotedevicecontrol.exceptions;

public class IncorrectDeviceStateException extends RuntimeException{
    public IncorrectDeviceStateException(String message) {
        super(message);
    }
}
