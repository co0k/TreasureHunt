package org.apptirol.innsbruckmastermeter.model.quadTree;

/* *********************************************************************** *
 * QuadLeaf.java                                                           *
 * *********************************************************************** *
 * date created    : August, 2012                                          *
 * email           : info@kirstywilliams.co.uk                             *
 * author          : Kirsty Williams                                       *
 * version         : 1.0                                                   *
 * *********************************************************************** */

import java.util.ArrayList;

public class QuadLeaf<T> {
	public final double x;
	public final double y;
	public final ArrayList<T> values;

	public QuadLeaf(double x, double y, T value) {
		this.x = x;
		this.y = y;
		this.values = new ArrayList<T>(1);
		this.values.add(value);
	}
}