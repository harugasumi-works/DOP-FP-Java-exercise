import java.util.function.Function;
public record DeviceMetadata(DeviceID id, Rooms location) {
	
	public static Function<DeviceID, Function<Rooms, DeviceMetadata>> metadataFactory(){
		return id -> loc -> new DeviceMetadata(id, loc);
	}
	
	
}
