import java.util.Arrays;
import java.util.List;
import java.util.Random;

final class DeviceRegistry {
	
	private static final List<Class<? extends DeviceID>> CATEGORIES =
	        List.of(LightIDs.class, TempIDs.class, DoorIDs.class);
	
	private static final Random RAND = new Random();
	
	@SuppressWarnings("unchecked")
	private static final List<DeviceID> ALL_DEVICES = (List<DeviceID>) CATEGORIES.stream()
	        .flatMap(c -> Arrays.stream(c.getEnumConstants()))
	        .toList();

	static DeviceID randomAny() {
	        return ALL_DEVICES.get(RAND.nextInt(ALL_DEVICES.size()));
}
}