
public sealed interface EventError {
	record ConnectionFailure(DeviceMetadata device) implements EventError {}
}
