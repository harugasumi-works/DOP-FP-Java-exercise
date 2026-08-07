public enum DoorIDs implements DeviceID {
	main_DOOR1(Rooms.LIVINGROOM),
	main_DOOR2(Rooms.LIVINGROOM),
	cook_DOOR1(Rooms.KITCHEN),
	cook_DOOR2(Rooms.KITCHEN),
	out_DOOR1(Rooms.PORCH),
	out_DOOR2(Rooms.PORCH),
	sleep_DOOR1(Rooms.BEDROOM),
	sleep_DOOR2(Rooms.BEDROOM);
	
	private final Rooms room;
	
	DoorIDs(Rooms room){
		this.room = room;
	}
	
	public Rooms get() {
		return room;
	}
	
}
