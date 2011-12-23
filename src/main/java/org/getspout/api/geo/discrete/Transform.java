package org.getspout.api.geo.discrete;

import org.getspout.api.math.Quaternion;
import org.getspout.api.math.Vector3m;

public class Transform {	
	Point position;
	Quaternion rotation;
	Vector3m scale;	
	
	Transform parent = null;
	
	
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public Quaternion getRotation() {
		return rotation;
	}
	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}
	public Vector3m getScale() {
		return scale;
	}
	public void setScale(Vector3m scale) {
		this.scale = scale;
	}
	public Transform getParent() {
		return parent;
	}
	public void setParent(Transform parent) {
		this.parent = parent;
	}
	
	public Transform add(Transform t){
		Transform r = new Transform();
		r.position = position.add(t.getPosition());
		r.rotation = rotation.multiply(t.getRotation());
		r.scale = (Vector3m) scale.add(t.getScale());
		return r;
	}
	
	public Transform getAbsolutePosition(){
		if(parent == null) return this;
		return this.add(parent.getAbsolutePosition());
		
	}
}