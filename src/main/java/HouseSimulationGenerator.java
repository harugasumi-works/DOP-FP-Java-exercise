import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HouseSimulationGenerator {
	
	
	private static DeviceMetadata metadataGen() {
		DeviceID metadata = DeviceRegistry.randomAny();
        return switch (metadata) {
        	case DoorIDs id -> DeviceMetadata.metadataFactory().apply(metadata).apply(id.get());
        	case LightIDs id -> DeviceMetadata.metadataFactory().apply(metadata).apply(id.get());
        	case TempIDs id -> DeviceMetadata.metadataFactory().apply(metadata).apply(id.get());
    };
	}
	
	private static List<DeviceMetadata> dataToList() {
		return IntStream.rangeClosed(1, 10)
				.mapToObj(_ -> metadataGen())
				.distinct()
				.collect(Collectors.toUnmodifiableList());
	}
	
	private static ConnectionStatus randomConnStatus() {
	    ConnectionStatus[] values = ConnectionStatus.values();
	    return values[ThreadLocalRandom.current().nextInt(values.length)];
	}

	private static DoorState randomDoorStatus() {
	    DoorState[] values = DoorState.values();
	    return values[ThreadLocalRandom.current().nextInt(values.length)];
	}

	private static LightState randomLightStatus() {
	    LightState[] values = LightState.values();
	    return values[ThreadLocalRandom.current().nextInt(values.length)];
	}
	
	private static double randomTemp() {
		double temp = ThreadLocalRandom.current().nextDouble(35, 41);
		String concat = String.format("%.1f", temp);
		double finalTemp = Double.parseDouble(concat);
		return finalTemp;
	}
	
	public static List<Event> eventListGen() {
		return dataToList().stream()
				.map(metadata -> switch (metadata.id()) {
	            case DoorIDs _->
	                Event.doorFactory().apply(metadata).apply(randomDoorStatus(), randomConnStatus());
	            case TempIDs _ ->
	                Event.tempFactory().apply(metadata).apply(randomTemp(), randomConnStatus());
	            case LightIDs _ ->
	                Event.lightFactory().apply(metadata).apply(randomLightStatus(), randomConnStatus());
	        })
				.collect(Collectors.toUnmodifiableList());
		
	}
}
