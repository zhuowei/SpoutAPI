/*
 * This file is part of SpoutAPI (http://wiki.getspout.org/).
 * 
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.api.io;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.getspout.api.Spout;
import org.getspout.api.geo.World;
import org.getspout.api.geo.point.Point;
import org.getspout.api.inventory.ItemStack;
import org.getspout.api.material.Material;
import org.getspout.api.material.MaterialData;
import org.getspout.api.math.Vector3;


public class SpoutInputStream extends InputStream{
	ByteBuffer buffer;
	public SpoutInputStream(ByteBuffer buffer) {
		this.buffer = buffer;
	}
	
	public SpoutInputStream(byte[] data) {
		buffer = ByteBuffer.allocate(data.length);
		buffer.put(data);
	}


	public Point readPoint(){
		double x = readDouble();
		double y = readDouble();
		double z = readDouble();
		long lsb = readLong();
		long msb = readLong();
		World world = Spout.getGame().getWorld(new UUID(msb, lsb));
		return new Point(world, x, y, z);
	}

	public Vector3 readVector(){
		double x = readDouble();
		double y = readDouble();
		double z = readDouble();
		return new Vector3(x,y,z);
	}

	public ItemStack readItemStack(ItemStack item){
		int id = readInt();
		short dura = readShort();
		short amt = readShort();
		return new ItemStack(id, amt, dura);
	}

	public Material readMaterial(){
		int id = readInt();
		short dura = readShort();
		return MaterialData.getMaterial(id, dura);
	}
	
	@Override
	public int read() {
		return buffer.get();
	}
	
	@Override
	public int read(byte[] b, int len, int off) {
		buffer.get(b, len, off);
		return b.length;
	}
	
	public int read(byte[] b) {
		buffer.get(b);
		return b.length;
	}
	
	public short readShort() {
		return buffer.getShort();
	}
	
	public int readInt() {
		return buffer.getInt();
	}
	
	public long readLong() {
		return buffer.getLong();
	}
	
	public float readFloat() {
		return buffer.getFloat();
	}
	
	public double readDouble() {
		return buffer.getDouble();
	}
	
	public char readChar() {
		return buffer.getChar();
	}
	
	public String readString(){
		short size =  buffer.getShort();
		StringBuilder builder = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			builder.append(buffer.getChar());
		}
		return builder.toString();
	}
	
	public ByteBuffer getRawBuffer() {
		return buffer;
	}
}
