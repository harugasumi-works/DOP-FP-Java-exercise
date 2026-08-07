
public enum LightIDs implements DeviceID{
	out_LIGHT1(Rooms.PORCH),
	out_LIGHT2(Rooms.PORCH),
	sleep_LIGHT1(Rooms.BEDROOM),
	sleep_LIGHT2(Rooms.BEDROOM),
	main_LIGHT1(Rooms.LIVINGROOM),
	main_LIGHT2(Rooms.LIVINGROOM),
	cook_LIGHT1(Rooms.KITCHEN),
	cook_LIGHT2(Rooms.KITCHEN);
	
private final Rooms room;
	
	LightIDs(Rooms room){
		this.room = room;
	}
	
	public Rooms get() {
		return room;
	}
}
