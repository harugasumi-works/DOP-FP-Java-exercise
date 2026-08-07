import java.util.function.BiFunction;
import java.util.function.Function;

public sealed interface Event permits Temp, Light, Door {
	
    @SuppressWarnings("preview")
	default DeviceMetadata device() {
        return switch (this) {
            case Door(DeviceMetadata d, _, _) -> d;
            case Light(DeviceMetadata d, _, _) -> d;
            case Temp(DeviceMetadata d, _, _) -> d;
        };
    }

    @SuppressWarnings("preview")
	default ConnectionStatus connection() {
        return switch (this) {
            case Door(_, _, ConnectionStatus c) -> c;
            case Light(_, _, ConnectionStatus c) -> c;
            case Temp(_, _, ConnectionStatus c) -> c;
        };
    }

    public static Function<DeviceMetadata, BiFunction<Double, ConnectionStatus, Event>> tempFactory() {
        return metadata -> (temp, conn) -> new Temp(metadata, temp, conn);
    }

    public static Function<DeviceMetadata, BiFunction<DoorState, ConnectionStatus, Event>> doorFactory() {
        return metadata -> (state, conn) -> new Door(metadata, state, conn);
    }

    public static Function<DeviceMetadata, BiFunction<LightState, ConnectionStatus, Event>> lightFactory() {
        return metadata -> (state, conn) -> new Light(metadata, state, conn);
    }
}