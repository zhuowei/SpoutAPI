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

import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.getspout.api.geo.point.Point;
import org.getspout.api.inventory.ItemStack;
import org.getspout.api.material.Material;
import org.getspout.api.math.Vector3;


public class SpoutOutputStream extends OutputStream{
	ByteBuffer buffer = ByteBuffer.allocate(1024);
	public SpoutOutputStream() {
	}

	public void writePoint(Point point){
		this.writeDouble(point.getX());
		this.writeDouble(point.getY());
		this.writeDouble(point.getZ());
		this.writeLong(point.getWorld().getUID().getLeastSignificantBits());
		this.writeLong(point.getWorld().getUID().getMostSignificantBits());
	}

	public void writeVector(Vector3 vector){
		this.writeDouble(vector.getX());
		this.writeDouble(vector.getY());
		this.writeDouble(vector.getZ());
	}

	public void writeItemStack(ItemStack item){
		this.writeInt(item.getTypeId());
		this.writeShort(item.getDurability());
		this.writeShort((short)item.getAmount());
	}

	public void writeMaterial(Material material){
		this.writeInt(material.getRawId());
		this.writeShort((short) material.getRawData());
	}
	
	@Override
	public void write(byte[] b) {
		while(buffer.remaining() < b.length){
			expand();
		}
		buffer.put(b);
	}
	
	@Override
	public void write(byte[] b, int len, int off) {
		while(buffer.remaining() < b.length){
			expand();
		}
		buffer.put(b, len, off);
	}
	
	@Override
	public void write(int b) {
		if (buffer.remaining() < 1) {
			expand();
		}
		buffer.put((byte)b);
	}
	
	public void writeShort(short s) {
		if (buffer.remaining() < 2) {
			expand();
		}
		buffer.putShort(s);
	}
	
	public void writeInt(int i) {
		if (buffer.remaining() < 4) {
			expand();
		}
		buffer.putInt(i);
	}
	
	public void writeLong(long l) {
		if (buffer.remaining() < 8) {
			expand();
		}
		buffer.putLong(l);
	}
	
	public void writeFloat(float f) {
		if (buffer.remaining() < 4) {
			expand();
		}
		buffer.putFloat(f);
	}
	
	public void writeDouble(double d) {
		if (buffer.remaining() < 8) {
			expand();
		}
		buffer.putDouble(d);
	}
	
	public void writeChar(char ch) {
		if (buffer.remaining() < 2) {
			expand();
		}
		buffer.putChar(ch);
	}
	
	public void writeString(String s){
		if (buffer.remaining() < (2 + s.length() * 2)) {
			expand((2 + s.length() * 2) + buffer.capacity() * 2);
		}
		buffer.putShort((short) s.length());
		for (int i = 0; i < s.length(); i++) {
			buffer.putChar(s.charAt(i));
		}
	}
	
	public ByteBuffer getRawBuffer() {
		return buffer;
	}
	
	private void expand() {
		expand(buffer.capacity() * 2);
	}
	
	private void expand(int size) {
		ByteBuffer replacement = ByteBuffer.allocate(size);
		replacement.put(buffer.array());
		buffer = replacement;
	}
}
