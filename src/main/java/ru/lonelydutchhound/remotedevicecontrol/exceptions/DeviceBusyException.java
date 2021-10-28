package ru.lonelydutchhound.remotedevicecontrol.exceptions;

public class DeviceBusyException extends RuntimeException{
    public DeviceBusyException(String message) {
        super(message);
    }
}
