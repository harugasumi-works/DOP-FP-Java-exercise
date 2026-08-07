
public enum TempIDs implements DeviceID {
	main_TEMP1(Rooms.LIVINGROOM),
	main_TEMP2(Rooms.LIVINGROOM),
	cook_TEMP1(Rooms.KITCHEN),
	cook_TEMP2(Rooms.KITCHEN),
	out_TEMP1(Rooms.PORCH),
	out_TEMP2(Rooms.PORCH),
	sleep_TEMP1(Rooms.BEDROOM),
	sleep_TEMP2(Rooms.BEDROOM);
	
private final Rooms room;
	
	TempIDs(Rooms room){
		this.room = room;
	}
	
	public Rooms get() {
		return room;
	}
}
