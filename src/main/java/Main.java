
import java.util.List;
import java.util.function.Supplier;



public class Main {
	

	
    @SuppressWarnings("preview")
	public static void main(String[] args) {
   
    	EventProc<Event> proc = event -> {
    		if (event.connection() == ConnectionStatus.DISCONNECTED) {
    			return new Fail<>(new EventError.ConnectionFailure(event.device()));
    		}
        return switch (event) {
            case Door(DeviceMetadata(_, Rooms location), DoorState state, _) when state == DoorState.OPEN ->
                new Success<>("Successfully detected. \nThe door in " + location + " is open");

            case Door(DeviceMetadata(_, Rooms location), _, _) ->
                new Success<>("Successfully detected. \nThe door in " + location + " is locked");

            case Light(DeviceMetadata(_, Rooms location), LightState state, _) when state == LightState.ON ->
                new Success<>("Successfully detected. \nThe light in " + location + " is on");

            case Light(DeviceMetadata(_, Rooms location), _, _) ->
                new Success<>("Successfully detected. \nThe light in " + location + " is off");

            case Temp(DeviceMetadata(_, Rooms location), double temp, _) when temp >= 40.0 ->
                new Success<>("Successfully detected. \nCurrent temperature in " + location + " exceeded 40. Proceeding adjustment");

            case Temp(DeviceMetadata(_, Rooms location), double temp, _) ->
                new Success<>("Successfully detected. \nCurrent temperature in " + location + " is " + temp);
        };
        };
    
    /**
    Function<String, DeviceMetadata> id = DeviceMetadata.metadataFactory().apply("door_1");
    DeviceMetadata doorData = id.apply("living_room");
    
    BiFunction<Boolean, Boolean, Event> door = Event.doorFactory().apply(doorData);
    Event doorEvent = door.apply(true, true); 
    
    System.out.println(((Success<String>) proc.execute(doorEvent)).value());
    **/ //below code's behind the scene
    
    //Before: Writing everything in Main class
    //List<DeviceMetadata> doorData = List.of(
    		//DeviceMetadata.metadataFactory().apply("door_2").apply("Living_room"), 
    		//DeviceMetadata.metadataFactory().apply("door_3").apply("living_room"));   
   
    /**List<Event> doorEvents = doorData.stream()
    		.map(metadata -> Event.doorFactory().apply(metadata).apply(true, true))
    		.toList();
    
    List<DeviceMetadata> lightData = List.of(
    		DeviceMetadata.metadataFactory().apply("light_1").apply("Living_room"), 
    		DeviceMetadata.metadataFactory().apply("light_3").apply("living_room"));   
    
    List<Event> lightEvents = lightData.stream()
    		.map(metadata -> Event.lightFactory().apply(metadata).apply(true, false))
    		.toList();
    
    List<Event> allEvents = Stream.concat(doorEvents.stream(), lightEvents.stream())
            .collect(Collectors.toList());
    **/
    
    //After: Use abstraction to reduce texts in Main class
    
    List<Supplier<Result<String>>> deferredTasks = HouseSimulationGenerator.eventListGen().stream()
    		.map(event -> (Supplier<Result<String>>)() -> proc.execute(event))  // <--- Notice the () -> wrapper!
    		.toList();
    
    /**
     * 	static class State {
	    	List<String> box = new ArrayList<>();
	    	Throwable error;
	}
     * Gatherer<Supplier<Result<String>>, State, Result<List<String>>> factory = Gatherer.ofSequential(
    	    State::new,
    	    (state, task, _) -> {
    	        switch (task.get()) {
    	            case Success(String value) -> state.box.add(value);
    	            case Fail(Throwable err) -> {
    	                state.error = err;
    	                return false;
    	            }
    	        }
    	        return true;
    	    },
    	    (state, downstream) -> {
    	        if (state.error != null) {
    	            downstream.push(new Fail<>(state.error));
    	        } else {
    	            downstream.push(new Success<>(state.box));
    	        }
    	    }
    	);
    	
    
    deferredTasks.stream()
    	.gather(factory)
    	.forEach(result -> { switch(result) {
    		case Success(List<String> done) -> done.forEach(System.out::println);
    		case Fail(Throwable err) -> System.out.println(err.getMessage());
    			}
    		});
    **/ //Old code to demonstrate the ability to short circuit
    
    deferredTasks.stream()
    			 .map(Supplier::get)
    			 .forEach(x -> { switch (x) {
    			    case Success(String value) -> System.out.println(value+"\n");
    			    case Fail(EventError err) -> System.out.println(describeError(err)+"\n");
    			}});
    
    }
    
    static String describeError(EventError error) {
        return switch (error) {
            case EventError.ConnectionFailure(DeviceMetadata(DeviceID id, Rooms location)) ->
                "⚠ Device " + id + " in " + location + " lost connection";
        };
    }
    
    
}
    