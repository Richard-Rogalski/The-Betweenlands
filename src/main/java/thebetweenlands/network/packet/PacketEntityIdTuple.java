package thebetweenlands.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import thebetweenlands.network.base.IPacket;
import thebetweenlands.network.base.Packet;

public abstract class PacketEntityIdTuple extends Packet  {
	private int aId;
	private int bId;

	public PacketEntityIdTuple() {
	}

	public PacketEntityIdTuple(Entity a, Entity b) {
		aId = a.getEntityId();
		bId = b == null ? -1 : b.getEntityId();
	}

	@Override
	public void deserialize(ByteBuf buffer) {
		aId = buffer.readInt();
		bId = buffer.readInt();
	}

	@Override
	public void serialize(ByteBuf buffer) {
		buffer.writeInt(aId);
		buffer.writeInt(bId);
	}

	public int getAId() {
		return aId;
	}

	public int getBId() {
		return bId;
	}
}
