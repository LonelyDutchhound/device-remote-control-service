package ru.lonelydutchhound.remotedevicecontrol.exceptions;

public class DevicePowerOffException extends RuntimeException{
    public DevicePowerOffException(String message) {
        super(message);
    }
}
